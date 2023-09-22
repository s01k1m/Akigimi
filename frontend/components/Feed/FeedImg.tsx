import Image from "next/image"

interface FeedImgProps {
    image: string
}

const FeedImg: React.FC<FeedImgProps> = ({ image }) => {
    return (
        <div className="flex justify-center">
            <Image
                src={image}
                alt="유저가 올린 사진"
                width={250}
                height={150}
                className="h-[20vh] mx-[5vw] overflow-hidden rounded-md"  
            />
        </div>
    )
}

export default FeedImg