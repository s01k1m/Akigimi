import  '@/styles/FeedPage.css'

const FeedTap = () => {
    return (
        <div>
            <div className="radio-input">
            <label>
            <input value="value-1" name="value-radio" id="value-1" type="radio" checked />
            <span>친구 아끼머</span>
            </label>
            <label>
                <input value="value-2" name="value-radio" id="value-2" type="radio" />
            <span>미닝템 아끼머</span>
            </label>
            <span className="selection"></span>
            </div>
        </div>
    )
}

export default FeedTap
