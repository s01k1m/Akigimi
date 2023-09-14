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
        <div className="flex w-[50vw] max-w-xs">
             <div onClick={handleToggleLike} className="me-[1vw]">
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

            <div className="blue-btn max-w-[270px] mb-[1vh]"> 
                {description}
            </div>
        </div>
    )
}

export default Description