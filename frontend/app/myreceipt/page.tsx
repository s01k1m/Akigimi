'use client'
import Title from "@/components/MyReceipt/Title"
import ReceiptCircle from "@/components/WriteReceipt/ReceiptCircle"
import ReceipTriangle from "@/components/MyReceipt/ReceiptTriangle"
import ReceiptList from "@/components/MyReceipt/ReceiptList"
import { useState } from "react"

const MyReceipt = () => {
    const [challengeState, setChallengeState] = useState<number>(2)
    return (
        <div className="flex flex-col justfiy-center items-center">
            <div>
                <Title />
            </div>
            <div className="flex justify-center transform translate-y-1/2">
                <ReceiptCircle />
            </div>
            <div className="items-center bg-[#EEE] w-[70vw] h-[100%] max-w-[350px] min-w-[300px] rounded-md pb-8">
                <ReceiptList />
            </div>
            
                {challengeState === 0 &&
                <div className="flex justify-center transform -translate-y-1/2">
                    <ReceiptCircle />
                </div>
                 }
                {challengeState === 1 && 
                <div className="flex flex-col justify-center transform -translate-y-2/3">
                    <ReceipTriangle />
                </div>
                }
                {challengeState === 1 && 
                <div>
                    {/* merge 후 컴포넌트 버튼 사용하기 */}
                    <button onClick={() => setChallengeState(2)}>다시 도전하러 가기!</button>
                </div>
                }
                {challengeState === 2 &&
                    <div>
                        <div className="retry-sticker flex items-center ps-2 -mt-2 show">재도전 중이에요</div>
                    </div>
                }

            </div>
    )
}

export default MyReceipt