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
    userId: number
    feedId: number
}

const FeedWriteInfo: React.FC<ItemProps> = ({ imgUrl, name, place, item, price, image, isLiked, description, userId, feedId }) => {
    return (
        <div>
            <div className="ms-[5vw] mb-[2vh]" onClick={() => console.log('프로필 클릭함')}>
                {/* TODO 프로필 누르면 유저 페이지로 이동 */}
                <FeedUserProfile imgUrl={imgUrl} name={name} />
            </div>
            <div className="flex mb-[1vh]">
                <FeedWriterReceiptInfo place={place} item={item} price={price} />
            </div>
            <div className="mb-[1vh]">
                <FeedImg image={image} />
            </div>
            <div className="flex justify-start">
                <Description isLiked={isLiked} description={description} feedId={feedId} />
            </div>
        </div>
    )
}

export default FeedWriteInfo