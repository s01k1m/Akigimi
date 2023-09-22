interface HistoryItemProps {
    date: string
    type: string
    amount: number
    balance: number
}

const HistoryItem: React.FC<HistoryItemProps> = ({ date, type, amount, balance }) => {
    
    return (
    <div className="flex justify-center">
        <div className="absolute">
            <div className="text-sm text-[#757575] font-light mt-[5vh] -ms-[10vw]">{type}</div>
        </div>
        <div className="flex w-[80%] justify-between mt-[3vh] mb-[5vh] items-center">
            <div className="text-sm font-semibold tracking-widest">{date}</div>
            <div className="flex flex-col justify-end">
                {type==='송금' ? (
                    <>
                        <div className={`text-[#0049F2] text-[16px] tracking-widest`}>-{amount}원</div>
                        <div className="text-[12px] font-light tracking-widest justify-end flex">{balance}원</div>
                    </>
                ): (
                    <>
                        <div className={`text-[16px] tracking-widest`}>{amount}원</div>
                        <div className="text-[12px] font-light tracking-widest justify-end flex">{balance}원</div>
                    </>
                    
                    )}
            </div> 
        </div>
    </div> 
    )
}

export default HistoryItem