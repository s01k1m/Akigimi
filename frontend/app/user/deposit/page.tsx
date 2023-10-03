import AccountInfo from "@/components/TransactionHistory/AccountInfo"
import History from "@/components/TransactionHistory/History"
import Footer from "@/app/Footer"

const TransactionHistory = () => {
    return (
        <div>
            <AccountInfo type={"DEPOSIT"} />
            <History accountType={"DEPOSIT"} />
            <Footer />
        </div>
    )
}

export default TransactionHistory