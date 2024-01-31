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


function SignIn(){

    // 레퍼런스 객체
    const idRef = useRef<HTMLInputElement | null>(null);
    const passwordRef = useRef<HTMLInputElement | null>(null);

    const [cookie, setCookies] = useCookies();
    // 상태값
    const [id, setId] = useState<string>('');
    const [password, setPassword] = useState<string>('');

    const [message, setMessage] = useState<string>('');

    // navigate //
    const navigate = useNavigate();



    const signInResponse = async (responseBody: ResponseBody<SignInResponseDto>) => {
        if (!responseBody) return;

        const { code } = responseBody;
        if (code === ResponseCode.VALIDATION_FAIL) {
            alert('아이디와 비밀번호를 다시입력하세요.');
            return;
        }
        if (code === ResponseCode.SIGN_IN_FAIL) {
            setMessage('로그인정보가 일치하지않습니다..');
            return;
        }
        if (code === ResponseCode.DATABASE_ERROR) {
            alert('Database error.');
            return;
        }
        if (code !== ResponseCode.SUCCESS) return;

        const { accessToken, refreshToken } = responseBody as SignInResponseDto;

        const oneHourInSeconds = 3600; // 1 hour in seconds
        const sevenDaysInSeconds = 7 * 24 * 3600; // 7 days in seconds
        setCookies('accessToken', accessToken, { maxAge: oneHourInSeconds, path: '/' });
        setCookies('refreshToken', refreshToken, { maxAge: sevenDaysInSeconds, path: '/' });

        navigate(MAIN_PATH); // Navigate only after a successful login
    };





    // onChange
    const onIdChangeHandler = (event:ChangeEvent<HTMLInputElement>) => {
        const { value } = event.target;
        setId(value);
        setMessage('');
    };
    const onPasswordChangeHandler = (event:ChangeEvent<HTMLInputElement>) => {
        const { value } = event.target;
        setPassword(value);
        setMessage('');
    };

    const onSignUpButtonClickHandler = () => {
        navigate('/auth/sign-up');
    };
    const onSignInButtonClickHandler = () => {
        if(!id || !password) {
            alert('아이디와 비밀번호 모두 입력해주세요.');
            return;
        }
        const requestBody: SignInRequestDto = { id, password };
        signInRequest(requestBody)
            .then(signInResponse)
            .catch((error) => {
                // Handle error case here if needed
            });
        // Removed navigate(MAIN_PATH) from here to ensure it only happens after successful login
    };

    const onFormSubmitHandler = (event: FormEvent<HTMLFormElement>) => {
        event.preventDefault(); // Prevent the default form submission
        onSignInButtonClickHandler(); // Call your sign in handler
    };

    // key down
    const onIdKeyDownHandler = (event: KeyboardEvent<HTMLInputElement>) => {
        if(event.key !== 'Enter') return;
        if(!passwordRef.current) return;
        passwordRef.current.focus();
    };
    const onPasswordKeyDownHandler = (event: KeyboardEvent<HTMLInputElement>) => {
        if(event.key !== 'Enter') return;
        onSignInButtonClickHandler();
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