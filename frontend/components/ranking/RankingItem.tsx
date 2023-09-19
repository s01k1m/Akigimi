import Image from "next/image"

interface rankingItemProps {
    id: number
    imgUrl: string
    userName: string
    product: string
    gage: number
}

const RankingItem: React.FC<rankingItemProps> = ({ id, imgUrl, userName, product, gage }) => {
    return (
        <div className="flex">
           <div className="rounded-full bg-[#0049F2] text-white">{id}</div> 
           <div>
                <div>
                  <Image
                    src={imgUrl}
                    alt="유저 프로필"
                    width={55}
                    height={55}
                  />  
                </div>
                <div>{userName}</div>
                <div>{product}</div>
                <div>{gage}</div>
           </div>
        </div>
    )
}

export default RankingItem