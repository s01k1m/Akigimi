'use client'
import HistoryList from "./HistoryList"
import { useEffect, useState, useRef } from "react"
import '@/styles/HistoryTap.css'

const History = () => {
    // 모달창 관리를 위한 상태 값
    const [isOpened, setIsOpened] = useState<boolean>(false)
    
    // 모달창을 여는 함수
    const ModalOpen = () => {
        console.log('click')
        setIsOpened(!isOpened)
    }

    // 모달창을 닫는 함수 (배경을 클릭한 경우)
    const handleCloseModal = (e: MouseEvent) => {
        if (modalRef.current && !modalRef.current.contains(e.target as Node)) {
            setIsOpened(false);
        }
    }

    // 모달 창이 열린 경우 배경을 클릭 했을 때
    const modalRef = useRef<HTMLDivElement>(null)

    // 모달 창 안의 탭
    // 선택한 값을 넘겨주면 된다
    const [selectedValue, setSelectedValue] = useState<string>('일주일')
    const [selectedValueSecond, setSelectedValueSecond] = useState<string>('전체')
    
    useEffect(() => {
        if (isOpened) {
            document.addEventListener("click", handleCloseModal)
        } else {
            document.removeEventListener("click", handleCloseModal)
        }

        return () => {
            document.removeEventListener("click", handleCloseModal)
        }
    }, [isOpened])

    // 기본 선택 값
    useEffect(() => {
        // selectedValue 값을 1주일
        const defaultSelected = document.getElementById('value-1') as HTMLInputElement ;
        if (defaultSelected) {
            defaultSelected.checked = true;
        }
        
        // selectedValue 값을 전체
        const defaultSelectedSecond = document.getElementById('value-11') as HTMLInputElement ;
        if (defaultSelectedSecond) {
            defaultSelectedSecond.checked = true;
        }
    }, []);

    // 기간 변화 감지
    const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        setSelectedValue(e.target.value)
    }

    // 거래 유형 변화 감지
    const handleChangeSecond = (e: React.ChangeEvent<HTMLInputElement>) => {
        setSelectedValueSecond(e.target.value)
    }

    return (
        <div className={`mt-[12vh] relative${isOpened ? 'overflow-hidden' : ''}`}>
            <div className="flex justify-center space-x-48">
                <div className="text-[20px] font-normal">거래내역</div>
                <div>
                    <button className="w-[15vw] min-w-[75px] max-w-[100px] h-[4vh] min-h-[27px] bg-[#0049F2] text-white text-[13px] font-semibold tracking-[.25em] rounded-lg"
                    onClick={ModalOpen}
                    >조회 설정</button>
                </div>
            </div>
            <div>
                <HistoryList
                    period={selectedValue}
                    type={selectedValueSecond}
                />
            </div>
            {isOpened ? (
                <div 
                    className="fixed -bottom-12 left-0 w-full h-[50vh] flex flex-col justify-center backdrop-[#757575] bg-white rounded-[50px] drop-shadow-[0_50px_50px_rgba(0,0,0,0.5)]"
                    ref={modalRef}   
                >
                    {/* 조회 기간 */}
                    <div className="flex flex-col items-center">
                        <div className="mb-[2vh]">조회기간</div>
                        <div className="mb-[3vh]">
                            <div className='justify-center'>
                                <div className="radio-input">
                                    <label>
                                    <input value="일주일" name="value-radio-period" id="value-1" type="radio" onChange={handleChange} checked={selectedValue === '일주일'} />
                                    <span>일주일</span>
                                    </label>
                                    <label>
                                        <input value="1개월" name="value-radio-period" id="value-2" type="radio" onChange={handleChange} checked={selectedValue === '1개월'} />
                                    <span>1개월</span>
                                    </label>
                                    <label>
                                        <input value="2개월" name="value-radio-period" id="value-3" type="radio" onChange={handleChange} checked={selectedValue === '2개월'} />
                                    <span>2개월</span>
                                    </label>
                                    <div className="selection"></div>
                                </div>
                            </div>
                        </div>
                    </div> 
                    {/* 유형 */}
                    <div className="flex flex-col items-center mt-[4vh]">
                        <div className="mb-[2vh]">거래유형</div>
                        <div className="mb-[3vh]">
                            <div className='justify-center'>
                                <div className="radio-input">
                                    <label>
                                    <input value="전체" name="value-radio" id="value-11" type="radio" onChange={handleChangeSecond} checked={selectedValueSecond === '전체'} />
                                    <span>전체</span>
                                    </label>
                                    <label>
                                        <input value="출금만" name="value-radio" id="value-12" type="radio" onChange={handleChangeSecond} checked={selectedValueSecond === '출금만'} />
                                    <span>출금만</span>
                                    </label>
                                    <label>
                                        <input value="입금만" name="value-radio" id="value-13" type="radio" onChange={handleChangeSecond} checked={selectedValueSecond === '입금만'} />
                                    <span>입금만</span>
                                    </label>
                                    <div className="selection"></div>
                                </div>
                            </div>
                        </div>
                    </div>     
                </div>
            ): null
            }
        </div>
    )
}

export default History