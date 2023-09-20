import FeedItem from "./FeedItem"
import { useState } from "react"
import axios from "axios"

type selectedProps =  {
    selectedValue: string
}

const FeedList: React.FC<selectedProps> = ({ selectedValue }) => {
    // selectedValue 선택 된 값 파라미터로 넘기기
    
    // const [feedData, setFeedData] = useState<[]>()
    // const token = sessionStorage.getItem('token')
    // // 피드 정보 api 호출하기
    // const getFeedData = async () => {
    //     await axios
    //         .get(`/feeds?lastFeedtId=${lastFeedtId}?count=${count}`, {
    //             headers: {
    //                 'Authorization': `Bearer ${token}`
    //             }
    //         })
    //         .then((response) => {
    //             console.log(response)
    //             setFeedData(response.data.list)
    //         })
    //         .catch((error) => {
    //             console.error('error')
    //         })
    // }

    const feedItems: any = [
        { id: 1 , userProfile:'/images/black.png', akimLocation: "투썸", akimItemName: "아메리카노", savePrice: 5100, userName: "코딩하는 조성찬1", image: "/images/black.png", description: "커피 참고 정신력으로 버텼다" },
        { id: 2, userProfile:'/images/black.png', akimLocation: "투썸22", akimItemName: "아메리카노오", savePrice: 5100, userName: "코딩하는 조성찬2", image: "/images/black.png", description: "글시 적은 경우안녕하세요오호호호호하하하하하하 글씨가 몇자가 되면 마ㅇ인려려려 어 반가워 안녕 응 반갑다나 눈 아 응 그럼 82까지는 9해도 되 몇자ㅁ응 안녕 어 반" },
        { id: 3, userProfile:'/images/black.png', akimLocation: "투썸33", akimItemName: "아메리카노호", savePrice: 5100, userName: "코딩하는 조성찬3", image: "/images/black.png", description: "어 화이팅" },
    ]
    return (
        <div>
            {feedItems.map((item: any) => (
                <FeedItem 
                    key={item.id} 
                    imgUrl={item.userProfile} 
                    name={item.userName} 
                    place={item.akimLocation} 
                    item={item.akimItemName} 
                    price={item.savePrice} 
                    image={item.image} 
                    isLiked={item.isLikedFeed} 
                    description={item.description} />
            ))}
        </div>
    )
}

export default FeedList