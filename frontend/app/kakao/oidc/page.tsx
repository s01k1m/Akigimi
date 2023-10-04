"use client";
import axios from "axios";
import * as React from "react";
import { useEffect, useState } from "react";
import { useRouter } from "next/navigation";
import Image from "next/image";
import dynamic from "next/dynamic";

export default function Login() {
  // let id_token: string | null = "";
  let urlStr: string = "";
  // let url: URL = undefined;
  // let authorize_code: string = "";
  let id_token: string | null = "";
  // let urlStr: string = window.location.href;
  let url: URL | undefined = undefined; // <---- and this line
  // const [authorize_code, setAuthorize_code] = useState<string>("");
  let authorize_code: string = "";
  // token =  window.localStorage.getItem("access_token");

  let token: string = "";
  let redirect_uri: string = "";
  const app_key = process.env.NEXT_PUBLIC_KAKAO_CLIENT_ID;
  const router = useRouter();
  let token_request_url: string = "";

  const forLogin = async () => {
    token = window.localStorage.getItem("access_token");
    urlStr = window.location.href;
    url = new URL(urlStr); // <---- and this line
    authorize_code = url.searchParams.get("code");

    //로컬호스트와 배포 주소에 따라서 requset_url을 동적으로 변환
    // let urlOrigin = url.origin;
    // redirect_uri = `${urlOrigin}/kakao/oidc`;

    // 백엔드에서 로직 처리해서 로컬스토리지에 저장된 리다이렉트 url 가져오기
      redirect_uri = window.localStorage.getItem('redirect_url')
    };  
    console.log('리다이렉트 url은?', redirect_uri)
    // console.log('리다이렉트 url은?', redirect_uri)

  


  // 1. 카카오 로그인 성공한 유저를 리다이렉트된 주소에서 코드를 파싱한다
  // 2. authorize_code 이걸 token_request_url 카카오 서버로 보낸다. 그러면 카카오로부터 이 유저의 id_token을 받아올 수 있다.

  // getLoginURL : 카카오 서버로부터 id_token을 가져온다
  const getLoginURL = async () => {
    await axios
      .get(token_request_url)
      .then((response) => {
        id_token = response.data.id_token;
        window.localStorage.setItem("id_token", response.data.id_token); // pending active인지 체킹해야하는 access token 임
      })
      .then(() => {
        // 3. it_token으로 우리 유저인지 DB 확인하러 갑니다
        verifyOurUser();
        console.log("우리 회원 맞아요?");
      })
      .catch((error) => {
        console.error("getLoginURL HTTP 요청 중 오류 발생:", error);
      });
  };

  const verifyOurUser = async () => {
    let data = {
      idToken: id_token,
    };
    await axios
      .post("/api/kakao/login", JSON.stringify(data), {
        // json을 json타입의 text로 변환
        headers: {
          "Content-Type": `application/json`, // application/json 타입 선언
        },
      })
      .then((response) => {
        const access_token: string = response.data.data.accessToken;
        const refresh_token: string = response.data.data.refreshToken;
        window.localStorage.setItem("access_token", access_token);
        window.localStorage.setItem("refresh_token", refresh_token);
        console.log("우리 회원 맞아요");
        console.log('우리 회원이 맞는경우의 응답', response)
        // TODO: 회원이므로 6자리 로그인으로 페이지 전환해야됨
        window.sessionStorage.setItem("userId", response.data.userId); // 세션스토리지에 유저아이디 저장
        if (response.data.data.userState === "PENDING") {
          // pending 인 경우 닉네임 페이지로 이동시키기
          // TODO: 사용자가 어떤 값이 부족 한지 판단 한 다음 경우에 따라 router를 분류하는 작업이 필요합니다
          // 회원가입을 진행하는 중간에 종료 될 수 있기 때문.
          router.replace("/login/register");
        } else {
          // 임시
          router.replace("/login"); // 이거 중간평가용 임시임 나중에 6자리 로그인으로 변경해야함
        }
      })
      .then(() => {
        // router.replace("/login");
        // router.replace("/main"); // 이거 중간평가용 임시임 나중에 6자리 로그인으로 변경해야함
      })
      .catch((error) => {
        // 회원이 아니면 400 Bad Request를 반환하므로 회원가입으로 페이지 전환해야됨
        getSignup();
        console.log("회원이 아니래요");
      });
  };

  // getSignup: 새 유저에게 JWT 토큰을 발급합니다
  const getSignup = async () => {
    let data = {
      idToken: id_token,
    };

    await axios
      .post("/api/kakao/signup", JSON.stringify(data), {
        // json을 json타입의 text로 변환
        headers: {
          "Content-Type": `application/json`, // application/json 타입 선언
        },
      })
      .then((response) => {
        const access_token: string = response.data.data.accessToken;
        const refresh_token: string = response.data.data.refreshToken;
        window.localStorage.setItem("access_token", access_token);
        window.localStorage.setItem("refresh_token", refresh_token);
        console.log("JWT 발급해줄게요");
      })
      .then(() => {
        router.replace("/login/register");
      })
      .catch((error) => {
        console.error("getSignup HTTP 요청 중 오류 발생:", error);
      });
  };

  useEffect(() => {
    console.log("순서1");
    console.log('리다이렉트 url', redirect_uri)
    if (window) {
      forLogin()
        .then(() => {
          
          token_request_url = `https://kauth.kakao.com/oauth/token?grant_type=authorization_code&client_id=5d06715a9e4afbca55173788a79e3674&redirect_uri=${redirect_uri}&code=${authorize_code}`;
        })
        .then(() => {
          getLoginURL();
        });
    }
  }, []);

  return (
    <div className="h-full w-full flex flex-col justify-center">
      <div className="text-center">로그인 중입니다.기다려주세요</div>
      <Image
        src="/images/danceJokerbear.gif"
        alt="로딩 이미지"
        width={100} // 실제 이미지의 가로 크기로 설정하세요
        height={100} // 실제 이미지의 세로 크기로 설정하세요
        layout="responsive" // 이미지 크기를 유지하도록 설정
        // className="w-full h-auto height-auto"
        priority
      ></Image>
    </div>
  );
}
