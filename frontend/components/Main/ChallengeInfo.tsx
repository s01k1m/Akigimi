import Image from "next/image"
import '@/styles/ChallengeInfo.css'

interface ChallengeInfoProps {
    item: string,
    day: number,
    date: number,
    itemImg: string
}

const ChallengeInfo: React.FC<ChallengeInfoProps> = ({ item, day, date, itemImg }) => {
    console.log('아이템 이미지', itemImg)
    return (
        <div className="flex flex-col items-center message">
            <div>
                <span className="item">{item}</span>
                를 향한 
            </div>
            <div className="mb-4">
                <span className="date">{day}</span>
                일의 도전
            </div>
            <div>
                <span className="day">{date}</span>
                일차
            </div>
            <div>
                <Image
                    src={itemImg}
                    alt="item"
                    width={250}
                    height={150}
                    className="rounded-lg backdrop-blur-sm"
                />
            </div>  
        </div>
    )
}

export default ChallengeInfo