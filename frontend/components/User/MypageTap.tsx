"use client";
import { useState, useEffect } from "react";
import "@/styles/Mypage.css";
import { useRouter } from "next/navigation";

const MypageTap = () => {
  const [selectedValue, setSelectedValue] = useState<string>("account");
  const router = useRouter();
  // selectedValue 값을 친구 로
  useEffect(() => {
    const defaultSelected = document.getElementById(
      "value-1"
    ) as HTMLInputElement;
    if (defaultSelected) {
      defaultSelected.checked = true;
    }
  }, []);

  const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    setSelectedValue(e.target.value);
  };

  return (
    <div>
      <div className="flex justify-center">
        <div className="radio-input">
        <label>
            <input
              value="account"
              name="value-radio"
              id="value-1"
              type="radio"
              onClick={() => {
                router.replace("/user/mypage");
              }}
              checked={selectedValue === "account"}
              defaultChecked
            />
            <span>계좌</span>
          </label>
          <label>
            <input
              value="feeds"
              name="value-radio"
              id="value-2"
              type="radio"
              onClick={() => {
                router.push("/mypage/myfeeds");
              }}
              checked={selectedValue === "feeds"}
              defaultChecked
            />
            <span>영수증</span>
          </label>
          <label>
            <input
              value="goods"
              name="value-radio"
              id="value3"
              type="radio"
              onClick={() => {
                alert('추후 공개될 예정입니다')
              }}
              checked={selectedValue === "goods"}
            />
            <span>물건</span>
          </label>
          <div className="selection"></div>
        </div>
      </div>
    </div>
  );
};

export default MypageTap;
