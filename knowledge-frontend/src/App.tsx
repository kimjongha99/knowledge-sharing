import React, {useEffect, useState} from 'react';
import './App.css';
import {Route, Routes, useNavigate} from 'react-router-dom';
import {ARTICLE_DETAIL_PATH, AUTH_PATH, MAIN_PATH, USER_PATH} from 'constant';
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
import Post from "./views/Article/ArticleDetail/Post";
import ArticlePost from "./views/Article/ArticleDetail/Post";


function App() {
    const navigate = useNavigate();
    const { user, setUser } = useUserStore();
    const [cookies, setCookie, removeCookie] = useCookies(['accessToken', 'refreshToken']);



    useEffect(() => {
        // Function to refresh access token
        const refreshAccessToken = async () => {
            if (!cookies.refreshToken) {
                navigate('/auth/sign-in'); // If refreshToken is also missing, redirect to sign-in    // 추후에 재정비해야겠음 엑세스토큰이 없어지면 바로 로그인페이지로가네..
                return;
            }

            try {
                await axios.post('http://localhost:4040/api/v1/auth/refresh', {
                    refreshToken: cookies.refreshToken,
                }, {
                    withCredentials: true,
                });

                // No need to handle the response as the new accessToken will be set by the server in the cookie
            } catch (error) {
                console.error('Failed to refresh access token:', error);
                removeCookie('accessToken');
                removeCookie('refreshToken');
                navigate('/auth/sign-in'); // Redirect to sign-in page if token refresh fails
            }
        };

        // If accessToken is missing, try to refresh it
        if (!cookies.accessToken) {
            refreshAccessToken();
        }
    }, [cookies.accessToken, cookies.refreshToken, navigate, removeCookie, setCookie]);

    useEffect(() => {
        // Function to fetch user data
        const fetchUserData = async () => {
            if (cookies.accessToken) { // Check if accessToken is available
                try {
                    const response = await axios.get('http://localhost:4040/api/v1/users', {
                        headers: {
                            Authorization: `Bearer ${cookies.accessToken}` // Include the token in the Authorization header
                        },
                        withCredentials: true // Ensures cookies are sent with the request
                    });
                    const { userId, email, profileImageUrl, role, type } = response.data;
                    setUser({ userId, email, profileImageUrl, role, type }); // Update the user state in Zustand store
                } catch (error) {
                    console.error('Failed to fetch user data:', error);
                    // Handle error appropriately
                }
            }
        };

        fetchUserData(); // Invoke the function to fetch user data

    }, [cookies.accessToken, setUser]); // Only re-run the effect if cookies.accessToken or setUser changes




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

            </Route>
        </Routes>
    );
}

export default App;
