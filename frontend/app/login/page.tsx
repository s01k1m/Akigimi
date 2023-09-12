import Image from "next/image";
import KakaoLoginButton from "@/components/login/KakaoLoginButton";

export default function Login() {
  return (
    <div className="flex flex-col items-center justify-around h-full">
      <div style={{ width: "80%" }}>
        <Image
          src="/logo.png"
          alt="로고 사진"
          width="0"
          height="0"
          sizes="100vw"
          className="w-full h-auto"
        ></Image>
      </div>

      <KakaoLoginButton></KakaoLoginButton>
    </div>
  );
}
