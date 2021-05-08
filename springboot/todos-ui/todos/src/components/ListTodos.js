import React, { Component } from 'react'
import { connect } from 'react-redux'
import moment from 'moment'
import _ from 'lodash'
import { fetchAllTodos, deleteTodo } from '../actions'

class ListTodosComponent extends Component {

    componentDidMount() {
        this.refreshTodos();
    }

    refreshTodos() {
        this.props.fetchAllTodos(this.props.userId);
    }

    deleteTodoClicked = (id) => {
        //console.log('deleteTodoClicked ', id);
        this.props.deleteTodo(this.props.userId, id);
    }

    addTodoClicked = () => {
        //console.log('addTodoClicked');
        this.props.history.push(`/todos/-1`)
    }

    updateTodoClicked = (id) => {
        //console.log('updateTodoClicked ', id);
        this.props.history.push(`/todos/${id}`);
    }

    render() {
        return (
            <div>
                <h1>List Todos</h1>
                <div className="container">
                    <table className="table">
                        <thead>
                            <tr>
                                <th>Description</th>
                                <th>Target Date</th>
                                <th>IsCompleted?</th>
                                <th>Update</th>
                                <th>Delete</th>
                            </tr>
                        </thead>
                        <tbody>
                            {
                                this.props.todos &&
                                this.props.todos.map(
                                    todo =>
                                        <tr key={todo.id}>
                                            <td>{todo.description}</td>
                                            <td>{moment(todo.targetDate).format('YYYY-MM-DD')}</td>
                                            <td>{todo.done.toString()}</td>
                                            <td><button className="btn btn-success" onClick={() => this.updateTodoClicked(todo.id)}>Update</button></td>
                                            <td><button className="btn btn-warning" onClick={() => this.deleteTodoClicked(todo.id)}>Delete</button></td>
                                        </tr>
                                )                                
                            }
                        </tbody>
                    </table>
                    <div className="row">
                        <button className="btn btn-success" onClick={this.addTodoClicked}>Add</button>
                    </div>
                </div>
            </div>
        )
    }
}

const mapStateToProps = state => {
    return { 
        todos: _.values(state.todos),
        userId: state.auth.userId,
    };
  };

export default connect(
    mapStateToProps,
    {fetchAllTodos, deleteTodo}
)(ListTodosComponent);