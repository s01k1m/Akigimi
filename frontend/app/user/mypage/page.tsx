"use client";
import { useState } from "react";
import Image from "next/image";
import MypageTap from "@/components/User/UserTap";
import FollowerButton from "@/components/User/FollowerButton";
import FollowingButton from "@/components/User/FollowingButton";
import MypageAccouts from "@/components/User/MypageAccount";
import FriendCard from "@/components/User/FriendCard";
import { BiSearchAlt } from "react-icons/bi";

export default function Mypage() {
  const [content, setContent] = useState<string>("account");
  let withdrawBalance: number = 0;
  let depositBalance: number = 0;

  // const getUserInfo() => {

  // }

  return (
    <div className="w-full flex flex-col justify-center items-center">
      <Image
        src="/profile.jpg"
        alt="profile img"
        width={200} // 실제 이미지의 가로 크기로 설정하세요
        height={200} // 실제 이미지의 세로 크기로 설정하세요
        // layout="fill" // 이미지 크기를 유지하도록 설정
        // className="w-full h-auto height-auto"
        className="rounded-full mt-[43px]"
        onClick={() => {
          setContent("account");
        }}
      ></Image>
      <div
        className="m-4"
        onClick={() => {
          setContent("account");
        }}
      >
        NAME
      </div>
      <div className="w-full flex flex-row justify-around items-center px-[91px] mb-4">
        <div
          className="hover:bg-gray1 w-[80px] h-[50px] rounded"
          onClick={() => {
            setContent("follower");
            console.log("팔로워");
          }}
        >
          <FollowerButton total={10} />
        </div>
        <div
          className="hover:bg-gray1 w-[80px] h-[50px] rounded"
          onClick={() => {
            setContent("following");
            console.log("팔로잉");
          }}
        >
          <FollowingButton total={15} />
        </div>
      </div>
      <div className="m-4">
        <MypageTap></MypageTap>
      </div>
      {content === "account" ? (
        <div className="w-full">
          <div className="w-full px-[44px] text-left font-black text-[20px] mt-[32px] mb-[21px] ">
            계좌
          </div>

          <MypageAccouts
            withdrawBalance={"10000"}
            depositBalance={"2000"}
          ></MypageAccouts>
        </div>
      ) : content === "follower" ? (
        <div className="w-full relative flex flex-col justify-center items-center">
          <input
            type="text"
            placeholder="친구검색"
            className="mb-3 w-[80%] h-12 rounded p-2 bg-gray1"
          ></input>

          <div className="absolute top-[5px] left-[5px] p-2">
            <BiSearchAlt size="30"></BiSearchAlt>
          </div>
          {/* <FriendCard id imgUrl userName product gage></FriendCard> */}
          <div>dfdf</div>
        </div>
      ) : content === "following" ? (
        // <FriendCard></FriendCard>
        <div>asdfdsf</div>
      ) : null}
    </div>
  );
}
