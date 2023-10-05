'use client'
import axios from "axios"
import { useState, useEffect } from "react"
import '@/styles/MainPageButton.css'
import Footer from "@/app/Footer"
import Image from "next/image"
import ReceiptCircle from "@/components/WriteReceipt/ReceiptCircle"
import { useRouter } from "next/navigation"

type PageParams = {
    itemId: number
  }
  
export default function page({ params }: { params: PageParams }) {
    const itemId = params.itemId
    const router = useRouter();
    // 목표 기간 입력
    const [inputValue, setInputValue] = useState<number>();
    
    // 챌린지 기본 정보
    const [productName, setProductName] = useState<string>("");
    const [productPrice, setProductPrice] = useState<number>();
    const [productImg, setProductImg] = useState<string>();
    
    // 토큰 가져오기
    let token: string = "";
    
    useEffect(() => {
        if (typeof window !== "undefined") {
        token = window.localStorage.getItem("access_token");
        }
    }, []);
    
    // 챌린지 시작 api 호출
    const startChallenge = async () => {
        if (typeof window !== "undefined") {
            token = window.localStorage.getItem("access_token");
            }
        const requestBody = {
            challengePeriod: inputValue,
            itemId: itemId
        }
        await axios
            .post('/api/challenges', requestBody, {
                headers: {
                    Authorization: `Bearer ${token}`,
                    "Content-Type": "application/json",
                  },
            })
            .then((response) => {
                console.log('챌린지 다시 시작 성공', response)
                // 챌린지 다시 시작 제출 성공 하면 메인 페이지로 보내기
                router.push('/main')
            })
            .catch((error) => {
                console.log('챌린지 다시 시작 실패', error)
                if (error.response.data.message === "이미 챌린지 진행중입니다.") {
                    alert('이미 챌린지를 진행중이에요.')
                }
                // 챌린지를 도전 중인 유저가 챌린지를 시작하려고 하면 메인페이지로 보내기
                router.push('/main')
            })
    }
    
    // 아이템 아이디로 아이템 기본 정보 가져오는 api 호출
    const getItemInfo = async () => {
        await axios
            .get(`/api/product/detail?id=${itemId}`, {
                headers: {
                    Authorization: `Bearer ${token}`,
                    "Content-Type": "multipart/form-data",
                  },
            })
            .then((response) => {
                console.log('아이템 기본 정보 조회 성공', response.data)
                setProductName(response.data.data.name)
                setProductPrice(response.data.data.price)
                setProductImg(response.data.data.image)
            })
            .catch((error) => {
                console.log('아이템 기본 정보 조회 실패', error)
            })
    }
    
    useEffect(() => {
        getItemInfo()
    }, [])
return (
    <div>
        <div className="flex flex-col items-center mt-[2vh]">
            <div className="z-10 -mb-[15px]">
                <ReceiptCircle />
            </div>
            <div className="w-[70vw] h-[63vh] min-w-[320px] max-w-[4px] rounded-md bg-[#EEE] flex flex-col items-center ">
                <div className="w-[40%] rounded-xl bg-[#D9D9D9] text-[25px] flex justify-center items-center tracking-widest font-semibold mt-[30px]">AKGIMI</div>
                <div className="w-[70%] h-[7%] ps-[10px] rounded-lg bg-[#FFF] flex items-center text-[#757575] tracking-wide ps-[5px] mt-[4vh]">{productName}</div>
                <div className="w-[70%] h-[7%] ps-[10px] rounded-lg bg-[#FFF] flex items-center text-[#757575] tracking-wide ps-[5px] mt-[1vh]">{productPrice}</div>
                <div className="h-[120px]"></div>
                <div className="absolute mt-[30vh] rounded-lg" style={{ width: '220px', height: '100px'}}>
                    <Image
                        src={productImg}
                        alt="아이템 물건"
                        className="-mt-8 rounded-lg"
                        layout="fill"
                        objectFit="cover"
                    />
                </div>
                <div className="w-[70%] mt-5 text-[#757575] z-20">목표기간을 정해주세요</div>
                <div className="flex z-10 justify-center gap-5 -mt-2">
                    <div className="rounded-full w-8 h-8 bg-[#EEE]"></div>
                    <div className="rounded-full w-8 h-8 bg-[#EEE]"></div>
                    <div className="rounded-full w-8 h-8 bg-[#EEE]"></div>
                    <div className="rounded-full w-8 h-8 bg-[#EEE]"></div>
                </div>
                <div className="w-[70%] h-[15vh] bg-white rounded-lg -mt-[15px] flex items-center">
                    <button className="ms-3 w-[50px] h-[50px] px-3 focus:bg-[#EEE] rounded-full" onClick={() => setInputValue(10)}>10</button>
                    <button className="w-[50px] h-[50px] focus:bg-[#EEE] rounded-full" onClick={() => setInputValue(20)}>20</button>
                    <button className="w-[50px] h-[50px] focus:bg-[#EEE] rounded-full" onClick={() => setInputValue(30)}>30</button>
                    <button className="w-[50px] h-[50px] focus:bg-[#EEE] rounded-full" onClick={() => setInputValue(40)}>40</button>
                </div>
            </div>
          
            <button className="button-common-small blue-btn absolute bottom-[15vh] max-w-[300px]"
                onClick={startChallenge}
            >챌린지 시작</button>
        <Footer />
        </div>
    </div>
)
}