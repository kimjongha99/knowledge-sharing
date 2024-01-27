import axios from 'axios';
export const axiosInstance = axios.create({
    baseURL: 'http://localhost:4040/api/v1/',
    // You can set more default options here (headers, timeout, etc.)
});