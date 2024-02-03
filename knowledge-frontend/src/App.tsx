import React, {useEffect} from 'react';
import './App.css';
import {Route, Routes, useNavigate} from 'react-router-dom';
import {AUTH_PATH, MAIN_PATH, USER_PATH} from 'constant';
import Container from "./layouts/Container";
import Main from "./views/main";
import SignUp from "./views/SignUp";
import SignIn from "./views/SignIn";
import {User, useUserStore} from "./stores/user.store";
import {useCookies} from "react-cookie";
import axios from "axios";
import OAuth from "./views/OAuth";
import MyPage from "./views/MyPage";
import Article from "./views/Article";


function App() {
    const navigate = useNavigate();
    const { setUser, clearUser } = useUserStore();
    const [cookies, setCookie, removeCookie] = useCookies(['accessToken', 'refreshToken']);

    const oneHourInSeconds = 3600; // 1 hour in seconds


    // Create an Axios instance
    const apiClient = axios.create({
        baseURL: 'http://localhost:4040/api',
    });
    // Axios response interceptor
    apiClient.interceptors.response.use(
        response => response, // Just return the response if it's successful
        async error => {
            const originalRequest = error.config;
            if (error.response.status === 401 && !originalRequest._retry) { // If 401 response received
                originalRequest._retry = true; // Marking that we're retrying the request
                try {
                    const refreshTokenResponse = await axios.post('/v1/auth/refresh', {
                        refreshToken: cookies.refreshToken,
                    });

                    if (refreshTokenResponse.data && refreshTokenResponse.data.code === 'SU') {
                        const { NEWACCESSTOKEN } = refreshTokenResponse.data;
                        setCookie('accessToken', NEWACCESSTOKEN, { maxAge: oneHourInSeconds, path: '/' });
                        // Update the header of the failed request and retry
                        originalRequest.headers['Authorization'] = `Bearer ${NEWACCESSTOKEN}`;
                        return apiClient(originalRequest); // Retrying the original request with new token
                    }
                } catch (refreshError) {
                    clearUser();
                    navigate('/auth/sign-in', { replace: true });
                    return Promise.reject(refreshError);
                }
            }
            return Promise.reject(error);
        }
    );



    useEffect(() => {
        if (!cookies.accessToken) {
            clearUser(); // Clear user data if accessToken is not present
            navigate('/auth/sign-in', { replace: true }); // Redirect to sign-in
            return;
        }

        const fetchUserData = async () => {
            try {
                const response = await axios.get('http://localhost:4040/api/v1/users', {
                    headers: {
                        Authorization: `Bearer ${cookies.accessToken}`,
                    },
                });

                if (response.data && response.data.code === 'SU') {
                    setUser({
                        userId: response.data.userId,
                        email: response.data.email,
                        profileImageUrl: response.data.profileImageUrl,
                        role: response.data.role,
                        type: response.data.type
                    });
                } else {
                    // Handle failure (e.g., invalid token or user not found)
                    removeCookie('accessToken'); // Consider removing invalid accessToken
                    clearUser();
                    navigate('/auth/sign-in', { replace: true }); // Redirect to sign-in
                }
            } catch (error) {
                // Handle error (e.g., network error or server error)
                clearUser();
                navigate('/auth/sign-in', { replace: true }); // Redirect to sign-in
            }
        };

        fetchUserData();
    }, [cookies.accessToken, navigate, setUser, clearUser, removeCookie, setCookie]);



    return (
        <Routes>
            <Route path="/" element={<Container />}>
                <Route index element={<Main />} />
                <Route path={AUTH_PATH}>
                    <Route path='sign-up' element={<SignUp/>} />
                    <Route path='sign-in' element={<SignIn/>} />
                    <Route path='oauth-response/:token/:expirationTime' element={<OAuth/>} />
                </Route>

                <Route path={USER_PATH(':userId')} element={<MyPage/>}/>
                <Route path='articles'  element={<Article/>}/>


            </Route>
        </Routes>
    );
}

export default App;
