
import './style.css';
import React, {useState} from "react";
import {useNavigate} from "react-router-dom";
import axios, {AxiosError} from "axios";

interface ErrorResponse {
    message: string;
}
function AdminSignIn() {
    const [id, setId] = useState('');
    const [password, setPassword] = useState('');
    const navigate = useNavigate();


    const handleSignIn = async (e: React.FormEvent<HTMLFormElement>) => {
        e.preventDefault();
        try {
            const response = await axios.post('http://localhost:4040/api/v1/auth/sign-in',
                { id, password },
                { withCredentials: true } // 서버로부터 쿠키를 받기 위해 필요
            );

            // 로그인 성공 후 서버 응답에 따라 처리
            if (response.data.role === 'ADMIN') {
                console.log(response.data.role); // Check what exactly you receive from the server

                // 관리자 사용자인 경우 대시보드로 리다이렉트
                navigate('/admin/dashboard');
            } else {
                // 관리자가 아닌 경우 경고 메시지 표시
                alert("Access denied. Only admin can enter.");
            }
        } catch (error) {
            // 로그인 실패 처리
            if (axios.isAxiosError(error) && error.response) {
                const serverError = error.response.data.message || "An error occurred during login.";
                alert("Login failed: " + serverError);
            } else {
                alert("An error occurred during login.");
            }
        }
    };


    return(

        <div id="warp">
            <div id="main-contain">
                <form onSubmit={handleSignIn}>
                    <input
                        type="text"
                        value={id}
                        onChange={(e) => setId(e.target.value)}
                        placeholder="ID"
                    />
                    <input
                        type="password"
                        value={password}
                        onChange={(e) => setPassword(e.target.value)}
                        placeholder="Password"
                    />
                    <button type="submit">Sign In</button>
                </form>
            </div>


        </div>
    )

}

export default AdminSignIn;