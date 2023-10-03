'use client'
import axios from "axios";
import Image from "next/image";
import { useEffect, useState } from "react";

interface FriendProps {
  id: number;
  imgUrl: string;
  userName: string;
  challengeId: null | string;
  gage: number;
}

const FriendCard: React.FC<FriendProps> = ({
  id,
  imgUrl,
  userName,
  challengeId,
  gage,
}) => {

  let token: string = "";
  // 챌린지 아이디로 현재 도전 중인 물건 정보 가져와야 함
  const [productName, setProductName] = useState<string>();
  const getProductName = async () => {
    if (typeof window !== "undefined") {
      token = window.localStorage.getItem("access_token");
      }
    await axios
      .get('/api/challenges/in-progress', {
        headers: {
          'Authorization': `Bearer ${token}`
        }
      })
      .then((response) => {
        setProductName(response.data.data.productName)
      })
      .catch((error) => {
        console.log(error)
      })
  }

  useEffect(() => {
    getProductName();
  }, [productName])


  return (
    <div className="flex items-center w-[80%] h-[96px] bg-gray1 px-[15px]">
      <Image
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
        <div className="text-[13px] text-[#757575] font-normal">{productName}</div>

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
