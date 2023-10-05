import axios from "axios"
import { useEffect, useState } from "react"
import RankingItem from "./RankingItem"

interface RankingPropsType {
    selectedValue: string
}



const RankingList: React.FC<RankingPropsType> = ({ selectedValue }) => {
    const [rankingList, setRankingList] = useState<[]>([])
    let token: string = "";
    useEffect(() => {
        token = window.localStorage.getItem("access_token");
    }, []);
    // api 받아 오기 selectedValue로 구분 지어서 부르기
    const getRankingList = async () => {
        await axios
            .get(`/api/ranking`, {
                headers: {
                    Authorization: `Bearer ${token}`
                  },
            })
            .then((response) => {
                console.log('랭킹 리스트 조회 성공', response.data.data)
                setRankingList(response.data.data)
            })
            .catch((error) => {
                console.log('랭킹 리스트 조회 실패', error)
            })
    }

    useEffect(() => {
        getRankingList()
    }, [])
    return (
        <div>
            {rankingList.map((item: any, index) => (
                    <RankingItem
                        key={item.id}
                        id={index+1}
                        imgUrl={item.userImgUrl}
                        userName={item.userNickname}
                        product={item.productName}
                        gage={item.percentage}
                    />
            ))

            }
        </div>
    )
}

export default RankingList