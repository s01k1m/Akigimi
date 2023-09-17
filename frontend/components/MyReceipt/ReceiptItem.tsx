interface itemProps {
    akimPlace: string
    saving: number
    date: string
    imgUrl: string
}

const ReceiptItem: React.FC<itemProps> = ({ akimPlace, saving, date, imgUrl }) => {
    return (
        <div>
            <div className="bg-white">
                <img src={imgUrl} alt="" />
                <div>
                    <div>{akimPlace}</div>
                    <div>{saving}절약</div>
                </div>
            </div>
        </div>
    )
}

export default ReceiptItem