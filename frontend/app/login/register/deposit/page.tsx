// export default function CreateDepositAccount() {
//   return (
//     <main>
//       <span>저축계좌</span>
//     </main>
//   );
// }
"use client";
import React, { useRef, useState, useEffect, useCallback } from "react";
import axios from "axios";
import { redirect, useRouter } from "next/navigation";
import { BasicButton } from "@/components/login/BasicButton";
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
  // position: absolute;
  // top: 50%;
  // left: 50%;
  // transform: translate(-50%, -50%);
  // width: 236px;
  text-align: center;
`;

export default function CreateDepositAccount() {
  const [isLoading, setIsLoading] = useState<boolean>(false); // 페이지 로딩 중인지 확인하기 위하여
  const [password1, setPassword1] = useState<string>("");
  const [password2, setPassword2] = useState<string>(undefined);
  const [didMatch, setDidMatch] = useState<boolean>(false);
  // const inputRef = useRef<HTMLInputElement>(null);
  const [accountNum, setAccountNum] = useState<string>("0000000000000000"); // 계좌 번호
  // let accountNum: number = 0;
  const router = useRouter();
  const [content, setContent] = useState<RegisterInfo[] | undefined>([]);
  const [createdDeposit, setCreatedDeposit] = useState<boolean>(false);
  let token = "";

  useEffect(() => {
    if (typeof window !== "undefined") {
      token = window.localStorage.getItem("access_token");
    }
  }, []);

  const info = {
    title: "저축계좌를 만들고 있어요!",
    menual: "새로 만든 저축계좌의 정보는 다음과 같아요",
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

  const test = () => {
    if (password1 != password2) {
      setDidMatch(false);
    } else {
      setDidMatch(true);
    }
  };

  useEffect(() => {
    calculateAccount();
  }, [accountNum]);

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
        setCreatedDeposit(true);
        setAccountNum(response.data.data.accountNumber);
      });
  };
  // 버튼에 전달할 함수
  // TODO: 백 아직 완성 안되서 이거 완성되면 연결할 것

  // 계좌 비밀번호를 전송하는 함수
  const handleClick = async () => {
    token = window.localStorage.getItem("access_token");
    // const formData = new FormData();
    // formData.append("bank", "SSAFY");
    // formData.append("accountType", "WITHDRAW");
    // formData.append("accountNumber", accountNum);
    // formData.append("password", password1);

    const payload: {
      bank: string;
      accountType: string;
      accountNumber: string;
      password: string;
    } = {
      bank: "SSAFY",
      accountType: "DEPOSIT",
      accountNumber: accountNum,
      password: password1,
    };

    await axios
      .post("/api/account/new/password", payload, {
        headers: {
          Authorization: `Bearer ${token}`,
          "Content-Type": "application/json",
        },
      })
      .then((response) => {
        console.log(response.data.message);
        router.push("/login/register/simple");
      });
  };

  const account = () => {
    return (
      <AccountWrapper className="flex justify-center">
        <AccountNum
          className="p-2 m-4"
          // htmlFor="withdrawalAccount"
          onClick={() => {
            // TODO: 계좌번호 클릭하면 복사되는 로직 만들고 싶음. 사용자한테 편할 것 같은 기능이라서
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
      </AccountWrapper>
    );
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

          <div className="bg-gray1 p-8 rounded shadow-lg shadow-gray-400 mb-14">
            <div
              className={`${
                createdDeposit ? "" : "blur-sm animate-pulse"
              } w-[96px] h-[37px] p-auto flex justify-center items-center rounded-full bg-tossblue text-white font-bold shadow shadow-black`}
            >
              싸피은행
            </div>
            <AccountWrapper className="flex justify-center">
              <AccountNum
                className="p-2 m-4"
                // htmlFor="withdrawalAccount"
                onClick={() => {
                  // TODO: 계좌번호 클릭하면 복사되는 로직 만들고 싶음. 사용자한테 편할 것 같은 기능이라서
                  console.log("클릭");
                }}
              >
                {accountNum != "0000000000000000" &&
                accountNumArr.length > 0 ? (
                  <div>
                    {accountNumArr.map((account, index) => (
                      <span key={index}>
                        {account}
                        {index === accountNumArr.length - 1 ? "" : " - "}
                      </span>
                    ))}
                  </div>
                ) : (
                  <div className="invisible">0</div>
                )}
              </AccountNum>
            </AccountWrapper>
          </div>
          <label htmlFor="password1" className="font-semibold">
            입금계좌 비밀번호
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
            입금계좌 비밀번호 확인
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
            //     alert("입금 비밀번호를 제대로 입력해주세요");
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
                    // 비밀번호1 과 비밀번호2가 서로 맞지 않으면 다음 페이지로 넘어갈 수 없음
                    e.preventDefault;
                    alert("입금 비밀번호를 제대로 입력해주세요");
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
