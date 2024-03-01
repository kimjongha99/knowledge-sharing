import './style.css';
import Profile from "./Profile";
import Follow from "./Follow";
import ToFollow from "./ToFollow";
import MyArticles from "./MyArticles";
import MyQuiz from "./MyQuiz";

export default function MyPage(){



    return(
        <div>
            <main id="Main">
                <div id="main-left">
                    {/*<section id="main-left-one"></section>*/}
                    <MyArticles/>
                    {/*<section id="main-left-two"></section>*/}
                    {/*<section id="main-left-three"></section>*/}
                    <MyQuiz/>
                </div>
                <div id="main-right">
                    <Profile/>
                    {/*<section id="main-right-two"></section>*/}
                    {/*<section id="main-right-three"></section>*/}
                    {/*<section id="main-right-four"></section>*/}

                    <Follow/>
                    <ToFollow/>
                </div>
            </main>
        </div>
    )
}


