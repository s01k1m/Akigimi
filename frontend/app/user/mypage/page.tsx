"use client";
import { useState } from "react";
import Image from "next/image";
import MypageTap from "@/components/User/UserTap";
import FollowerButton from "@/components/User/FollowerButton";
import FollowingButton from "@/components/User/FollowingButton";
import MypageAccouts from "@/components/User/MypageAccount";
import FriendCard from "@/components/User/FriendCard";
import { BiSearchAlt } from "react-icons/bi";
import axios from "axios";
import { useEffect } from "react";

interface Friend {
  id: number;
  imgUrl: string;
  userName: string;
  product: string;
  gage: number;
}

type FriendsList = Friend[];
export default function Mypage() {
  const [content, setContent] = useState<string>("account");
  const [withdrawBalance, setWithdrawBalance] = useState<string>("");
  const [depositBalance, setDepositBalance] = useState<string>("");
  const [searchWord, setSearchWord] = useState<string>("");
  const [friendsList, setFriendsList] = useState<FriendsList>([]);

  // const getUserInfo() => {

  // }

  const getBalance = async () => {
    let token = window.localStorage.getItem("access_token");
    axios
      .get("/api/account/amount?accountType=WITHDRAW", {
        headers: {
          Authorization: `Bearer ${token}`,
          "Content-Type": "multipart/form-data",
        },
      })
      .then((response) => {
        setWithdrawBalance(response.data.data.balance);
      });
    axios
      .get("/api/account/amount?accountType=DEPOSIT", {
        headers: {
          Authorization: `Bearer ${token}`,
          "Content-Type": "multipart/form-data",
        },
      })
      .then((response) => {
        setDepositBalance(response.data.data.balance);
      });
  };

  // type: FOLLOWING FOLLOWED
  const getfriendsList = async (type: string) => {
    let token = window.localStorage.getItem("access_token");
    axios
      .get(`/friends?friendType=${type}`, {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      })
      .then((response) => {
        setFriendsList(response.data.data.friends);
      });
  };

  useEffect(() => {
    // Moved the initial setFriendsList here
    setFriendsList([
      {
        id: 0,
        imgUrl: "/images/profile.jpg",
        userName: "SK",
        product: "유니폼",
        gage: 80,
      },
    ]);

    getBalance();
  }, []); // Empty dependency array means this runs once on component mount

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
            // getfriendsList("FOLLOWED");
          }}
        >
          <FollowerButton total={10} />
        </div>
        <div
          className="hover:bg-gray1 w-[80px] h-[50px] rounded"
          onClick={() => {
            setContent("following");
            // setContent("FETCH following DATA ");
            // getfriendsList("FOLLOWING");
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
            withdrawBalance={withdrawBalance}
            depositBalance={depositBalance}
          ></MypageAccouts>
        </div>
      ) : content === "follower" ? (
        <div className="w-full relative flex flex-col justify-center items-center">
          <input
            type="text"
            placeholder="친구검색"
            value={searchWord}
            onChange={(e) => {
              console.log(e.target.value);
              setSearchWord(e.target.value);
            }}
            className="mb-3 w-[80%] ps-12 h-12 rounded p-2 bg-gray1"
          ></input>

          <div
            className="absolute top-2 left-16"
            onClick={() => {
              console.log("친구검색");
            }}
          >
            <BiSearchAlt size="30"></BiSearchAlt>
          </div>
          {friendsList?.map((person) => {
            return (
              <FriendCard
                id={person.id}
                imgUrl={person.imgUrl}
                userName={person.userName}
                product={person.product}
                gage={person.gage}
              ></FriendCard>
            );
          })}
        </div>
      ) : content === "following" ? (
        <div className="w-full flex flex-col justify-center items-center">
          {friendsList?.map((person) => {
            return (
              <FriendCard
                id={person.id}
                imgUrl={person.imgUrl}
                userName={person.userName}
                product={person.product}
                gage={person.gage}
              ></FriendCard>
            );
          })}
        </div>
      ) : null}
    </div>
  );
}
