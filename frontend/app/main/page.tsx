import MoneyGageBar from "@/components/Main/MoneyGageBar"
import DayGageBar from "@/components/Main/DayGageBar"
import GoToSelectItem from "@/components/Main/GoToSelectItem"

const Main = () => {
    // 물건이 없는 경우 api에서 값 받아오기
    return (
        <div className="bg-slate-100" style={{ width: '100%'}}>
        <div className="flex flex-col items-center justify-center" style={{ width: '100%'}}>
            <MoneyGageBar />
           <DayGageBar />
        </div>
        <div className="flex justify-center mt-60" >
            <GoToSelectItem />  
        </div>

        </div>
    )
}

export default Main
  