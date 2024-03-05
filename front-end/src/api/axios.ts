import axios from 'axios';

// src/api/axios.ts
const axiosInstance = axios.create({
    baseURL: process.env.REACT_APP_API_URL ,// 환경 변수 사용
    withCredentials: true

});

export default axiosInstance;
