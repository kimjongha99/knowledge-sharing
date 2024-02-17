import React, {ChangeEvent, useRef, useState, KeyboardEvent, FormEvent} from 'react';
import {useCookies} from "react-cookie";
import {useNavigate} from "react-router-dom";
import {ResponseBody} from "../../types";
import {SignInResponseDto} from "../../apis/dto/response/response/auth";
import {ResponseCode} from "../../types/enums";
import {SignInRequestDto} from "../../apis/dto/request/auth";
import {signInRequest, SNS_SIGN_IN_URL} from "../../apis";
import InputBox from "../../components/InputBox";
import {MAIN_PATH} from "../../constant";
import './style.css';
import {replaceBehavior} from "@testing-library/user-event/dist/keyboard/plugins";
import axios from "axios";
import {useUserStore} from "../../stores/user.store";

interface AxiosError {
    response?: {
        status: number;
        data: any; // Replace 'any' with a more specific type if you know the structure of your error response
    };
}

function SignIn() {
    const idRef = useRef<HTMLInputElement | null>(null);
    const passwordRef = useRef<HTMLInputElement | null>(null);
    const [id, setId] = useState<string>('');
    const [password, setPassword] = useState<string>('');
    const [message, setMessage] = useState<string>('');
    const navigate = useNavigate();

    const onIdChangeHandler = (event: ChangeEvent<HTMLInputElement>) => {
        setId(event.target.value);
        setMessage('');
    };

    const onPasswordChangeHandler = (event: ChangeEvent<HTMLInputElement>) => {
        setPassword(event.target.value);
        setMessage('');
    };

    const onSignUpButtonClickHandler = () => {
        navigate('/auth/sign-up');
    };

    const processSignInResponse = (response: { statusCode: number; errorMessage: any; }) => {
        if (response.statusCode === 200) {
            // Assuming you would handle token storage here
            navigate(MAIN_PATH, { replace: true });
        } else {
            setMessage(response.errorMessage || 'An unexpected error occurred.');
        }
    };

    const onSignInButtonClickHandler = async () => {
        if (!id || !password) {
            alert('아이디와 비밀번호 모두 입력해주세요.');
            return;
        }

        try {
            const response = await axios.post('http://localhost:4040/api/v1/auth/sign-in', { id, password }, {
                withCredentials: true // Ensures cookies are sent with the request
            });

            processSignInResponse(response.data);
        } catch (error) {
            if (axios.isAxiosError(error) && error.response) {
                setMessage(error.response.data.errorMessage || 'An error occurred during the sign-in process.');
            } else {
                setMessage('An error occurred during the sign-in process.');
            }
        }
    };

    const onFormSubmitHandler = (event: FormEvent<HTMLFormElement>) => {
        event.preventDefault();
        onSignInButtonClickHandler();
    };

    const onIdKeyDownHandler = (event: KeyboardEvent<HTMLInputElement>) => {
        if (event.key === 'Enter' && passwordRef.current) {
            passwordRef.current.focus();
        }
    };

    const onPasswordKeyDownHandler = (event: KeyboardEvent<HTMLInputElement>) => {
        if (event.key === 'Enter') {
            onSignInButtonClickHandler();
        }
    };

    // OAuth 로그인 //
    const onSnsSignInButtonClickHandler = (type: 'kakao' | 'naver') => {
        window.location.href = SNS_SIGN_IN_URL(type);
    };


    return (
        <div id='sign-in-wrapper'>
            <form onSubmit={onFormSubmitHandler}>
                <div className='sign-in-box'>
                    <div className='sign-in-content-box'>
                        <div className='sign-in-content-input-box'>
                            <InputBox ref={idRef} title='아이디' placeholder='아이디를 입력해주세요.' type='text' value={id} onChange={onIdChangeHandler} onKeyDown={onIdKeyDownHandler} />
                            <InputBox ref={passwordRef} title='비밀번호' placeholder='비밀번호를 입력해주세요.' type='password' value={password} onChange={onPasswordChangeHandler} isErrorMessage message={message} onKeyDown={onPasswordKeyDownHandler}/>
                        </div>
                        <div className='sign-in-content-button-box'>
                            <button type='submit' className='primary-button-lg full-width' id='login-bar'>로그인</button>
                            <div className='text-link-lg-full-width' onClick={onSignUpButtonClickHandler}>회원가입 이동</div>
                        </div>
                        <div className='sign-in-content-divider'></div>
                        <div className='sign-in-content-sns-sign-in-box'>
                            <div className='sign-in-content-sns-sign-in-title'>SNS 로그인</div>
                            <div className='sign-in-content-sns-sign-in-button-box'>
                                <div className='kakao-sign-in-button' onClick={() => onSnsSignInButtonClickHandler('kakao')} ></div>
                                <div className='naver-sign-in-button' onClick={() => onSnsSignInButtonClickHandler('naver')} ></div>
                            </div>
                        </div>
                    </div>
                </div>
            </form>
        </div>
    );


}

export default SignIn;