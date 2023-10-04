'use client'
import SuccessLogin from "@/components/SuccessChallenge/SuccessLogin"
import { useRouter } from "next/navigation";
import { useEffect, useState } from "react"

const SuccessChallenge = () => {
    // 비밀번호 틀렸을 때 화면 재렌더링을 위한 상태 변화 변수
    const [incorrectPassword, setIncorrectPassword] = useState<boolean>(false);

    // 비밀번호 틀렸을 때 하위 컴포넌트에서 실행할 함수
    const handleIncorrectPasswordChange = () => {
        setIncorrectPassword(!incorrectPassword);
    }

    // 비밀번호 틀렸을 때 변수 상태 변화하면 화면 재렌더링 하기
    useEffect(() => {
        console.log('비밀번호 틀려서 상태 변화됨', incorrectPassword)
    }, [incorrectPassword])
    
    return (
        <div>
            <div className="flex flex-col justify-center items-center mt-[20vh] text-[22px] font-bold tracking-wide">
                <div>챌린지 성공을 축하해요</div>
                <div>자동 이체를 위해</div>
                <div>비밀번호를 입력해주세요</div> 
            </div>
            <SuccessLogin onIncorrectPasswordChange={handleIncorrectPasswordChange} />
        </div>
    )
}

export default SuccessChallenge