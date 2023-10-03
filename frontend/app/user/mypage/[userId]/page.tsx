import Mypage from "../page";

type PageParams = {
    itemId: number
  }
  
export default function page({ params }: { params: PageParams }) {
    const itemId = params.itemId
    return (
        <div>
            
        </div>
    )
}