"use client";
import React, { useRef, useState, useEffect, useCallback } from "react";
import axios from "axios";
import { redirect, useRouter } from "next/navigation";
import { BasicButton } from "@/components/login/BasicButton";
import Image from "next/image";
import styled from "styled-components";

export interface RegisterInfo {
  title: string;
  menual: string;
}

interface Bank {
  id: number;
  name: string;
  logo: string;
}

type BankList = Bank[];

const bankList: BankList = [
  { id: 0, name: "싸피은행", logo: "/ssafy logo.webp" },
  { id: 1, name: "멀티은행", logo: "/kakaobank logo.png" },
];

const AccountWrapper = styled.div`
  position: relative;
  cursor: pointer;
`;

const AccountNum = styled.div`
  z-index: 9;
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  width: 236px;
  text-align: center;
`;

export default function Signup() {
  const [isLoading, setIsLoading] = useState<boolean>(false); // 페이지 로딩 중인지 확인하기 위하여
  const [password1, setPassword1] = useState<string>("");
  const [password2, setPassword2] = useState<string>(undefined);
  const [didMatch, setDidMatch] = useState<boolean>(false);
  const inputRef = useRef<HTMLInputElement>(null);
  const [accountNum, setAccountNum] = useState<string>("0"); // 계좌 번호
  // let accountNum: number = 0;
  const router = useRouter();
  const [content, setContent] = useState<RegisterInfo[] | undefined>([]);
  const [selected, setSelected] = useState<string>("0");
  let token = "";

  useEffect(() => {
    if (typeof window !== "undefined") {
      token = window.localStorage.getItem("access_token");
    }
  }, []);

  const info = {
    title: "출금계좌를 연결하고 있어요!",
    menual: "필수 정보를 입력해주세요",
  };
  const [accountNumArr, setAccountNumArr] = useState<Array<string>>([]); // 계좌번호를 ui를 위해 4자리씩 끊어서 Array로 저장

  const calculateAccount = () => {
    const newAccountNumArr = [];
    const lengthOfAccountNum = String(accountNum).length;

    const chunck: number = 4; // 4자리씩 자르기
    for (let index = 0; index < accountNum.length; index += chunck) {
      console.log("모드리치가 계좌를 연산합니다");
      let temp;
      temp = accountNum.slice(index, index + chunck);
      newAccountNumArr.push(temp);
    }
    setAccountNumArr(newAccountNumArr); // 상태를 업데이트
  };

  // TODO: 폼 컴포넌트화등 리팩토링에 대한 고민

  const storeAccount = (e: React.ChangeEvent<HTMLInputElement>) => {
    const inputValue: string = e.target.value;
    const isNumber: boolean = /^[0-9]*$/.test(inputValue);
    if (isNumber) {
      setAccountNum(inputValue);
    } else {
      // 입력이 숫자가 아닌 경우 처리
      e.preventDefault();
      alert("숫자를 입력하세요");
    }
  };

  const test = () => {
    if (password1 != password2) {
      setDidMatch(false);
    } else {
      setDidMatch(true);
    }
  };

  useEffect(() => {
    calculateAccount();
  }, [accountNum, selected]);

  useEffect(() => {
    test();
    console.log(didMatch);
  }, [password2]);

  useEffect(() => {
    getFakeAccount();
  }, []);

  // 백에서 데이터 가져오기
  const getFakeAccount = async () => {
    const formData = new FormData();
    formData.append("accountType", "DEPOSIT");
    formData.append("bank", "SSAFY");

    await axios
      .post("/api/account/new", formData, {
        headers: {
          Authorization: `Bearer ${token}`,
          "Content-Type": "application/json",
        },
      })
      .then((response) => {
        console.log(response);
        setAccountNum(response.data.data.accountNumber);
      });
  };
  // 버튼에 전달할 함수
  // TODO: 백 아직 완성 안되서 이거 완성되면 연결할 것
  const handleClick = async () => {
    router.push("/api/account/new/password");

    const formData = new FormData();
    formData.append("bank", password1); // 파일 첨부

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

          <div className="bg-gray1 p-8 rounded flex flex-wrap  justify-center items-center shadow-xl mb-14">
            <div className="w-full flex justify-start mb-3 ">
              <select
                className="rounded-full w-40 ps-4 pl-6 py-2"
                onChange={(e: React.ChangeEvent<HTMLSelectElement>) => {
                  setSelected(e.target.value);
                }}
                value={selected}
              >
                {bankList.map((value, index) => {
                  var selected = value.id === selected ? "selected" : "false";
                  return (
                    <option key={index} value={value.id} selected={selected}>
                      {value.name}
                    </option>
                  );
                })}
              </select>
            </div>

            <AccountWrapper className="flex justify-center">
              <AccountNum
                // htmlFor="withdrawalAccount"
                onClick={() => {
                  console.log("클릭");
                }}
              >
                {accountNumArr.length > 0 ? (
                  <div>
                    {accountNumArr.map((account, index) => (
                      <span key={index}>
                        {account}
                        {index === accountNumArr.length - 1 ? "" : " - "}
                      </span>
                    ))}
                  </div>
                ) : (
                  <div></div>
                )}
              </AccountNum>
              <input
                id="withdrawalAccount"
                className="bg-white p-2 m-4 text-white"
                onChange={(e: React.ChangeEvent<HTMLInputElement>) =>
                  storeAccount(e)
                }
                maxLength={16}
                // disabled
              ></input>
            </AccountWrapper>
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
              onChange={(e: React.ChangeEvent<HTMLInputElement>) => {
                setPassword1(e.target.value);
              }}
              className="bg-[#F2F2F2] h-9 w-68 flex-1 p-2 rounded"
            ></input>
          </div>
          <label htmlFor="password1" className="font-semibold">
            출금계좌 비밀번호 확인
          </label>
          <div className="flex justify-around my-3">
            <input
              type="password"
              id="pass"
              name="password1"
              required={true}
              minLength={4}
              maxLength={4}
              value={password2}
              onChange={(e: React.ChangeEvent<HTMLInputElement>) => {
                setPassword2(e.target.value);
              }}
              className="bg-[#F2F2F2] h-9 w-68 flex-1 p-2 rounded"
            ></input>
          </div>
          {didMatch == true ? (
            <div className="text-green-600">비밀번호가 일치합니다</div>
          ) : (
            <div className="text-red-500">비밀번호가 일치하지 않습니다</div>
          )}
          <div
            className="absolute inset-x-0 bottom-0 mb-8"
            // onClick={(e) => {
            //   if (!didMatch) {
            //     e.preventDefault;
            //     alert("출금 비밀번호를 제대로 입력해주세요");
            //     return false;
            //   }
            // }}
          >
            {didMatch && password1.length == 4 ? (
              <BasicButton
                msg={"NEXT"}
                handleClick={() => handleClick()}
              ></BasicButton>
            ) : (
              <BasicButton
                msg={"NEXT"}
                handleClick={(e) => {
                  if (!didMatch) {
                    e.preventDefault;
                    alert("출금 비밀번호를 제대로 입력해주세요");
                    return false;
                  }
                }}
              ></BasicButton>
            )}
          </div>
        </div>
      )}
    </div>
  );
}
