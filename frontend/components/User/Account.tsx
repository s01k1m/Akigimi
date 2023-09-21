import Image from "next/image";

interface AccountProps {
  src: string;
  name: string;
  balance: string;
}

export default function Account({ src, name, balance }: AccountProps) {
  return (
    <div className="flex items-center">
      <Image
        src={src}
        alt="profile img"
        width={50} // 실제 이미지의 가로 크기로 설정하세요
        height={50} // 실제 이미지의 세로 크기로 설정하세요
        // layout="fill" // 이미지 크기를 유지하도록 설정
        // className="w-full h-auto height-auto"
        className="rounded-full me-[27px]"
      ></Image>
      <div>
        <div>{name}</div>
        <div>{balance}원</div>
      </div>
    </div>
  );
}
