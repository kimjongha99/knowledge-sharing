import './style.css'
import {useNavigate} from "react-router-dom";

const MainRightOne =()=> {
    const navigate = useNavigate();
    const goArticle =()=>{
        navigate("/articles")
    }

    return(
        <section id="main-left-two">

            <div onClick={goArticle}>아티클 이동</div>
        </section>

        )
}

export  default  MainRightOne