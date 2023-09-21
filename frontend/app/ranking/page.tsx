import RankingTap from "@/components/ranking/RankingTap"
import '@/styles/RankingTitle.css'

const Ranking = () => {
    return (
        <div className="flex flex-col items-center">
            <div className="ranking-title mt-14">절약왕</div>
            <div>
                <RankingTap />
            </div>
        </div>
    )
}

export default Ranking