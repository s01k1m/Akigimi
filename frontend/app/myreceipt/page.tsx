import Title from "@/components/MyReceipt/Title"
import ReceiptCircle from "@/components/WriteReceipt/ReceiptCircle"
import ReceiptList from "@/components/MyReceipt/ReceiptList"

const MyReceipt = () => {
    return (
        <div className="flex flex-col justfiy-center items-center">
            <div>
                <Title />
            </div>
            <div className="flex justify-center transform translate-y-1/2">
                <ReceiptCircle />
            </div>
            <div className="items-center bg-[#EEE] w-[70vw] h-[100%] max-w-[350px] min-w-[300px] rounded-md pb-8">
                <ReceiptList />
            </div>
            <div className="flex justify-center transform -translate-y-1/2">
                <ReceiptCircle />
            </div> 
        </div>
    )
}

export default MyReceipt