import Image from "next/image"
const Heart = () => {
    return (
        <div>
            <Image
                src="/images/heart.png"
                alt="heart"
                width={33}
                height={33}
            />
        </div>
    )
}

export default Heart