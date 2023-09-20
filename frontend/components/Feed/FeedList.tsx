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

const initialFeedItems: FeedItem[] = [
    
    { id: 1 , userProfile:'/images/black.png', akimLocation: "투썸", akimItemName: "아메리카노", savePrice: 5100, userName: "코딩하는 조성찬1", image: "/images/black.png", description: "커피 참고 정신력으로 버텼다", isLikedFeed: true },
    { id: 2, userProfile:'/images/black.png', akimLocation: "투썸22", akimItemName: "아메리카노오", savePrice: 5100, userName: "코딩하는 조성찬2", image: "/images/black.png", description: "글시 적은 경우안녕하세요오호호호호하하하하하하 글씨가 몇자가 되면 마ㅇ인려려려 어 반가워 안녕 응 반갑다나 눈 아 응 그럼 82까지는 9해도 되 몇자ㅁ응 안녕 어 반", isLikedFeed: true },
    { id: 3, userProfile:'/images/black.png', akimLocation: "투썸33", akimItemName: "아메리카노호", savePrice: 5100, userName: "코딩하는 조성찬3", image: "/images/black.png", description: "어 화이팅", isLikedFeed: true },
    { id: 4, userProfile:'/images/black.png', akimLocation: "투썸33", akimItemName: "아메리카노호", savePrice: 5100, userName: "코딩하는 조성찬3", image: "/images/black.png", description: "어 화이팅", isLikedFeed: true },
    { id: 5, userProfile:'/images/black.png', akimLocation: "투썸33", akimItemName: "아메리카노호", savePrice: 5100, userName: "코딩하는 조성찬3", image: "/images/black.png", description: "어 화이팅", isLikedFeed: true },
    { id: 6, userProfile:'/images/black.png', akimLocation: "투썸33", akimItemName: "아메리카노호", savePrice: 5100, userName: "코딩하는 조성찬3", image: "/images/black.png", description: "어 화이팅", isLikedFeed: true },
    { id: 7, userProfile:'/images/black.png', akimLocation: "투썸33", akimItemName: "아메리카노호", savePrice: 5100, userName: "코딩하는 조성찬3", image: "/images/black.png", description: "어 화이팅", isLikedFeed: true },
    { id: 8, userProfile:'/images/black.png', akimLocation: "투썸33", akimItemName: "아메리카노호", savePrice: 5100, userName: "코딩하는 조성찬3", image: "/images/black.png", description: "어 화이팅", isLikedFeed: true },
    { id: 9, userProfile:'/images/black.png', akimLocation: "투썸33", akimItemName: "아메리카노호", savePrice: 5100, userName: "코딩하는 조성찬3", image: "/images/black.png", description: "어 화이팅", isLikedFeed: true },
    { id: 10, userProfile:'/images/black.png', akimLocation: "투썸33", akimItemName: "아메리카노호", savePrice: 5100, userName: "코딩하는 조성찬3", image: "/images/black.png", description: "어 화이팅", isLikedFeed: true },
    { id: 11, userProfile:'/images/black.png', akimLocation: "투썸33", akimItemName: "아메리카노호", savePrice: 5100, userName: "코딩하는 조성찬3", image: "/images/black.png", description: "어 화이팅", isLikedFeed: true },
    { id: 12, userProfile:'/images/black.png', akimLocation: "투썸33", akimItemName: "아메리카노호", savePrice: 5100, userName: "코딩하는 조성찬3", image: "/images/black.png", description: "어 화이팅", isLikedFeed: true },
    { id: 13, userProfile:'/images/black.png', akimLocation: "투썸33", akimItemName: "아메리카노호", savePrice: 5100, userName: "코딩하는 조성찬3", image: "/images/black.png", description: "어 화이팅", isLikedFeed: true },
    { id: 14, userProfile:'/images/black.png', akimLocation: "투썸33", akimItemName: "아메리카노호", savePrice: 5100, userName: "코딩하는 조성찬3", image: "/images/black.png", description: "어 화이팅", isLikedFeed: true },
    { id: 15, userProfile:'/images/black.png', akimLocation: "투썸33", akimItemName: "아메리카노호", savePrice: 5100, userName: "코딩하는 조성찬3", image: "/images/black.png", description: "어 화이팅", isLikedFeed: true },
    { id: 16, userProfile:'/images/black.png', akimLocation: "투썸33", akimItemName: "아메리카노호", savePrice: 5100, userName: "코딩하는 조성찬3", image: "/images/black.png", description: "어 화이팅", isLikedFeed: true },
    { id: 17, userProfile:'/images/black.png', akimLocation: "투썸33", akimItemName: "아메리카노호", savePrice: 5100, userName: "코딩하는 조성찬3", image: "/images/black.png", description: "어 화이팅", isLikedFeed: true },
    { id: 18, userProfile:'/images/black.png', akimLocation: "투썸33", akimItemName: "아메리카노호", savePrice: 5100, userName: "코딩하는 조성찬3", image: "/images/black.png", description: "어 화이팅", isLikedFeed: true },
    { id: 19, userProfile:'/images/black.png', akimLocation: "투썸33", akimItemName: "아메리카노호", savePrice: 5100, userName: "코딩하는 조성찬3", image: "/images/black.png", description: "어 화이팅", isLikedFeed: true },
    { id: 20, userProfile:'/images/black.png', akimLocation: "투썸33", akimItemName: "아메리카노호", savePrice: 5100, userName: "코딩하는 조성찬3", image: "/images/black.png", description: "어 화이팅", isLikedFeed: true },
    { id: 21, userProfile:'/images/black.png', akimLocation: "투썸33", akimItemName: "아메리카노호", savePrice: 5100, userName: "코딩하는 조성찬3", image: "/images/black.png", description: "어 화이팅", isLikedFeed: true },
    { id: 22, userProfile:'/images/black.png', akimLocation: "투썸33", akimItemName: "아메리카노호", savePrice: 5100, userName: "코딩하는 조성찬3", image: "/images/black.png", description: "어 화이팅", isLikedFeed: true },
    { id: 23, userProfile:'/images/black.png', akimLocation: "투썸33", akimItemName: "아메리카노호", savePrice: 5100, userName: "코딩하는 조성찬3", image: "/images/black.png", description: "어 화이팅", isLikedFeed: true },
    { id: 24, userProfile:'/images/black.png', akimLocation: "투썸33", akimItemName: "아메리카노호", savePrice: 5100, userName: "코딩하는 조성찬3", image: "/images/black.png", description: "어 화이팅", isLikedFeed: true },

]

const FeedList: React.FC<selectedProps> = ({ selectedValue }) => {
    const [visibleItems, setVisibleItems] = useState<number>(4);
    const [feedItems, setFeedItems] = useState<FeedItem[]>(initialFeedItems);
    const [loading, setLoading] = useState<boolean>(false);

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

    const fetchData = async () => {
      setLoading(true);
      // Simulated delay
      await new Promise((resolve) => setTimeout(resolve, 2000));
        
      const nextBatchSize = 4
      const newVisibleItems = Math.min(visibleItems + nextBatchSize, feedItems.length);
      const newItemsToDisplay = feedItems.slice(visibleItems, newVisibleItems)
      
      setVisibleItems(newVisibleItems);
      setFeedItems((prevFeedItems) => [...prevFeedItems, ...newItemsToDisplay])
      setLoading(false)
    }

    useEffect(() => {
        const handleScroll = () => {
          if (
            window.innerHeight + document.documentElement.scrollTop ===
            document.documentElement.offsetHeight
          ) {
            fetchData();
          }
        }
    
        window.addEventListener("scroll", handleScroll);
        return () => {
          window.removeEventListener("scroll", handleScroll);
        }
      }, [visibleItems])

    return (
        <div>
            {feedItems.slice(0, visibleItems).map((item: any) => (
                <FeedItem 
                    key={item.id} 
                    itemId={item.id}
                    imgUrl={item.userProfile} 
                    name={item.userName} 
                    place={item.akimLocation} 
                    item={item.akimItemName} 
                    price={item.savePrice} 
                    image={item.image} 
                    isLiked={item.isLikedFeed} 
                    description={item.description} />
            ))}
            {loading && <div>Loading...</div>}
        </div>
    )
}

export default FeedList