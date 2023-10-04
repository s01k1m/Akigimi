import HistoryItem from "./HistoryItem"
import { useState, useEffect } from "react"
import axios from "axios"

interface ListPropsType {
    period: string
    type: string
    accountType: string
}

interface HistoryItem {
    transferDateTime: string
    type: string
    amount: number
    balance: number
}

const HistoryList: React.FC<ListPropsType> = ({ period, type, accountType }) => {
    const [historyData, setHistoryData] = useState<HistoryItem[]>([]);
    // period, type 고려해서 api 무한 스크롤 불러오기
    let token: string = "";

    useEffect(() => {
        if (typeof window !== "undefined") {
        token = window.localStorage.getItem("access_token");
        }
        getHistoryInfo()
    }, []);

    const getHistoryInfo = async () => {
        await axios 
            .get(`/api/account/transaction/history`, {
                params: {
                    accountType: `${accountType}`
                },
                headers: {
                    Authorization: `Bearer ${token}`,
                  },
            })
            .then((response) => {
                console.log('거래 내역 조회 성공', response.data.data.transfer)
                setHistoryData(response.data.data.transfer)
       
            })
            .catch((error) => {
                console.log('거래 내역 조회 실패', error)
            })
    }


    return (
        <div className="pb-[100px]">
            {historyData.map((item, index) => (
                <HistoryItem
                    key={index}
                    date={item.transferDateTime}
                    type={item.type}
                    amount={item.amount}
                    balance={item.balance}
                 />
            ))}
        </div>
    )
}

export default HistoryList