'use client'
import ReceiptItem from "./ReceiptItem"
import { useState, useEffect } from "react"
import axios from "axios"
import { useInView } from "react-intersection-observer";

type ReceiptItem = {
    price: number
    akimLocation: string
    description: string
    createdTime: string
    image: string
  };


const ReceiptList = () => {
    // 영수증 기록 api 불러오기 (정렬, date 값 가공해서 달라고 요청하기)
    const ReceiptItems = [
        { id: 1, akimPlace:'GS편의점', saving: 6000, date: '23.08.04', imgUrl: '/images/character1.png'},
        { id: 2, akimPlace:'GS편의점2', saving: 6500, date: '', imgUrl: '/images/character1.png'},
        { id: 3, akimPlace:'CU편의점2', saving: 2000, date: '23.08.05', imgUrl: '/images/character1.png'},
    ]

    const userId = window.localStorage.getItem('userId')
    const token = window.localStorage.getItem('accessToken')

    // api 로 불러올 모든 아이템
    const [receiptItems, setReceiptItems] = useState<ReceiptItem[]>([]);

    // 지금 이 순간에 불러온 최근 아이템
    let toCheckReceiptId = []

    // 관찰할 객체에 ref 달기
    const [ref, inView] = useInView()

    // 요청 보낼 마지막 lastViewId
    const [lastViewId, setLastViewId] = useState<number>(9223372036854775807);

    // 다음 영수증이 불러오질 때까지 로딩
    const [loading, setLoading] = useState<boolean>(false);

    // api 데이터 불러오기
    const receiptData = async () => {
        await axios
            .get(`receipts/${userId}`, {
                params: {
                    lastReceiptId: 10,
                    numberOfReceipt: 5
                },
                headers: {
                    'Authorization': `Bearer ${token}`
                }
            })
            .then((response) => {
                console.log('피드 조회 성공', response)

                // 요청 성공 시에 리스트 뒤로 붙여주기
                setReceiptItems([...receiptItems, ...(response.data.data.list)])

                // 새로 가져온 값만 리스트에 담아주기
                toCheckReceiptId.push([response.data.data.list])

                // 첫 번째 요청 이후에 viewId 값 상태 변경해주기
                setLastViewId(toCheckReceiptId[toCheckReceiptId.length-1])
                console.log('마지막 피드의 아이디 값은?', lastViewId)

                
              })
            .then(() => {
              // 마지막 요청을 판단하는 방법

            })
            .catch((error) => {
                console.log('피드 조회 실패', error)
            })
            .finally (() => {
              setLoading(false)
            })
    }

    
    return (
        <div className="flex flex-col items-center mt-6">
            {receiptItems.map((item: any) => (
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
