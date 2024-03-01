import './style.css';
import React, {SetStateAction, useState} from "react";
import axios from "axios";
import {useNavigate} from "react-router-dom";
import axiosInstance from "../../api/axios";

export default function SignUp() {
    const [id, setId] = useState<string>('');
    const [email, setEmail] = useState('');
    const [message, setMessage] = useState('');
    const [certificationNumber, setCertificationNumber] = useState('');
    const [emailSendMessage, SetEmailSendMessage] = useState('');

    const [verificationMessage, setVerificationMessage] = useState('');
    const [password, setPassword] = useState<string>('');
    const [confirmPassword, setConfirmPassword] = useState<string>('');
    const [passwordError, setPasswordError] = useState<string>('');
    const [signMessage, SetSignMessage] = useState('');

    const navigate = useNavigate();
    // 아이디 체크 헨들러,  axios
    //region
    const handleIdChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        setId(e.target.value);
        setMessage(''); // Reset message on ID change
    };
    const checkIdDuplicate = async () => {
        try {
            // Make the POST request to check ID
            const response = await axiosInstance.post('/api/v1/auth/id-check', {id});
            // Handle success response
            if (response.status === 200) {
                // Set message from the response data
                setMessage(response.data.data);
            }
        } catch (error) {
            if (axios.isAxiosError(error)) {
                // Check if the error response exists and has the expected format
                if (error.response && error.response.data && error.response.status === 409) {
                    // Handle duplicate ID by setting error message
                    setMessage(error.response.data.errorMessage);
                } else {
                    console.error('An error occurred:', error.message);
                    setMessage('An unexpected error occurred.');
                }
            } else {
                console.error('An error occurred:', error);
                setMessage('An unexpected error occurred.');
            }
        }
    };
    //endregion


    //이메일 인증번호 전송 핸들러, axios
    //region
    // 입력 변경 시 이메일 상태 업데이트
    const handleEmailChange = (e: React.ChangeEvent<HTMLInputElement>) => setEmail(e.target.value);
    const sendEmailCertification = async () => {
        try {
            const response = await axiosInstance.post('/api/v1/auth/email-certification', {
                email,
                id
            });
            if (response.status === 200) {
                SetEmailSendMessage(response.data.data);
            }
        } catch (error: any) {
            if (axios.isAxiosError(error) && error.response) {
                SetEmailSendMessage(error.response.data.errorMessage);
            } else {
                console.error('An error occurred:', error);
                SetEmailSendMessage('An unexpected error occurred.');
            }
        }
    };
    //endregion


    //이메일 인증번호 확인 헨든러, axios
    //region
    // 인증번호 변경을 위한 새로운 핸들러
    const handleCertificationNumberChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        setCertificationNumber(e.target.value);
        setVerificationMessage(''); // 입력 변경 시 확인 메시지 재설정
    };
    const verifyCertificationNumber = async () => {
        try {
            const response = await axiosInstance.post('/api/v1/auth/check-certification', {
                email,
                id,
                certificationNumber
            });

            if (response.status === 200) {
                setVerificationMessage(response.data.data);
            }
        } catch (error: any) {
            if (axios.isAxiosError(error) && error.response) {
                setVerificationMessage(error.response.data.errorMessage);
            } else {
                console.error('An error occurred:', error);
                setVerificationMessage('An unexpected error occurred.');
            }
        }
    };
    //endregion

    // 비밀번호 일치 확인
    //region

    // Handle password change
    const handlePasswordChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        setPassword(e.target.value);
        // Reset password error state whenever the user modifies the password
        if (passwordError) setPasswordError('');
    };
    // Handle confirm password change
    const handleConfirmPasswordChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        setConfirmPassword(e.target.value);
        // Reset password error state whenever the user modifies the confirm password
        if (passwordError) setPasswordError('');
    };
    // Function to validate password and confirm password match
    const validatePassword = () => {
        if (password !== confirmPassword) {
            setPasswordError('Passwords do not match.');
            return false;
        }
        setPasswordError('');
        return true;
    };


    //endregion

    //회원가입핸들러
    //region
    const handleSubmit = async (e: React.FormEvent<HTMLFormElement>) => {
        e.preventDefault(); // Prevent default form submission
        if (validatePassword()) {
            console.log('Password and confirm password match.');
        } else {
            console.log('Password and confirm password do not match.');
        }
        try {
            await axiosInstance.post('/api/v1/auth/sign-up', {
                email,
                id,
                password,
                certificationNumber,
            });
            // On successful sign-up, navigate to the sign-in page
            navigate('/auth/sign-in');
        } catch (error: any) {
            if (axios.isAxiosError(error) && error.response) {
                // 서버 응답에서 오류 메시지를 설정합니다.
                SetSignMessage(error.response.data.errorMessage);
            } else {
                console.error('An error occurred:', error);
                SetSignMessage('An unexpected error occurred.');
            }
        }
    }
    //endregion

        return (
            <div className='sign-up-main'>
                <div className="mx-auto max-w-sm space-y-6">
                    <div className="space-y-4">
                        <div className="space-y-2 text-center">
                            <h1 className="text-3xl font-bold">
                                회원가입
                            </h1>
                            <p className="text-gray-500 dark:text-gray-400">
                                정보를 입력하고 회원가입을 하세요
                            </p>
                        </div>
                        <form onSubmit={handleSubmit}>


                            <div className="space-y-2"><label
                                className="text-sm font-medium leading-none peer-disabled:cursor-not-allowed peer-disabled:opacity-70"
                                htmlFor="id">
                                ID
                            </label>
                                <div className="flex items-start space-x-2">
                                    <input
                                        className="flex h-10 w-full rounded-md border border-input bg-background px-3 py-2 text-sm ring-offset-background file:border-0 file:bg-transparent file:text-sm file:font-medium placeholder:text-muted-foreground focus-visible:outline-none focus-visible:ring-2 focus-visible:ring-ring focus-visible:ring-offset-2 disabled:cursor-not-allowed disabled:opacity-50"
                                        type="text"
                                        value={id}
                                        onChange={handleIdChange}
                                        placeholder="Enter your ID"/>

                                    <button onClick={checkIdDuplicate}
                                            className="inline-flex items-center justify-center whitespace-nowrap rounded-md text-sm font-medium ring-offset-background transition-colors focus-visible:outline-none focus-visible:ring-2 focus-visible:ring-ring focus-visible:ring-offset-2 disabled:pointer-events-none disabled:opacity-50 bg-primary text-primary-foreground hover:bg-primary/90 h-10 px-4 py-2">
                                        이메일 중복체크
                                    </button>

                                </div>
                                <div id='response-message'>
                                    {message && <p>{message}</p>}
                                </div>
                            </div>


                            <div className="space-y-2">
                                <label
                                    className="text-sm font-medium leading-none peer-disabled:cursor-not-allowed peer-disabled:opacity-70"
                                    htmlFor="email">
                                    Email
                                </label>
                                <input type="email"
                                       required
                                       placeholder="m@example.com"
                                       onChange={handleEmailChange}
                                       value={email}
                                       className="flex h-10 w-full rounded-md border border-input bg-background px-3 py-2 text-sm ring-offset-background file:border-0 file:bg-transparent file:text-sm file:font-medium placeholder:text-muted-foreground focus-visible:outline-none focus-visible:ring-2 focus-visible:ring-ring focus-visible:ring-offset-2 disabled:cursor-not-allowed disabled:opacity-50"/>

                                <button onClick={sendEmailCertification}
                                        className="inline-flex items-center justify-center whitespace-nowrap rounded-md text-sm font-medium ring-offset-background transition-colors focus-visible:outline-none focus-visible:ring-2 focus-visible:ring-ring focus-visible:ring-offset-2 disabled:pointer-events-none disabled:opacity-50 bg-primary text-primary-foreground hover:bg-primary/90 h-10 px-4 py-2">
                                    이메일인증번호 전송
                                </button>
                                <div id='response-message'>
                                    {emailSendMessage && <p>{emailSendMessage}</p>}
                                </div>


                                <div className="flex items-start space-x-2">
                                    <input type="text"
                                           placeholder="이메일 인증 코드"
                                           value={certificationNumber}
                                           onChange={handleCertificationNumberChange}
                                           autoComplete="text"
                                           className="flex h-10 w-full rounded-md border border-input bg-background px-3 py-2 text-sm ring-offset-background file:border-0 file:bg-transparent file:text-sm file:font-medium placeholder:text-muted-foreground focus-visible:outline-none focus-visible:ring-2 focus-visible:ring-ring focus-visible:ring-offset-2 disabled:cursor-not-allowed disabled:opacity-50"/>


                                    <button onClick={verifyCertificationNumber}
                                            className="inline-flex items-center justify-center whitespace-nowrap rounded-md text-sm font-medium ring-offset-background transition-colors focus-visible:outline-none focus-visible:ring-2 focus-visible:ring-ring focus-visible:ring-offset-2 disabled:pointer-events-none disabled:opacity-50 bg-primary text-primary-foreground hover:bg-primary/90 h-10 px-4 py-2">
                                        확인
                                    </button>
                                    <div id='response-message'>
                                        {verificationMessage && <p>{verificationMessage}</p>}
                                    </div>
                                </div>
                            </div>


                            <div className="space-y-2">
                                <label htmlFor="password"
                                       className="text-sm font-medium leading-none peer-disabled:cursor-not-allowed peer-disabled:opacity-70">Password</label>
                                <input
                                    type="password"
                                    className="flex h-10 w-full rounded-md border border-input bg-background px-3 py-2 text-sm ring-offset-background file:border-0 file:bg-transparent file:text-sm file:font-medium placeholder:text-muted-foreground focus-visible:outline-none focus-visible:ring-2 focus-visible:ring-ring focus-visible:ring-offset-2 disabled:cursor-not-allowed disabled:opacity-50"
                                    id="password"
                                    value={password}
                                    onChange={handlePasswordChange}
                                    autoComplete="password"
                                    required
                                />
                            </div>
                            <div className="space-y-2">
                                <label htmlFor="confirm-password"
                                       className="text-sm font-medium leading-none peer-disabled:cursor-not-allowed peer-disabled:opacity-70">Confirm
                                    Password</label>
                                <input
                                    type="password"
                                    className="flex h-10 w-full rounded-md border border-input bg-background px-3 py-2 text-sm ring-offset-background file:border-0 file:bg-transparent file:text-sm file:font-medium placeholder:text-muted-foreground focus-visible:outline-none focus-visible:ring-2 focus-visible:ring-ring focus-visible:ring-offset-2 disabled:cursor-not-allowed disabled:opacity-50"
                                    id="confirm-password"
                                    value={confirmPassword}
                                    onChange={handleConfirmPasswordChange}
                                    autoComplete="confirm-password"
                                    required
                                />
                                {passwordError && <p className="text-red-500 text-xs">{passwordError}</p>}
                            </div>


                            <button type="submit"
                                    className="inline-flex items-center justify-center whitespace-nowrap rounded-md text-sm font-medium ring-offset-background transition-colors focus-visible:outline-none focus-visible:ring-2 focus-visible:ring-ring focus-visible:ring-offset-2 disabled:pointer-events-none disabled:opacity-50 bg-primary text-primary-foreground hover:bg-primary/90 h-10 px-4 py-2 w-full">
                                회원가입
                            </button>
                            {signMessage && <p className="text-red-500 text-xs">{signMessage}</p>}


                        </form>
                    </div>
                </div>


            </div>
        )
    }


