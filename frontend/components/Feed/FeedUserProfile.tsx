import Image from "next/image"

interface FeedUserProfileProps {
    imgUrl: string;
    name: string
}

const FeedUserProfile: React.FC<FeedUserProfileProps> = ({ imgUrl, name }) => {
    return (
        <div className="flex items-end">
            <div className="rounded-full">
                <Image
                    src={imgUrl}
                    alt="img"
                    width={30}
                    height={30}
                    className="w-[40px] h-[40px] rounded-full"
                />
            </div>
            <div className="ms-[5%] text-[17px] font-semibold">{name}</div>
        </div>
    )
}

export default FeedUserProfile