'use client'
import Title from "@/components/MyReceipt/Title"
import ReceiptCircle from "@/components/WriteReceipt/ReceiptCircle"
import ReceiptTriangle from "@/components/MyReceipt/ReceiptTriangle"
import ReceiptList from "@/components/MyReceipt/ReceiptList"
import { useEffect, useState } from "react"
import '@/styles/MainPageButton.css'
import Footer from "../Footer"

const MyReceipt = () => {
    const [challengeState, setChallengeState] = useState<number>(0)
    const [failedChallenge, setFailedChallenge] = useState<boolean>(false);
    useEffect(() => {
        if (typeof window !== "undefined") {
            setChallengeState(parseInt(window.localStorage.getItem("challengeStage")));
            let state: string = window.localStorage.getItem("failedChallenge")
            if (state === "1") {
                setFailedChallenge(true)
            }
        }
    }, [])
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
                {/* {failedChallenge ? (
                    <>
                        <div className="flex flex-col justify-center transform -translate-y-2/3">
                            <ReceiptTriangle />
                        </div>
                    
                        <div>
                            <button onClick={() => setChallengeState(2)} className="button-common blue-btn mb-[10vh]">다시 도전하러 가기!</button>
                        </div>
                    </>
                ): (
                    null
                    )}
                
                    <div>
                        <div className="retry-sticker flex items-center ps-2 -mt-2 show">재도전 중이에요</div>
                    </div>
                     */}
                <Footer />
            </div>
    )
}

export default MyReceipt
