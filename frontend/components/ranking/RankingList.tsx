import RankingItem from "./RankingItem"
const RankingList = () => {
    const rankingData = [
        {id: 1, imgUrl: '/kakao logo.png', userName: '영혼없는 고서영', product: '나이키 어쩌구 신발', gage: 80},
        {id: 2, imgUrl: '/kakao logo.png', userName: '영혼없는 고서영', product: '나이키 어쩌구 신발', gage: 70},
        {id: 3, imgUrl: '/kakao logo.png', userName: '영혼없는 고서영', product: '나이키 어쩌구 신발', gage: 60},
        {id: 4, imgUrl: '/kakao logo.png', userName: '영혼없는 고서영', product: '나이키 어쩌구 신발', gage: 80},
        {id: 5, imgUrl: '/kakao logo.png', userName: '영혼없는 고서영', product: '나이키 어쩌구 신발', gage: 80},
        {id: 6, imgUrl: '/kakao logo.png', userName: '영혼없는 고서영', product: '나이키 어쩌구 신발', gage: 80},
    ]
    return (
        <div>
            {rankingData.map((item: any) => (
                    <RankingItem
                        key={item.id}
                        id={item.id}
                        imgUrl={item.imgUrl}
                        userName={item.userName}
                        product={item.product}
                        gage={item.gage}
                    />
            ))

            }
        </div>
    )
}

export default RankingList