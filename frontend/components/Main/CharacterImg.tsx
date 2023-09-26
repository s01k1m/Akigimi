import Image from "next/image";

interface CharacterImgProps {
    stage: number
}

const CharacterImg: React.FC<CharacterImgProps> = ({ stage }) => {
    if (stage === 1) {
        stage = 0
    }
    return (
        <div className="m-auto pe-14">
            <Image
                src={`/images/character${stage}.png`}
                alt="character"
                width={170}
                height={150}
             />
        </div>
    )
}

export default CharacterImg