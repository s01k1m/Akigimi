"use client";
import * as React from "react";
import styled from "styled-components";
import { useEffect, useState } from "react";
import Link from "next/link";
import { redirect } from "next/navigation";
import axios from "axios";

interface ButtonProps {
  msg: string;
  handleClick: (event: React.MouseEvent<HTMLButtonElement, MouseEvent>) => void;
}

const ButtonWrapper = styled.div`
  max-width: 500px;
  margin: 0 auto;
  width: 100%;
`;

export function BasicButton({ handleClick, msg }: ButtonProps) {
  const [isLoading, setIsLoading] = useState<boolean>(true);
  useEffect(() => {
    setIsLoading(false);
  }, []);
  return (
    <ButtonWrapper>
      {isLoading ? (
        <div>로딩중</div>
      ) : (
        <div className="flex w-full justify-center">
          <button
            className="rounded bg-tossblue max-w-[480px] w-[80%] h-9 text-white tracking-widest"
            onClick={handleClick}
          >
            {msg}
          </button>
        </div>
      )}
    </ButtonWrapper>
  );
}