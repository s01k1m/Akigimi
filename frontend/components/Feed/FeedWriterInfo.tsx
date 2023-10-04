import FeedUserProfile from "./FeedUserProfile"
import FeedWriterReceiptInfo from "./FeedWriterReciptInfo"
import FeedImg from "./FeedImg"
import Description from "./Description"
import { useRouter } from "next/navigation"

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
    likedCount: number
}

const FeedWriteInfo: React.FC<ItemProps> = ({ imgUrl, name, place, item, price, image, isLiked, description, userId, feedId, likedCount }) => {
    const router = useRouter();
    const profileClick = () => {
        console.log('프로필 클릭함', userId)
        if (typeof window !== "undefined") {
            let myUserId = parseInt(window.sessionStorage.getItem("userId"));

            if (myUserId === userId) {
                // 나의 프로필을 누른 경우
                router.push('/user/mypage')
            } else {
                // 친구의 프로필을 누른 경우
                router.push(`/user/mypage/${userId}`)
            }
        }
        

    }
    return (
        <div>
            <div className="ms-[5vw] mb-[2vh]" onClick={profileClick}>
                <FeedUserProfile imgUrl={imgUrl} name={name} />
            </div>
            <div className="flex mb-[1vh]">
                <FeedWriterReceiptInfo place={place} item={item} price={price} />
            </div>
            <div className="mb-[1vh]">
                <FeedImg image={image} />
            </div>
            <div className="flex justify-start">
                <Description isLiked={isLiked} description={description} feedId={feedId} likedCount={likedCount} />
            </div>
        </div>
    )
}

export default FeedWriteInfo