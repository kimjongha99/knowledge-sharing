import React, { useEffect } from 'react';
import { useCookies } from 'react-cookie';
import { useNavigate, useParams } from 'react-router-dom';

export default function OAuth() {
  const { accessToken, refreshToken } = useParams();

  const [cookie, setCookies] = useCookies(['accessToken','refreshToken']);
  const navigate = useNavigate();

  useEffect(() => {
    // oauth 토큰 전달해주기 //
    if (!accessToken || !refreshToken) return; // 유효하지 않은 토큰 정보는 처리하지 않습니다.


    // 액세스 토큰 쿠키 설정
    setCookies('accessToken', accessToken, {
      path: '/',
      maxAge: 3600,
      secure: true, // HTTPS를 통해서만 쿠키를 전송합니다.
      sameSite: 'none' // 쿠키가 모든 컨텍스트에서 전송되어야 함을 나타냅니다. 크로스 사이트 요청에 사용될 수 있습니다.
    });


    // 리프레시 토큰 쿠키 설정
    // 리프레시 토큰의 만료 시간은 일반적으로 서버 측에서 결정됩니다.
    // 여기서는 예시로 7일 후로 설정합니다. 실제 애플리케이션에서는 서버에서 받은 만료 시간을 사용하세요.
    setCookies('refreshToken', refreshToken, {
      path: '/',
      maxAge: 60 * 60 * 24 * 7, // 7일을 초 단위로 설정합니다.
      secure: true,
      sameSite: 'none'
    });

    navigate('/'); // 설정이 끝나면 홈페이지로 리다이렉트합니다.
  }, [accessToken, navigate, setCookies]); // 의존성 배열에 포함된 변수들이 변경될 때마다 이펙트 실행

  return (
    <>
    </>
  )
}
