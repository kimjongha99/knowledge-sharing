import './style.css';
import {SetStateAction, useState} from "react";
import {useCookies} from "react-cookie";
import {useNavigate} from "react-router-dom";
import {useUserStore} from "../../stores/userStore";

export default function Side() {
    // 사이드바의 너비는250 높이는 헤더와 푸터 사이이다.
// 활성 항목을 추적하는 상태
    const [activeIndex, setActiveIndex] = useState(0);
    const [cookies] = useCookies(['accessToken', 'refreshToken']); // Include refreshToken if needed
    const navigate = useNavigate(); // Use the useNavigate hook
    const { user, setUser } = useUserStore();

    // activeIndex 상태를 클릭한 항목의 인덱스로 업데이트합니다.
    const handleItemClick = (index: SetStateAction<number>) => {
        setActiveIndex(index);
        if (index === 0) {
            navigate(`/`);
        }

        if (index === 1 && isLoggedIn) {
            navigate(`/user/${user?.userId}`);
        }
        if (index === 2) {
            navigate(`/articles`);
        }
        if (index === 3) {
            navigate(`/practice`);
        }
    };

    const isLoggedIn = cookies.accessToken && cookies.refreshToken;










    return(

        <div >
            <nav className="main-menu">
                <ul>
                    <li className={`nav-item ${activeIndex === 0 ? 'active' : ''}`} onClick={() => handleItemClick(0)}>
                        <a >
                            <span className="nav-text">Home</span>
                        </a>
                    </li>
                    {isLoggedIn &&(
                            <li className={`nav-item ${activeIndex === 1 ? 'active' : ''}`} onClick={() => handleItemClick(1)}>
                                <a>
                                    <span className="nav-text">Profile</span>
                                </a>
                            </li>
                        )}
                        
                    <li className={`nav-item ${activeIndex === 2 ? 'active' : ''}`} onClick={() => handleItemClick(2)}>
                        <a>
                            <span className="nav-text" id='knowledge-article'> Knowledge Article[지식공유]</span>
                        </a>
                    </li>
                    <li className={`nav-item ${activeIndex === 3 ? 'active' : ''}`} onClick={() => handleItemClick(3)}>
                        <a>
                            <span className="nav-text">Practice[학습]</span>
                        </a>
                    </li>
                    <li className={`nav-item ${activeIndex === 4 ? 'active' : ''}`} onClick={() => handleItemClick(4)}>
                        <a >
                            <span className="nav-text">Settings</span>
                        </a>
                    </li>
                </ul>
            </nav>
        </div>
    )
}
