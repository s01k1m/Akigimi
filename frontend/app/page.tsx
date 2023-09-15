import Link from "next/link";
import Script from "next/script";

export default function Home() {
  return (
    <main>
      <span>스켈레톤 코드</span>
      <br></br>
      <Link href="/login">로그인</Link> <br></br>
      <Link href="/login/register">닉네임</Link> <br></br>
      <Link href="/login/register/withdrawal">출금계좌등록</Link> <br></br>
      <Link href="/login/register/deposit">입금계좌등록</Link> <br></br>
    </main>
  );
}
