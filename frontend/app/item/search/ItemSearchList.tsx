import ItemSearchItem from "./ItemSearchItem"

const ItemSearchList = () => {
    const ItemList = [
        {itemId: 1, itemName: '닌텐도 스위치', itemPrice: 150000, itemImg: '/images/heart.png'},    
        {itemId: 2, itemName: '닌텐도 스위치', itemPrice: 150000, itemImg: '/images/heart.png'},    
        {itemId: 3, itemName: '닌텐도 스위치', itemPrice: 150000, itemImg: '/images/heart.png'},    
        {itemId: 4, itemName: '닌텐도 스위치', itemPrice: 150000, itemImg: '/images/heart.png'},    
    ]
    
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