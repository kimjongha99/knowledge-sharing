
import './style.css'
import React, {ChangeEvent, useEffect, useRef, useState} from "react";
import {useNavigate, useParams} from "react-router-dom";
import {useUserStore} from "../../stores/user.store";
import {useCookies} from "react-cookie";
import axios from "axios";

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
        const [isPasswordChange, setPasswordChange] = useState(false);
        const [password, setPassword] = useState('');
        const [changePassword, setChangePassword] = useState('');
        const [profileImage, setProfileImage] = useState(null);
        const [userInfo, setUserInfo] = useState({
            userId: '',
            email: '',
            profileImageUrl: ''
        });

        const handleImageUpload = async () => { // 변경된 부분: 매개변수 제거
            const fileInput = document.getElementById('fileInput') as HTMLInputElement | null;

            if (!fileInput) {
                console.error('파일 입력 요소를 찾을 수 없습니다.');
                return;
            }

            const file = fileInput.files?.[0]; // Optional chaining을 사용하여 널 체크

            if (!file) {
                console.error('파일을 선택하지 않았습니다.');
                return;
            }

            const formData = new FormData();
            formData.append('image', file);

            try {
                const response = await axios.post('http://localhost:4040/api/files/upload', formData, {
                    headers: {
                        'Content-Type': 'multipart/form-data',
                        'Authorization': `Bearer ${cookies.accessToken}`,
                    },
                });

                // 서버에서의 응답을 콘솔로 출력합니다.
                console.log('업로드 결과:', response.data);

                // 필요하다면 응답을 처리하거나 상태를 업데이트할 수 있습니다.
            } catch (error: any) { // 'error'를 'any'로 형변환
                if (error.response) {
                    const { message } = error.response.data;
                    console.error('업로드 에러:', message);
                } else {
                    console.error('네트워크 에러:', error.message);
                }
            }
        };

        useEffect(() => {
            if (!userId) return;

            const accessToken = cookies.accessToken; // Retrieving the access token from cookies
            const getUserUrl = `http://localhost:4040/api/v1/user/${userId}`;

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

                <img src={userInfo.profileImageUrl} alt="프로필 이미지" />

                <div>User ID: {userInfo.userId}</div>
                <div>Email: {userInfo.email}</div>
                <button onClick={handleImageUpload}>프로필 이미지 업로드</button>

            </div>

        );
    };

        return (
        <>
            <UserTop />
        </>    );
}

export default MyPage;
