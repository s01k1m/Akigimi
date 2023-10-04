'use client'
import ReceiptCircle from "@/components/WriteReceipt/ReceiptCircle"
import ReceiptList from "@/components/MyReceipt/ReceiptList"
import '@/styles/MainPageButton.css'
import Footer from "@/app/Footer"

const MyFeeds = () => {
    return (
        <div className="flex flex-col justify-center items-center">
            <div className='flex flex-col items-center mt-10 font-bold text-[22px] tracking-widest'>
                나의 절약 기록
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
            <Footer />
            </div>
    )
}

export default MyFeeds

