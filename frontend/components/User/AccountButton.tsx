"use client";
import { useRouter } from "next/navigation";

interface ButtonProps {
  src: string;
}

export default function AccountButton({ src }: ButtonProps) {
  const router = useRouter();
  const url = src;
  return (
    <div
      className="bg-tossblue w-[42px] h-[47px] rounded text-center text-white"
      onClick={() => {
        router.push(url);
      }}
    >
      <div>내역조회</div>
    </div>
  );
}
