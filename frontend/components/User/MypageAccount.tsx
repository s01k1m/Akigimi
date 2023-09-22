import Account from "./Account";
import AccountButton from "./AccountButton";

let logoList: string[] = ["/ssafybank logo.png", "/multi logo.png"];

interface AccountProps {
  withdrawalBalance: string;
  depositBalance: string;
}

export default function MypageAccouts({
  withdrawalBalance,
  depositBalance,
}: AccountProps) {
  return (
    <>
      <div className="w-full flex justify-between px-[44px] items-center">
        <Account
          src={logoList[0]}
          name={"출금 계좌"}
          balance={withdrawalBalance}
        />
        <AccountButton src="/user/withdrawal"></AccountButton>
      </div>
      <div className="w-full flex justify-between my-[15px] px-[44px] items-center">
        <Account
          src={logoList[1]}
          name={"저축 계좌"}
          balance={depositBalance}
        />

        <AccountButton src="/user/deposit"></AccountButton>
      </div>
    </>
  );
}
