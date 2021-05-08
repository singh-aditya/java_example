import React, { Component } from 'react'
import { connect } from 'react-redux';
import { login } from '../actions';

class LoginComponent extends Component {

    state = {
        email: 'user1@company.com',
        password: 'user123'
    }


    handleInputChange = (event) => {
        this.setState(
            {
                [event.target.name]
                    : event.target.value
            }
        )
    }


    onLoginClicked = () => {
        this.props.login(this.state.email, this.state.password);
    }

    render() {
        return (
            <div>
                <h1>Login</h1>
                <div className="container">
                    {this.props.hasLoginFailed && <div className="alert alert-warning">Invalid Credentials</div>}
                    {this.props.showSuccessMessage && <div>Login Sucessful</div>}
                    Email: <input type="text" name="email" value={this.state.email} onChange={this.handleInputChange} />
                    Password: <input type="password" name="password" value={this.state.password} onChange={this.handleInputChange} />
                    <button className="btn btn-success" onClick={this.onLoginClicked}>Login</button>
                </div>
            </div>
        )
    }
}
const mapStateToProps = state => {
    return { 
        hasLoginFailed: state.auth.hasLoginFailed,
        showSuccessMessage: state.auth.isLoggedIn
    };
  };

export default connect(
    mapStateToProps,
    {login}
)(LoginComponent);