import React, {useEffect} from 'react';
import './App.css';
import {Route, Routes, useNavigate} from "react-router-dom";
import Main from "./views/Main";
import Container from "./layouts/Container";
import SignIn from "./views/SignIn";
import SignUp from "./views/SignUp";
import {useUserStore} from "./stores/userStore";
import {useCookies} from "react-cookie";
import MyPage from "./views/MyPage";
import Articles from "./views/Article/Articles";
import ArticleDetail from "./views/Article/ArticleDetail";
import ArticlePost from "./views/Article/ArticlePost";
import Practice from "./views/Practice";
import QuizPost from "./views/Quiz/QuizPost";
import QuizDetail from "./views/Quiz/QuizDetail";
import Exam from "./views/Exam";
import ExamDetail from "./views/Exam/ExamDetail";
import QuizEdit from "./views/Quiz/QuizEdit";
import axiosInstance from "./api/axios";

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
            await axiosInstance.post('/api/v1/auth/refresh', {
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
                const response = await axiosInstance.get('/api/v1/users', {
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

          <Route path="/practice" element={<Practice />}/>
          <Route path="/card-set/:cardSetId" element={<QuizDetail />} />
          <Route path='/quiz/post' element={<QuizPost/>}/>
          <Route path="/card-set/edit/:cardSetId" element={<QuizEdit />} />


          <Route path="/exam" element={<Exam />}/>
          <Route path="/quiz/:cardSetId" element={<ExamDetail />} />



         {/*<Route path='/test' element={<Test/>}/>*/}
      </Route>
      </Routes>


  );
}

export default App;
