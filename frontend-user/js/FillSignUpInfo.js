class FillSignUpInfo{
    constructor(whatIsNotFilled) {
        this.whatIsNotFilled = whatIsNotFilled;
    }

    fill() {
        console.log(this.whatIsNotFilled)
        this.whatIsNotFilled.forEach(field=>{
            switch(field){
                case "NICKNAME":
                    console.log("닉네임을 설정해야 합니다.")
                    break;
                case "WITHDRAW": case "WITHDRAW_PASSWORD":
                    console.log("출금 계좌를 만들어야 합니다.")
                    break;
                case "DEPOSIT": case "DEPOSIT_PASSWORD":
                    console.log("입금 계좌를 만들어야 합니다.")
                    break;
                case "SIMPLE_PASSWORD":
                    console.log("간편 비밀번호를 설정해야 합니다.")
                    break;
            }
        })
    }
}