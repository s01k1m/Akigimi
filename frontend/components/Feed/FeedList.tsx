import FeedItem from "./FeedItem"

const FeedList = () => {
    // 피드 정보 api는 이곳에서 불러오자
    const feedItems: any = [
        { id: 1, userProfile:'/images/black.png', akimLocation: "투썸", akimItemName: "아메리카노", savePrice: 5100, userName: "코딩하는 조성찬1", image: "/images/black.png", description: "커피 참고 정신력으로 버텼다 내 자신 기특해! 만ㅇ갸에 글씨가 겁나게 길어지면 어떡햐냐오 그럼 삐져 나오네!! 20자는 그럼 어느정도느느 되야 200자라고 할 수 있ㅇㅇㅇㅇㅇㅇㅇ" },
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