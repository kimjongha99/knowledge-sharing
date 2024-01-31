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
import MyPage from "./views/MyPage";
import OAuth from "./views/OAuth";


function App() {

    const {user, setUser} = useUserStore();
    const [cookies, setCookie] = useCookies(['accessToken', 'refreshToken']);
    const navigate = useNavigate();

    // Function to refresh access token
    const refreshAccessToken = async () => {
        try {
            const response = await axios.post('http://localhost:4040/refresh', {
                refreshToken: cookies.refreshToken
            });

            const { newAccessToken } = response.data;
            const oneHourInSeconds = 3600;
            setCookie('accessToken', newAccessToken, { maxAge: oneHourInSeconds, path: '/' });
            return newAccessToken;
        } catch (error) {
            console.error('Error refreshing access token: ', error);
            navigate('/');
            alert("리프래쉬토큰이 만료되었습니다 다시로그인하세요")
        }
    };
    axios.interceptors.response.use(response => response, async error => {
        const originalRequest = error.config;
        if (error.response.status === 401 && !originalRequest._retry) {
            originalRequest._retry = true;
            const newAccessToken = await refreshAccessToken();
            axios.defaults.headers.common['Authorization'] = `Bearer ${newAccessToken}`;
            originalRequest.headers['Authorization'] = `Bearer ${newAccessToken}`;
            return axios(originalRequest);
        }
        return Promise.reject(error);
    });



    // Fetch user info when the app component is mounted or accessToken changes
    useEffect(() => {
        // If there's no accessToken, don't attempt the request
        if (!cookies.accessToken) {
            console.log("No access token present.");
            return;
        }

        axios.get('http://localhost:4040/api/v1/users', {
            headers: {
                'Authorization': `Bearer ${cookies.accessToken}`
            }
        })
            .then(response => {
                const { userId, email, profileImageUrl,role,type } = response.data;
                setUser({ userId, email, profileImageUrl,role,type }); // Update the user state in Zustand store
            })
            .catch(error => {
                console.error("Error fetching user data: ", error);
                // Handle error, e.g. reset user state or show error message
            });
    }, [cookies.accessToken, setUser]); // Add setUser to dependency array to avoid exhaustive-deps warning


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


            </Route>
        </Routes>
    );
}

export default App;
