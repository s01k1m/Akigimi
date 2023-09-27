'use client'
import MoneyGageBar from "@/components/Main/MoneyGageBar"
import DayGageBar from "@/components/Main/DayGageBar"
import GoToSelectItem from "@/components/Main/GoToSelectItem"
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
    const [stage, setStage] = useState<number>(1)
    const [percentage, setPercentage] = useState<number>(0)
    const [productName, setProductName] = useState<string>("")
    const [productPrice, setProductPrice] = useState<number>(0)
    const [productImg, setProductImg] = useState<string>("")
    const [challengePeriod, setChallengePeriod] = useState<number>(0)
    const [days, setDays] = useState<number>(0)
    const [balance, setBalance] = useState<number>(0)
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
                // setStage(data.characterStatus + 1)
                setProductName(data.productName)
                setProductPrice(data.productPrice.toLocaleString())
                setProductImg(data.productImg)
                setChallengePeriod(data.challengePeriod)
                setDays(data.days)
                setPercentage(data.percentage)
                setProductImg(data.productImgUrl)
                setBalance(data.balance.toLocaleString())
                setIsLoading(false)
            })
            
            .catch((error) => {
                console.error('챌린지 조회 에러', error)
            })
            .finally(() => {
                setIsLoading(false)
            })
    } 

    // 하드 데이터 용 api
    // const inProgress = async () => {
    //     const requestBody = {
    //         itemId: 1,
    //         challengePeriod: 50
    //     };
    //     console.log('버튼 눌렀을 때 토큰 잘 불러오는지 확인', token)
    //     await axios
    //         .post('/api/challenges', requestBody, {
    //             headers: {
    //                 'Authorization': `Bearer ${token}`,
    //                 'Content-Type': 'application/json'
    //             }
    //         })
    //         .then((response) => {
    //             console.log('챌린지 생성 성공', response.data)
    //         })
    //         .catch((error) => {
    //             if (error.response.data.code === '011') {
    //                 console.log('자원이 존재하지 않는 경우')
    //             } else if (error.response.data.code === '017') {
    //                 console.log('이미 챌린지 진행 중인 경우')
    //                 challengeData()
    //                 alert('이미 챌린지를 진행중입니다')
    //             } else if (error.response.data.code === '012') {
    //                 alert('챌린지에 참여하세요')
    //             }
    //             console.log('챌린지 생성 에러', error)
    //         })
    // } 


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
                            router.push('/item')
                            }}
                        >
                        미닝템 정하러 가기
                    </button>
                </div>
            }
            {stage !== 0 &&
                <div className="flex justify-center mt-10">
                    <ChallengeInfo item={productName} day={challengePeriod} date={days} itemImg={productImg} />
                </div>
            }
            {stage === 6 &&
                <div className="flex justify-center -mt-1.5">
                    <GoToBuyBtn />
                </div>
            }
            {stage === 7 &&
                <div className="flex justify-center -mt-1.5">
                <GoToRetryBtn />
                </div>
            }
            <div className="flex" style={{ marginTop: stage >= 6 ? "-25px" : (stage === 0 ? "170px" : "23px")}}>
                <IconBtn />
                <CharacterImg stage={stage}  />
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
  