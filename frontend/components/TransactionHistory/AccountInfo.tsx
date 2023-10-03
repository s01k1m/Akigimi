'use client'
import Image from "next/image"
import { useState, useEffect } from "react"
import axios from "axios"

const AccountInfo = ({ type }) => {
    const [bank, setBank] = useState<string>("싸피 은행")
    const [account, setAccount] = useState<string>("")
    const [balance, setBalance] = useState<number>()
    const [bankImg, setBankImg] = useState<string>("/images/ssafybank logo.png")
    console.log('지금 계좌의 타입은', type)
    useEffect(() => {
        if (type === 'DEPOSIT'){
            setBank("멀티 은행")
            setBankImg("/images/multi logo.png")
        }
    }, [])
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
                    accountType: `${type}`
                },
                headers: {
                    Authorization: `Bearer ${token}`,
                  },
            })
            .then((response) => {
                console.log('계좌 잔액 조회 성공', response.data)
                setBalance(response.data.data.balance)
                setAccount(response.data.data.accountNumber)
            })
            .catch((error) => {
                console.log('계좌 잔액 조회 실패', error)
            })
    }


    return (
        <div className="flex ms-[10%] mt-[10vh]">
            <div>
                <Image
                    src={bankImg}
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