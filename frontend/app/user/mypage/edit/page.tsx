'use client'
import Image from "next/image";
import { useEffect, useState } from "react";
import { BiSolidPencil } from 'react-icons/bi';
import Footer from "@/app/Footer";
import '@/styles/MainPageButton.css';
import axios from "axios";

const editMyPage = () => {
    // 세션에 저장된 유저 정보 가져오기
    const [nickname, setNickName] = useState<string>();
    const [profileImage, setProfileImage] = useState<string>();
    useEffect(() => {
        if (typeof window !== "undefined") {
        setNickName(window.sessionStorage.getItem("nickname"))
        setProfileImage(window.sessionStorage.getItem("profileImageUrl"))
        }
    }, [])

    // 프로필 이미지 바꾸기
    let token: string = "";
    const changeProfileImg = async () => {
        if (typeof window !== "undefined") {
            token = window.localStorage.getItem('access_token')
        }
        await axios
            .post('api/user/profile', {
                headers: {
                    Authorization: `Bearer ${token}`,
                    "Content-Type": "application/json",
                  },
            })
    }
    return (
        <div>
             <div className="w-full flex flex-col justify-center items-center">
                <BiSolidPencil 
                    size={40} 
                    color={'#757575'} 
                    className="absolute top-10 right-[40px]"
                    onClick={() => 
                    //  TODO 수정 완료 시키고 마이페이지로 이동
                    console.log('수정 완료')
                }
                 />
                <Image
                    src={profileImage}
                    alt="profile img"
                    width={200} // 실제 이미지의 가로 크기로 설정하세요
                    height={200} // 실제 이미지의 세로 크기로 설정하세요
                    // layout="fill" // 이미지 크기를 유지하도록 설정
                    // className="w-full h-auto height-auto"
                    className="rounded-full mt-[43px]"
                    onClick={() => {
                        console.log('프로필 수정 해야지')
                    }}
                >
                </Image>
                <div 
                    className="-mt-[30px] z-10 bg-black bg-opacity-10 rounded-b-full w-[100px] h-[25px] flex justify-center items-center"
                    onClick={changeProfileImg}
                >사진 바꾸기</div>
                <div className="flex mt-10 items-center">
                    <div className="me-[15px]">이름</div>
                    <input type="text" placeholder="고서영" className="bg-[#EEE] rounded-lg w-[50vw] h-[4vh] ps-[10px]" />
                </div>
                <div className="flex items-center mt-2 -ms-3">
                    <div className="me-[15px]">닉네임</div>
                    <input type="text" placeholder="어쩌구어쩌구 고서영" className="bg-[#EEE] rounded-lg w-[50vw] h-[4vh] ps-[10px]" />
                </div>
                <button className="flex justify-start mt-10 bg-[#EEE] rounded-lg text-[#757575] w-36 h-10 flex justify-center items-center">비밀번호 변경하기</button>
                <button className="button-common-small blue-btn absolute bottom-24">수정하기</button>
                <button className="absolute bottom-16 text-[#757575]">회원탈퇴하기</button>
            </div>
            <Footer />
        </div>
    )
}

export default editMyPage