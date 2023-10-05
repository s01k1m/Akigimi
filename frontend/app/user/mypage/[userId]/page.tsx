'use client'
import { useEffect, useState } from "react"
import Footer from "@/app/Footer"
import Image from "next/image"

type PageParams = {
    userId: number
  }
  
export default function page({ params }: { params: PageParams }) {
    const userId = params.userId

    // 유저의 닉네임
    const [nickname, setNickName] = useState<string>();
    // 유저의 프로필 이미지
    const [profileImage, setProfileImage] = useState<string>();
    // 유저의 

    // useId로 유저 정보를 불러옵니다
    
    return (
        <div>
            <div className="h-[230px]"></div>
            <div className="absolute top-0 rounded-full mt-[33px]" style={{ width: '200px', height: '200px', overflow: 'hidden' }}>
                <Image
                src={profileImage}
                alt="profile img"
                layout="fill"
                objectFit="cover"
                className="rounded-full"
                ></Image>
            </div>
            <Footer />
        </div>
    )
}