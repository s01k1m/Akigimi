'use client'
import RankingList from "./RankingList"
import { useState, useEffect } from "react";

const RankingTap = () => {
    const [selectedValue, setSelectedValue] = useState<string>('오늘')

    // selectedValue 값을 친구 로
    useEffect(() => {
        const defaultSelected = document.getElementById('value-1');
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
                    <input value="오늘" name="value-radio" id="value-1" type="radio" onChange={handleChange} checked={selectedValue === '오늘'} defaultChecked />
                <span>오늘의</span>
                </label>
                <label>
                    <input value="이주" name="value-radio" id="value-2" type="radio" onChange={handleChange} checked={selectedValue === '이주'} />
                <span>이주의</span>
                </label>
                <label>
                    <input value="이달" name="value-radio" id="value-3" type="radio" onChange={handleChange} checked={selectedValue === '이달'} />
                <span>이달의</span>
                </label>
                <div className="selection"></div>
                </div>
            </div>

            <div>
                <RankingList />
            </div>
        </div>
    )
}

export default RankingTap