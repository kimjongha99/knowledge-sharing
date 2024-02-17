import './style.css';
import {useCookies} from "react-cookie";
import {useNavigate} from "react-router-dom";
import {useUserStore} from "../../stores/userStore";




export default function Header() {
    const [cookies, , removeCookie] = useCookies(['accessToken', 'refreshToken']); // Include refreshToken if needed
    const navigate = useNavigate();
    const { user, setUser } = useUserStore();
    const clearUser = useUserStore((state) => state.clearUser); // Get the clearUser function

    // accessToken과 RefreshToken이 모두 존재하는지 확인합니다.
    const isLoggedIn = cookies.accessToken && cookies.refreshToken;
    const handleLogin = () => navigate('/auth/sign-in');
    const handleSignUp = () => navigate('/auth/sign-up');
    const cookieNames: Array<'accessToken' | 'refreshToken'> = ['accessToken', 'refreshToken']; // Define the cookie names here


    function handleLogout() {
        cookieNames.forEach(cookieName => {
            if (cookies[cookieName]) {
                removeCookie(cookieName);
            }
        });
        clearUser();
        window.location.replace("/")

    }
    const profileImageStyle = {
        backgroundImage: `url(${user?.profileImageUrl || 'default_profile_image_url_here.png'})`, // Provide a default image URL if needed
        backgroundSize: 'cover',
        backgroundPosition: 'center',
        backgroundRepeat: 'no-repeat',
        width: '50px',
        height: '50px',
        borderRadius: '50%',
    };
    return (
        <header>
            <div className='bar1'>
                <div id="logo">
                    <svg width={40} height={32} viewBox="0 0 40 32" fill="none" xmlns="http://www.w3.org/2000/svg">
                        <path fillRule="evenodd" clipRule="evenodd"
                              d="M12.8 32H0V21.9849C0 17.1239 4.0116 13.1839 8.96 13.1839H15.2V0.468341C15.2 0.251458 15.3792 0.0754376 15.6 0.0754376C15.7184 0.0754376 15.8308 0.127301 15.9068 0.216883L17.3616 1.92719C18.402 1.07577 19.74 0.563816 21.2 0.563816H22C23.442 0.563816 24.7656 1.06359 25.8 1.89654L27.2932 0.14066C27.3692 0.0514706 27.4812 0 27.6 0C27.8208 0 28 0.175628 28 0.392904V15.3896H23.36C17.528 15.3896 12.8 20.0337 12.8 25.7623V32Z"
                              fill="#45413E"/>
                        <path fillRule="evenodd" clipRule="evenodd"
                              d="M29.76 17.0569V12.0875C29.76 12.0836 29.76 12.0797 29.76 12.0758V6.79004C29.76 6.57435 29.9392 6.4 30.16 6.4C30.266 6.4 30.3676 6.44095 30.4424 6.51389L31.9576 7.98784C32.7864 7.42501 33.7936 7.09504 34.88 7.09504C35.97 7.09504 36.9808 7.42735 37.8112 7.99369L39.3176 6.52793C39.3924 6.45499 39.494 6.41404 39.6 6.41404C39.8208 6.41404 40 6.58878 40 6.80408V23.2632C40 28.0883 35.9884 32 31.04 32H14.4V25.7938C14.4 20.969 18.4116 17.0569 23.36 17.0569H29.76Z"
                              fill="#45413E"/>
                    </svg>
                    Knowledge <br/> sharing
                </div>
                아티클 및 지식공유를 하는 웹사이트입니다


                <div id="utility">
              <span className="material-symbols-outlined">

                 {isLoggedIn ? (
                     <span className="material-symbols-outlined">
                         <div id='mini-profile' >
                             <div id='mini-profile-img'>
                          <div className="profile-image" style={profileImageStyle}></div> {/* Set the profile image style here */}
                             </div>
                         {user?.userId} 님 환영합니다.
                           <div></div>

                         <button className='mr-10' onClick={handleLogout}>로그아웃</button>
                         </div>

                     </span>
                 ) : (
                     // If not logged in, show login and signup options
                     <span className="material-symbols-outlined">
                            <button onClick={handleLogin}>로그인</button>
                         {" / "}
                         <button onClick={handleSignUp}>회원가입</button>
                        </span>
                 )}
              </span>

                </div>
            </div>

        </header>
    );

}