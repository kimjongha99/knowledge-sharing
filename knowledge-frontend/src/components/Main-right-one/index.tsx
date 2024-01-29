import React from "react";
import './style.css';
import { useNavigate } from 'react-router-dom';
import {AUTH_PATH, SIGN_IN_PATH, SIGN_UP_PATH} from "../../constant";
import {useUserStore} from "../../stores/user.store";
import {useCookies} from "react-cookie";

const MainRightOne =()=>{
    const navigate = useNavigate();
    const [cookies, , removeCookie] = useCookies(['accessToken', 'refreshToken']); // Include refreshToken if needed
    const user = useUserStore((state) => state.user);
    const clearUser = useUserStore((state) => state.clearUser); // Get the clearUser function
    const cookieNames: Array<'accessToken' | 'refreshToken'> = ['accessToken', 'refreshToken']; // Define the cookie names here

    function handleLogin() {
        navigate(AUTH_PATH +SIGN_IN_PATH);
    }
    function handleSignUp() {
        navigate(AUTH_PATH+SIGN_UP_PATH);
    }

    const isAuthenticated = () => {
        return cookies.accessToken && user;
    };

    function handleLogout() {
        // Clear cookies
        cookieNames.forEach(cookieName => {
            if (cookies[cookieName]) {
                removeCookie(cookieName);
            }
        });
        clearUser();
        window.location.replace("/")

    }
    return(
        <section id="main-right-one">
            여기는 로그인창이고
            로그인 안했을시 로그인 회원가입 버튼만 보이게할 예정
            <br/>
            로그인시 유저 정보 간단한게 보이게할예정  + 로그아웃버튼
            {!isAuthenticated() && ( // Render buttons only if not authenticated
                <>
                    <button onClick={handleLogin}>Login</button>
                    <button onClick={handleSignUp}>Sign up</button>
                </>
            )}

            {isAuthenticated() && (
                <>
                    <button onClick={handleLogout}>로그아웃</button>
                </>

            )}


        </section>
    )
}

export default MainRightOne;