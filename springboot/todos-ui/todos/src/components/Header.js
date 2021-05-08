import React from 'react'
import { Link as RouterLink } from 'react-router-dom'
import { connect } from 'react-redux'
import {AppBar, Toolbar, Button, makeStyles} from '@material-ui/core'
import { logout } from '../actions'

const useStyles = makeStyles({
    root: {
      flexGrow: 1,
    },
    auth: {
        flexGrow: 1,
        justifyContent: 'flex-end',
    },
  });

const HeaderComponent = ({isUserLoggedIn, userId, logout}) => {
    const classes = useStyles();
    return (
        <div className={classes.root}>
          <AppBar position="static">
            <Toolbar>
                { isUserLoggedIn && <Button color="inherit" component={RouterLink} to={`/welcome/${userId}`} >Home</Button>}
                { isUserLoggedIn && <Button color="inherit" component={RouterLink} to="/todos" >Todos</Button>}
                { !isUserLoggedIn && <Button className={classes.auth} color="inherit" component={RouterLink} to="/login" >Login</Button>} 
                { isUserLoggedIn && <Button className={classes.auth} color="inherit" component={RouterLink} to="/logout" onClick={logout} >Logout</Button>} 
            </Toolbar>
          </AppBar>
        </div>
    );
}

const mapStateToProps = state => {
    return { 
        isUserLoggedIn: state.auth.isLoggedIn,
        userId: state.auth.userId
    };
  };

export default connect(
    mapStateToProps,
    {logout}
)(HeaderComponent)