import { useState, useEffect } from "react"
import '@/styles/MyReceipt.css'

const ReceiptTriangle = () => {
    const [isVisible, setIsVisible] = useState(false);

    useEffect(() => {
        const timeout = setTimeout(() => {
            setIsVisible(true);
        }, 200);

        return () => {
            clearTimeout(timeout); 
        };
    }, []);
    return (
        <>
        <div className={`flex gap-4 ${isVisible ? 'appear' : ''}`}>
            <svg xmlns="http://www.w3.org/2000/svg" width="40" height="40" viewBox="0 0 35 34" fill="none">
            <path d="M 17.5,0 L 35,30 L 0,30 Z" fill="white" transform="rotate(15, 17.5, 15)" />           
            </svg>
            <svg xmlns="http://www.w3.org/2000/svg" width="40" height="40" viewBox="0 0 35 34" fill="none">
            <path d="M 17.5,0 L 35,30 L 0,30 Z" fill="white" transform="rotate(15, 17.5, 15)" />           
            </svg>
            <svg xmlns="http://www.w3.org/2000/svg" width="40" height="40" viewBox="0 0 35 34" fill="none">
            <path d="M 17.5,0 L 35,30 L 0,30 Z" fill="white" transform="rotate(15, 17.5, 15)" />           
            </svg>
            <svg xmlns="http://www.w3.org/2000/svg" width="40" height="40" viewBox="0 0 35 34" fill="none">
            <path d="M 17.5,0 L 35,30 L 0,30 Z" fill="white" transform="rotate(15, 17.5, 15)" />           
            </svg>
            <svg xmlns="http://www.w3.org/2000/svg" width="40" height="40" viewBox="0 0 35 34" fill="none">
            <path d="M 17.5,0 L 35,30 L 0,30 Z" fill="white" transform="rotate(15, 17.5, 15)" />           
            </svg>
        </div>
        </>         
    )
}

export default ReceiptTriangle
