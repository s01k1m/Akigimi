import Receipt from "@/components/WriteReceipt/Receipt"
import ReviewInput from "@/components/WriteReceipt/ReviewInput"
import Footer from "@/app/Footer"
import '@/styles/ChallengeInfo.css'

const WriteReview = () => {
    return (
        <div className="w-full h-full flex flex-col items-center">
            <div className="text-center receipt-message mt-10">후기 기록하기</div>
            <Receipt />
            <div><ReviewInput /></div>
            <Footer />
        </div>
    )
}

export default WriteReview       