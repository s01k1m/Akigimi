"use client";
import axios from "axios";
import * as React from "react";
import { useEffect } from "react";
import { redirect } from "next/navigation";

let id_token: string | null = "";

const urlStr = window.location.href;
const url = new URL(urlStr);
const authorize_code = url.searchParams.get("code");

const redirect_uri = "http://localhost:3000/kakao/oidc";
const app_key = process.env.NEXT_PUBLIC_KAKAO_CLIENT_ID;
const token_request_url = `https://kauth.kakao.com/oauth/token?grant_type=authorization_code&client_id=${app_key}&redirect_uri=${redirect_uri}&code=${authorize_code}`;
export default function Login() {
  // 1. 카카오 로그인 성공한 유저를 리다이렉트된 주소에서 코드를 파싱한다
  // 2. authorize_code 이걸 token_request_url 카카오 서버로 보낸다. 그러면 카카오로부터 이 유저의 id_token을 받아올 수 있다.

  // getLoginURL : 카카오 서버로부터 id_token을 가져온다
  const getLoginURL = async () => {
    await axios
      .get(token_request_url)
      .then((response) => {
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
        // redirect("/login/register");
      })
      .catch((error) => {
        console.error("getSignup HTTP 요청 중 오류 발생:", error);
      });
  };

  // const getExtraInfo = async () => {
  //   // header에 Authorization Bearer {{ACCESS_TOKEN}}
  // };
  useEffect(() => {
    getLoginURL();
  }, [token_request_url]);
  return <div>로그인 중입니다.</div>;
}
