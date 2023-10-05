'use client'
import Image from "next/image"
import { HiPencilAlt } from 'react-icons/hi'
import { BiReceipt } from 'react-icons/bi'
import { FaRankingStar } from 'react-icons/fa6'
import { useRouter } from "next/navigation"

const IconBtn = () => {
    const router = useRouter()
    return (
        <>
        <div className="flex flex-col w-16 -mt-[55px] mb-[30px]" style={{width: `20%`}}>
            <span className="flex flex-col items-center"
                onClick={() => router.push('/myreceipt')}
            >
                <BiReceipt
                    size={50}
                    className="mb-2"
                    color={`#202632`}
                />
                <span>영수증</span>
            </span>
            <span className="flex flex-col items-center"
                onClick={() => router.push('/ranking')}
            >
                <FaRankingStar
                    size={50}
                    className="mb-2"
                    color={`#202632`}
                />
                <span>랭킹</span>
            </span>
            <span className="flex flex-col items-center"
                onClick={() => 
                    // router.push('/feed/review')
                    alert('추후 공개 될 서비스 입니다')
                }
            >
                <HiPencilAlt 
                    size={50}
                    className="mb-2"
                    color={`#202632`}
                />
                <span>후기</span>
            </span>
        </div>
        </>
    )
}

export default IconBtn