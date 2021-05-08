import React from 'react'
import { Route, Redirect } from 'react-router-dom'
import { connect } from 'react-redux' 

const AuthenticatedRoute = (props) => {
    return props.isUserLoggedIn ?
    <Route {...props} /> :
    <Redirect to="/login" />;
}


const mapStateToProps = state => {
    return { 
        isUserLoggedIn: state.auth.isLoggedIn
    };
  };

export default connect(
    mapStateToProps
)(AuthenticatedRoute);
