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

    const getItemList = async () => {
        await axios
            .get(('/api/items/search'), {
                params: {
                    value: value,
                    category: category,
                    range: range
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
    }, [])

    return (
        <div className="flex flex-wrap gap-5 justify-center mt-5">
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