import React, {useEffect} from 'react';
import './App.css';
import {Route, Routes, useNavigate} from "react-router-dom";
import Main from "./views/Main";
import Container from "./layouts/Container";
import SignIn from "./views/SignIn";
import SignUp from "./views/SignUp";
import {useUserStore} from "./stores/userStore";
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
import {useCookies} from "react-cookie";
import OAuth from "./views/OAuth";

function App() {
    const { user, setUser } = useUserStore();
    const [cookies, setCookie, removeCookie] = useCookies(['accessToken', 'refreshToken']);
    const navigate = useNavigate();

  // 유저 정보 가져오고 리프레쉬 토큰 발급해주는 로직//
   //region
    useEffect(() => {
        const checkAuthStatus = async () => {
            if (!cookies.accessToken && cookies.refreshToken) {
                // 액세스 토큰이 없고 리프레시 토큰이 있을 경우, 액세스 토큰 갱신 시도
                await refreshAccessToken();
            } else if (cookies.accessToken) {
                // 액세스 토큰이 있는 경우, 만료 시간 체크 후 필요하다면 갱신 시도
                const isTokenExpired = checkTokenExpiration(cookies.accessToken);
                if (isTokenExpired) {
                    await refreshAccessToken();
                    console.log('토큰이 만료되었습니다.');

                } else {
                    fetchUserData();
                }
            }
        };

        checkAuthStatus();
    }, [cookies.accessToken, cookies.refreshToken]);





    const refreshAccessToken = async () => {
        try {
            const response = await axiosInstance.post('/api/v1/auth/refresh', {
                refreshToken: cookies.refreshToken,
            }, {
                withCredentials: true,
            });
            const { newAccessToken } = response.data; // 새로운 accessToken 받아오기
            setCookie('accessToken', newAccessToken, { path: '/', maxAge: 3600, secure: true, sameSite: 'none' });
            fetchUserData(); // 새 토큰으로 사용자 정보 가져오기
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
    function checkTokenExpiration(token: string): boolean {
        // JWT는 세 부분으로 나누어져 있으며, '.'을 기준으로 구분됩니다.
        const payload = token.split('.')[1];
        if (!payload) {
            throw new Error('Token structure is incorrect');
        }

        // 페이로드 부분을 Base64 디코딩하여 JSON 객체로 변환합니다.
        const decodedPayload = JSON.parse(atob(payload));

        // exp 필드는 Unix 시간(초 단위)으로 토큰의 만료 시간을 나타냅니다.
        const exp = decodedPayload.exp;
        if (!exp) {
            throw new Error('Expiration time is missing in the token payload');
        }

        // 현재 시간(초 단위)을 구하고, 만료 시간과 비교합니다.
        const currentTime = Math.floor(Date.now() / 1000);
        return currentTime > exp;
    }

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
         <Route path='oauth-response/:accessToken/:expirationTime' element={<OAuth/>} />
              {/*:refreshToken/:refreshExpirationTime*/}


         {/*<Route path='/test' element={<Test/>}/>*/}
      </Route>
      </Routes>


  );
}

export default App;
