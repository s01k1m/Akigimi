"use client";
import React, { useRef, useState, useEffect } from "react";
import Link from "next/link";
import axios from "axios";
import { redirect, useRouter } from "next/navigation";

export default function Signup() {
  const [isLoading, setIsLoading] = useState<boolean>(true); // 페이지 로딩 중인지 확인하기 위하여
  const [nickname, setNickname] = useState<string>("");
  const [isUnique, setIsUnique] = useState<boolean>(false);
  const [isChecking, setIsChecking] = useState<boolean>(false); // 닉네임 체크 중인지 확인하기 위하여
  const inputRef = useRef<HTMLInputElement>(null);
  const router = useRouter();
  const [cnt, setCnt] = useState<number>(0);
  const duplicateCheck = async () => {
    const token = window.localStorage.getItem("access_token");
    setCnt(1); // 검색경험
    setIsChecking(true); // 체크 중 true
    console.log("닉네임 중복체크를 합니다");
    setIsUnique(false); // 유니크값 리셋
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
    const token = window.localStorage.getItem("access_token");
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
    <div className="">
      {isLoading ? (
        <div>로딩중</div>
      ) : (
        <>
          <div className="header text-[22px]">계정을 만들고 있어요</div>
          <div className="explanation text-base">필수 정보를 입력해주세요</div>
          <label htmlFor="nickname text-xs">닉네임</label>
          <div>
            <input
              type="text"
              id="nickname"
              placeholder="닉네임을 입력해주세요"
              ref={inputRef}
              value={nickname}
              onChange={onChange}
              className="bg-[#F2F2F2]"
            ></input>
            <button
              onClick={(e) => {
                e.preventDefault();
                duplicateCheck();
              }}
            >
              중복체크
            </button>
            {/* <button
              onClick={(e) => {
                e.preventDefault();
                router.push("/");
                // router.replace("/");
              }}
            >
              링크 푸쉬하기
            </button> */}
          </div>
          {cnt > 0 && isUnique ? ( // 검사해본 적 있고 유니크하면
            <div>사용할 수 있는 닉네임입니다</div>
          ) : cnt == 0 ? ( // 한번도 중복 검사 해본 적 없으면
            <div>닉네임 중복 검사를 해주세요</div>
          ) : setIsChecking ? ( // 중복 검사 진행 중이면?
            <div>중복 검사 중입니다</div>
          ) : (
            // 유니크하지 않은데 중복 검사를 해본 적 있으면
            <div id="nicknameComment">이미 있는 닉네임입니다</div>
          )}

          <button>NEXT</button>
          <Link href="/login">이미 계정을 소유하고 있으신가요?</Link>
        </>
      )}
    </div>
  );
}
