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
    notPurchasedItem: string;
    saving: number;
    akgimiPlace: string;
    content: string;
    isPublic: boolean;
    photo: string;
  }

const ReceiptInput = () => {
    // 잔액 부족한 경우 모달 창 관리
    const [isOpened, setIsOpened] = useState<boolean>(false)

    // formData 데이터 한 번에 관리
    const [formData, setFormData] = useState({
        notPurchasedItem: '',
        saving: '',
        akgimiPlace: '',
        content: '',
        isPublic: false,
        photo: '',
    })
    
    // input 값에 변화가 생기면 FormData에 담아주기
    const handleInputChange = (e: ChangeEvent<HTMLInputElement | HTMLTextAreaElement>) => {
        const { name, value } = e.target

        let parsedValue: string | number = value;
        if (name === 'saving') {
            parsedValue = parseFloat(value);
        }

        setFormData({
            ...formData,
            [name]: parsedValue,
        });
        
    
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

    // 공개 토글
    const handleCheckboxClick: MouseEventHandler<HTMLInputElement> = (event) => {
        const checkedValue: boolean = event.currentTarget.checked
        setFormData({ ...formData, isPublic: checkedValue})
      };

    // form 제출
    const router = useRouter()

    // token
    let token: string = "";
    const [nickname, setNickName] = useState<string>();
    useEffect(() => {
        if (typeof window !== "undefined") {
            token = window.localStorage.getItem("access_token");
            setNickName(window.sessionStorage.getItem("nickname"));
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
                } else if (code === '014') {
                    console.log('계좌에 돈이 부족합니다')
                    setIsOpened(true)
                } else if (code === '001'){
                    alert('빠짐없이 입력해주세요')
                }
                console.log('절약 기록 작성 실패', error)
            })
        
        
    }
    // content 글자수 세기
    const [inputCount, setInputCount] = useState<number>(0)

    // 계좌 잔액
    const [balance, setBalance] = useState<number>(0)
      
      // 계좌 조회 API
      const checkBalance = async () => {       
        await axios
        .get('/api/account/amount', {
            params: {
                accountType: 'WITHDRAW'
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
        checkBalance()
    }, [balance])

    

    return (
        <>
        <div>
           
            <div className='flex flex-col items-center bg-[#EEE] w-[70vw] max-w-[330px] min-w-[300px]'>
                <div className='logo mb-3 mt-6'>AKGIMI</div>
                <form id='myForm' className='flex flex-col items-center'>
                    <input 
                        type="text" 
                        name="notPurchasedItem"
                        value={formData.notPurchasedItem}
                        onChange={handleInputChange}
                        placeholder="끼밍템을 입력해주세요" 
                    />
                    <input 
                        type="number" 
                        name="saving" 
                        value={formData.saving}
                        onChange={handleInputChange}
                        placeholder="가격을 입력해주세요"
                    />
                    <input 
                        type="text" 
                        name="akgimiPlace"
                        value={formData.akgimiPlace}
                        onChange={handleInputChange}
                        placeholder="장소를 입력해주세요" 
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
                        <div className='flex justify-between mt-2 mb-6 w-[100%]'>
                            {/* 공개 여부 토글 */}
                            <p>공개 여부</p>
                            <input 
                                type="checkbox" 
                                name="isOpened"
                                defaultChecked={formData.isPublic}
                                onClick={handleCheckboxClick}
                                className="switch me-2" 
                            />
                            <div className='ms-2'>{inputCount}/200</div>  
                        </div>
                </form>
            </div>
            <div className='flex justify-center z-0 -mt-4'>
                <ReceiptCircle />
            </div>
            <div className='flex justify-center mt-2'>
                <button type="button" className="button-common-small blue-btn" onClick={handleSubmit}>기록 남기기</button>
            </div>

            <div
                className={`fixed inset-0 ${
                isOpened ? 'bg-gray-500 bg-opacity-30' : 'hidden'
                }`}
                onClick={() => setIsOpened(false)}
            ></div>

            <div className='z-10 fixed bottom-0 w-[100%] max-w-[500px] min-w-[490px] flex justfiy-center -ms-[85px]'>
                {isOpened ? (
                    // 잔액 부족 시 모달 창 띄우기
                    <div className='w-[100%]'>
                        <div className="relative h-[250px] bg-white rounded-2xl drop-shadow-lg p-8 flex flex-col justify-start ps-[20vw]">
                        <p className="modal-big-text pb-2">{nickname}님,</p>
                        <div className="modal-big-text pb-2">출금계좌의 잔액을</div>
                        <div className="modal-big-text">확인해주세요.</div>
                        <p className="modal-small-text mt-[3vh]">현재 잔액은 {balance}원이에요.</p>
                    </div>
            </div>
                ) : (
                    null
                )}
                
            </div>
            </div>
        
        </>
    )
}

export default ReceiptInput