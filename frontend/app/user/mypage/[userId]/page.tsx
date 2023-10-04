'use client'
import { useEffect } from "react"
import Footer from "@/app/Footer"

type PageParams = {
    userId: number
  }
  
export default function page({ params }: { params: PageParams }) {
    const userId = params.userId
    useEffect(() => {
        console.log(1, userId)

    }, [])
    return (
        <div>
            {userId}의 마이페이지
            <Footer />
        </div>
    )
}