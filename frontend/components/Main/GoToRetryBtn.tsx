import { useState, useEffect } from "react"

const GoToRetryBtn = () => {
    const [isOpened, setIsOpened] = useState<boolean>(false);

    return (
        <div>
            <button 
                className="button-common go-to-retry"
                onClick={() => setIsOpened(true)}
            >다시 도전하기</button>
        </div>
    )
}

export default GoToRetryBtn     