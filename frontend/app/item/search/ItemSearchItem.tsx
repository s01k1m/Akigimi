import Image from "next/image"

interface ItemSearchPropsType {
    itemId: number
    itemName: string
    itemPrice: number
    itemImg: string
}

const ItemSearchItem: React.FC<ItemSearchPropsType> = ({ itemId, itemName, itemPrice, itemImg }) => {
    return (
        <div>
            <div className="w-[40vw] h-[25vh] max-w-[200px] min-[170px] rounded-lg bg-[#F5F5F5] flex flex-col items-center">
                <Image
                    src={itemImg}
                    alt="itemImg"
                    width={100}
                    height={100}
                    className="mt-2"
                />
                <div>
                    <div className="w-full mt-3">{itemName}</div>
                    <div className="w-full flex mt-1">
                        <div>{itemPrice}</div>
                        <button className="bg-tossblue rounded-xl text-white px-3 ms-2">챌린지 시작</button>
                </div>
                </div>
            </div>
        </div>
    )
}

export default ItemSearchItem