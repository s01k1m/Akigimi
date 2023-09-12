'use client'
import MoneyGageBar from "@/components/Main/MoneyGageBar"
import DayGageBar from "@/components/Main/DayGageBar"
import GoToSelectItem from "@/components/Main/GoToSelectItem"
import IconBtn from "@/components/Main/IconBtn"
import CharacterImg from "@/components/Main/CharacterImg"
import ChallengeInfo from "@/components/Main/ChallengeInfo"
import GoToBuyBtn from "@/components/Main/GoToBuyBtn"
import GoToRetryBtn from "@/components/Main/GoToRetryBtn"
import { useQuery } from "react-query"
import axios from 'axios'
import { useState } from "react"

const Main = () => {
    const [stage, setState] = useState<number>(0)

    return (
        <div className={`background-${stage}`} style={{ width: '100%'}}>
        <div className="flex flex-col items-center justify-center" style={{ width: '100%'}}>
            <MoneyGageBar />
           <DayGageBar />
        </div>
        {stage === 0 &&
            <div className="flex justify-center mt-60" >
                <GoToSelectItem />  
            </div>
        }
        {stage !== 0 &&
            <div className="flex justify-center mt-10">
                <ChallengeInfo item={"닌텐도"} day={50} date={1} itemImg={"/images/nintendo.png"} />
            </div>
        }
        {stage === 5 &&
            <div className="flex justify-center -mt-1.5">
                <GoToBuyBtn />
            </div>
        }
        {stage === 6 &&
            <div className="flex justify-center -mt-1.5">
            <GoToRetryBtn />
            </div>
        }
        <div className="flex" style={{ marginTop: stage >= 5 ? "0px" : (stage === 0 ? "120px" : "53px")}}>
            <IconBtn />
            <CharacterImg stage={stage}  />
        </div>
        

        </div>
    )
}

export default Main
  