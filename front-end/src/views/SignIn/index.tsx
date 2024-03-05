import './style.css';
import {useState} from "react";
import {useNavigate} from "react-router-dom";
import axiosInstance from "../../api/axios";
import axios from "axios";
import {useCookies} from "react-cookie";

export default function SignIn(){
    const [id, setId] = useState('');
    const [password, setPassword] = useState('');
    const [errorMessage, setErrorMessage] = useState('');
    const navigate = useNavigate();

    const [cookies, setCookie] = useCookies(['accessToken', 'refreshToken']);

    const handleSignIn = async (e: React.FormEvent<HTMLFormElement>) => {
        e.preventDefault(); // Prevent the form from submitting in the traditional way

        try {
            const response = await axiosInstance.post('/api/v1/auth/sign-in', {
                id,
                password,
            },
                {
                    withCredentials: true
                }

            );

            if (response.status === 200) {
                // Extract tokens
                const { accessToken, refreshToken, expirationTime } = response.data.data;

                // Set tokens in cookies
                // You might want to specify path, domain, secure (for https) and sameSite (for CSRF protection) based on your requirements
                setCookie('accessToken', accessToken, { path: '/', maxAge: expirationTime, secure: true, sameSite: 'none' });
                setCookie('refreshToken', refreshToken, { path: '/', maxAge: 60 * 60 * 24 * 7, secure: true, sameSite: 'none' });
                console.log('Cookies after setting:', cookies);


                // Sign-in was successful, navigate to the home page
                navigate('/');
            }
        } catch (error: any) {
            if (axios.isAxiosError(error) && error.response) {
                // Display an error message from the server, if available
                setErrorMessage(error.response.data.errorMessage);
            } else {
                // General error handling
                console.error('An error occurred:', error);
                setErrorMessage('An unexpected error occurred.');
            }
        }
    };




    // OAuth 로그인 //
    const DOMAIN = 'https://knowledge-sharing-two.vercel.app';
    const API_DOMAIN = `${DOMAIN}/api/v1`;
    const SNS_SIGN_IN_URL = (type: 'kakao' | 'naver') => `${API_DOMAIN}/auth/oauth2/${type}`;
    const onSnsSignInButtonClickHandler = (type: 'kakao' | 'naver') => {
        window.location.href = SNS_SIGN_IN_URL(type);
    };


    return(
        <div>

            <div className="mx-auto max-w-sm space-y-6">
                <form onSubmit={handleSignIn} >
                <div className="space-y-4" >
                    <div className="space-y-2 text-center">
                        <h1 className="text-3xl font-bold mt-40">
                            로그인
                        </h1>
                        <p className="text-gray-500 dark:text-gray-400">
                            로그인 혹은 소셜로그인을하세요
                        </p>
                    </div>
                    
                    <div className="space-y-2">
                        <label className="text-sm font-medium leading-none peer-disabled:cursor-not-allowed peer-disabled:opacity-70" htmlFor="id">
                        id
                      </label>
                        <div className="flex items-start space-x-2">
                            <input type="text" id="id" required
                                   className="flex h-10 w-full rounded-md border border-input bg-background px-3 py-2 text-sm ring-offset-background file:border-0 file:bg-transparent file:text-sm file:font-medium placeholder:text-muted-foreground focus-visible:outline-none focus-visible:ring-2 focus-visible:ring-ring focus-visible:ring-offset-2 disabled:cursor-not-allowed disabled:opacity-50"
                                   placeholder="아이디를 입력하세요"
                                   value={id}
                                   onChange={(e) => setId(e.target.value)} />
                        </div>
                    </div>
                    
                    
                    
                    
                    
                    <div className="space-y-2"><label className="text-sm font-medium leading-none peer-disabled:cursor-not-allowed peer-disabled:opacity-70" htmlFor="password">
                        Password
                    </label>
                        <input type="password" id="password" required
                               placeholder="비밀번호"
                               autoComplete="password"

                               className="flex h-10 w-full rounded-md border border-input bg-background px-3 py-2 text-sm ring-offset-background file:border-0 file:bg-transparent file:text-sm file:font-medium placeholder:text-muted-foreground focus-visible:outline-none focus-visible:ring-2 focus-visible:ring-ring focus-visible:ring-offset-2 disabled:cursor-not-allowed disabled:opacity-50"
                               onChange={(e) => setPassword(e.target.value)} />

                    </div>

                    
                    
                    
                      <button className="inline-flex items-center justify-center whitespace-nowrap rounded-md text-sm font-medium ring-offset-background transition-colors focus-visible:outline-none focus-visible:ring-2 focus-visible:ring-ring focus-visible:ring-offset-2 disabled:pointer-events-none disabled:opacity-50 bg-primary text-primary-foreground hover:bg-primary/90 h-10 px-4 py-2 w-full">
                      Sign Up
                      </button>
                    {errorMessage && (
                        <p className="inline-flex items-center justify-center whitespace-nowrap rounded-md text-sm font-medium ring-offset-background transition-colors focus-visible:outline-none focus-visible:ring-2 focus-visible:ring-ring focus-visible:ring-offset-2 disabled:pointer-events-none disabled:opacity-50 bg-primary text-red-500 text-xs hover:bg-primary/90 h-10 px-4 py-2 w-full">{errorMessage}</p>
                    )}
                </div>
                </form>
            </div>
            <div className='sign-in-content-sns-sign-in-box'>
                <div className='sign-in-content-sns-sign-in-title'>SNS 로그인</div>
                <div className='sign-in-content-sns-sign-in-button-box'>
                    <div className='kakao-sign-in-button' onClick={() => onSnsSignInButtonClickHandler('kakao')}></div>
                    <div className='naver-sign-in-button'  onClick={() => onSnsSignInButtonClickHandler('naver')} ></div>
                </div>
            </div>
        </div>
    )
}


