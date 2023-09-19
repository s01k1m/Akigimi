'use client'
import '@/styles/WriteReceipt.css'
import '@/styles/MainPageButton.css'
import ReceiptCircle from './ReceiptCircle'
import { ChangeEvent, useState, useRef, useEffect } from 'react'
import { AiOutlineFileAdd } from 'react-icons/ai'
import Image from 'next/image'
import { useRouter } from 'next/navigation'
import axios from 'axios'
import Modal from './Modal'

const ReceiptInput = () => {
    const [isOpened, setIsOpened] = useState<boolean>(false)
    const [formData, setFormData] = useState({
        notPurchasedItem: '',
        saving: '',
        akgimiPlace: '',
        content: '',
        isPublic: '',
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
    
    // form 제출
    const router = useRouter()
    const token = `eyJ0eXBlIjoiQUNDRVNTVE9LRU4iLCJhbGciOiJIUzI1NiJ9.eyJpZCI6OTk5OSwidXNlclN0YXRlIjoiUEVORElORyIsImlhdCI6MTY5NTA5MTQ0MywiZXhwIjoxNjk1MjcxNDQzfQ.ZtvhjRaPo4LfBdi8RHzm5giPsH6RP1luAVgj8EY_VDI`
    const handleSubmit = async (e: React.FormEvent<HTMLFormElement>) => {
        e.preventDefault()
        
        console.log(formData)
        console.log(typeof(formData.saving))
        
        // 제출 api
        await axios
            .post('http://25.4.167.82:8080/feed', formData, {
                headers: {
                    'Authorization': `Bearer ${token}`,
                    'Content-Type': 'multipart/form-data'
                }
            })
            .then((response) => {
                console.log('절약 기록 작성 성공', response.data)
            })
            .then(() => {
                // feed 페이지로 이동
                router.push('/feed')
            })
            .catch((error) => {
                console.log('절약 기록 작성 실패', error)
                // 잔액이 부족한 경우 모달창 띄우기
                // setIsOpened(true)
            })
        
        
    }
    // content 글자수 세기
    const [inputCount, setInputCount] = useState<number>(0)

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
                        <div className='absolute'>
                            {imgSrc ? (
                                <Image 
                                src={imgSrc}
                                alt=""
                                width={100}
                                height={100}
                            />
                            ) : null} 
                        </div>
                        
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
                                value={formData.isPublic}
                                onClick={( e: ChangeEvent<HTMLInputElement>) => {
                                    const checkedValue: boolean = e.target.checked
                                    handleInputChange({ target: {name: "isPublic", value: checkedValue }})
                                }}
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
            {/* 추후 기능 */}
            <div className='sticky bottom-0'>
                {isOpened ? (
                    <Modal change={500} /> 
                ) : (
                    null
                )}
                
            </div>
        </div>
        
        </>
    )
}

export default ReceiptInput