'use client'
import ReceiptCircle from "../WriteReceipt/ReceiptCircle"
import FeedWriteInfo from "./FeedWriterInfo"
import { useState, useEffect } from "react"

interface ItemProps {
    imgUrl: string
    name: string
    place: string
    item: string
    price: number 
    image: string 
    isLiked: boolean 
    description: string
}

const FeedItem: React.FC<ItemProps> = ({ imgUrl, name, place, item, price, image, isLiked, description }) => {
    // 글자 수 게시별 마다 margin 다르게
    const descriptionLength: number = description.length
    const [margin, setMargin] = useState<number>(0)
    // 글자 수가 아닌 div 의 높이로 변경할 예정
    // 컴포넌트 렌더링 후 마진 작업이 들어가기 때문에 다른 방법을 찾아야 할 듯 함
    const calculateMargin = () => {
        if (descriptionLength <= 35){
            setMargin(2)
        } else if (descriptionLength <= 55){
            setMargin((margin) => 5)
        } else if (descriptionLength <= 66){
            setMargin(7)
        } else if (descriptionLength <= 82){
            setMargin(9)
        } else if (descriptionLength <= 91){
            setMargin(13)
        } else {
            setMargin(15)
        }
    }

    useEffect(() => {
        calculateMargin()
    }, [margin])
    
    console.log(descriptionLength, margin)

    return (
        <div className={`mb-[${margin}vh] rounded-md`}>
            <div className="z-1 translate-y-[2vh] flex justify-center">
                <ReceiptCircle />
            </div>
            <div className="bg-[#F5F5F5] w-[100%] h-[45vh] flex flex-col justify-content items-center z-0 rounded-md">
                <div className="mt-[3vh] bg-[#F5F5F5] rounded-md ">
                <FeedWriteInfo 
                imgUrl={imgUrl} 
                name={name} 
                place={place} 
                item={item} 
                price={price} 
                image={image} 
                isLiked={isLiked} 
                description={description}
                />
                </div>
            </div>
        </div>
    )
    }


export default FeedItem