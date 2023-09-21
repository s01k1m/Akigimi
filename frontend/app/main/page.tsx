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
import '@/styles/MainPageButton.css'

const Main = () => {

    const token = `eyJ0eXBlIjoiQUNDRVNTVE9LRU4iLCJhbGciOiJIUzI1NiJ9.eyJpZCI6OTk5OSwidXNlclN0YXRlIjoiUEVORElORyIsImlhdCI6MTY5NTI1NTQ5MywiZXhwIjoxNjk1NDM1NDkzfQ.Wwg5ar8uOp2xZmt6JO7aRyhPTHuIxduFcrx1pdV-vAM`
    const [stage, setStage] = useState<number>(0)
    const [percentage, setPercentage] = useState<number>(0)
    const [productName, setProductName] = useState<string>("")
    const [productPrice, setProductPrice] = useState<number>(0)
    const [productImg, setProductImg] = useState<string>("")
    const [challengePeriod, setChallengePeriod] = useState<number>(0)
    const [days, setDays] = useState<number>(0)
    
    const challengeData = async () => {
        await axios
            .get('http://25.4.167.82:8080/api/challenges/in-progress', {
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
                setProductPrice(data.productPrice)
                setProductImg(data.productImg)
                setChallengePeriod(data.challengePeriod)
                setDays(data.days)
                setPercentage(data.percentage)
                setProductImg(data.productImgUrl)
            })
            .catch((error) => {
                console.error('챌린지 조회 에러', error)
            })
    } 

    // 하드 데이터 용 api
    const inProgress = async () => {
        const requestBody = {
            itemId: 1,
            challengePeriod: 50
        };
        await axios
            .post('http://25.4.167.82:8080/api/challenges', requestBody, {
                headers: {
                    'Authorization': `Bearer ${token}`,
                    'Content-Type': 'application/json'
                }
            })
            .then((response) => {
                console.log('챌린지 생성 성공', response.data)
            })
            .catch((error) => {
                if (error.response.data.code === '011') {
                    console.log('자원이 존재하지 않는 경우')
                } else if (error.response.data.code === '017') {
                    console.log('이미 챌린지 진행 중인 경우')
                    challengeData()
                    console.log('물건 가격', productPrice)
                    alert('이미 챌린지를 진행중입니다')
                }
                console.error('챌린지 생성 에러', error)
            })
    } 


    useEffect(() => {
        challengeData()
    }, [stage])
  
    return (
        <div className={`background-${stage}`} style={{ width: '100%'}}>
        <div className="flex flex-col items-center justify-center" style={{ width: '100%'}}>
            <MoneyGageBar percentage={percentage} productPrice={productPrice} stage={stage}  />
           <DayGageBar challengePeriod={challengePeriod} days={days} stage={stage}   />
        </div>
        {stage === 0 &&
            <div className="flex justify-center mt-60" >
                  {/* 물건 정하러 가기 버튼  */}
                  <button 
                    className="go-to-btn"
                    onClick={() => {
                        console.log('click')
                        inProgress()
                        setStage(1)
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
        <div className="flex" style={{ marginTop: stage >= 5 ? "0px" : (stage === 0 ? "120px" : "53px")}}>
            <IconBtn />
            <CharacterImg stage={stage}  />
        </div>
        

        </div>
    )
}

export default Main
  