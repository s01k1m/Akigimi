"use client";
import React, { useRef, useState, useEffect } from "react";
import Link from "next/link";
import axios from "axios";
import { redirect, useRouter } from "next/navigation";
import { BasicButton } from "@/components/login/BasicButton";

interface RegisterInfo {
  title: string;
  menual: string;
}

export default function Signup() {
  const [isLoading, setIsLoading] = useState<boolean>(true); // 페이지 로딩 중인지 확인하기 위하여
  const [nickname, setNickname] = useState<string>("");
  const [isUnique, setIsUnique] = useState<boolean>(false);
  const [isChecking, setIsChecking] = useState<boolean>(false); // 닉네임 체크 중인지 확인하기 위하여
  const inputRef = useRef<HTMLInputElement>(null);
  const [cnt, setCnt] = useState<number>(0);
  const router = useRouter();

  const token = window.localStorage.getItem("access_token");

  // handleClick() : 버튼 컴포넌트에 넘겨줄 함수 prop을 만듦
  const handleClick = async () => {
    router.push("/login/register/withdrawal");

    const formData = new FormData();
    formData.append("nickname", nickname); // 파일 첨부

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
  // duplicateCheck() : 닉네임 중복 검사 함수
  const duplicateCheck = async () => {
    setCnt(1); // 검색 경험 기록한다
    setIsChecking(true); // 중복 체크를 하고 있는 중이므로 true
    setIsUnique(false); // 유니크값 리셋한다
    await setNickname(nickname.trim()); // 사용자 입력값 앞뒤 공백을 모두 제거
    await axios
      .get(`/api/user/nickname/duplicate?nickname=${nickname}`)
      .then((response) => {
        // false 라면 유니크한 닉네임이다
        // true 라면 중복 닉네임이다
        console.log(response);
        setIsChecking(false);
        if (response.data.data) {
          // 중복 true이면
          alert("이미 존재하는 닉네임 입니다");
          setIsUnique(false);
        } else {
          // 중복 false라면
          setIsUnique(true);
        }
      });
  };

  // 닉네임 추천을 받고 최초로 닉네임 인풋칸에 띄어주어야한다
  const getRecommendation = async () => {
    console.log("닉네임 추천을 받습니다");
    await axios
      .get("/api/user/nickname/recommend", {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      })
      .then((response) => {
        setNickname(response.data.data.nickname);
      });
  };
  useEffect(() => {
    getRecommendation();
    setIsLoading(false);
  }, []);

  const onChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    setNickname(event.target.value);
  };

  return (
    <div className="h-full w-full">
      {isLoading ? (
        <div>로딩중</div>
      ) : (
        <div className="p-9 relative h-full">
          <div className="header text-[22px] pt-2 pb-[14px] font-bold">
            계정을 만들고 있어요
          </div>
          <div className="explanation text-base mb-[65px] text-grey0">
            필수 정보를 입력해주세요
          </div>
          <label htmlFor="nickname" className="font-semibold">
            닉네임
          </label>
          <div className="flex justify-around my-3">
            <input
              type="text"
              id="nickname"
              placeholder="닉네임을 입력해주세요"
              ref={inputRef}
              value={nickname}
              onChange={onChange}
              className="bg-[#F2F2F2] h-9 w-68 flex-1 p-2 rounded"
            ></input>
            <button
              onClick={(e) => {
                e.preventDefault();
                duplicateCheck();
              }}
              className={
                "rounded w-24 h-9 ms-3 flex-none text-white " +
                (isUnique ? "bg-gray-500" : "bg-tossblue")
              }
            >
              중복체크
            </button>
          </div>
          {cnt > 0 && isUnique ? ( // 검사해본 적 있고 유니크하면
            <div className="text-green-600">사용할 수 있는 닉네임입니다</div>
          ) : cnt == 0 ? ( // 한번도 중복 검사 해본 적 없으면
            <div className="text-red-500">닉네임 중복 검사를 해주세요</div>
          ) : isChecking ? ( // 중복 검사 진행 중이면?
            <div>중복 검사 중입니다</div>
          ) : (
            // 유니크하지 않은데 중복 검사를 해본 적 있으면
            <div id="nicknameComment">이미 있는 닉네임입니다</div>
          )}
          <div
            className="absolute inset-x-0 bottom-0 mb-8"
            onClick={(e) => {
              if (nickname && cnt && isUnique) {
              } else {
                e.preventDefault;
                if (!cnt) {
                  // 중복 검사를 해본 적 없으면
                  alert("닉네임 중복검사를 해주세요");
                } else {
                  // 중복 닉네임이라면
                  alert("중복되지 않는 닉네임을 작성해주세요");
                }
                return false;
              }
            }}
          >
            {cnt && isUnique ? (
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
