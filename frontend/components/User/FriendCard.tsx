import Image from "next/image";

export default function FriendCard() {
  return (
    <div className="flex items-center w-[80%] h-[96px] bg-gray1 px-[15px]">
      <Image
        src="/profile.jpg"
        alt="profile img"
        width={50} // 실제 이미지의 가로 크기로 설정하세요
        height={50} // 실제 이미지의 세로 크기로 설정하세요
        // layout="fill" // 이미지 크기를 유지하도록 설정
        // className="w-full h-auto height-auto"
        className="rounded-full me-[27px]"
      ></Image>
      <div className="grow">
        <div className="text-lg">친구</div>
        <div>나이키 어쩌구 아이템</div>
      </div>
    </div>
  );
}
