'use client'
import Image from "next/image"
import { useState, useEffect } from "react"

interface itemProps {
    akimPlace: string
    saving: number
    date: string
    imgUrl: string
}

const ReceiptItem: React.FC<itemProps> = ({ akimPlace, saving, date, imgUrl }) => {
  
    // date
    const dateString = date; // 예시: 'YYYY-MM-DDTHH:MM:SS' 형식의 문자열
    const time = new Date(dateString);

    const year = time.getFullYear(); // 연도를 추출
    const month = time.getMonth() + 1; // 월을 추출 (0부터 시작하므로 +1 해줌)
    const day = time.getDate(); // 날짜를 추출

    const formattedDate = `${year}. ${month}. ${day}`;

    return (
        <div>
            {date? (
                null
            ) : null}
            <div className="text-[20px] font-bold mb-1">{formattedDate}</div>
            <div className="flex flex-col mb-[2vh] bg-white w-[50vw] rounded-xl h-[100px] max-w-[280px] min-w-[200px]">
                <div className="flex flex-end align-bottom ms-[5%] mt-[1vh]">
                    <div className="w-[100px]"></div>
                        <div style={{ width: '80px', height: '80px', overflow: 'hidden' }} className="me-2 flex absolute rounded-lg">
                            <Image src={imgUrl} alt="영수증 사진" 
                                className="me-[2vw]"
                                objectFit="cover"
                                layout="fill"
                                />
                        </div>
                        <div className="flex flex-col align-bottom justify-end mt-[30px]">
                            <div className="receipt-place">{akimPlace}</div>
                            <div>
                                <span className="receipt-saving">{saving}</span>
                                <span className="receipt-saving black">원</span> 절약</div>
                        </div>
                </div>
    
            </div>
        </div>
    )
}

export default ReceiptItem
