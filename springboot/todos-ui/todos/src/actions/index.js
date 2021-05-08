import { 
    LOGIN, 
    LOGOUT,
    LOGIN_FAILED,
    FETCH_TODOS_LIST,
    FETCH_TODO,
    CRETAE_TODO,
    UPDATE_TODO,
    DELETE_TODO, 
} from './types'
import history from '../components/history'
import AuhenticationService from '../api/authenticationService'
import TodoService from '../api/todoService'

export const login = (email, password) => dispatch => {
    
    AuhenticationService.executeJwtAuthentication(email, password)
    .then((response) => {
        AuhenticationService.registerSuccessfulLogin(response.headers.userid, response.headers.authorization);
        dispatch({ type: LOGIN, payload: {email: email, userId: response.headers.userid}});
        history.push(`/welcome/${response.headers.userid}`);
    })        
    .catch(() => {
        dispatch({ type: LOGIN_FAILED });
    });    
}

export const logout = () => {
    AuhenticationService.logout();
    history.push("/logout");
    return { type: LOGOUT };
}

export const fetchAllTodos = (userId) => dispatch => {
    TodoService.retrieveAllTodos(userId)
    .then((response) => {
        //console.log('fetchAllTodos ', response);
        dispatch({ type: FETCH_TODOS_LIST, payload: response.data._embedded.todoList});
    });
}

export const fetchTodo = (userId, todoId) => dispatch => {
    TodoService.retrieveTodo(userId, todoId)
    .then((response) =>{
        //console.log('fetchTodo ', response);
        dispatch({ type: FETCH_TODO, payload: response.data});
    });
}

export const createTodo = (userId, todo) => dispatch => {
    TodoService.createTodo(userId, todo)
    .then((response) => {
        //console.log('createTodo ', response);
        dispatch({ type: CRETAE_TODO, payload: response.data});
        history.push(`/todos`);
    });
}

export const updateTodo = (userId, todoId, todo) => dispatch => {
    TodoService.updateTodo(userId, todoId, todo)
    .then((response) => {
        //console.log('updateTodo ',response);
        dispatch({ type: UPDATE_TODO, payload: response.data});
        history.push(`/todos`);
    });
}

export const deleteTodo = (userId, todoId) => dispatch => {
    TodoService.deleteTodo(userId, todoId)
    .then((response) => {
        //console.log('deleteTodo ',response);
        dispatch({ type: DELETE_TODO, payload: todoId});
    });
}