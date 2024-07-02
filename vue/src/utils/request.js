import axios from 'axios'
import router from "@/router";

// Create a new axios instance
const request = axios.create({
    baseURL: process.env.VUE_APP_BASEURL,   // Backend API address ip:port
    timeout: 30000                          // 30s request timeout
})

// request interceptor
// Can do some processing before the request is sent
// For example, uniformly add token, uniformly encrypt request parameters
request.interceptors.request.use(config => {
    config.headers['Content-Type'] = 'application/json;charset=utf-8';        // Set request header format
    let user = JSON.parse(localStorage.getItem("xm-user") || '{}')  // Get cached user information
    config.headers['token'] = user.token  // Set request header

    return config
}, error => {
    console.error('request error: ' + error) // for debug
    return Promise.reject(error)
});

// response interceptor
// Can uniformly handle results after the interface response
request.interceptors.response.use(
    response => {
        let res = response.data;

        // Compatible with string data returned by the server
        if (typeof res === 'string') {
            res = res ? JSON.parse(res) : res
        }
        if (res.code === '401') {
            router.push('/login')
        }
        return res;
    },
    error => {
        console.error('response error: ' + error) // for debug
        return Promise.reject(error)
    }
)

export default request
