'use client'
import ItemSearchItem from "./ItemSearchItem"
import axios from "axios"
import { useEffect, useState } from "react"

const ItemSearchList = ({ value, category, range }) => {
    console.log(value, category, range)

    const ItemList = [
        {itemId: 1, itemName: '닌텐도 스위치', itemPrice: 150000, itemImg: '/images/heart.png'},    
        {itemId: 2, itemName: '닌텐도 스위치', itemPrice: 150000, itemImg: '/images/heart.png'},    
        {itemId: 3, itemName: '닌텐도 스위치', itemPrice: 150000, itemImg: '/images/heart.png'},    
        {itemId: 4, itemName: '닌텐도 스위치', itemPrice: 150000, itemImg: '/images/heart.png'},    
    ]
    const [Items, setItems] = useState<[]>([]);

    let token: string = "";
    const getItemList = async () => {
        if (typeof window !== "undefined") {
            token = window.localStorage.getItem("access_token");
        }
        await axios
            .get(('/api/product/search'), {
                headers: {
                    Authorization: `Bearer ${token}`,
                  },
                params: {
                    name: value,
                    startMoney: range[0],
                    endMondy: range[1]
                }
            })
            .then((response) => {
                console.log('물건 검색 조회 성공', response.data.data.list)
                setItems(response.data.data.list)
            })
            .catch((error) => {
                console.log('물건 검색 조회 실패', error.response)
            })
    }
    
    useEffect(() => {
        getItemList()
    }, [value, range])

    return (
        <div className="flex flex-wrap gap-5 justify-center mt-5 mb-[100px]">
            {ItemList.map((item) => 
                <ItemSearchItem
                    key={item.itemId}
                    itemId={item.itemId}
                    itemName={item.itemName}
                    itemPrice={item.itemPrice}
                    itemImg={item.itemImg}
                />
            )}
        </div>
    )
}

export default ItemSearchList