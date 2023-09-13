import '@/styles/WriteReceipt.css'
import ReceiptCircle from './ReceiptCircle'

const Receipt = () => {
    return (
        <div className='mx-auto -mt-1 h-[100%] w-[80%] -mt-4'>
            <div className='flex justify-center transform translate-y-1/2'><ReceiptCircle /></div>
            <div className="receipt"></div>
            <div className='flex justify-center transform -translate-y-1/2'><ReceiptCircle /></div>
        </div>
    )
}

export default Receipt          