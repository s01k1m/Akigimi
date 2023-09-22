import AccountInfo from "@/components/TransactionHistory/AccountInfo"
import History from "@/components/TransactionHistory/History"
import Footer from "@/app/Footer"

const TransactionHistory = () => {
    return (
        <div>
            <AccountInfo />
            <History />
            <Footer />
        </div>
    )
}

export default TransactionHistory