import axios from "axios"
import { useEffect, useState } from "react"
import RankingItem from "./RankingItem"

interface RankingPropsType {
    selectedValue: string
}

const RankingList: React.FC<RankingPropsType> = ({ selectedValue }) => {
    const [rankingList, setRankingList] = useState<[]>([])
    // api 받아 오기 selectedValue로 구분 지어서 부르기
    const getRankingList = async () => {
        await axios
            .get(`/api/ranking/${selectedValue}`)
            .then((response) => {
                console.log('랭킹 리스트 조회 성공')
                setRankingList(response.data.data.list)
            })
            .catch((error) => {
                console.log('랭킹 리스트 조회 실패', error)
            })
    }

    useEffect(() => {
        getRankingList()
    }, [])
    // 무한 스크롤
    const rankingData = [
        {id: 1, imgUrl: '/ssafybank logo.png', userName: '영혼없는 고서영', product: '나이키 어쩌구 신발', gage: 80},
        {id: 2, imgUrl: '/ssafybank logo.png', userName: '영혼없는 고서영', product: '나이키 어쩌구 신발', gage: 70},
        {id: 3, imgUrl: '/ssafybank logo.png', userName: '영혼없는 고서영', product: '나이키 어쩌구 신발', gage: 60},
        {id: 4, imgUrl: '/ssafybank logo.png', userName: '영혼없는 고서영', product: '나이키 어쩌구 신발', gage: 80},
        {id: 5, imgUrl: '/ssafybank logo.png', userName: '영혼없는 고서영', product: '나이키 어쩌구 신발', gage: 80},
        {id: 6, imgUrl: '/ssafybank logo.png', userName: '영혼없는 고서영', product: '나이키 어쩌구 신발', gage: 80},
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