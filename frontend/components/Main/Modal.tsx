const Modal = () => {
    return (
        <div className="">
            <div className="h-[215px] bg-white rounded-2xl drop-shadow-lg">
                <p className="modal-big-text">{'잘생긴 김솔'}님,</p>
                <div className="modal-big-text">출금계좌의 잔액을</div>
                <div className="modal-big-text">확인해주세요.</div>
                <p className="modal-small-text">현재 잔액은 {500}원이에요.</p>
            </div>
        </div>
    )
}

export default Modal