'use client'
import { useState, useEffect, useRef } from "react"
import axios from "axios"


const Modal = () => {
    // 계좌 잔액 상태 관리
    const [balance, setBalance] = useState<number>()
    const modalRef = useRef<HTMLDivElement>(null)

    // 계좌 조회 API
    const checkBalance = async () => {
        
        let token: string = "";

        useEffect(() => {
            if (typeof window !== "undefined") {
            token = window.localStorage.getItem("access_token");
            }
        }, []);

        await axios
        .get('/api/account/amount', {
            params: {
                accountType: 'DEPOSIT'
            },
            headers: {
                'Authorization': `Bearer ${token}`,
            }
        })
        .then((response) => {
            setBalance(response.data.data.balance)
            console.log('계좌 잔액 조회 성공', response.data)
            
        })
        .catch((error) => {
            console.log('계좌 잔액 조회 실패', error)
        })
    }

    useEffect(() => {
        // checkBalance()
    }, [])
    
    return (
        <div>
            <div className="relative h-[250px] bg-white rounded-2xl drop-shadow-lg p-8">
                <div className="absolute right-7 text-[20px]"
                >X</div>
                {/* use 정보는 redux에서 가져오자 */}
                <p className="modal-big-text pb-2">{'잘생긴 김솔'}님,</p>
                <div className="modal-big-text pb-2">출금계좌의 잔액을</div>
                <div className="modal-big-text">확인해주세요.</div>
                <p className="modal-small-text mt-[3vh]">현재 잔액은 {balance}원이에요.</p>
            </div>
        </div>
    )}


export default Modal