'use client'
import { AiOutlineHeart } from "react-icons/ai";
import { AiFillHeart } from "react-icons/ai";

import { useState } from "react";

interface DescriptionProps {
    isLiked: boolean
    description: string
}

const Description: React.FC<DescriptionProps> = ({ isLiked, description }) => {
    const [liked, setLiked] = useState(isLiked);

    const handleToggleLike = () => {
        setLiked((prevLiked) => !prevLiked);
    };
    return (
        <div className="flex w-[50%] max-w-xs ms-[5vw]">
             <div onClick={handleToggleLike} className="me-[1vw] mb-2">
            {liked ? (
                <AiOutlineHeart 
                     color="#0049F2"
                     size={'40px'}
                />
            ) : (
                <AiFillHeart
                    color="#0049F2"
                    size={'40px'}
                />
            )}
            </div>

            <div className="max-w-[270px] mb-[1vh]"> 
                {description}
            </div>
        </div>
    )
}

export default Description