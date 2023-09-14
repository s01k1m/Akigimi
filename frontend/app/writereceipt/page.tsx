import Receipt from "@/components/WriteReceipt/Receipt"
import ReceiptInput from "@/components/WriteReceipt/ReceiptInput"
import Modal from "@/components/Main/Modal"
import '@/styles/ChallengeInfo.css'

const WriteReceipt = () => {
    return (
        <div className="w-full h-full flex flex-col items-center">
            <Modal />
            <div className="text-center receipt-message mt-10">절약 기록하기</div>
            <Receipt />
            <div className="z-10 -translate-y-[525px]"><ReceiptInput /></div>
            
        </div>
    )
}

export default WriteReceipt       