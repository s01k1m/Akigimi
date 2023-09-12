"use client";
import * as React from "react";
import styled from "styled-components";
import Router from "next/router";
import { kakaoInit } from "@/utils/kakaoinit";
import Image from "next/image";

const KakaoLoginButton = () => {
  const kakaoLogin = async () => {
    // 카카오 초기화

    const kakao = kakaoInit();
    // 카카오 로그인 구현
    kakao.Auth.login({
      success: () => {
        kakao.API.request({
          url: "/v2/user/me", // 사용자 정보 가져오기
          success: (res: any) => {
            // 로그인 성공할 경우 정보 확인 후 /kakao 페이지로 push
            console.log(res);
            // Router.push("/kakao");
          },
          fail: (error: any) => {
            console.log(error);
          },
        });
      },
      fail: (error: any) => {
        console.log(error);
      },
    });
  };

  return (
    <Wrapper>
      <Button.Container>
        <Button.ButtonList>
          <Button.KakaoButton onClick={kakaoLogin}>
            <Image
              src="/kakao logo.png"
              alt="카카오 로고 사진"
              width="20"
              height="20"
              style={{
                display: "inline-block",
                position: "absolute",
                left: 14,
                margin: "3px 0",
                height: "auto",
              }}
            ></Image>
            <Button.ButtonText>카카오 로그인</Button.ButtonText>
          </Button.KakaoButton>
        </Button.ButtonList>
      </Button.Container>
    </Wrapper>
  );
};

export default KakaoLoginButton;

const Wrapper = styled.div`
  max-width: 720px;

  margin: 0 auto;
`;

const Header = {
  Container: styled.div`
    text-align: center;
  `,

  Title: styled.h2``,
};

const Button = {
  Container: styled.div``,

  ButtonList: styled.div`
    display: flex;
    flex-direction: column;
    align-items: center;
  `,

  KakaoButton: styled.button`
    background-color: #fef01b;
    position: relative;
    width: 300px;
    height: 45px;

    margin: 6px 0;

    border: none;
    border-radius: 6px;

    cursor: pointer;
  `,

  ButtonText: styled.h4`
    margin: 0;
    padding: 0;
    display: inline-block;
    font-size: 18px;
    color: #000000d9;
  `,
};
