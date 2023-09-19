import Image from "next/image"

interface FeedImgProps {
    image: string
}

const FeedImg: React.FC<FeedImgProps> = ({ image }) => {
    return (
        <div>
            <Image
                src={image}
                alt="유저가 올린 사진"
                width={250}
                height={150}
                className="w-[50vw] h-[20vh] max-w-xs mx-[5vw] rounded-md"  
            />
        </div>
    )
}

export default FeedImg