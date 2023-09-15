'use client'
import '@/styles/WriteReceipt.css'
import '@/styles/MainPageButton.css'
import ReceiptCircle from './ReceiptCircle'
import { ChangeEvent, useState, useRef } from 'react'
import { AiOutlineFileAdd } from 'react-icons/ai'
import Image from 'next/image'
// import Modal from '../Main/Modal'

const ReceiptInput = () => {
    const [formData, setFormData] = useState({
        notPurchasedItem: '',
        saving: '',
        akgimPlace: '',
        photo: '',
        content: '',
        isOpened: ''
    })
    
    const handleInputChange = (e: ChangeEvent<HTMLInputElement>) => {
        const { name, value } = e.target
        if (name === 'photo') {
            const file = value
           
        }
        
        setFormData({
            ...formData,
            [name]: value,
        });
    }
    
    const handleSubmit = (e: React.FormEvent<HTMLFormElement>) => {
        e.preventDefault()
        console.log(formData)
    }
    
    // 사진 추가
    const fileRef: any = useRef<HTMLInputElement>(null);
    const handleClick = () => {
        fileRef?.current?.click();
    };
    

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
                        name="akgimPlace"
                        value={formData.akgimPlace}
                        onChange={handleInputChange}
                        placeholder="장소를 입력해주세요" 
                        />
                    <div className='relative flex justify-center items-center'>
                        <input 
                            type="" 
                            name="photo"
                            value={formData.photo}
                            className='h-[100px] relative' 
                            disabled
                            />
                        <div className='absolute'>
                            <AiOutlineFileAdd size={40} color="gray" onClick={handleClick} />
                        </div>
                        {/* <Image 
                            src={imageUrl}
                            alt=""
                            width={50}
                            height={50}
                        /> */}
                        
                    </div>
                    {/* 사진 첨부 form hidden  ref 전달*/}
                    <input 
                        ref={fileRef}
                        type="file" 
                        name='photo'
                        onChange={e => onUpload(e)}
                        accept="image/*"
                        hidden
                        />
                    <input 
                        type="text" 
                        name="content"
                        value={formData.content}
                        onChange={handleInputChange}
                        className='h-[100px]' 
                        placeholder='내용을 입력해주세요'
                        />
                    <div className='flex mt-1 mb-8 pe-28'>
                        {/* 공개 여부 토글 */}
                        <p className='pe-4'>공개 여부</p>
                        <input 
                            type="checkbox" 
                            name="isOpened"
                            value={formData.isOpened}
                            onClick={( e: ChangeEvent<HTMLInputElement>) => {
                                const checkedValue: boolean = e.target.checked
                                console.log(checkedValue)
                                handleInputChange({ target: {name: "isOpened", value: checkedValue }})
                            }}
                            className="switch" 
                        />  
                    </div>
                </form>
            </div>
            <div className='flex justify-center z-0 -mt-4'>
                <ReceiptCircle />
            </div>
            <div className='flex justify-center mt-2'>
                <button type="button" className="button-common-small blue-btn" onClick={handleSubmit}>기록 남기기</button>
            </div>
            {/* <Modal /> */}
        </div>
        
        </>
    )
}

export default ReceiptInput