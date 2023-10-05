'use client'
import { useEffect, useState } from "react"
import Footer from "@/app/Footer"

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
            {userId}의 마이페이지
            <Footer />
        </div>
    )
}