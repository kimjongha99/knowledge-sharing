import React, {useEffect, useState} from 'react';
import './App.css';
import {Route, Routes, useNavigate} from 'react-router-dom';
import { AUTH_PATH, USER_PATH} from 'constant';
import Container from "./layouts/Container";
import Main from "./views/main";
import SignUp from "./views/SignUp";
import SignIn from "./views/SignIn";
import {User, useUserStore} from "./stores/user.store";
import {useCookies} from "react-cookie";
import axios from "axios";
import MyPage from "./views/MyPage";
import Article from "./views/Article";
import ArticleDetail from "./views/Article/ArticleDetail";
import ArticlePost from "./views/Article/ArticleDetail/Post";
import AdminSignIn  from "./views/Admin/AdminSignIn";
import AdminDashboard from "./views/Admin/AdminDashboard";


function App() {
    const navigate = useNavigate();
    const { user, setUser } = useUserStore();
    const [cookies, setCookie, removeCookie] = useCookies(['accessToken', 'refreshToken']);


    useEffect(() => {
        const checkAuthStatus = async () => {
            if (cookies.accessToken) {
                fetchUserData();
            } else if (!cookies.accessToken && cookies.refreshToken) {
                refreshAccessToken();
            }
            // No else part needed for now as per the given logic
        };

        checkAuthStatus();
    }, [cookies.accessToken, cookies.refreshToken]);

    const refreshAccessToken = async () => {
        try {
            await axios.post('http://localhost:4040/api/v1/auth/refresh', {
                refreshToken: cookies.refreshToken,
            }, {
                withCredentials: true,
            });
            // 새로운 액세스 토큰이 쿠키에 설정됨
        } catch (error) {
            console.error('Failed to refresh access token:', error);
            removeCookie('accessToken');
            removeCookie('refreshToken');
            // 여기서 사용자에게 로그인이 필요하다는 메시지를 표시할 수도 있음
        }
    };


    const fetchUserData = async () => {
        if (cookies.accessToken) {
            try {
                const response = await axios.get('http://localhost:4040/api/v1/users', {
                    headers: { Authorization: `Bearer ${cookies.accessToken}`},
                    withCredentials: true,
                });

                if (response.data.statusCode === 200) {
                    const { userId, email, profileImageUrl, role, type } = response.data.data;
                    setUser({ userId, email, profileImageUrl, role, type });
                } else {
                    // Handle non-success status codes appropriately
                    console.error('Failed to fetch user data with non-success status code:', response.data);
                }
            } catch (error) {
                console.error('Failed to fetch user data:', error);
                removeCookie('accessToken');
                // Optionally, redirect to sign-in page or show a message
            }
        }
    };




    return (
        <Routes>
            <Route path="/" element={<Container />}>
                <Route index element={<Main />} />
                <Route path={AUTH_PATH}>
                    <Route path='sign-up' element={<SignUp/>} />
                    <Route path='sign-in' element={<SignIn/>} />
                </Route>
                <Route path={USER_PATH(':userId')} element={<MyPage/>}/>

                <Route path='articles' element={<Article/>}/>
                <Route path='articles/:articleId' element={<ArticleDetail/>}/>
                <Route path='articles/post' element={<ArticlePost/>}/>



                <Route path='admin'>
                    <Route path='sign-in' element={<AdminSignIn/>} />
                    <Route path='dashboard' element={<AdminDashboard/>}/>
                </Route>

            </Route>
        </Routes>
    );
}

export default App;
