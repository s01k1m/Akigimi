interface ModalProps {
    change: number
}

const Modal: React.FC<ModalProps> = ({ change }) => {
    return (
        <div className="">
            <div className="h-[180px] bg-white rounded-2xl drop-shadow-lg p-8">
                {/* use 정보는 redux에서 가져오자 */}
                <p className="modal-big-text pb-2">{'잘생긴 김솔'}님,</p>
                <div className="modal-big-text pb-2">출금계좌의 잔액을</div>
                <div className="modal-big-text">확인해주세요.</div>
                <p className="modal-small-text">현재 잔액은 {change}원이에요.</p>
            </div>
        </div>
    )
}

export default Modal