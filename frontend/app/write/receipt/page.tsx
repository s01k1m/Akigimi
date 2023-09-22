import Receipt from "@/components/WriteReceipt/Receipt"
import ReceiptInput from "@/components/WriteReceipt/ReceiptInput"
import Footer from "@/app/Footer"
import '@/styles/ChallengeInfo.css'

const WriteReceipt = () => {
    return (
        <div className="w-full h-full flex flex-col items-center">
            <div className="text-center receipt-message mt-10">절약 기록하기</div>
            <Receipt />
            <div className=""><ReceiptInput /></div>
            <Footer />
        </div>
    )
}

export default WriteReceipt       