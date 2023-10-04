"use client";
import React from "react";

interface ItemStatusProps {
  status: string;
}

type FunctionReturningFunction = () => void;
// 디자인 모음
const ItemButton = ({ status }: ItemStatusProps) => {
  const buttonClassMap: Record<string, string> = {
    ongoing: "border border-1 border-tossblue text-tossblue",
    success: "border border-1 border-tossblue bg-tossblue text-white",
    fail: "border border-1 border-black text-black",
    successbut: "border border-1 border-black bg-black text-white",
  };
  // 버튼 내용 텍스트 모음
  const buttonContentsMap: Record<string, string> = {
    ongoing: "진행 중",
    success: "후기 보기",
    fail: "실패",
    successbut: "후기 작성",
  };
  // 버튼 동작 모음
  const actionClassMap: Record<string, FunctionReturningFunction> = {
    ongoing: () => {
      console.log("온고잉버튼 클릭");
    },
    success: () => {
      console.log("성공버튼 클릭");
    },
    fail: () => {
      console.log("실패버튼 클릭");
    },
    successbut: () => {
      console.log("성공했는데 후기없어");
    },
  };
  const buttonClassName = `w-[67px] h-[18px] text-center rounded-full ${
    buttonClassMap[status] || ""
  }`;

  return (
    <div className="text-[10px]">
      <button className={buttonClassName} onClick={actionClassMap[status]}>
        {buttonContentsMap[status]}
      </button>
    </div>
  );
};

export default ItemButton;
