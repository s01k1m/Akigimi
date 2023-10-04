'use client'
import axios from "axios"
import { useState, useEffect, useRef } from "react"
import '@/styles/MainPageButton.css'
import Footer from "@/app/Footer"
import Image from "next/image"
import ReceiptCircle from "@/components/WriteReceipt/ReceiptCircle"
import { useRouter } from "next/navigation"
import { AiOutlineFileAdd } from 'react-icons/ai'


  
export default function addItem() {
    const router = useRouter()
    // 목표 기간 입력
    const [inputValue, setInputValue] = useState<number>();
    
    // 챌린지 정보 직접 입력
    const [productName, setProductName] = useState<string>();
    const [productPrice, setProductPrice] = useState<number>();
    
    const onChangeName = (e: React.ChangeEvent<HTMLInputElement>) => {
        setProductName(e.target.value)
    }

    const onChangePrice = (e: React.ChangeEvent<HTMLInputElement>) => {
        const price = parseFloat(e.target.value);
        if (!isNaN(price)) {
            setProductPrice(price);
        }   
    }

    // 사진 추가
    // 사진 input (원래 input 창을 숨기고 새로운 모양을 만들었음)
    const fileRef: any = useRef<HTMLInputElement>(null);
    const handleClick = () => {
        fileRef?.current?.click();
    };

    // 사진 url로 변경하여 미리보기 띄우기
    const [imgSrc, setImgSrc] = useState<string>("")
    const [file, setFile] = useState<any>()
    const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        const file = e.target.files?.[0]
        setFile(e.target.files?.[0])
        if (!file) return

        const fileReader = new FileReader()
        fileReader.readAsDataURL(file)
        fileReader.onload = (e) => {
            if (typeof e.target?.result === 'string') {
                setImgSrc(e.target?.result)
            } 
            
        }
    }

    // 토큰 가져오기
    let token: string = "";
    
    useEffect(() => {
        if (typeof window !== "undefined") {
        token = window.localStorage.getItem("access_token");
        }
    }, []);
    
    // 물건 추가 api 호출
    const startChallenge = async () => {
        console.log('api 호출 했을 때 파일 상태', file)
        if (typeof window !== "undefined") {
            token = window.localStorage.getItem("access_token");
            }
        const requestBody = {
            name: productName,
            detail: productName,
            price: productPrice,
            challengePeriod: inputValue,
            image: file,
            thumbnail: "",
            url: "",

        }

        await axios
            .post('/api/product/new', requestBody, {
                headers: {
                    Authorization: `Bearer ${token}`,
                    "Content-Type": "multipart/form-data",
                  },
            })
            .then((response) => {
                console.log('물건 직접 추가 성공', response)
                // 물건 추가 성공하면 물건 조회 페이지로 이동시키기
                router.push('/item')
            })
            .catch((error) => {
                console.log('물건 직접 추가 실패', error)
                alert('모든 정보 다 입력했나 확인하기')
            })
    }

return (
    <div>
        <div className="flex flex-col items-center mt-[3vh]">
            <div className="z-10 -mb-[15px]">
                <ReceiptCircle />
            </div>
            <div className="w-[70vw] h-[60vh] min-w-[320px] max-w-[4px] rounded-md bg-[#EEE] flex flex-col items-center">
                <div className="w-[40%] rounded-xl bg-[#D9D9D9] text-[25px] flex justify-center items-center tracking-widest font-semibold mt-[30px]">AKGIMI</div>
                <input 
                    type="text"
                    placeholder="미닝템을 입력하세요" 
                    value={productName}
                    onChange={onChangeName}
                    className="w-[70%] h-[7%] ps-[10px] rounded-lg bg-[#FFF] flex items-center text-[#757575] tracking-wide ps-[5px] mt-[4vh]" />
                <input 
                    type="number"
                    placeholder="가격을 입력하세요" 
                    value={productPrice}
                    onChange={onChangePrice}
                    className="w-[70%] h-[7%] ps-[10px] rounded-lg bg-[#FFF] flex items-center text-[#757575] tracking-wide ps-[5px] mt-[1vh]" />

                <div className="w-[70%] mt-5 text-[#757575] z-20">사진을 추가해주세요</div>
                <div className="bg-white mt-1 w-[70%] rounded-lg h-[18vh]">
                <div className='relative flex justify-center items-center'>
                        <input 
                            type="" 
                            name="photo"
                            className='h-[100px] relative' 
                            disabled
                        />
                        <div className='absolute'>
                            <AiOutlineFileAdd size={40} color="gray" onClick={handleClick} />
                        </div>
                            {imgSrc ? (
                            <div className='absolute mt-9' style={{ width: '200px', height: '130px', overflow: 'hidden' }}>
                                <Image 
                                    src={imgSrc}
                                    alt="preview"
                                    objectFit="cover"
                                    layout='fill'
                                />
                                <div className='absolute'>
                                <div onClick={handleClick} className='bg-white/50 rounded-md text-zinc-500 p-[2px]'>다른 사진</div>
                                </div>
                                </div>
                            ) : null} 
                        
                    </div>
                <input 
                        ref={fileRef}
                        type="file" 
                        name='photo'
                        onChange={handleChange}
                        accept="image/*"
                        hidden
                        />
                </div>
            </div>
          
            <button className="button-common-small blue-btn absolute bottom-[15vh] max-w-[300px]"
                onClick={startChallenge}
            >미닝템 추가하기</button>
        <Footer />
        </div>
    </div>
)
}