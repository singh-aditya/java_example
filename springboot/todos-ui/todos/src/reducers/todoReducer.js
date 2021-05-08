import _ from 'lodash'
import { FETCH_TODOS_LIST, FETCH_TODO, CRETAE_TODO, DELETE_TODO, UPDATE_TODO } from '../actions/types';

export const todosReducer = (state = {}, action) => {
    switch (action.type) {
        case FETCH_TODOS_LIST:
            return {...state, ..._.mapKeys(action.payload, 'id')};
        case FETCH_TODO:
            return { ...state, [action.payload.id]: action.payload };
        case CRETAE_TODO:
            return { ...state, [action.payload.id]: action.payload };
        case UPDATE_TODO:
            return { ...state, [action.payload.id]: action.payload };
        case DELETE_TODO:
            return _.omit(state, action.payload);
        default:
            return state;
    }
};