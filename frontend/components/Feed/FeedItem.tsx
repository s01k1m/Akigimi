'use client'
import ReceiptCircle from "../WriteReceipt/ReceiptCircle"
import FeedWriteInfo from "./FeedWriterInfo"
import { useState, useEffect, useRef } from "react"

interface ItemProps {
    itemId: number
    imgUrl: string
    name: string
    place: string
    item: string
    price: number 
    image: string 
    isLiked: boolean 
    description: string
}

const FeedItem: React.FC<ItemProps> = ({ imgUrl, name, place, item, price, image, isLiked, description, itemId }) => {

    
    return (
        <div className="rounded-md w-[100%] flex flex-col items-center">
            <div className="z-10 translate-y-[2vh] flex justify-center">
                <ReceiptCircle />
            </div>
            <div className="bg-[#F5F5F5] w-[100%] h-[100%] flex flex-col justify-content items-center z-0 rounded-md">
                <div className="mt-[3vh] bg-[#F5F5F5] rounded-md ">
                <div>{itemId}</div>
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