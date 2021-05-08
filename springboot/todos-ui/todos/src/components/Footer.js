import React from 'react'
import { AppBar, Toolbar, Typography, Icon, makeStyles} from '@material-ui/core'

const useStyles = makeStyles({
    appBar: {
    top: 'auto',
    bottom: 0, 
    },
  });

const FooterComponent = () => {
    const classes = useStyles();
    return (
        <AppBar position="fixed" color="inherit" className={classes.appBar} >
            <Toolbar>
                <Typography variant="caption" color="inherit">
                    Feel Free to modify. <Icon fontSize="small">copyright</Icon> Singh.
                </Typography>
            </Toolbar>
        </AppBar>            
    )
}

export default FooterComponent