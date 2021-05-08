import axios from 'axios'
import { BASE_URL } from './constants'

const API_URL = BASE_URL + '/users';

class TodoDataService {

    retrieveAllTodos(userId) {
        //console.log('retrieveAllTodos for ', userId);
        return axios.get(`${API_URL}/${userId}/todos`);
    }

    retrieveTodo(userId, id) {
        //console.log('retrieveTodo ', id, ' for ', userId);
        return axios.get(`${API_URL}/${userId}/todos/${id}`);
    }

    deleteTodo(userId, id) {
        //console.log('deleteTodo ', id, ' for ', userId);
        return axios.delete(`${API_URL}/${userId}/todos/${id}`);
    }

    updateTodo(userId, id, todo) {
        //console.log('updateTodo ', id, ' for ', userId);
        return axios.put(`${API_URL}/${userId}/todos/${id}`, todo);
    }

    createTodo(userId, todo) {
        //console.log('createTodo for ', userId);
        return axios.post(`${API_URL}/${userId}/todos/`, todo);
    }

}

export default new TodoDataService()