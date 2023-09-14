interface FeedWriterReceiptInfoProps {
    place: string
    item: string
    price: number
}

const FeedWriterReceiptInfo: React.FC<FeedWriterReceiptInfoProps> = ({ place, item, price }) => {
    return (
        <div className="flex ms-[5vw]">
                <p className="me-[2vw]">{place}</p>
                <p className="me-[2vw]">{item}</p>
                <p className="me-[2vw]">{price}</p>
        </div>
    )
}

export default FeedWriterReceiptInfo