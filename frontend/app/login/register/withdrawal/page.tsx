"use client";
import React, { useRef, useState, useEffect } from "react";
import axios from "axios";
import { redirect, useRouter } from "next/navigation";
import { BasicButton } from "@/components/login/BasicButton";

export interface RegisterInfo {
  title: string;
  menual: string;
}

export default function Signup() {
  const [isLoading, setIsLoading] = useState<boolean>(false); // 페이지 로딩 중인지 확인하기 위하여
  const [password1, setPassword1] = useState<string>("");
  const [didMatch, setDidMatch] = useState<boolean>(false);
  const inputRef = useRef<HTMLInputElement>(null);

  const router = useRouter();
  const [content, setContent] = useState<RegisterInfo[] | undefined>([]);
  const token = window.localStorage.getItem("access_token");
  const info = {
    title: "출금계좌를 연결하고 있어요!",
    menual: "필수 정보를 입력해주세요",
  };

  // TODO: 비밀번호 암호화
  // TODO: 비밀번호1 비밀번호2 같은지 확인 로직
  // TODO: 폼 컴포넌트화등 리팩토링에 대한 고민

  const handleClick = async () => {
    router.push("/login/register/deposit");

    const formData = new FormData();
    formData.append("password", password1); // 파일 첨부

    await axios
      .post("/api/user/nickname", formData, {
        headers: {
          Authorization: `Bearer ${token}`,
          "Content-Type": "multipart/form-data",
        },
      })
      .then((response) => {
        console.log(response);
      });
  };
  return (
    <div className="h-full w-full">
      {isLoading ? (
        <div>로딩중</div>
      ) : (
        <div className="p-9 relative h-full">
          <div className="header text-[22px] pt-2 pb-[14px] font-bold">
            {info.title}
          </div>
          <div className="explanation text-base mb-[65px] text-grey0">
            {info.menual}
          </div>
          <label htmlFor="password1" className="font-semibold">
            출금계좌 비밀번호
          </label>
          <div className="flex justify-around my-3">
            <input
              type="password"
              id="pass"
              name="password1"
              required={true}
              minLength={4}
              maxLength={4}
              value={password1}
              onChange={(e) => {
                console.log(e.target.value);
                setPassword1(e.target.value);
              }}
              className="bg-[#F2F2F2] h-9 w-68 flex-1 p-2 rounded"
            ></input>
          </div>

          <div
            className="absolute inset-x-0 bottom-0 mb-8"
            onClick={(e) => {
              if (!didMatch) {
                e.preventDefault;
                alert("출금 비밀번호를 제대로 입력해주세요");
                return false;
              }
            }}
          >
            {didMatch ? (
              <BasicButton
                msg={"NEXT"}
                handleClick={() => handleClick()}
              ></BasicButton>
            ) : (
              <BasicButton msg={"NEXT"} handleClick={() => {}}></BasicButton>
            )}
          </div>
        </div>
      )}
    </div>
  );
}
