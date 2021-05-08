import axios from 'axios'
import { BASE_URL } from './constants'

export const USER_SESSION_ATTRIBUTE_NAME = 'authenticatedUser';
const API_URL = BASE_URL + '/users';

class AuthenticationService {

    executeJwtAuthentication(email, password) {
        return axios.post(`${API_URL}/login`, {
            email: email,
            password
        }, {
            headers : {"Access-Control-Allow-Origin": "*"}
        })
    }

    registerSuccessfulLogin(userId, token) {
        sessionStorage.setItem(USER_SESSION_ATTRIBUTE_NAME, userId);
        this._setupAxiosInterceptors(token);
    }

    logout() {
        sessionStorage.removeItem(USER_SESSION_ATTRIBUTE_NAME);
    }

    isUserLoggedIn() {
        let user = sessionStorage.getItem(USER_SESSION_ATTRIBUTE_NAME)
        if (user === null) return false
        return true
    }

    getLoggedInUserId() {
        let userId = sessionStorage.getItem(USER_SESSION_ATTRIBUTE_NAME)
        if (userId === null) return ''
        return userId
    }

    _setupAxiosInterceptors(token) {

        axios.interceptors.request.use(
            (config) => {
                if (this.isUserLoggedIn()) {
                    config.headers.authorization = token
                }
                return config
            }
        )
    }
}

export default new AuthenticationService()