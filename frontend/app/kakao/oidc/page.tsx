"use client";
import axios from "axios";
import * as React from "react";
import { useEffect } from "react";
const access_token = "";
const id_token = "";
export default function Login() {
  const urlStr = window.location.href;
  const url = new URL(urlStr);
  const authorize_code = url.searchParams.get("code"); // 카카오 로그인 성공한 유저를 리다이렉트된 주소에서 코드를 파싱한다
  console.log(authorize_code); // authorize_code 이걸 카카오 서버로 보낸다

  const redirect_uri = "http://localhost:3000/kakao/oidc";

  const app_key = process.env.NEXT_PUBLIC_KAKAO_CLIENT_ID;

  const token_request_url = `https://kauth.kakao.com/oauth/token?grant_type=authorization_code&client_id=${app_key}&redirect_uri=${redirect_uri}&code=${authorize_code}`;
  console.log(token_request_url);

  const getLoginURL = async () => {
    await axios
      .get(token_request_url)
      .then((response) => {
        console.log("모드리치모드리치");
        console.log(response);
      })
      .then(() => {
        console.log(loginUrl);
      })
      .catch((error) => {
        console.error("HTTP 요청 중 오류 발생:", error);
      });
  };
  if (token_request_url) {
    getLoginURL();
  }

  return <div>로그인 중입니다.</div>;
}
