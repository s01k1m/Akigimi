'use client'
import ItemSearchItem from "./ItemSearchItem"
import axios from "axios"
import { useEffect, useState } from "react"
import '@/styles/MainPageButton.css'

interface Item {
    id: number
    name: string
    price: number
    image: string
}

const ItemSearchList = ({ value, category, range }) => {
    
    const [Items, setItems] = useState<Item[]>([]);

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
                    endMoney: range[1]
                }
            })
            .then((response) => {
                console.log('물건 검색 조회 성공', response.data)
                setItems(response.data.data.searchProductResponseList)
            })
            .catch((error) => {
                console.log('물건 검색 조회 실패', error.response)
            })
    }
    
    useEffect(() => {
        console.log(value, category, range)
        getItemList()
    }, [value, range])

    return (
        <div className="flex flex-wrap gap-5 justify-center mt-5 mb-[100px]">
            {Items.length > 0 ?  (
                Items.map((item) => 
                    <ItemSearchItem
                        key={item.id}
                        itemId={item.id}
                        itemName={item.name}
                        itemPrice={item.price}
                        itemImg={item.image}
                    />
                )
            ) : (
                <div>Loading...</div>
            )}
        </div>
    )
}

export default ItemSearchList