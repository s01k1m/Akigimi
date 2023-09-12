import { useRouter } from "next/navigation"

const GoToBuyBtn = () => {
    const router = useRouter()
    return (
        <div>
            <button 
                className="button-common go-to-buy"
                onClick={() => router.push('/withdraw')}
            >
                사러가기
            </button>
        </div>
    )
}

export default GoToBuyBtn