import '@/styles/MainPageButton.css'
import { useRouter } from 'next/router';


const GoToSelectItem = () => {
    // const router = useRouter();
    const goToSelect = () => {
        // router.push('/item-search')
    }
    return (
        <>
        <button 
            className="go-to-btn"
        >
            미닝템 정하러 가기
        </button>
        </>
    )
}

export default GoToSelectItem