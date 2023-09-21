import HistoryItem from "./HistoryItem"

interface ListPropsType {
    period: string
    type: string
}

const HistoryList: React.FC<ListPropsType> = ({ period, type }) => {
    // period, type 고려해서 api 무한 스크롤 불러오기
    const HistoryData = [
        {id: 1, transferDateTime: '8.7', type: '송금', amount: 5000, balance: 15000 },
        {id: 2, transferDateTime: '8.6', type: '입금', amount: 5000, balance: 20000 },
        {id: 3, transferDateTime: '8.5', type: '송금', amount: 5000, balance: 15000 },
        {id: 4, transferDateTime: '8.5', type: '입금', amount: 5000, balance: 20000 }
    ]
    return (
        <div>
            {HistoryData.map((item) => (
                <HistoryItem
                    key={item.id}
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