'use client'
import { useEffect, useState } from "react"

interface FeedWriterReceiptInfoProps {
    place: string
    item: string
    price: number
}

const FeedWriterReceiptInfo: React.FC<FeedWriterReceiptInfoProps> = ({ place, item, price }) => {
    // 내용이 너무 긴 경우
    const [content, setContent] = useState<boolean>(false)
    useEffect(() => {
        if (place.length + item.length > 12) {
            setContent(true)
        }
    }, [])

    // 좋아요 개수 받기
    return (
        <>
        {content ? (
            <div>

                <div className="flex ms-[5vw]">
                    <p className="me-[2vw]">{place}</p>
                </div>
                <div className="flex ms-[5vw]">
                    <p className="me-[2vw]">{item}</p>
                    <p className="me-[2vw] font-semibold">{price.toLocaleString()}원</p> 
                </div>
            </div>
        ) : (
          <div className="flex ms-[5vw]">
            <p className="me-[2vw]">{place}</p>
            <p className="me-[2vw]">{item}</p>
            <p className="me-[2vw] font-semibold">{price.toLocaleString()}원</p>
          </div>
        )}
      </>
    );
  };

export default FeedWriterReceiptInfo