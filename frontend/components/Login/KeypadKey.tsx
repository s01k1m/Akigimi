"use client";
import * as React from "react";
import { KeypadKeys } from "./Keypad";
import "@/styles/user/keypad.css";
export interface IKeypadKeyProps {
  keySize?: number;
  keypadKey: KeypadKeys;
  onKeyPressed: (keyPressed: KeypadKeys) => void;
}

export const KeypadKey = ({
  keypadKey,
  keySize,
  onKeyPressed,
}: IKeypadKeyProps) => {
  return (
    <div
      className="keypad-key"
      data-role="button"
      onClick={() => onKeyPressed(keypadKey)}
      // style={keySize ? { width: keySize, height: keySize } : undefined}
      tabIndex={0}
    >
      {keypadKey?.toString()}
    </div>
  );
};
