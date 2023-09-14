import FeedTap from "@/components/Feed/FeedTap"
import FeedList from "@/components/Feed/FeedList"

const Feed = () => {
    return (
        <div>
            <FeedTap />
            <div className="flex justify-center">
                <div>
                    <FeedList />  
                </div>
            </div>
        </div>
    )
}

export default Feed 