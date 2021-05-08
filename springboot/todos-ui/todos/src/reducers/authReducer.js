import { LOGIN, LOGIN_FAILED, LOGOUT } from '../actions/types';

const INTIAL_STATE = {
  isLoggedIn: null,
  hasLoginFailed: false,
  userId: null,
  email: null
};

const authReducer = (state = INTIAL_STATE, action) => {
  switch (action.type) {
    case LOGIN:
      return { ...state, isLoggedIn: true, hasLoginFailed: false, userId: action.payload.userId, email: action.payload.email };
    case LOGIN_FAILED:
      return { ...state, isLoggedIn: false, hasLoginFailed: true, userId: null, email: null};
    case LOGOUT:
      return { ...state, isLoggedIn: false, hasLoginFailed: false, userId: null, email: null };
    default:
      return state;
  }
};

export default authReducer;
