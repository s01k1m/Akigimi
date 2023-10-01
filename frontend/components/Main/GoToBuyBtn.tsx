import { useRouter } from "next/navigation"

const GoToBuyBtn = () => {
    const router = useRouter()
    return (
        <div>
            <button 
                className="button-common blue-btn"
                onClick={() => router.push('/login/withdraw')}
            >
                사러가기
            </button>
        </div>
    )
}

export default GoToBuyBtn