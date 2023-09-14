import ReceiptCircle from "../WriteReceipt/ReceiptCircle"
import '@/styles/WriteReceipt.css'

const FeedReceipt = () => {
    return (
        <div>
            <div className='flex justify-center transform translate-y-1/2'><ReceiptCircle /></div>
            <div className="feed-receipt mx-auto"></div>
       
        </div>
    )
}

export default FeedReceipt