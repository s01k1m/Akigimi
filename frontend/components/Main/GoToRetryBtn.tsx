import { useRouter } from "next/navigation"

const GoToRetryBtn = () => {
    const router = useRouter();
    return (
        <div>
            <button 
                className="button-common go-to-retry"
                onClick={() => {
                    router.push('/main/retry')
                }}
            >다시 도전하기</button>
        </div>
    )
}

export default GoToRetryBtn     