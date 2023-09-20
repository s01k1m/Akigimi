"use client"

import '@/styles/GageBar.css'
import { useState, useEffect } from 'react'
import Image from 'next/image';

interface moneyPropsType  {
    percentage: number
    productPrice: number
}

const MoneyGageBar: React.FC<moneyPropsType> = ({ percentage, productPrice }) => {
    const [gage, setGage] = useState<number>(percentage)
  
    // 게이지바 선물 이모지
    const imageSrc: string = '../assets/images/gift.png'
    return (
        <>
        <div className='Gage-Bar mt-[62px] mb-4 relative flex items-center justify-center'>
            <Image 
                src="/images/gift.png" 
                alt="gift" 
                width={40} 
                height={40} 
                className="z-0 absolute right-[-10px]"/>
            <p className='flex z-10'>
                {productPrice*percentage*0.01}/{productPrice}
            </p>
            <div className='Money-Gage z-0 absolute top-1/2 left-0 transform -translate-y-1/2' style={{ width: `${percentage}%` }}></div>
        </div>
        </>
    )
}

export default MoneyGageBar