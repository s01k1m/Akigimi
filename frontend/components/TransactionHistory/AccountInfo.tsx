'use client'
import Image from "next/image"
import { useState, useEffect } from "react"
import axios from "axios"

const AccountInfo = () => {
    // 로컬 스토리지에서 불러오기 or API 불러오기
    const [bank, setBank] = useState<string>("싸피 은행")
    const [account, setAccount] = useState<string>("1234-1234-1234-1234")
    const [balance, setBalance] = useState<number>(150000)

    let token: string = "";

    useEffect(() => {
        if (typeof window !== "undefined") {
        token = window.localStorage.getItem("access_token");
        }
        getAccountInfo()
    }, [balance]);

    const getAccountInfo = async () => {
        await axios 
            .get(`/api/account/amount`, {
                params: {
                    accountType: 'WITHDRAW'
                },
                headers: {
                    Authorization: `Bearer ${token}`,
                  },
            })
            .then((response) => {
                console.log('계좌 잔액 조회 성공', response.data)
                setBalance(response.data.data.balance)
            })
            .catch((error) => {
                console.log('계좌 잔액 조회 실패', error)
            })
    }


    return (
        <div className="flex ms-[10%] mt-[10vh]">
            <div>
                <Image
                    src="/ssafybank logo.png"
                    alt="싸피뱅크"
                    width={45}
                    height={45}
                    className="rounded-full"
                />
            </div>
            <div className="ms-[5%]">
                <div className="text-[15px] font-normal">{bank}</div>
                <div className="text-[#757575] text-[15px] mb-[1vh]">{account}</div>
                <div className="text-[20px] font-bold tracking-widest">{balance}원</div>
            </div>
        </div>
    )
}

export default AccountInfo