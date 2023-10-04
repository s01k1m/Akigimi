"use client";
import ReceiptItem from "./ReceiptItem";
import { useState, useEffect } from "react";
import axios from "axios";
import { useInView } from "react-intersection-observer";
import { dividerClasses } from "@mui/material";

type ReceiptItem = {
  price: number;
  akimLocation: string;
  description: string;
  createdTime: string;
  image: string;
};

const ReceiptList = () => {
  let userId: number = 0;

  let token: string = "";

  useEffect(() => {
    if (typeof window !== "undefined") {
      token = window.localStorage.getItem("access_token");
      userId = parseInt(window.sessionStorage.getItem("userId"))
    }
    receiptData();
  }, []);

  // api 로 불러올 모든 아이템
  const [receiptItems, setReceiptItems] = useState<ReceiptItem[]>([]);

  // 지금 이 순간에 불러온 최근 아이템
  let toCheckReceiptId = [];

  // 관찰할 객체에 ref 달기
  const [ref, inView] = useInView();

  // 요청 보낼 마지막 lastViewId
  const [lastViewId, setLastViewId] = useState<number>(922337203685477580);
  const [count, setCount] = useState<number>(5)

  // 다음 영수증이 불러오질 때까지 로딩
  const [loading, setLoading] = useState<boolean>(false);

  // api 데이터 불러오기
  const receiptData = async () => {
    setLoading(true)
    if (typeof window !== "undefined") {
      token = window.localStorage.getItem("access_token");
      }
    await axios
      .get(`/api/receipts/${userId}`, {
        params: {
          lastReceiptId: lastViewId,
          numberOfReceipt: count,
        },
        headers: {
          Authorization: `Bearer ${token}`,
        },
      })
      .then((response) => {
        console.log("영수증 조회 성공", response.data.data.list);

        // 요청 성공 시에 리스트 뒤로 붙여주기
        setReceiptItems([...receiptItems, ...response.data.data.list]);

        const data: [] = response.data.data.list;
        const length: number = data.length;
        setLastViewId(data[data.length-1]['receiptId'])
        if (data.length < 5) {
          setCount(0)
        }
        console.log("마지막 피드의 아이디 값은?", lastViewId);
      })
      .catch((error) => {
        console.log("피드 조회 실패", error);
      })
      .finally(() => {
        setLoading(false);
      });
  };

  useEffect(() => {
    // inView가 true 일 때만 실행한다
    if (inView) {
      console.log(inView, '무한 스크롤 요청 중이에요')
      // 실행할 함수
      receiptData()
    }
  }, [inView])

  return (
    <div className="flex flex-col items-center mt-6 mb-[30px] mt-[40px]">
      {receiptItems.map((item: any) => (
        <ReceiptItem
          key={item.id}
          akimPlace={item.akgimiPlace}
          saving={item.price}
          date={item.createdDateTime}
          imgUrl={item.photo}
        />
      ))}
      <div ref={ref}></div>
      {loading && <div>Loading...</div>}
    </div>
  );
};

export default ReceiptList;
