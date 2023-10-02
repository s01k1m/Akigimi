'use client'
import Title from "@/components/MyReceipt/Title"
import ReceiptCircle from "@/components/WriteReceipt/ReceiptCircle"
import ReceiptTriangle from "@/components/MyReceipt/ReceiptTriangle"
import ReceiptList from "@/components/MyReceipt/ReceiptList"
import { useState } from "react"
import '@/styles/MainPageButton.css'
import Footer from "../Footer"

const MyReceipt = () => {
    const [challengeState, setChallengeState] = useState<number>(1)
    return (
        <div className="flex flex-col justify-center items-center">
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
                    <ReceiptTriangle />
                </div>
                }
                {challengeState === 1 && 
                <div>
                    <button onClick={() => setChallengeState(2)} className="common-button-small">다시 도전하러 가기!</button>
                </div>
                }
                {challengeState === 2 &&
                    <div>
                        <div className="retry-sticker flex items-center ps-2 -mt-2 show">재도전 중이에요</div>
                    </div>
                }
                <Footer />
            </div>
    )
}

export default MyReceipt
