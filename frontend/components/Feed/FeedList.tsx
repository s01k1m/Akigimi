import FeedItem from "./FeedItem"
import { useState, useEffect } from "react"
import axios from "axios"
import { useInView } from "react-intersection-observer";

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
    // 초기 피드 리스트
    const [feedItems, setFeedItems] = useState<FeedItem[]>([]);

    // 새로 불러오는 리스트의 값만 담을 변수
    let toCheckFeedId = []
 
    // 관찰할 객체에 ref 달기
    const [ref, inView] = useInView()

    // 요청 보낼 마지막 lastViewId
    const [lastViewId, setLastViewId] = useState<number>(9223372036854775807);

    // 다음 피드가 불러오질 때까지 로딩
    const [loading, setLoading] = useState<boolean>(false);

    // selectedValue 선택 된 값 파라미터로 넘기기
    
    const [feedData, setFeedData] = useState<[]>()
    const token = `eyJ0eXBlIjoiQUNDRVNTVE9LRU4iLCJhbGciOiJIUzI1NiJ9.eyJpZCI6OTk5OSwidXNlclN0YXRlIjoiUEVORElORyIsImlhdCI6MTY5NTI1NTQ5MywiZXhwIjoxNjk1NDM1NDkzfQ.Wwg5ar8uOp2xZmt6JO7aRyhPTHuIxduFcrx1pdV-vAM`

    // 피드 정보 api 호출하기 // 무한 스크롤 적용하기
    const getFeedData = async () => {
      setLoading(true)
        await axios
            .get('http://25.7.186.86:8080/api/feeds', {
                params: {
                  lastFeedId: 9,
                  numberOfFeed: 5
                },
                headers: {
                    'Authorization': `Bearer ${token}`
                }
            })
            .then((response) => {
                console.log('피드 조회 성공', response)

                // 요청 성공 시에 리스트 뒤로 붙여주기
                setFeedItems([...feedItems, ...(response.data.data.list)])

                // 새로 가져온 값만 리스트에 담아주기
                toCheckFeedId.push([response.data.data.list])

                // 첫 번째 요청 이후에 viewId 값 상태 변경해주기
                setLastViewId(toCheckFeedId[toCheckFeedId.length-1])
                console.log('마지막 피드의 아이디 값은?', lastViewId)

                
              })
            .then(() => {
              // 마지막 요청을 판단하는 방법

            })
            .catch((error) => {
                console.log('피드 조회 실패', error)
            })
            .finally (() => {
              setLoading(false)
            }
)
    }
    
    useEffect(() => {
      // inView가 true 일 때만 실행한다
      if (inView) {
        console.log(inView, '무한 스크롤 요청 중이에요')

        // 실행할 함수
        getFeedData()
      }
    }, [inView])

    return (
        <div className="mx-0">
            {feedItems && feedItems.length > 0 &&
            feedItems.map((item: any) => (
              <>
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
              </>
            ))}
            <div ref={ref}>스크롤을 내리고 있어요</div>
            {loading && <div>Loading...</div>}
        </div>
    )
}

export default FeedList