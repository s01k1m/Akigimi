"use client"

import '@/styles/GageBar.css'
import { useState } from 'react'
import Image from 'next/image';

interface DayPropsType {
    days: number
    challengePeriod: number
}

const DayGageBar: React.FC<DayPropsType> = ({ days, challengePeriod }) => {

    // gage 받아서 너비 계산 해줘야됨
    const [gage, setGage] = useState<number>(100)
    return (
        <>
        <div className='Gage-Bar relative flex items-center justify-center'>
            <p className='flex z-10'>
                {days}/{challengePeriod}
            </p>
            <div className='Date-Gage z-0 absolute top-1/2 left-0 transform -translate-y-1/2'  style={{ width: `${(days/challengePeriod)*100}%` }}>
                <div className="flex items-center translate-y-2">
                    <Image 
                        src="/images/runner.png" 
                        alt="gift" 
                        width={60} 
                        height={60} 
                        className="z-0 absolute right-[-25px]"/>
                </div>
            </div>
        </div>
        </>
    )
}

export default DayGageBar