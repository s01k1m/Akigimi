"use client";
import { useState } from "react";
import Image from "next/image";
import MypageTap from "@/components/User/MypageTap";
import FollowerButton from "@/components/User/FollowerButton";
import FollowingButton from "@/components/User/FollowingButton";
import MypageAccouts from "@/components/User/MypageAccount";
import FriendCard from "@/components/User/FriendCard";
import { BiSearchAlt } from "react-icons/bi";
import axios from "axios";
import { useEffect } from "react";
import Footer from "@/app/Footer";

interface Friend {
  id: number;
  profileImageUrl: string;
  nickname: string;
  challengeId: number;
  accumulatedAmount: number;
}

type FriendsList = Friend[];
export default function Mypage() {
  const [content, setContent] = useState<string>("account");
  const [withdrawBalance, setWithdrawBalance] = useState<string>("");
  const [depositBalance, setDepositBalance] = useState<string>("");
  const [searchWord, setSearchWord] = useState<string>("");
  const [friendsList, setFriendsList] = useState<FriendsList>([]);
  
  // 세션에 저장된 유저 정보 가져오기
  const [nickname, setNickName] = useState<string>();
  const [profileImage, setProfileImage] = useState<string>();
  useEffect(() => {
    if (typeof window !== "undefined") {
      setNickName(window.sessionStorage.getItem("nickname"))
      setProfileImage(window.sessionStorage.getItem("profileImageUrl"))
    }
  }, [])

  // const getUserInfo = () => {
  // }

  const getBalance = async () => {
    let token: string = "";
    if (typeof window !== "undefined") {
      token = window.localStorage.getItem("access_token");
    }
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
  const [countFollowing, setCountFollowing] = useState<number>();
  const [countFollowed, setCountFollowed] = useState<number>();
  const getfriendsList = async (type: string) => {
    let token = window.localStorage.getItem("access_token");

    axios
      .get(`/api/friends?friendType=${type}`, {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      })
      .then((response) => {
        setFriendsList(response.data.data.friends);
        console.log(response.data.data.friends)
        if (type === 'FOLLOWING') {
          setCountFollowing(response.data.data.friends.length)
        } else {
          setCountFollowed(response.data.data.friends.length)
        }
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
    // 팔로워 팔로잉 몇명인지 세기 위해 불러옵니다
    getfriendsList("FOLLOWED"); // 나의 팔로워
    getfriendsList("FOLLOWING"); // 내가 팔로우한 애들
    
  }, []); // Empty dependency array means this runs once on component mount

  return (
    <div className="w-full flex flex-col justify-center items-center">
      <Image
        src={profileImage}
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
        {nickname}
      </div>
      <div className="w-full flex flex-row justify-around items-center px-[91px] mb-4">
        <div
          className="hover:bg-gray1 w-[80px] h-[50px] rounded"
          onClick={() => {
            setContent("follower");
            getfriendsList("FOLLOWED");
          }}
        >
          <FollowerButton total={countFollowed} />
        </div>
        <div
          className="hover:bg-gray1 w-[80px] h-[50px] rounded"
          onClick={() => {
            setContent("following");
            // setContent("FETCH following DATA ");
            getfriendsList("FOLLOWING");
          }}
        >
          <FollowingButton total={countFollowing} />
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
              <>
              <FriendCard
                id={person.id}
                imgUrl={person.profileImageUrl}
                userName={person.nickname}
                challengeId={person.challengeId}
                gage={person.accumulatedAmount}
              ></FriendCard>
            </>
            );
          })}
        </div>
      ) : content === "following" ? (
        <div className="w-full flex flex-col justify-center items-center">
          {friendsList?.map((person) => {
            return (
              <FriendCard
                id={person.id}
                imgUrl={person.profileImageUrl}
                userName={person.nickname}
                challengeId={person.challengeId}
                gage={person.accumulatedAmount}
              ></FriendCard>
            );
          })}
        </div>
      ) : null}
      <Footer />
    </div>
  );
}
