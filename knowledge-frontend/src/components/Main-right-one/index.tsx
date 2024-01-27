import React from "react";
import './style.css';
import { useNavigate } from 'react-router-dom';
import {AUTH_PATH} from "../../constant";

const MainRightOne =()=>{
    const navigate = useNavigate();

    function handleLogin() {
        navigate(AUTH_PATH);
    }




    return(
        <section id="main-right-one">



             <button onClick={handleLogin}>Login</button>



        </section>
    )
}

export default MainRightOne;