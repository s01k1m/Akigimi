'use client'
import FeedList from './FeedList'
import { useState, useEffect } from 'react'
import  '@/styles/FeedPage.css'

const FeedTap = () => {
    const [selectedValue, setSelectedValue] = useState<string>('친구')

    // selectedValue 값을 친구 로
    useEffect(() => {
        const defaultSelected = document.getElementById('value-1') as HTMLInputElement ;
        if (defaultSelected) {
            defaultSelected.checked = true;
        }
    }, []);

    const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        setSelectedValue(e.target.value)
    }
    
    return (
        <div>
            <div className='flex justify-center'>
                <div className="radio-input">
                <label>
                <input value="친구" name="value-radio" id="value-1" type="radio" onChange={handleChange} checked={selectedValue === '친구'} defaultChecked />
                <span>친구 아끼머</span>
                </label>
                <label>
                    <input value="미닝템" name="value-radio" id="value-2" type="radio" onChange={handleChange} checked={selectedValue === '미닝템'} />
                <span>미닝템 아끼머</span>
                </label>
                <div className="selection"></div>
                </div>
            </div>
            <div className='flex justify-center min-w-lg min-w-[450px]'>
                <FeedList selectedValue={selectedValue} />
            </div>
        </div>
    )
}

export default FeedTap
