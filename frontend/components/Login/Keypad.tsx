"use client";
import React from "react";
import { useEffect, useState } from "react";
import { KeypadKey } from "./KeypadKey";
import { shuffle } from "@/utils/Shuffle";
import "@/styles/user/keypad.css";
export interface IKeypadProps {
  // onKeyPressed: (keyPressed: KeypadKeys) => void;
  onKeyPressed: any;
  // onKeyPressed: (
  //   event: React.MouseEvent<HTMLButtonElement, MouseEvent>
  // ) => void;
}
// 숫자 셔플 해야됨
export enum KeypadKeys {
  ONE = "1",
  TWO = "2",
  THREE = "3",
  FOUR = "4",
  FIVE = "5",
  SIX = "6",
  SEVEN = "7",
  EIGHT = "8",
  NINE = "9",
  ZERO = "0",
}

let enumKeys: string[] = Object.keys(KeypadKeys);
const shuffled: string[] = shuffle(enumKeys);

const KEY_SIZE = 50;

export const Keypad = ({ onKeyPressed }: IKeypadProps) => {
  const [isLoading, setIsLoading] = useState<Boolean>(true);
  // const rows: string[][] = [
  //   [...Object.keys(KeypadKeys).slice(0, 3)],
  //   [...Object.keys(KeypadKeys).slice(3, 6)],
  //   [...Object.keys(KeypadKeys).slice(6, 9)],
  //   [...Object.keys(KeypadKeys).slice(9, 12)],
  // ];
  console.log("셔플드", shuffled);
  const rows: string[][] = [
    [...shuffled.slice(0, 3)],
    [...shuffled.slice(3, 6)],
    [...shuffled.slice(6, 9)],
    [...shuffled.slice(9, 12)],
  ];
  // 셔플드 해서 배열을 섞어주기때문에 컴포넌트 다 생성된후에 클라이언트 ui 만들어주는 것 이 반드시 필요.
  // 서버와 클라이언트 불일치 방지
  useEffect(() => {
    setIsLoading(false);
  }, []);

  return (
    <div className="absolute bottom-4">
      {isLoading ? (
        <div>로딩</div>
      ) : (
        <div className="keypad">
          {rows.map((row: any, idx: number) => (
            <div className="keypad-row" key={idx}>
              {row.map((keyPadNumber: keyof typeof KeypadKeys) => {
                return (
                  <KeypadKey
                    keypadKey={KeypadKeys[keyPadNumber]}
                    key={keyPadNumber}
                    keySize={KEY_SIZE}
                    onKeyPressed={
                      (keyPressed: KeypadKeys) => onKeyPressed(keyPressed)
                      // console.log(keyPressed)
                    }
                  />
                );
              })}
            </div>
          ))}
        </div>
      )}
    </div>
  );
};
