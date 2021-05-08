import React, { Component } from 'react'
import moment from 'moment'
import { Formik, Form, Field, ErrorMessage } from 'formik';
import { connect } from 'react-redux'
import { fetchTodo, createTodo, updateTodo } from '../actions'

class TodoComponent extends Component {

    state = {
        id: this.props.match.params.id,
        description: this.props.match.params.id === "-1" ?
                        "" :
                        this.props.todo.description,
        targetDate: this.props.match.params.id === "-1" ? 
                        moment(new Date()).format('YYYY-MM-DD') :
                        moment(this.props.todo.targetDate).format('YYYY-MM-DD'),                    
    }

    componentDidMount() {

        //console.log('Todo componentDidMount ', this.state)
        if (this.state.id === "-1" || this.props.todo != null) {
            return
        }
        this.props.fetchTodo(this.props.userId, this.state.id);
    }

    validate = (values) => {
        let errors = {}
        if (!values.description) {
            errors.description = 'Enter a Description'
        } else if (values.description.length < 5) {
            errors.description = 'Enter atleast 5 Characters in Description'
        }

        if (!moment(values.targetDate).isValid()) {
            errors.targetDate = 'Enter a valid Target Date'
        }

        return errors

    }

    onSubmit = (values) => {

        //console.log('Todo OnSubmit ', this.state)

        let todo = {
            id: this.state.id,
            description: values.description,
            targetDate: values.targetDate
        }

        if (this.state.id === "-1") {
            this.props.createTodo(this.props.userId, todo);
        } else {
            this.props.updateTodo(this.props.userId, this.state.id, todo);
        }

        //console.log('Todo onSubmit ', values);
    }

    render() {

        let { description, targetDate } = this.state

        return (
            <div>
                <h1>Todo</h1>
                <div className="container">
                    <Formik
                        initialValues={{ description, targetDate }}
                        onSubmit={this.onSubmit}
                        validateOnChange={false}
                        validateOnBlur={false}
                        validate={this.validate}
                        enableReinitialize={true}
                    >
                        {
                            (props) => (
                                <Form>
                                    <ErrorMessage name="description" component="div"
                                        className="alert alert-warning" />
                                    <ErrorMessage name="targetDate" component="div"
                                        className="alert alert-warning" />
                                    <fieldset className="form-group">
                                        <label>Description</label>
                                        <Field className="form-control" type="text" name="description" />
                                    </fieldset>
                                    <fieldset className="form-group">
                                        <label>Target Date</label>
                                        <Field className="form-control" type="date" name="targetDate" />
                                    </fieldset>
                                    <button className="btn btn-success" type="submit">Save</button>
                                </Form>
                            )
                        }
                    </Formik>

                </div>
            </div>
        )
    }
}

const mapStateToProps = (state, ownProps) => {
    return { 
        todo: state.todos[ownProps.match.params.id],
        userId: state.auth.userId,
    };
  };

export default connect(
    mapStateToProps,
    {fetchTodo, createTodo, updateTodo}
)(TodoComponent);