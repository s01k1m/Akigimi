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
        <div className="flex items-center">
          { (id <= 3) ? (
            <div className="rounded-full bg-tossblue text-white w-[40px] h-[40px] flex justify-center items-center text-[30px] font-semi-bold">{id}</div> 
            ) : (
            <div className="rounded-full bg-white text-tossblue w-[40px] h-[40px] flex justify-center items-center text-[30px] font-semi-bold border border-tossblue">{id}</div> 
          )

          }
           <div className="flex items-center bg-[#F5F5F5] rounded-lg w-[60vw] max-w-[300px] min-w-[280px] h-[12vh] ms-[5vw] mb-[2vh]">
                <div className="ms-[10px]">
                  <Image
                    src={imgUrl}
                    alt="유저 프로필"
                    width={55}
                    height={55}
                    className="rounded-full"
                  />  
                </div>
                <div className="ms-[10px]">
                  <div className="text-[13px] font-semibold mb-[0.5vh]">{userName}</div>
                  <div className="text-[13px] text-[#757575] font-normal">{product}</div>

                  <div>
                    <div className="w-[40vw] h-[15px] max-w-[210px] min-w-[180px] bg-white rounded-full">
                      <div className={`w-[${gage}%] h-[15px] max-w-[210px] bg-[#0049F2] rounded-full`}></div>
                    </div>
                  </div>
                </div>
           </div>
        </div>
    )
}

export default RankingItem