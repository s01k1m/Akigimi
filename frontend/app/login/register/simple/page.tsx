"use client";
import { useEffect, useState } from "react";
import { Keypad, KeypadKeys } from "@/components/login/Keypad";
import Circle from "@/components/login/SimpleLoginCircle";
import axios from "axios";
export default function Login() {
  const [keypadEntries, setKeypadEntries] = useState<string>("");
  const [keypadArray, setKeypadArray] = useState<string[]>([]);
  const [tryLogin, setTryLogin] = useState<Boolean>(false);
  // 키가 눌렸을 때 데이터 저장 처리
  const handleKeypadKeyPress = (keyPadKey: KeypadKeys): void => {
    let tempEntries: string[] = [];
    let newKeypadEntries: string[] = keypadArray;
    setKeypadArray(keypadArray);
    newKeypadEntries.push(keyPadKey.toString());
    setKeypadEntries(newKeypadEntries.join(""));
    // Array to String Without Commas in JavaScript
    // console.log("모드리치", newKeypadEntries.join(""));
    // console.log(newKeypadEntries);
    if (newKeypadEntries.join("").length === 6) {
      let token = window.localStorage.getItem("access_token");
      // 백 데이터 저장 요청
      const registerSimplePassword = async () => {
        console.log("데이터보내기 시작");
        const formData = new FormData();
        formData.append("simplePassword", newKeypadEntries.join(""));
        console.log(formData);
        await axios
          .post("/api/user/password/simple", formData, {
            headers: {
              Authorization: `Bearer ${token}`,
              "Content-Type": "multipart/form-data",
            },
          })
          .then((response) => {
            console.log(response);
            console.log("간편로그인 비밀번호가 저장되었습니다.");
            setTryLogin(false);
          })
          .catch(() => {
            setTryLogin(false);
          });
      };
      // 1. 입력값을 비운다.
      // 2. 키패드를 잠시 비활성화합니다.
      setTryLogin(true);
      console.log(newKeypadEntries.join(""));

      registerSimplePassword();
      newKeypadEntries = [];
      tempEntries = [];
      setKeypadEntries("");
      setKeypadArray([]);
    }
  };

  return (
    <div className="digit-login relative h-full w-full flex flex-col items-center">
      <div className="text-center font-bold mt-[30%] mb-[33px]">
        당신의
        <br />
        비밀번호를 만들어 주세요
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
