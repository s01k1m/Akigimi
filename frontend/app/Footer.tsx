import Link from "next/link"
import { AiOutlineHome, AiOutlinePlusSquare } from 'react-icons/ai'
import { PiUsersBold } from 'react-icons/pi'
import { BiUser } from 'react-icons/bi'
import { AiOutlineSearch } from 'react-icons/ai'
const Footer = () => {
    return (
        <div className="flex justify-center shadow-2xl shadow-inner fixed -bottom-1 bg-white rounded-xl w-[100%] max-w-[500px] mt-[10px] h-[70px]">
            <div className="flex justfiy-center items-center mx-[6%]">
                <Link href={'/main'} className="flex flex-col items-center">
                    <div className="mb-[5px]">
                        <AiOutlineHome size={30} color={`#757575`} />
                    </div>
                    <p>홈</p>
                </Link>
            </div>
            <div className="flex justfiy-center items-center mx-[6%]">
                <Link href={'/item/search'}>
                    <div className="mb-[5px]">
                        <AiOutlineSearch size={30} color={`#757575`} />
                    </div>
                    <p className="text-sm">검색</p>
                </Link>
            </div>
            <div className="flex justfiy-center items-center mx-[6%]">
                <Link href={'/write/receipt'} className="flex flex-col items-center">
                <div className="mb-[5px]">
                    <AiOutlinePlusSquare size={30} color={`#757575`} />
                </div>
                    <p className="text-sm">기록</p>
                </Link>
            </div>
            <div className="flex justfiy-center items-center mx-[6%]">
                <Link href={'/feed'} className="flex flex-col items-center" >
                <div className="mb-[5px]">
                    <PiUsersBold size={30} color={`#757575`} />
                </div>
                    <p className="text-sm">피드</p>
                </Link>
            </div>
            <div className="flex justfiy-center items-center mx-[6%]">
                <Link href={'/user/mypage'} className="flex flex-col items-center">
                <div className="mb-[5px]">
                    <BiUser size={30} color={`#757575`} />
                </div>
                    <p className="text-sm">MY</p>
                </Link>
            </div>
        </div>
     
    )
}

export default Footer