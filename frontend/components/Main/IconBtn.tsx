'use client'
import Image from "next/image"
import { useRouter } from "next/navigation"

const IconBtn = () => {
    const router = useRouter()
    return (
        <>
        <div className="flex flex-col w-16" style={{width: `20%`}}>
            <span className="flex flex-col items-center"
                onClick={() => router.push('/myreceipt')}
            >
                <Image
                    src="/images/PurchaseOrder.png"
                    alt="receipt"
                    width={50}
                    height={50}
                    className="mb-2"
                    onClick={() => {
                        router.push('/myreipt')
                    }}
                />
                <span className="mb-5">영수증</span>
            </span>
            <span className="flex flex-col items-center"
                onClick={() => router.push('/ranking')}
            >
                <Image
                    src="/images/SilverMedal.png"
                    alt="ranking"
                    width={50}
                    height={50}
                    className="mb-2"
                    onClick={() => {
                        router.push('/ranking')
                    }}
                />
                <span>랭킹</span>
            </span>
        </div>
        </>
    )
}

export default IconBtn