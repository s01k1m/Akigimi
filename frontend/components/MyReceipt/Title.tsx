'use client'
import { useState } from 'react'
import axios from 'axios'
import '@/styles/MyReceipt.css'

const Title = () => {
    // 현재 내가 도전 중인 챌린지 api 불러오기
    const [item, setItem] = useState<string>("닌텐도")
    return (
        <div className='flex flex-col items-center mt-10' id='my-receipt'>
            <div><span>{item}</span>를 위한</div>
            <div>나의 절약 기록</div>
        </div>
    )
}

export default Title
