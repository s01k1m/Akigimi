'use client'
import '@/styles/WriteReceipt.css'
import '@/styles/MainPageButton.css'
import ReceiptCircle from './ReceiptCircle'
import React, { ChangeEvent, useState, useRef, useEffect } from 'react'
import { MouseEventHandler, FormEvent  } from 'react'
import { AiOutlineFileAdd } from 'react-icons/ai'
import Image from 'next/image'
import { useRouter } from 'next/navigation'
import axios from 'axios'



interface FormData {
    content: string;
    photo: string;
  }

const ReviewInput = () => {
    // formData 데이터 한 번에 관리
    const [formData, setFormData] = useState({
        content: '',
        photo: '',
    })
    
    // input 값에 변화가 생기면 FormData에 담아주기
    const handleInputChange = (e: ChangeEvent<HTMLInputElement | HTMLTextAreaElement>) => {
        const { name, value } = e.target

        // 글자수 세기
        if (e.target.placeholder === '내용을 입력해주세요') {
            setInputCount(e.target.value.length)
        }
    }
    
    
    // 사진 input (원래 input 창을 숨기고 새로운 모양을 만들었음)
    const fileRef: any = useRef<HTMLInputElement>(null);
    const handleClick = () => {
        fileRef?.current?.click();
    };
    
    // 사진 url로 변경하여 미리보기 띄우기
    const [imgSrc, setImgSrc] = useState<string>("")
    const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        const file = e.target.files?.[0]
        if (!file) return

        setFormData((prevFormData: any) => ({
            ...prevFormData,
            photo: file
        }))

        

        const fileReader = new FileReader()
        fileReader.readAsDataURL(file)
        fileReader.onload = (e) => {
            if (typeof e.target?.result === 'string') {
                setImgSrc(e.target?.result)
            } 
            
        }
    }

    // form 제출
    const router = useRouter()

    // token
    let token: string = "";
    useEffect(() => {
        if (typeof window !== "undefined") {
            token = window.localStorage.getItem("access_token");
        }
    }, [token])

    const handleSubmit = async (e: React.FormEvent<HTMLFormElement> | React.MouseEvent<HTMLButtonElement>) => {
        e.preventDefault()
        
        console.log(formData)

        if (typeof window !== "undefined") {
            token = window.localStorage.getItem("access_token");
        }
     
        if (formData.photo === ""){
            alert('사진을 추가해주세요')
            return
        } 
        // 제출 api
        await axios
            .post('/api/feeds', formData, {
                headers: {
                    'Authorization': `Bearer ${token}`,
                    'Content-Type': 'multipart/form-data'
                }
            })
            .then((response) => {
                console.log('절약 기록 작성 성공', response.data)

            })
            .then(() => {
                // main 챌린지 페이지로 이동
                router.push('/feed')
            })
            .catch((error) => {
                let code: string = error.response.data.code
                if (code === '012') {
                    alert('현재 참여중인 챌린지가 없어요 챌린지를 등록해주세요')
                } else if (code === '001'){
                    alert('빠짐없이 입력해주세요')
                }
                console.log('절약 기록 작성 실패', error)
            })
        
        
    }
    // content 글자수 세기
    const [inputCount, setInputCount] = useState<number>(0)

    

    return (
        <>
        <div>
            <div className='flex flex-col items-center bg-[#EEE] w-[70vw] max-w-[330px] min-w-[300px] h-[65vh]'>
                <div className='logo mb-3 mt-6'>AKGIMI</div>
                <form id='myForm' className='flex flex-col items-center'>
                    <input 
                        type="text" 
                        name="notPurchasedItem"
                        placeholder="끼밍템을 입력해주세요"
                        disabled 
                    />
                    <input 
                        type="number" 
                        placeholder="가격을 입력해주세요"
                        disabled
                    />
                    <input 
                        type="text" 
                        placeholder="장소를 입력해주세요" 
                        disabled
                    />
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
                            <div className='absolute -mt-[5px]' style={{ width: '200px', height: '95px', overflow: 'hidden' }}>
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
                    {/* 사진 첨부 form hidden  ref 전달*/}
                    <input 
                        ref={fileRef}
                        type="file" 
                        name='photo'
                        onChange={handleChange}
                        accept="image/*"
                        hidden
                        />
                    {/* content 입력 */}
                    <textarea
                        name="content"
                        value={formData.content}
                        onChange={handleInputChange}
                        className='h-[100px] w-[100%]'
                        maxLength={200}
                        placeholder='내용을 입력해주세요'
                        />
                </form>
            </div>
            <div className='flex justify-center z-0 -mt-4'>
                <ReceiptCircle />
            </div>
            <div className='flex justify-center mt-2'>
                <button type="button" className="button-common-small blue-btn" onClick={handleSubmit}>기록 남기기</button>
            </div>
            </div>
        
        </>
    )
}

export default ReviewInput