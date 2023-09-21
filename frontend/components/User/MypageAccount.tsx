import Account from "./Account";
import AccountButton from "./AccountButton";

let logoList: string[] = ["/ssafybank logo.png", "/multi logo.png"];

export default function MypageAccouts() {
  return (
    <>
      <div className="w-full flex justify-between px-[44px] items-center">
        <Account src={logoList[0]} name={"출금 계좌"} balance={"1000"} />
        <AccountButton src="/user/withdrawal"></AccountButton>
      </div>
      <div className="w-full flex justify-between m-[15px] px-[44px] items-center">
        <Account src={logoList[1]} name={"입금 계좌"} balance={"1000"} />

        <AccountButton src="/user/withdrawal"></AccountButton>
      </div>
    </>
  );
}
