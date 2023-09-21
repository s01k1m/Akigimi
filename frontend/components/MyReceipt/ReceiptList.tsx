'use client'
import ReceiptItem from "./ReceiptItem"
import { useState, useEffect } from "react"

const ReceiptList = () => {
    // 영수증 기록 api 불러오기 (정렬, date 값 가공해서 달라고 요청하기)
    const ReceiptItems = [
        { id: 1, akimPlace:'GS편의점', saving: 6000, date: '23.08.04', imgUrl: '/images/character1.png'},
        { id: 2, akimPlace:'GS편의점2', saving: 6500, date: '', imgUrl: '/images/character1.png'},
        { id: 3, akimPlace:'CU편의점2', saving: 2000, date: '23.08.05', imgUrl: '/images/character1.png'},
    ]

    
    return (
        <div className="flex flex-col items-center mt-6">
            {ReceiptItems.map((item: any) => (
                <ReceiptItem
                    key={item.id}
                    akimPlace={item.akimPlace}
                    saving={item.saving}
                    date={item.date}
                    imgUrl={item.imgUrl} 
                  />
            ))}
        </div>
    )
}

export default ReceiptList
