"use client"

import '@/styles/GageBar.css'
import { useState } from 'react'
import Image from 'next/image';

const MoneyGageBar = () => {
    const [goalCost, setGoalCost] = useState<number>(150000)
    const [savedCost, setSavedCost] = useState<number>(0)
    // gage 받아서 너비 계산 해줘야됨
    const [gage, setGage] = useState<number>(100)
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
                {savedCost}/{goalCost}
            </p>
            <div className='Money-Gage z-0 absolute top-1/2 left-0 transform -translate-y-1/2' style={{ width: `${gage}px` }}></div>
        </div>
        </>
    )
}

export default MoneyGageBar