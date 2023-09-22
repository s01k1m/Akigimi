import Image from "next/image";

interface FriendProps {
  id: number;
  imgUrl: string;
  userName: string;
  product: null | string;
  gage: number;
}

const FriendCard: React.FC<FriendProps> = ({
  id,
  imgUrl,
  userName,
  product,
  gage,
}) => {
  return (
    <div className="flex items-center w-[80%] h-[96px] bg-gray1 px-[15px]">
      <Image
        // src="/profile.jpg"
        src={imgUrl}
        alt="profile img"
        width={55} // 실제 이미지의 가로 크기로 설정하세요
        height={55} // 실제 이미지의 세로 크기로 설정하세요
        // layout="fill" // 이미지 크기를 유지하도록 설정
        // className="w-full h-auto height-auto"
        className="rounded-full me-[27px]"
      ></Image>
      <div className="ms-[10px]">
        <div className="text-[13px] font-semibold mb-[0.5vh]">{userName}</div>
        <div className="text-[13px] text-[#757575] font-normal">{product}</div>

        <div>
          <div className="w-[40vw] h-[15px] max-w-[210px] min-w-[180px] bg-white rounded-full">
            <div
              className={`w-[${gage}%] h-[15px] max-w-[210px] bg-[#0049F2] rounded-full`}
            ></div>
          </div>
        </div>
      </div>
    </div>
  );
};

export default FriendCard;
