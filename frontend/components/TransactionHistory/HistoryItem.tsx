import { useEffect, useState } from "react"

interface HistoryItemProps {
    date: string
    type: string
    amount: number
    balance: number
}

const HistoryItem: React.FC<HistoryItemProps> = ({ date, type, amount, balance }) => {
  

    const dateString = date; // 예시: 'YYYY-MM-DDTHH:MM:SS' 형식의 문자열
    const time = new Date(dateString);

    const year = time.getFullYear(); // 연도를 추출
    const month = time.getMonth() + 1; // 월을 추출 (0부터 시작하므로 +1 해줌)
    const day = time.getDate(); // 날짜를 추출

    const formattedDate = `${year}. ${month}. ${day}`;


    return (
    <div className="flex justify-center">
        <div className="absolute">
            <div className="text-sm text-[#757575] font-light mt-[5vh] -ms-[10vw]">{type}</div>
        </div>
        <div className="flex w-[80%] justify-between mt-[3vh] mb-[5vh] items-center">
            <div className="text-sm font-semibold tracking-widest">{formattedDate}</div>
            <div className="flex flex-col justify-end">
                {type==='출금' ? (
                    <>
                        <div className={`text-[16px] tracking-widest`}>-{amount}원</div>
                        <div className="text-[12px] font-light tracking-widest justify-end flex">{balance}원</div>
                    </>
                ): (
                    <>
                        <div className={`text-[16px] tracking-widest text-[#0049F2] `}>{amount}원</div>
                        <div className="text-[12px] font-light tracking-widest justify-end flex">{balance}원</div>
                    </>
                    
                    )}
            </div> 
        </div>
    </div> 
    )
}

export default HistoryItem