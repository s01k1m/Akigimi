'use client'
import { useEffect, useState, useCallback } from "react";
import { useRouter } from "next/navigation";
import axios from "axios";

const SuccessLogin = ({ onIncorrectPasswordChange }) => {
    // 컴포넌트화 리팩토링 필요

    // 챌린지 성공 시, 물건 구매 url로 보내주기
    const router = useRouter();

    // token 가져오기
    const [token, setToken] = useState<string>("");

    useEffect(() => {
        if (typeof window !== "undefined") {
            const storedToken = window.localStorage.getItem("access_token");
            if (storedToken) {
                setToken(storedToken);
            }
        }
    }, []);
    console.log('토큰 챌린지 생성하고 자동 계좌 이체를 위한 비밀번호를 입력할 때', token)

    // 랜덤 배열 생성
    const [randomList, setRandomList] = useState<number[]>([]);
    useEffect(() => {
        const newRandomList = Array.from({length: 9}, (_, i) => i + 1).sort(() => Math.random() - 0.5);
        setRandomList(newRandomList);
    }, []);

    // 비밀번호 담기
    const [password, setPassword] = useState<number[]>([]);
    // 비밀번호 입력하면 리스트에 업데이트 하기
    const handleNumberClick = useCallback((num: number) => {
        if (password.length < 6) {
            const newPassword = [...password, num];
            setPassword(newPassword);
        }
    }, [password]);

    console.log(password)

    // 비밀번호 확인 API 호출하기
    const checkPassword = async () => {
        const requestBody = {
            userPassword: password.join('')
        };
        await axios
            .post('/api/account/withdraw', requestBody, {
                headers: {
                    'Authorization': `Bearer ${token}`,
                    'Content-Type': 'application/json'
                }
            })
            .then((response) => {
                console.log('챌린지 성공후 비밀번호 입력', response.data);
                // 챌린지 성공 api 호출하기 (상태 변화)
                success();
            })

            
            .catch((error) => {
                console.log('챌린지 성공 후 비밀번호 틀림', error);
                setPassword([]);
                alert('비밀번호를 다시 입력해주세요');
                onIncorrectPasswordChange();
                // TODO: 비밀번호 틀렸을 때 화면 다시 렌더링이 안 됨
            })
        }

     
    // 비밀번호 6개 업데이트 동그라미 상태 변화
    useEffect(() => {
        if (password.length > 0 && password.length <= 6) {
            const element = document.getElementById(`${password.length}`);
            if (element) {
                element.style.backgroundColor = "blue";
            }
        } 
        
        if (password.length === 6 ) {
            checkPassword();
        } 
    }, [password]);

    // 챌린지 성공 api 호출 함수
    const success = async () => {
        await axios
            .patch(`/api/challenges/succeed`, true, {
                headers: {
                    'Authorization': `Bearer ${token}`,
                    'Content-Type': 'application/json'
                }
            })
            .then((response) => {
                console.log('챌린지 성공 api 호출 성공', response)
                router.push('/main')
                // router.push(`${response.data.data.productUrl}`)
            })
            .catch((error) => {
                console.log('챌린지 성공 api 호출 실패', error)
                console.log('토큰이 잇잖아', token)
            })
    }


    return (
        <div>
            <div className="flex gap-5 justify-center mt-5">
                <div id="1" className="rounded-full bg-[#D9D9D9] w-6 h-6"></div>
                <div id="2" className="rounded-full bg-[#D9D9D9] w-6 h-6"></div>
                <div id="3" className="rounded-full bg-[#D9D9D9] w-6 h-6"></div>
                <div id="4" className="rounded-full bg-[#D9D9D9] w-6 h-6"></div>
                <div id="5" className="rounded-full bg-[#D9D9D9] w-6 h-6"></div>
                <div id="6" className="rounded-full bg-[#D9D9D9] w-6 h-6"></div>
            </div>
            <div className="flex justify-center">
                <div className="flex justify-center items-center mt-10 w-[30vw] h-8 bg-[#F2F2F2] rounded-md text-[#757575] text-[12px]">비밀번호를 몰라요</div>
            </div>
            <div className="flex flex-col justify-center items-center mt-20 gap-14">
                <div className="flex gap-10">
                    <div className="flex justify-center items-center text-[22px] w-20 h-10 font-bold hover:bg-gray-200 rounded-md" onClick={() => handleNumberClick(randomList[0])}>{randomList[0]}</div>
                    <div className="flex justify-center items-center text-[22px] w-20 h-10 font-bold hover:bg-gray-200 rounded-md" onClick={() => handleNumberClick(randomList[1])}>{randomList[1]}</div>
                    <div className="flex justify-center items-center text-[22px] w-20 h-10 font-bold hover:bg-gray-200 rounded-md" onClick={() => handleNumberClick(randomList[2])}>{randomList[2]}</div>
                </div>
                <div className="flex gap-10">
                    <div className="flex justify-center items-center text-[22px] w-20 h-10 font-bold hover:bg-gray-200 rounded-md" onClick={() => handleNumberClick(randomList[3])}>{randomList[3]}</div>
                    <div className="flex justify-center items-center text-[22px] w-20 h-10 font-bold hover:bg-gray-200 rounded-md" onClick={() => handleNumberClick(randomList[4])}>{randomList[4]}</div>
                    <div className="flex justify-center items-center text-[22px] w-20 h-10 font-bold hover:bg-gray-200 rounded-md" onClick={() => handleNumberClick(randomList[5])}>{randomList[5]}</div>
                </div>
                <div className="flex gap-10">
                    <div className="flex justify-center items-center text-[22px] w-20 h-10 font-bold hover:bg-gray-200 rounded-md" onClick={() => handleNumberClick(randomList[6])}>{randomList[6]}</div>
                    <div className="flex justify-center items-center text-[22px] w-20 h-10 font-bold hover:bg-gray-200 rounded-md" onClick={() => handleNumberClick(randomList[7])}>{randomList[7]}</div>
                    <div className="flex justify-center items-center text-[22px] w-20 h-10 font-bold hover:bg-gray-200 rounded-md" onClick={() => handleNumberClick(randomList[8])}>{randomList[8]}</div>
                </div>
            </div>
        </div>
    )
}

export default SuccessLogin