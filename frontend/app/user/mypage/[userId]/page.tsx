'use client'
import { useEffect, useState } from "react"
import Footer from "@/app/Footer"
import Image from "next/image"
import axios from "axios"
import FollowerButton from "@/components/User/FollowerButton"
import FriendCard from "@/components/User/FriendCard"
import FollowingButton from "@/components/User/FollowingButton"
import '@/styles/MainPageButton.css'
import ReceiptItem from "@/components/MyReceipt/ReceiptItem"
import ReceiptCircle from "@/components/WriteReceipt/ReceiptCircle"

type PageParams = {
    userId: number
  }

interface Friend {
    id: number;
    profileImageUrl: string;
    nickname: string;
    challengeId: number;
    accumulatedAmount: number;
}

type FriendsList = Friend[];

type ReceiptItem = {
    price: number;
    akimLocation: string;
    description: string;
    createdTime: string;
    image: string;
};
  
export default function page({ params }: { params: PageParams }) {
    const userId = params.userId

    // 유저의 닉네임
    const [nickname, setNickName] = useState<string>();
    // 유저의 프로필 이미지
    const [profileImage, setProfileImage] = useState<string>();
    
    // 내가 이 유저를 팔로우 하고 있는가?
    const [userFollwedSpecialUser, setUserFollwedSpecialUser] = useState<boolean>(false);

    // useId로 유저 정보를 불러옵니다
    let token: string = "";
    const getSpecialUserInfo = async () => {
        if (typeof window !== "undefined") {
            token = window.localStorage.getItem("access_token");
        }
        console.log('유저 정보를 가져오기 위한 토큰', token)
        await axios
            .get(`/api/friends/info?id=${userId}`, {
                headers: {
                    Authorization: `Bearer ${token}`
                }
            })
            .then((response) => {
                console.log('특정 유저의 상세 정보 조회', response.data)
                console.log('지금 내가 얘를 팔로우 햇나?', response.data.data.isFollowed)
                setProfileImage(response.data.data.profileImageUrl)
                setNickName(response.data.data.nickname)
                setUserFollwedSpecialUser(response.data.data.isFollowed)
            })
            .catch((error) => {
                console.log('유저 정보 조회 실패', error)
            })
    }

    // 특정 유저의 팔로잉/팔로워 리스트 조회
    const [friendsType, setFriendsType] = useState<string>();
    const [countFollowed, setCountFollowed] = useState<number>();
    const [countFollowing, setCountFollowing] = useState<number>();
    const [friendsList, setFriendsList] = useState<FriendsList>([]);
    const getfriendsList = async () => {
        console.log(friendsType, '친구의 타입')
        if (typeof window !== "undefined") {
            token = window.localStorage.getItem("access_token");
        }
        await axios
            .get(`/api/friends/follow?friendId=${userId}&friendType=${friendsType}`, {
                headers: {
                    Authorization: `Bearer ${token}`,
                }
            })
            .then((response) => {
                console.log('특정 유저의 친구 불러오기 성공', response.data.data)
                setFriendsList(response.data.data.friends)
                if (friendsType === 'FOLLOWED') {
                    setCountFollowed(parseInt(response.data.data.friends.length))
                } else {
                    setCountFollowing(parseInt(response.data.data.friends.length))
                }

            })
            .catch((error) => {
                console.log('특정 유저의 친구 불러오기 실패', error)
            })
    }

    // 친구 추가
    const [friendsState, setFriendsState] = useState<boolean>(false);
    const getFriend = async () => {
        if (typeof window !== "undefined") {
            token = window.localStorage.getItem("access_token");
        }
        const requestBody = {
            followee: userId
        }
        await axios
            .post('/api/friends', requestBody, {
                headers: {
                    Authorization: `Bearer ${token}`,
                    "Content-Type": "application/json",
                }
            })
            .then((response) => {
                console.log('친구 추가 성공', response.data)
                // true 이면 친구임(팔로잉), false이면 친구 아님(팔로우)
                setFriendsState(true)

            })
            .catch((error) => {
                console.log('친구 추가 실패', error)
            })
    }

    // 친구의 영수증 리스트 가져오기
    useEffect(() => {
      if (typeof window !== "undefined") {
        token = window.localStorage.getItem("access_token");
 
      }
      receiptData();
    }, []);
  
    // api 로 불러올 모든 아이템
    const [receiptItems, setReceiptItems] = useState<ReceiptItem[]>([]);
  
    // api 데이터 불러오기
    const receiptData = async () => {
      if (typeof window !== "undefined") {
        token = window.localStorage.getItem("access_token");
        }
      await axios
        .get(`/api/receipts/${userId}`, {
          params: {
            lastReceiptId: 900000000,
            numberOfReceipt: 5,
          },
          headers: {
            Authorization: `Bearer ${token}`,
          },
        })
        .then((response) => {
          console.log("영수증 조회 성공", response.data.data.list);
          setReceiptItems(response.data.data.list);

        })
        .catch((error) => {
          console.log("영수증 조회 실패", error);
        })
    };
  
    useEffect(() => {
        getfriendsList();
    }, [friendsType])

    useEffect(() => {
        getSpecialUserInfo();
        receiptData();
        console.log('내가 지금 팔로우 하고 있나?+++++++++', userFollwedSpecialUser)
    }, [friendsState, ])
    
    return (
        <div>
            <div className="flex flex-col items-center">
                <div className="h-[230px]"></div>
                <div className="absolute top-0 rounded-full mt-[33px]" style={{ width: '200px', height: '200px', overflow: 'hidden' }}>
                    <Image
                    src={profileImage}
                    alt="profile img"
                    layout="fill"
                    objectFit="cover"
                    className="rounded-full"
                    onClick={() => setFriendsList([])}
                    ></Image>
                </div>
                <div className="mt-4 font-semibold mb-4">{nickname}</div>
            </div>
            <div className="flex flex-col mb-5">
                <div className="flex flex-col w-full items-center justify-center">
                    <div className="w-full flex flex-row justify-around items-center px-[91px] mb-4">
                        <div className="w-[220px] flex justify-center">
                            <div
                            className="hover:bg-gray1 w-[80px] h-[50px] rounded"
                            onClick={async() => {
                                setFriendsType("FOLLOWED")
                            }}
                            >
                            <FollowerButton total={countFollowed} />
                            </div>
                        </div>
                        <div className="w-[220px] flex justify-center">
                            <div
                            className="hover:bg-gray1 w-[80px] h-[50px] rounded"
                            onClick={() => {
                                setFriendsType("FOLLOWING")
                                getfriendsList()
                            }}
                            >
                            <FollowingButton total={countFollowing} />
                            </div>
                        </div>
                
                    </div>
                    <div className="flex flex-col w-[60vw] items-center mb-5">
                        {!userFollwedSpecialUser ? (
                            <button className="button-common-small blue-btn" onClick={getFriend}>팔로우</button>
                        ) : (
                            <button className="border-tossblue border-solid text-tossblue w-[80%] font-semibold text-[25px] rounded-lg flex justify-center items-center border-2 h-[6vh] tracking-wider">팔로잉</button>
                        )}
                    </div>
                    <div className="w-full flex flex-col items-center">
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
            </div>
            </div>
                <div className="flex justify-center transform translate-y-1/2">
                    <ReceiptCircle />
                </div>
                <div className="flex flex-col justify-center bg-[#EEE] w-[70vw] h-[100%] max-w-[350px] min-w-[300px] rounded-md pb-8 ms-[12%]">
                    <div className="flex flex-col items-center mt-[30px]">
                    {receiptItems.map((item: any) => (
                        <ReceiptItem
                        key={item.id}
                        akimPlace={item.akgimiPlace}
                        saving={item.price}
                        date={item.createdDateTime}
                        imgUrl={item.photo}
                        />
                    ))}
                </div>
                </div>
       
            <Footer />
        </div>
    )
}

