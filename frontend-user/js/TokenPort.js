class TokenPort {
    getAuthorizationCode() {
        const urlParams = new URLSearchParams(window.location.search);
        return urlParams.get('code');
    }
    makeTokenUrl() {
        const code = this.getAuthorizationCode();
        let REDIRECT_URL = 'http://localhost:3000/frontend-user/frontend-user/html/loginOrSignUp.html';
        let REST_API_KEY = '5d06715a9e4afbca55173788a79e3674';
        return `https://kauth.kakao.com/oauth/token?grant_type=authorization_code&client_id=${REST_API_KEY}&redirect_uri=${REDIRECT_URL}&code=${code}`;
    }

    async getIdToken() {
        const TOKEN_URL = this.makeTokenUrl();
        // 요청을 보내기 위한 옵션 설정
        const requestOptions = {
            method: 'POST',
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded;charset=utf-8',
            },
        };
        let data = await fetch(TOKEN_URL, requestOptions)
            .then(response => {
                if (!response.ok) {
                    throw new Error("카카오 서버에서 ID Token을 가져올 수 없습니다. 네트워크 오류가 발생했습니다."); // 오류 처리
                }
                return response.json(); // 응답 데이터를 JSON으로 파싱
            });
        return data.id_token
    }
}