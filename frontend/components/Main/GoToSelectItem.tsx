'use client'
import '@/styles/MainPageButton.css'
import { useRouter } from 'next/navigation'
import axios from 'axios'

const GoToSelectItem = () => {
    // const router = useRouter()

    const token = `eyJ0eXBlIjoiQUNDRVNTVE9LRU4iLCJhbGciOiJIUzI1NiJ9.eyJpZCI6OTk5OSwidXNlclN0YXRlIjoiUEVORElORyIsImlhdCI6MTY5NTE3NDc3NywiZXhwIjoxNjk1MzU0Nzc3fQ.58F3t3w_nBSCD0wRrwExXc4VTdPJSrGBiqRwjlQ4XjU`

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
                }
                console.error('챌린지 생성 에러', error)
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