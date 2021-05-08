import React, {Component} from 'react'
import {Router, Route, Switch} from 'react-router-dom'
import AuthenticatedRoute from './AuthenticatedRoute'
import LoginComponent from './Login'
import ListTodosComponent from './ListTodos'
import ErrorComponent from './Error'
import HeaderComponent from './Header'
import FooterComponent from './Footer'
import LogoutComponent from './Logout'
import WelcomeComponent from './Welcome'
import TodoComponent from './Todo'
import history from './history'

class TodoApp extends Component {
    render() {
        return (
            <div className="ui contianer">
                <Router history={history}>
                    <>
                        <HeaderComponent/>
                        <Switch>
                            <Route path="/" exact component={LoginComponent}/>
                            <Route path="/login" component={LoginComponent}/>
                            <AuthenticatedRoute path="/welcome/:userId" component={WelcomeComponent}/>
                            <AuthenticatedRoute path="/todos/:id" component={TodoComponent}/>
                            <AuthenticatedRoute path="/todos" component={ListTodosComponent}/>
                            <Route path="/logout" component={LogoutComponent}/>
                            
                            <Route component={ErrorComponent}/>
                        </Switch>
                        <FooterComponent/>
                    </>
                </Router>
            </div>
        )
    }
}

export default TodoApp