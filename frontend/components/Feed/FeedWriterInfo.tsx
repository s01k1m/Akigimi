import FeedUserProfile from "./FeedUserProfile"
import FeedWriterReceiptInfo from "./FeedWriterReciptInfo"
import FeedImg from "./FeedImg"
import Description from "./Description"

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

const FeedWriteInfo: React.FC<ItemProps> = ({ imgUrl, name, place, item, price, image, isLiked, description }) => {
    
    // api 가져오기
    return (
        <div>
            <div className="ms-[5vw] mb-[2vh]">
                <FeedUserProfile imgUrl={imgUrl} name={name} />
            </div>
            <div className="flex mb-[1vh]">
                <FeedWriterReceiptInfo place={place} item={item} price={price} />
            </div>
            <div className="mb-[1vh]">
                <FeedImg image={image} />
            </div>
            <div className="flex justify-center">
                <Description isLiked={isLiked} description={description} />
            </div>
        </div>
    )
}

export default FeedWriteInfo