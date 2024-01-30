
import './style.css'
import React, {ChangeEvent, useEffect, useRef, useState} from "react";
import {useNavigate, useParams} from "react-router-dom";
import {useUserStore} from "../../stores/user.store";
import {useCookies} from "react-cookie";
import axios from "axios";
import ProfileImg from "./ProfileImg";

function MyPage() {
    // state : user userId path variable 상태 //
    const { userId} = useParams();
    //state 현재 유저 상태관리
    const user = useUserStore(state => state.user);
    // state : 쿠키 상태 //
    const [cookies, setCookie] = useCookies();
    // state : 마이페이지 여부 상태 //
    const [isMyPage, setMyPage] = useState<boolean>(false);

    // function : 네비게이터 함수 //
    const navigator = useNavigate();



    //유저 화면 상단 컴포넌트 , 하단에는 다른 로직이 들어갈예정
    const UserTop = () => {

        const [newPassword, setNewPassword] = useState(''); // State for new password
        const [showPasswordUpdate, setShowPasswordUpdate] = useState(false); // State to control the display of password update fields

        const [userInfo, setUserInfo] = useState({
            userId: '',
            email: '',
            profileImageUrl: ''
        });
        const updatePassword = () => {
            const updatePasswordUrl = `http://localhost:4040/api/v1/users/password`;
            const accessToken = cookies.accessToken; // Retrieving the access token from cookies

            axios.patch(updatePasswordUrl, {
                newPassword
            }, {
                headers: {
                    Authorization: `Bearer ${accessToken}` // Including the token in the Bearer Authorization header
                }
            })
                .then(response => {
                    const { code, message } = response.data;

                    alert(message); // Displaying the message

                    if (code === 'SU') {
                        // If success, reset cookies and state
                        setCookie('accessToken', '', { path: '/' }); // Clearing the access token cookie
                        // ... Any other state reset you need
                        navigator('/'); // Redirecting to login page or a route that initiates logout
                    }
                })
                .catch(error => {
                    if (!error.response) return;
                    const { message } = error.response.data;

                    alert(message); // Displaying the message, for errors as well
                });
        };


        const togglePasswordUpdate = () => {
            setShowPasswordUpdate(!showPasswordUpdate); // Toggle the display of the password update section
        };



        useEffect(() => {
            if (!userId) return;

            const accessToken = cookies.accessToken; // Retrieving the access token from cookies
            const getUserUrl = `http://localhost:4040/api/v1/users/${userId}`;

            axios.get(getUserUrl, {
                headers: {
                    Authorization: `Bearer ${accessToken}` // Including the token in the Bearer Authorization header
                }
            })
                .then(response => {
                    const responseBody = response.data;

                    if (!responseBody) return;
                    const { code, userId, email, profileImageUrl, message } = responseBody;

                    if (code !== 'SU') {
                        alert(message); // Displaying the message as it is
                        return;
                    }

                    // Setting the user info if the response is successful
                    setUserInfo({
                        userId,
                        email,
                        profileImageUrl
                    });
                    setMyPage(email === user?.email);
                })
                .catch(error => {
                    if (!error.response) return;
                    const responseBody = error.response.data;
                    const { message } = responseBody;

                    alert(message); // Displaying the message as it is, for errors as well
                });
        }, [userId, user, cookies.accessToken]); // Added cookies.accessToken as a dependency


        return (
            <div>
                <ProfileImg/>
                <div>User ID: {userInfo.userId}</div>
                <div>Email: {userInfo.email}</div>
                <button onClick={togglePasswordUpdate}>Change Password</button> {/* Button to show/hide password update */}
                {showPasswordUpdate && ( // Conditional rendering based on showPasswordUpdate state
                    <div>
                        <input
                            type="password"
                            value={newPassword}
                            onChange={(e) => setNewPassword(e.target.value)}
                            placeholder="Enter your new password"
                        />
                        <button onClick={updatePassword}>Update Password</button>
                    </div>
                )}
            </div>

        );
    };

        return (
        <>
            <UserTop />
        </>    );
}

export default MyPage;
