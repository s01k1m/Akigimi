'use client'
import '@/styles/MainPageButton.css'
import { useRouter } from 'next/navigation'

const GoToSelectItem = () => {
    const router = useRouter()

    return (
        <>
        <button 
            className="go-to-btn"
            onClick={() => router.push('/item')}
        >
            미닝템 정하러 가기
        </button>
        </>
    )
}
export default GoToSelectItem