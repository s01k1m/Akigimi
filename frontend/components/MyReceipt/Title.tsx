'use client'
import { useState, useEffect } from 'react'
import axios from 'axios'
import '@/styles/MyReceipt.css'

const Title = () => {
    // 현재 내가 도전 중인 챌린지 api 불러오기
    // 토큰
    let token: string = "";
    
    if (typeof window !== "undefined") {
    token = window.localStorage.getItem("access_token");
    }
      
     const challengeData = async () => {
         await axios
             .get('/api/challenges/in-progress', {
                 headers: {
                     'Authorization': `Bearer ${token}`,
                 }
             })
             .then((response) => {
                 console.log('챌린지 조회 성공', response.data)
                 setItem(response.data.data.productName)
             })
             .catch((error) => {
                 console.error('챌린지 조회 에러', error)
             })

     } 
 
     useEffect(() => {
         challengeData()
     }, [])
    const [item, setItem] = useState<string>("닌텐도")
    return (
        <div className='flex flex-col items-center mt-10' id='my-receipt'>
            <div><span>{item}</span>를 위한</div>
            <div>나의 절약 기록</div>
        </div>
    )
}

export default Title
