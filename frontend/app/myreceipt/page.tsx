import Title from "@/components/MyReceipt/Title"
import ReceiptList from "@/components/MyReceipt/ReceiptList"
const MyReceipt = () => {
    return (
        <div className="bg-red-100">
            <div>
                <Title />
            </div>
            <div>
                <ReceiptList />
            </div>
        </div>
    )
}

export default MyReceipt