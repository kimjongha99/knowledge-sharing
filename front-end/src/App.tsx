import React, {useEffect} from 'react';
import './App.css';
import {Route, Routes, useNavigate} from "react-router-dom";
import Main from "./views/Main";
import Container from "./layouts/Container";
import SignIn from "./views/SignIn";
import SignUp from "./views/SignUp";
import {useUserStore} from "./stores/userStore";
import {useCookies} from "react-cookie";
import axios from "axios";
import MyPage from "./views/MyPage";
import Articles from "./views/Article/Articles";
import ArticleDetail from "./views/Article/ArticleDetail";
import Test from "./views/Test";
import ArticlePost from "./views/Article/ArticlePost";

function App() {
    const { user, setUser } = useUserStore();
    const [cookies, setCookie, removeCookie] = useCookies(['accessToken', 'refreshToken']);
    const navigate = useNavigate();

  // 유저 정보 가져오고 리프레쉬 토큰 발급해주는 로직//
   //region
    useEffect(() => {
        const checkAuthStatus = async () => {
            if (cookies.accessToken) {
                fetchUserData();
            } else if (!cookies.accessToken && cookies.refreshToken) {
                await refreshAccessToken();
            }
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
        } catch (error) {
            console.error('Failed to refresh access token:', error);
            removeCookie('accessToken');
            removeCookie('refreshToken');
            navigate('/auth/sign-in');
        }
    };

    const fetchUserData = async () => {
        if (cookies.accessToken) {
            try {
                const response = await axios.get('http://localhost:4040/api/v1/users', {
                    headers: { Authorization: `Bearer ${cookies.accessToken}` },
                    withCredentials: true,
                });
                const { userId, email, profileImageUrl, role, type } = response.data.data;
                setUser({ userId, email, profileImageUrl, role, type });

            } catch (error) {
                console.error('Failed to fetch user data:', error);
                removeCookie('accessToken');
                navigate('/auth/sign-in'); // Optionally redirect to sign-in
            }
        }
    };

    //endregion

    return (
      <Routes>
          <Route path="/" element={<Container />}>
          <Route index element={<Main/>}/>
          <Route path="/auth">
              <Route path='sign-up' element={<SignUp/>} />
              <Route path='sign-in' element={<SignIn/>} />
          </Route>
          <Route path='/user/:userId' element={<MyPage/>} />


          <Route path="/articles" element={<Articles />}/>
          <Route path="/articles/:articleId" element={<ArticleDetail />} />
          <Route path='articles/post' element={<ArticlePost/>}/>

           <Route path='/test' element={<Test/>}/>
      </Route>
      </Routes>


  );
}

export default App;
