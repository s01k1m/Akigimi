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

    useEffect(() => {
        if (descriptionLength <= 35){
            setMargin(2)
        } else if (descriptionLength <= 55){
            setMargin(5)
        } else if (descriptionLength <= 66){
            setMargin(7)
        } else if (descriptionLength <= 82){
            setMargin(9)
        } else if (descriptionLength <= 91){
            setMargin(13)
        } else {
            setMargin(15)
        }
    })
    console.log(descriptionLength, margin)
    

    return (
        <div className={`mb-[${margin}vh] rounded-md`}>
            <div className="z-1 translate-y-[2vh] flex justify-center">
                <ReceiptCircle />
            </div>
            <div className="bg-[#F5F5F5] w-[100%] h-[45vh] flex flex-col justify-content items-center z-0 rounded-md">
                <div className="mt-[3vh] bg-[#F5F5F5] rounded-md">
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