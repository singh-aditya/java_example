import React from 'react'
import { Link  as RouterLink} from 'react-router-dom'
import { connect } from 'react-redux';
import { Container, Typography, Link} from '@material-ui/core';

const WelcomeComponent = (props) => {

    return (
        <>       
            <Container maxWidth="md">
                <Typography variant="h4">Welcome!</Typography>
                <Typography variant="body1">
                    Welcome {props.email} ({props.match.params.userId}).
                    You can manage your todos <Link component={RouterLink} to="/todos">here</Link>.
                </Typography>
            </Container>
        </>
    )
}

const mapStateToProps = state => {
    return { 
        email: state.auth.email
    };
  };

export default connect(
    mapStateToProps
)(WelcomeComponent);