import axios from "axios";
import { ACCESS_TOKEN, API_BASE_URL } from './Constants';

export const instance = axios.create({
    baseURL: API_BASE_URL
});

// add request interceptor
instance.interceptors.request.use(config => {

    const token = localStorage.getItem(ACCESS_TOKEN);
    if (token) {
        config.headers['Authorization'] = 'Bearer ' + token;
    }

    config.headers['Content-Type'] = 'application/json';

    return config;
}, error => {
    Promise.reject(error);
});

// add response interceptor
instance.interceptors.response.use(response => {
    console.log('response', response);
    return response;
}, error => {
    return Promise.reject(error);
});