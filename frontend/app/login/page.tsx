"use client";
import { useEffect, useState } from "react";
import { Keypad, KeypadKeys } from "@/components/Login/Keypad";
import Circle from "@/components/Login/SimpleLoginCircle";
import axios from "axios";
import { redirect, useRouter } from "next/navigation";

export default function Login() {
  const [keypadEntries, setKeypadEntries] = useState<string>("");
  const [keypadArray, setKeypadArray] = useState<string[]>([]);
  const [tryLogin, setTryLogin] = useState<Boolean>(false);
  const router = useRouter();
  let token: string = "";
  
  useEffect(() => {
    token = window.localStorage.getItem("access_token");
  }, []);
  // 의문!! 여기서 토큰 찍히는데 함수 실행하면 토큰 못잡는다

  // getUserInfo() : 로그인 성공하면 유저정보를 get 할 함수
  const getUserInfo = async () => {
    console.log("유저정보 가져오기", token);
    token = window.localStorage.getItem("access_token");
    await axios
      .get("/api/user/info", {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      })
      .then((response) => {
        console.log(response.data);
        console.log("정보를 가져왔습니다..");
        // TODO: 유저 정보 저장 필요
        window.sessionStorage.setItem("nickname", response.data.data.nickname);
        window.sessionStorage.setItem(
          "profileImageUrl",
          response.data.data.profileImageUrl
        );
        window.sessionStorage.setItem("userId", response.data.data.userId);

        router.push("/main");
      })
      .catch((err) => {
        console.log(err);
      });
  };

  // handleKeypadKeyPress(keyPadKey) :키가 눌렸을 때 데이터 저장 처리하고 6자리 입력되면 로그인 처리하는 함수
  const handleKeypadKeyPress = (keyPadKey: KeypadKeys): void => {
    token = window.localStorage.getItem("access_token");
    let tempEntries: string[] = [];
    let newKeypadEntries: string[] = keypadArray;
    setKeypadArray(keypadArray);
    newKeypadEntries.push(keyPadKey.toString());
    setKeypadEntries(newKeypadEntries.join(""));

    if (newKeypadEntries.join("").length === 6) {
      console.log("로그인시도", token);
      // 백 데이터 저장 요청
      const doSimpleLogin = async () => {
        // 사용자 비밀번호를 formData에 담는다
        const formData = new FormData();
        formData.append("simplePassword", newKeypadEntries.join(""));
        await axios
          .post("/api/user/password/simple/check", formData, {
            headers: {
              Authorization: `Bearer ${token}`,
              "Content-Type": "multipart/form-data",
            },
          })
          .then((response) => {
            if (response.data.data.isPasswordCorrect === true) {
              console.log("간편로그인을 성공했습니다.. 비밀번호 맞음");
              // TODO: SESSION 유저 정보 저장 필요 (토큰 접근을 위해 코드 수정했습니다)
              console.log('유저 정보 가져오기 성공',response.data.data)
              window.localStorage.setItem("access_token", response.data.data.accessToken);
              window.sessionStorage.setItem("userId", response.data.data.userId);
            } else {
              alert("비밀번호 틀렸습니다. 다시 입력하세요");
            }
            getUserInfo();
            setTryLogin(false);
          })
          .catch(() => {
            setTryLogin(false);
            // TODO: 틀린 갯수를 5번 체킹하는 로직 해야함
            // +1 해서 5번이면 비활성화하기
            // 처음 컴포넌트 로딩할때도 비활성화된 계정인지 확인해야할 듯 함
            alert("비밀번호 다시 입력하세요");
          });
      };
      // 2. 키패드를 잠시 비활성화합니다.
      setTryLogin(true);
      console.log(newKeypadEntries.join(""));
      // 로그인 시작
      doSimpleLogin();
      // 1. 입력값을 비운다.
      newKeypadEntries = [];
      tempEntries = [];
      setKeypadEntries("");
      setKeypadArray([]);
    }
  };
  return (
    <div className="digit-login relative h-full w-full flex flex-col items-center">
      <div className="text-center font-bold mt-[30%] mb-[33px]">
        아끼미를 쓰려면
        <br />
        비밀번호를 눌러주세요
      </div>
      <div className="flex justify-center items-center">
        <Circle entered={keypadEntries[0] ? true : false} />
        <Circle entered={keypadEntries[1] ? true : false} />
        <Circle entered={keypadEntries[2] ? true : false} />
        <Circle entered={keypadEntries[3] ? true : false} />
        <Circle entered={keypadEntries[4] ? true : false} />
        <Circle entered={keypadEntries[5] ? true : false} />
      </div>
      <button className="text-[12px] text-gray0 bg-gray1 mt-[50px] py-2 mb-[10%] w-[120px] rounded">
        비밀번호를 몰라요
      </button>
      <div className="w-full h-full flex justify-center">
        <Keypad onKeyPressed={handleKeypadKeyPress} />
      </div>
      {tryLogin ? (
        <div
          style={{
            position: "absolute",
            top: 0,
            width: "100vw",
            height: "100vh",
            zIndex: 100,
            backgroundColor: "pink, 0.8",
            // display: "none",
          }}
        >
          로그인중
        </div>
      ) : null}
    </div>
  );
}
