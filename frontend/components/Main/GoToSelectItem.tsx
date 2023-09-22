'use client'
import '@/styles/MainPageButton.css'
import { useRouter } from 'next/navigation'
import { useEffect } from 'react'
import axios from 'axios'

const GoToSelectItem = () => {
    // const router = useRouter()
    
    const inProgress = async () => {
        console.log('콘솔로그가 안찍혀')
        let token: string = ''
        useEffect(() => {
        
            if (typeof window !== "undefined") {
                console.log('로컬스토리지 접근 성공')
            token = window.localStorage.getItem("access_token");
            } else {
                console.log('로컬 스토리지 접근 실패')
            }
        
            console.log('콘솔 창 왜 안직혀')
        }, []);
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
                }
                console.error('챌린지 생성 에러', error, token)
            })
    } 

    return (
        <>
        <button 
            className="go-to-btn"
            onClick={() => {
                console.log('click')
                inProgress()
            }}
        >
            미닝템 정하러 가기
        </button>
        </>
    )
}
export default GoToSelectItem