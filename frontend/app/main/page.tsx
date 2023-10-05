'use client'
import MoneyGageBar from "@/components/Main/MoneyGageBar"
import DayGageBar from "@/components/Main/DayGageBar"
import IconBtn from "@/components/Main/IconBtn"
import CharacterImg from "@/components/Main/CharacterImg"
import ChallengeInfo from "@/components/Main/ChallengeInfo"
import GoToBuyBtn from "@/components/Main/GoToBuyBtn"
import GoToRetryBtn from "@/components/Main/GoToRetryBtn"
import axios from 'axios'
import { useState, useEffect } from "react"
import Footer from "../Footer"
import ChallengeSkelleton from "@/components/Main/ChallengeSkelleton"
import { useRouter } from "next/navigation"
import '@/styles/MainPageButton.css'

const Main = () => {
    const router = useRouter()
    const [isLoading, setIsLoading] = useState<boolean>(true)
    const [stage, setStage] = useState<number>(0)
    const [percentage, setPercentage] = useState<number>(0)
    const [productName, setProductName] = useState<string>("")
    const [productPrice, setProductPrice] = useState<number>(0)
    const [productImg, setProductImg] = useState<string>("")
    const [challengePeriod, setChallengePeriod] = useState<number>(0)
    const [days, setDays] = useState<number>(0)
    const [balance, setBalance] = useState<number>(0)
    
    // 토큰
    let token: string = "";
    
    useEffect(() => {
        if (typeof window !== "undefined") {
        token = window.localStorage.getItem("access_token");
        }
        console.log('토큰', token)
    }, []);

    const challengeData = async () => {
        await axios
            .get('/api/challenges/in-progress', {
                headers: {
                    'Authorization': `Bearer ${token}`,
                }
            })
            .then((response) => {
                console.log('챌린지 조회 성공', response.data)
        
                const data = response.data.data
                console.log('챌린지 상태', response.data.data.characterStatus)
                setStage(data.characterStatus + 1)
                setProductName(data.productName)
                setProductPrice(data.productPrice.toLocaleString())
                setProductImg(data.productImg)
                setChallengePeriod(data.challengePeriod)
                setDays(data.days)
                setPercentage(data.percentage)
                setProductImg(data.productImgUrl)
                setBalance(data.balance.toLocaleString())
                setIsLoading(false)
                
                // 나의 챌린지 상태(첫도전, 재도전) 로컬스토리지에 저장하기
                window.localStorage.setItem("challengeStage", data.tryCount)
                // 실패인 경우 로컬스토리지에 저장하기
                if (data.characterStatus + 1 === 7) {
                    window.localStorage.setItem("failedChallenge", "1")
                } else {
                    window.localStorage.setItem("failedChallenge", "0")

                }
            })
            
            .catch((error) => {
                console.error('챌린지 조회 에러', error)
            })
            .finally(() => {
                setIsLoading(false)
            })
    } 


    useEffect(() => {
        challengeData()
    }, [])
  
    return (
        <>
        {isLoading ? (
            <ChallengeSkelleton />
        ) : (
            <div className={`background-${stage}`} style={{ width: '100%'}}>
            <div className="flex flex-col items-center justify-center" style={{ width: '100%'}}>
                <MoneyGageBar percentage={percentage} productPrice={productPrice} balance={balance} stage={stage}  />
                <DayGageBar challengePeriod={challengePeriod} days={days} stage={stage}   />
            </div>
            {stage === 0 &&
                <div className="flex justify-center mt-40" >
                    {/* 물건 정하러 가기 버튼  */}
                    <button 
                        className="go-to-btn"
                        onClick={() => {
                            router.push('/item/search')
                            }}
                        >
                        미닝템 정하러 가기
                    </button>
                </div>
            }
            {stage !== 0 &&
                <div className="flex justify-center">
                    <ChallengeInfo item={productName} day={challengePeriod} date={days} itemImg={productImg} />
                </div>
            }
            {stage === 6 &&
                <div className="flex justify-center -mt-1.5">
                    <div className="h-[30px]"></div>
                    <GoToBuyBtn />
                </div>
            }
            {stage === 7 &&
                <div className="flex justify-center -mt-5">
                <div className="h-[30px]"></div>
                    <GoToRetryBtn />
            </div>
            }
            <div className="flex mb-[50px]" style={{ marginTop: stage >= 6 ? "-25px" : (stage === 0 ? "190px" : "0px")}}>
                <IconBtn />
                <div className="flex justify-center">
                    <CharacterImg stage={stage}  />
                </div>
            </div>
            
            <div className="flex justify-center w-[100%]">
                <Footer />
            </div>

            </div>
            
        )}
        </>
    )
}

export default Main
  