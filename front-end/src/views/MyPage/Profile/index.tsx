import './style.css';
import {useNavigate, useParams} from "react-router-dom";
import {useUserStore} from "../../../stores/userStore";
import {useCookies} from "react-cookie";
import {useState} from "react";
import ProfileImg from "./ProfileImg";
import axiosInstance from "../../../api/axios";

export default function Profile(){
    const [newPassword, setNewPassword] = useState(''); // State to hold the new password input by the user
    const [isEditing, setIsEditing] = useState(false); // State to toggle edit mode

    // state : user userId path variable 상태 //
    const { userId} = useParams();
    //state 현재 유저 상태관리
    const { user, setUser } = useUserStore();
    // state : 쿠키 상태 //
    const [cookies] = useCookies(['accessToken']);
    // state : 마이페이지 여부 상태 //
    const [isMyPage, setMyPage] = useState<boolean>(false);

    // function : 네비게이터 함수 //
    const navigator = useNavigate();

    const showAlert = () => {
        if (user?.type !== 'app') {
            alert('소셜 로그인 가입자는 회원정보 수정이 불가능합니다.');
            return true;
        }
        return false;
    };


    const handleChangePassword = async (e: { preventDefault: () => void; }) => {
        e.preventDefault(); // Prevent the default form submission behavior

        try {
            const response = await axiosInstance.patch('/api/v1/users/password', {
                newPassword: newPassword,
            }, {
                headers: {
                    Authorization: `Bearer ${cookies.accessToken}`,
                    'Content-Type': 'application/json'
                }
            });

            if (response.status === 200) {
                alert('Password successfully changed.');
                setIsEditing(false); // Turn off edit mode
                setNewPassword(''); // Reset the password input
                // Optionally, navigate or refresh the user's profile
            }
        } catch (error) {
            console.error('Failed to change password:', error);
            alert('Failed to change password.');
        }
    };


    return(
        <section id="main-right-two">
                <div  className="space-y-4">
                    <ProfileImg/>
                    <div id="profiles-text">

                    <p>id : {user?.userId}</p>
                    <p>Email: {user?.email}</p>
                    <p>가입 경로: {user?.type}</p>
                    <p>권한: {user?.role}</p>
                    <p className="flex items-center">
                        비밀번호: {isEditing ? (
                        <form onSubmit={handleChangePassword} className="flex items-center">
                            <input
                                type="password"
                                value={newPassword}
                                onChange={(e) => setNewPassword(e.target.value)}
                                required
                                className="password-input-class" // Add your input CSS class here
                            />
                            <button type="submit" className="save-button-class">Save</button> {/* Add your button CSS class here */}
                        </form>
                    ) : '************'}
                        <button
                            onClick={() => setIsEditing(!isEditing)}
                            className="inline-flex items-center justify-center whitespace-nowrap rounded-md text-sm font-medium ring-offset-background transition-colors focus-visible:outline-none focus-visible:ring-2 focus-visible:ring-ring focus-visible:ring-offset-2 disabled:pointer-events-none disabled:opacity-50 bg-primary text-primary-foreground hover:bg-primary/90 h-10 px-4 py-2 ml-4"> {/* Add your button CSS class here */}
                            {isEditing ? 'Cancel' : 'Edit'}
                        </button>
                    </p>
                </div>
            </div>
        </section>

    )
}


