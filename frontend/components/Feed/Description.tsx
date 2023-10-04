'use client'
import { AiOutlineHeart } from "react-icons/ai";
import { AiFillHeart } from "react-icons/ai";
import { useState, useEffect } from "react";
import axios from "axios";

interface DescriptionProps {
    isLiked: boolean
    description: string
    feedId: number
    likedCount: number
}

const Description: React.FC<DescriptionProps> = ({ isLiked, description, feedId,likedCount }) => {
    const [liked, setLiked] = useState(isLiked);

    // token 가져오기
    const [token, setToken] = useState<string>("");

    useEffect(() => {
        if (typeof window !== "undefined") {
            const storedToken = window.localStorage.getItem("access_token");
            if (storedToken) {
                setToken(storedToken);
            }
        }
        console.log(feedId, isLiked, '좋아요 눌렀는지 안 눌렀는지')
    }, []);

    const handleToggleLike = async () => {
        const likeBody = {
            feedId: feedId
        }
        // 좋아요를 안 한 게시글
        if (!liked) {
            console.log('좋아요 눌러야지 이제')
            await axios
                .post('/api/feeds/likes', likeBody, {
                    headers: {
                        Authorization: `Bearer ${token}`,
                        "Content-Type": "application/json",
                    }
                })
                .then((response) => {
                    console.log('좋아요 성공', response)
                    setLiked(true)
                })
        } else {
            // 좋아요 취소하기
            await axios
                .delete(`/api/feeds/likes?feedId=${feedId}`, {
                    headers: {
                        Authorization: `Bearer ${token}`,
                        "Content-Type": "application/json",
                    }
                })
                .then((response) => {
                    console.log('좋아요 취소 성공', response.data)
                    setLiked(false)
                })
                .catch((error) => {
                    console.log('좋아요 취소 실패', error)
                })

        }
    };
    return (
        <div>
            <div className="flex w-[80%] max-w-[500px] ms-[5vw] items-center">
                <div onClick={handleToggleLike} className="me-[1vw] mb-2">
                {!liked ? (
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
                <div className="text-[12px] text-[#757575]">
                    <span className="text-tossblue font-semibold">{likedCount}</span>
                명이 좋아합니다</div>
            </div>

            <div className="max-w-[270px] mb-[2vh] w-[70vw] ms-[5vw]"> 
                {description}
            </div>
        </div>
    )
}

export default Description