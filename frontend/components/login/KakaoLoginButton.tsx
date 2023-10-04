"use client";
import * as React from "react";
import styled from "styled-components";
import { useEffect, useState } from "react";
import Image from "next/image";
import Link from "next/link";
import axios from "axios";

interface URL {
  datas: {
    message: any;
    data: any;
  };
}
let loginUrl = "";
const KakaoLoginButton = () => {
  const [isLoading, setIsLoading] = useState<boolean>(true);

  const getLoginURL = async () => {
    await axios
      .get("/api/kakao/loginurl")
      .then((response) => {
        loginUrl = response.data.data;
        setIsLoading(false);
        // console.log('로그인 성공 했을 때', response.data)
      })
      .catch((error) => {
        console.error("HTTP 요청 중 오류 발생:", error);
      });
  };
  useEffect(() => {
    getLoginURL();
  }, []);

  return (
    <Wrapper>
      {isLoading ? (
        <Button.Container>
          <Button.ButtonList>
            <Button.KakaoButton className="animate-pulse ">
              <Image
                src="/images/kakao logo.png"
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
              <Button.ButtonText></Button.ButtonText>
            </Button.KakaoButton>
          </Button.ButtonList>
        </Button.Container>
      ) : (
        <Button.Container>
          <Button.ButtonList>
            <Link href={loginUrl}>
              {/* <Button.KakaoButton onClick={kakaoLogin}> */}
              <Button.KakaoButton>
                <Image
                  src="/images/kakao logo.png"
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
            </Link>
          </Button.ButtonList>
        </Button.Container>
      )}
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
