import './style.css';
import {SetStateAction, useState} from "react";
import {useCookies} from "react-cookie";

export default function Side() {
    // 사이드바의 너비는250 높이는 헤더와 푸터 사이이다.
// 활성 항목을 추적하는 상태
    const [activeIndex, setActiveIndex] = useState(0);
    const [cookies] = useCookies(['accessToken', 'refreshToken']); // Include refreshToken if needed

    // activeIndex 상태를 클릭한 항목의 인덱스로 업데이트합니다.
    const handleItemClick = (index: SetStateAction<number>) => {
        setActiveIndex(index);
    };

    const isLoggedIn = cookies.accessToken && cookies.refreshToken;










    return(

        <div>
            <nav className="main-menu">
                <ul>
                    <li className={`nav-item ${activeIndex === 0 ? 'active' : ''}`} onClick={() => handleItemClick(0)}>
                        <a href="#">
                            <i className="fa fa-house nav-icon"></i>
                            <span className="nav-text">Home</span>
                        </a>
                    </li>
                    {isLoggedIn &&(
                            <li className={`nav-item ${activeIndex === 1 ? 'active' : ''}`} onClick={() => handleItemClick(1)}>
                                <a href="#">
                                    <i className="fa fa-user nav-icon"></i>
                                    <span className="nav-text">Profile</span>
                                </a>
                            </li>
                        )}
                        
                    <li className={`nav-item ${activeIndex === 2 ? 'active' : ''}`} onClick={() => handleItemClick(2)}>
                        <a href="#">
                            <i className="fa fa-calendar-check nav-icon"></i>
                            <span className="nav-text">Schedule</span>
                        </a>
                    </li>
                    <li className={`nav-item ${activeIndex === 3 ? 'active' : ''}`} onClick={() => handleItemClick(3)}>
                        <a href="#">
                            <i className="fa fa-person-running nav-icon"></i>
                            <span className="nav-text">Activities</span>
                        </a>
                    </li>
                    <li className={`nav-item ${activeIndex === 4 ? 'active' : ''}`} onClick={() => handleItemClick(4)}>
                        <a href="#">
                            <i className="fa fa-sliders nav-icon"></i>
                            <span className="nav-text">Settings</span>
                        </a>
                    </li>
                </ul>
            </nav>
        </div>
    )
}
