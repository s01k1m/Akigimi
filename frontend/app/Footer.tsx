import Link from "next/link"
import { AiOutlineHome, AiOutlinePlusSquare } from 'react-icons/ai'
import { PiUsersBold } from 'react-icons/pi'
import { BiUser } from 'react-icons/bi'
const Footer = () => {
    return (
        <div className="flex w-full drop-shadow-2xl sticky bottom-0 bg-slate-100 justify-center gap-[20px] rounded-md">

                <Link href={'/main'} className="flex flex-col items-center">
                    <p>홈</p>
                    <AiOutlineHome size={40} />
                </Link>
  
                <Link href={'/write/receipt'} className="flex flex-col items-center">
                    <p>기록 남기기</p>
                    <AiOutlinePlusSquare size={40} />
                </Link>
                <Link href={'/feed'} className="flex flex-col items-center" >
                    <p>커뮤니티</p>
                    <PiUsersBold size={40} />
                </Link>

                <Link href={'/user/mypage'} className="flex flex-col items-center">
                    <p>마이 페이지</p>
                    <BiUser size={40} />
                </Link>
        </div>
    )
}

export default Footer