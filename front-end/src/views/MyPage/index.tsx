import './style.css';
import Profile from "./Profile";
import Follow from "./Follow";

export default function MyPage(){



    return(
        <div>
            <main id="Main">
                <div id="main-left">
                    <section id="main-left-one"></section>
                    <section id="main-left-two"></section>
               </div>
                <div id="main-right">
                    <Profile/>
                    {/*<section id="main-right-two"></section>*/}
                    {/*<section id="main-right-three"></section>*/}
                    <Follow/>
                    <section id="main-right-four"></section>
                </div>
            </main>
        </div>
    )
}


