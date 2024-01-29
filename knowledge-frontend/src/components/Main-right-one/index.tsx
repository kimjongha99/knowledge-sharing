import React from "react";
import './style.css';
import { useNavigate } from 'react-router-dom';
import {AUTH_PATH, SIGN_UP_PATH} from "../../constant";

const MainRightOne =()=>{
    const navigate = useNavigate();

    function handleLogin() {
        navigate(AUTH_PATH +SIGN_UP_PATH);
    }




    return(
        <section id="main-right-one">



             <button onClick={handleLogin}>회원가입 및 로그인   </button>



        </section>
    )
}

export default MainRightOne;