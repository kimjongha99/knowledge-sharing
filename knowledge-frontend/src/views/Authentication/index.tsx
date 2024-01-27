import React, {useState} from 'react';
import axios from 'axios';
import {axiosInstance} from "../../apis";

interface IdCheckRequestDto {
    id: string;
}

interface IdCheckResponseDto {
    code: string;
    message: string;
}

interface EmailCertificationRequestDto {
    id: string;
    email: string;
}
interface EmailCertificationResponseDto{
    code:string;
    message:string;
}
function Authentication() {

    const [userId, setUserId] = useState('');
    const [message, setMessage] = useState('');
    const [isError, setIsError] = useState(false);
    const [email, setEmail] = useState('');


    const handleInputChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        setUserId(e.target.value);
        // Reset messages when user starts typing
        setMessage('');
        setIsError(false);
    };
    const handleEmailChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        setEmail(e.target.value);
    };

    const sendEmailCertification = async () => {
        try {
            const requestBody: EmailCertificationRequestDto = {
                id: userId,
                email: email
            };
            const response = await axiosInstance.post('/auth/email-certification', requestBody);

            // Handle response here
            console.log('Email Certification Response:', response.data);
            // You might want to update the UI based on the response
        } catch (error: any) {
            // Handle errors
            console.error('Error:', error);
            // Update the UI to show the error message
        }
    };



    const checkUserId = async () => {
        try {
            const requestBody: IdCheckRequestDto = { id: userId };
            const response = await axiosInstance.post<IdCheckResponseDto>('auth/id-check', requestBody);
           if (response.data.code === 'SU') {
                setMessage('사용 가능합니다.');
                setIsError(false);
            } else {
                setMessage(response.data.message);
                setIsError(true);
            }
        } catch (error: any) {
            setIsError(true);
            if (error.response) {
                // Handle errors based on response status or data
                setMessage(`Error: ${error.response.data.message}`);
            } else if (error.request) {
                setMessage('No response received from the server.');
            } else {
                setMessage(`Error: ${error.message}`);
            }
        }
    };
    return (
        <div>
            <h2>Authentication Page</h2>

            <div className="flex flex-col items-center p-6 bg-white rounded-lg shadow-md max-w-md mx-auto">
                <h1 className="text-2xl font-bold mb-4">회원 가입</h1>
                <div className="w-full mb-4 flex flex-col">
                    <div className="mb-2">
                        <label htmlFor="userID" className="block text-lg font-Large mb-1">
                            아이디
                        </label>
                        <input
                            className="flex h-10 w-full rounded-md border border-input bg-background px-3 py-2 text-sm ring-offset-background file:border-0 file:bg-transparent file:text-sm file:font-medium placeholder:text-muted-foreground focus-visible:outline-none focus-visible:ring-2 focus-visible:ring-ring focus-visible:ring-offset-2 disabled:cursor-not-allowed disabled:opacity-50"
                            type="text"
                            value={userId}
                            onChange={handleInputChange}
                            placeholder="Enter user ID"                        />
                    </div>
                    <p style={{ color: isError ? 'red' : 'green' }}>{message}</p>
                    <button onClick={checkUserId}
                        className="inline-flex items-center justify-center whitespace-nowrap font-medium ring-offset-background transition-colors focus-visible:outline-none focus-visible:ring-2 focus-visible:ring-ring focus-visible:ring-offset-2 disabled:pointer-events-none disabled:opacity-50 hover:bg-primary/90 h-10 bg-gray-700 text-white py-1 px-2 rounded-md text-xs self-start">
                        중복체크
                    </button>
                </div>
                <div className="w-full mb-4">
                    <label htmlFor="password" className="block text-sm font-medium mb-1">
                        Password
                    </label>
                    <input
                        className="flex h-10 w-full rounded-md border border-input bg-background px-3 py-2 text-sm ring-offset-background file:border-0 file:bg-transparent file:text-sm file:font-medium placeholder:text-muted-foreground focus-visible:outline-none focus-visible:ring-2 focus-visible:ring-ring focus-visible:ring-offset-2 disabled:cursor-not-allowed disabled:opacity-50"
                        id="password"
                        placeholder="Enter your password"
                        type="password"
                    />
                </div>
                <div className="w-full mb-4">
                    <label htmlFor="password-check" className="block text-sm font-medium mb-1">
                        Password Check
                    </label>
                    <input
                        className="flex h-10 w-full rounded-md border border-input bg-background px-3 py-2 text-sm ring-offset-background file:border-0 file:bg-transparent file:text-sm file:font-medium placeholder:text-muted-foreground focus-visible:outline-none focus-visible:ring-2 focus-visible:ring-ring focus-visible:ring-offset-2 disabled:cursor-not-allowed disabled:opacity-50"
                        id="password-check"
                        placeholder="Confirm your password"
                        type="password"
                    />
                </div>
                <div className="w-full mb-4 flex flex-col">
                    <div className="mb-2">
                        <label htmlFor="email" className="block text-sm font-medium mb-1">
                            Email
                        </label>
                        <input
                            className="flex h-10 w-full rounded-md border border-input bg-background px-3 py-2 text-sm ring-offset-background file:border-0 file:bg-transparent file:text-sm file:font-medium placeholder:text-muted-foreground focus-visible:outline-none focus-visible:ring-2 focus-visible:ring-ring focus-visible:ring-offset-2 disabled:cursor-not-allowed disabled:opacity-50"
                            id="email"
                            placeholder="Enter your email"
                            type="email"
                            value={email}
                            onChange={handleEmailChange}
                        />
                    </div>
                    <button
                        onClick={sendEmailCertification}
                        className="inline-flex items-center justify-center whitespace-nowrap font-medium ring-offset-background transition-colors focus-visible:outline-none focus-visible:ring-2 focus-visible:ring-ring focus-visible:ring-offset-2 disabled:pointer-events-none disabled:opacity-50 hover:bg-primary/90 h-10 bg-gray-700 text-white py-1 px-2 rounded-md text-xs self-start">
                        인증번호 발송
                    </button>
                </div>
                <div className="w-full mb-4">
                    <label htmlFor="auth-code" className="block text-sm font-medium mb-1">
                        Authentication Code
                    </label>
                    <input
                        className="flex h-10 w-full rounded-md border border-input bg-background px-3 py-2 text-sm ring-offset-background file:border-0 file:bg-transparent file:text-sm file:font-medium placeholder:text-muted-foreground focus-visible:outline-none focus-visible:ring-2 focus-visible:ring-ring focus-visible:ring-offset-2 disabled:cursor-not-allowed disabled:opacity-50"
                        id="auth-code"
                        placeholder="Enter authentication code"
                    />
                </div>
                <button
                    className="inline-flex items-center justify-center whitespace-nowrap text-sm font-medium ring-offset-background transition-colors focus-visible:outline-none focus-visible:ring-2 focus-visible:ring-ring focus-visible:ring-offset-2 disabled:pointer-events-none disabled:opacity-50 hover:bg-primary/90 h-10 px-4 w-full bg-gray-700 text-white py-3 rounded-md">
                    Sign Up
                </button>
            </div>


        </div>


    );
}

export default Authentication;