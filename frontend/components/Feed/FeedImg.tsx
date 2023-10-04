import Image from "next/image"

interface FeedImgProps {
    image: string
}

const FeedImg: React.FC<FeedImgProps> = ({ image }) => {
    return (
        <>
        <div className="absolute flex justify-center rounded-md" style={{ width: '285px', height: '150px', overflow: 'hidden' }}>
            <Image
                src={image}
                alt="유저가 올린 사진"
                layout="fill"
                objectFit="cover"
                className="h-[20vh] mx-[5vw] overflow-hidden rounded-md"  
            />
        </div>
            <div className="h-[150px]"></div>
        </>
    )
}

export default FeedImg