import { combineReducers } from 'redux';
import authReducer from './authReducer';
import { todosReducer } from './todoReducer';


export default combineReducers({
  auth: authReducer,
  todos: todosReducer
});
