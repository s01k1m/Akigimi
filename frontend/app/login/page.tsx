import Image from "next/image";
import KakaoLoginButton from "@/components/login/KakaoLoginButton";

export default function Login() {
  return (
    <div className="flex flex-col items-center justify-around h-full">
      <div style={{ width: "80%" }}>
        <Image
          src="/logo.png"
          alt="로고 사진"
          width={400} // 실제 이미지의 가로 크기로 설정하세요
          height={483} // 실제 이미지의 세로 크기로 설정하세요
          layout="responsive" // 이미지 크기를 유지하도록 설정
          // className="w-full h-auto height-auto"
          priority
        ></Image>
      </div>

      <KakaoLoginButton></KakaoLoginButton>
    </div>
  );
}
