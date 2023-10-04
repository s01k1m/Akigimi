import Image from "next/image"
import { useRouter } from "next/navigation"

interface ItemSearchPropsType {
    itemId: number
    itemName: string
    itemPrice: number
    itemImg: string
}

const ItemSearchItem: React.FC<ItemSearchPropsType> = ({ itemId, itemName, itemPrice, itemImg }) => {
    const router = useRouter();
    return (
        <div>
            <div className="w-[40vw] h-[25vh] max-w-[200px] min-[170px] rounded-lg bg-[#F5F5F5] flex flex-col items-center">
                <div className="h-[120px]"></div>
                <div className="absolute rounded-lg" style={{width: '170px', height: '120px', overflow:'hidden'}}>
                    <Image
                        src={itemImg}
                        alt="itemImg"
                        layout="fill"
                        objectFit="cover"
                        className="mt-3"
                    />
                </div>
                <div>
                    <div className="w-full mt-3">{itemName}</div>
                    <div className="w-full flex mt-1">
                        <div>{itemPrice.toLocaleString()}원</div>
                        <button 
                            className="bg-tossblue rounded-xl text-white px-3 ms-2"
                            onClick={() => {
                                router.push(`/item/start/${itemId}`)
                            }}
                        >챌린지 시작</button>
                </div>
                </div>
            </div>
        </div>
    )
}

export default ItemSearchItem