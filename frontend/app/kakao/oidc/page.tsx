"use client";
import axios from "axios";
import * as React from "react";
import { useEffect, useState } from "react";
import { useRouter } from "next/navigation";
import Image from "next/image";
import dynamic from "next/dynamic";

export default function Login() {
  const [isReady1, setIsReady1] = useState<boolean>(false);
  const [isReady2, setIsReady2] = useState<boolean>(false);
  // let id_token: string | null = "";
  let urlStr: string = "";
  // let url: URL = undefined;
  // let authorize_code: string = "";
  let id_token: string | null = "";
  // let urlStr: string = window.location.href;
  let url: URL | undefined = undefined; // <---- and this line
  const [authorize_code, setAuthorize_code] = useState<string>("");
  // token =  window.localStorage.getItem("access_token");

  let token: string = "";
  const redirect_uri = "http://localhost:3000/kakao/oidc";
  const app_key = process.env.NEXT_PUBLIC_KAKAO_CLIENT_ID;
  const router = useRouter();
  let token_request_url: string = `https://kauth.kakao.com/oauth/token?grant_type=authorization_code&client_id=5d06715a9e4afbca55173788a79e3674&redirect_uri=${redirect_uri}&code=${authorize_code}`;

  const forLogin = async () => {
    token = window.localStorage.getItem("access_token");
    urlStr = window.location.href;
    url = new URL(urlStr); // <---- and this line
    setAuthorize_code(url.searchParams.get("code"));
    console.log("/", urlStr, "루카", url);
  };

  // 1. 카카오 로그인 성공한 유저를 리다이렉트된 주소에서 코드를 파싱한다
  // 2. authorize_code 이걸 token_request_url 카카오 서버로 보낸다. 그러면 카카오로부터 이 유저의 id_token을 받아올 수 있다.

  // getLoginURL : 카카오 서버로부터 id_token을 가져온다
  const getLoginURL = async () => {
    await axios
      .get(token_request_url)
      .then((response) => {
        console.log(response);
        id_token = response.data.id_token;
        window.localStorage.setItem("id_token", response.data.id_token);
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
        // TODO: 회원이므로 6자리 로그인으로 페이지 전환해야됨
        console.log(response.data.data.userState);
        if (response.data.data.userState === "PENDING") {
          router.replace("/login/register/withdraw");
          console.log(typeof response.data.data.userState);
        } else {
          router.replace("/main"); // 이거 중간평가용 임시임 나중에 6자리 로그인으로 변경해야함
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
    setIsReady1(true);
    if (window) {
      forLogin().then(() => {
        console.log("forLogin 변수들 저장함");
        setIsReady2(true);
      });
    }
  }, []);

  useEffect(() => {
    console.log("순서3");
    if (typeof window !== "undefined") {
      getLoginURL();
      console.log("모드리치", token_request_url);
      console.log("루카", authorize_code);
      console.log("GOAT");
    }
  }, [authorize_code]);

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
