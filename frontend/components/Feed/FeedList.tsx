import FeedItem from "./FeedItem"
import { useState, useEffect } from "react"
import axios from "axios"

type FeedItem = {
    id: number;
    userProfile: string;
    akimLocation: string;
    akimItemName: string;
    savePrice: number;
    userName: string;
    image: string;
    isLikedFeed: boolean;
    description: string;
  };
type selectedProps =  {
    selectedValue: string
}

const FeedList: React.FC<selectedProps> = ({ selectedValue }) => {
    const [visibleItems, setVisibleItems] = useState<number>(4);
    const [feedItems, setFeedItems] = useState<FeedItem[]>();
    const [loading, setLoading] = useState<boolean>(false);

    // selectedValue 선택 된 값 파라미터로 넘기기
    
    const [feedData, setFeedData] = useState<[]>()
    const token = `eyJ0eXBlIjoiQUNDRVNTVE9LRU4iLCJhbGciOiJIUzI1NiJ9.eyJpZCI6OTk5OSwidXNlclN0YXRlIjoiUEVORElORyIsImlhdCI6MTY5NTI1NTQ5MywiZXhwIjoxNjk1NDM1NDkzfQ.Wwg5ar8uOp2xZmt6JO7aRyhPTHuIxduFcrx1pdV-vAM`

    // 피드 정보 api 호출하기
    const getFeedData = async () => {
        await axios
            .get('http://25.7.186.86:8080/api/feeds', {
                params: {
                  lastFeedId: 100,
                  numberOfFeed: 5
                },
                headers: {
                    'Authorization': `Bearer ${token}`
                }
            })
            .then((response) => {
                console.log('피드 조회 성공', response)
                setFeedItems(response.data.data.list)
            })
            .catch((error) => {
                console.log('피드 조회 실패', error.response)
            })
    }
    
    useEffect(() => {
      getFeedData()
    }, [])

    return (
        <div className="mx-0">
            {feedItems && feedItems.length > 0 &&
            feedItems.slice(0, visibleItems).map((item: any) => (
                <FeedItem 
                    key={item.id} 
                    itemId={item.id}
                    imgUrl={item.userProfile} 
                    name={item.nickname} 
                    place={item.akgimiPlace} 
                    item={item.notPurchasedItem} 
                    price={item.price} 
                    image={item.photo} 
                    isLiked={item.isLikedFeed} 
                    description={item.content} />
            ))}
            {loading && <div>Loading...</div>}
        </div>
    )
}

export default FeedList