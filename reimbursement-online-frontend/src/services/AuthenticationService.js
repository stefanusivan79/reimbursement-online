import axios from 'axios';
import qs from 'qs';

const AuthenticationService = {
    loginUser(data) {

        const URL = 'http://localhost:8080/oauth/token';

        const payLoad = { ...data, grant_type: 'password' };

        const config = {
            headers: {
                "Content-Type": "application/x-www-form-urlencoded",
            },
            auth: {
                username: 'adminapp',
                password: 'adminapp'
            }
        };

        return axios.post(URL, qs.stringify(payLoad), config);
    }
}

export default AuthenticationService;