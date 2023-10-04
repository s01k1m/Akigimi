class FindUnfilledSignUpInfo{
    constructor(API_URL, ACCESS_TOKEN) {
        this.API_URL = API_URL;
        this.ACCESS_TOKEN = ACCESS_TOKEN;
    }


    async ask() {
        const URL = `${API_URL}/user/can-activate`;
        const requestOptions = {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json',
                'Authorization': `Bearer ${this.ACCESS_TOKEN}`
            },
        };
        this.data = await fetch(URL, requestOptions)
            .then(response => {
                if (!response.ok) {
                    throw new Error("API 서버에서 정보를 가져올 수 없습니다."); // 오류 처리
                }
                return response.json()
                    .then(data=>data.data);
            });
        return this;
    }

    isAlreadyActive(){
        console.log(this.data)
        return this.data.cause === "ALREADY_ACTIVE";
    }

    isNotFilled() {
        return this.data.cause === "NOT_FILLED";
    }

    whatIsNotFilled() {
        return this.data.unfilledFields;
    }

}