import AccountInfo from "@/components/TransactionHistory/AccountInfo"
import History from "@/components/TransactionHistory/History"
import Footer from "@/app/Footer"

interface typeProps {
    type: string
    accountType: string
}

const TransactionHistory = () => {
    return (
        <div>
            <AccountInfo type={"WITHDRAW"} />
            <History accountType={"WITHDRAW"} />
            <Footer />
        </div>
    )
}

export default TransactionHistory