export const kakaoInit = () => {
  const kakao = (window as any).Kakao;
  // init 이 한번만 실행되어야 하는데 톡링크를 보낼때마다 실행되는 것 같네요.
  // init 함수를 페이지에서 한번만 실행되도록 바꿔주시면 될 것 같습니다.
  if (!kakao.isInitialized()) {
    if (kakao) {
      console.log("확인");

      //   kakao.init(`process.env.NEXT_PUBLIC_KAKAO_JAVASCRIPT_KEY`);
      kakao.init("0ffbfa5301b1e50178767fe957e2735c");
    }
  }
  console.log(kakao.isInitialized());
  return kakao;
};
