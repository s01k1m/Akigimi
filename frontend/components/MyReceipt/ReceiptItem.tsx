import Image from "next/image"

interface itemProps {
    akimPlace: string
    saving: number
    date: string
    imgUrl: string
}

const ReceiptItem: React.FC<itemProps> = ({ akimPlace, saving, date, imgUrl }) => {

    return (
        <div>
            {date? (
                <div className="receipt-date mb-2">{date}</div>
            ) : null}
            <div className="flex flex-col mb-[2vh] bg-white w-[50vw] rounded-xl h-[100px] max-w-[280px] min-w-[200px]">
                <div className="flex flex-end align-bottom ms-[5%] mt-[1vh]">
                
                        <Image src={imgUrl} alt="영수증 사진" width={80} height={80} className="me-[2vw]" />
                        <div className="flex flex-col align-bottom justify-end">
                            <div className="receipt-place">{akimPlace}</div>
                            <div>
                                <span className="receipt-saving">{saving}</span>
                                <span className="receipt-saving black">원</span> 절약</div>
                        </div>
                </div>
    
            </div>
        </div>
    )
}

export default ReceiptItem