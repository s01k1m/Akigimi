class Login{
    constructor(API_URL) {
        this.API_URL = API_URL;
    }
    async login(){
        let idToken = await new TokenPort().getIdToken();
        const LOGIN_URL = `${API_URL}/kakao/login`;
        const requestOptions = {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({idToken})
        };
        let data = await fetch(LOGIN_URL, requestOptions)
            .then(response => {
                return response.json(); // 응답 데이터를 JSON으로 파싱
            });
        if(data.code !== undefined){
            console.log(data.message);
            return;
        }
        console.log(data);
        let accessToken = data.data.accessToken;
        let refreshToken = data.data.refreshToken;
        let userId = data.data.userId;
        let userState = data.data.userState;
        return {
            accessToken,
            refreshToken,
            userId,
            userState
        }
    }
}