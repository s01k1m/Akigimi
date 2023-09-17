import ReceiptItem from "./ReceiptItem"
const ReceiptList = () => {
    // 영수증 기록 api 불러오기
    const ReceiptItems = [
        { id: 1, akimPlace:'GS편의점', saving: 6000, date: '23.08.04', imgUrl: '/.png'},
        { id: 2, akimPlace:'GS편의점2', saving: 6500, date: '23.08.04', imgUrl: '/.png'}
    ]
    return (
        <div>
            {ReceiptItems.map((item: any) => (
                <ReceiptItem
                    key={item.id}
                    akimPlace={item.akimPlace}
                    saving={item.saving}
                    date={item.date}
                    imgUrl={item.imgUrl}
                  />
            ))}
        </div>
    )
}

export default ReceiptList