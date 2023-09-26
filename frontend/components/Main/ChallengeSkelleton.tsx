import MoneyGageBar from "./MoneyGageBar"
import DayGageBar from "./DayGageBar"
import IconBtn from "./IconBtn"
import CharacterImg from "./CharacterImg"
import Footer from "@/app/Footer"

const ChallengeSkelleton = () => {
    return (
        <div>
            <div style={{ width: '100%'}}>
            <div className="flex flex-col items-center justify-center" style={{ width: '100%'}}>
                <MoneyGageBar percentage={10} productPrice={0} balance={0} stage={1}  />
                <DayGageBar challengePeriod={0} days={0} stage={1}   />
            </div>
            <div className="flex mt-[52vh]">
                <IconBtn />
            </div>
            
            <div className="flex justify-center w-[100%]">
                <Footer />
            </div>

            </div>
        </div>
    )
}

export default ChallengeSkelleton