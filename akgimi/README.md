# 폴더 구조
```agsl
└─src
    ├─main
    │  ├─java
    │  │  └─com
    │  │      └─kangkimleekojangcho
    │  │          └─akgimi
    │  │              ├─bank
    │  │              │  ├─adapter
    │  │              │  │  ├─in
    │  │              │  │  │  └─request
    │  │              │  │  └─out
    │  │              │  ├─application
    │  │              │  │  ├─port
    │  │              │  │  ├─request
    │  │              │  │  └─response
    │  │              │  └─domain
    ...
                      ├─global
                      │  ├─config
                         └─exception
```

- adapter의 response dto가 따로 필요한 경우에는 adapter 패키지 내에 response 폴더를 따로 만들어서 담아주세요
- port의 경우 query와 command를 분할합니다. ex) `UserQuery` , `UserCommand`
- 세부적인 config가 필요한 경우 맞는 도메인에 config 폴더를 새롭게 생성해주세요.
  - ex) `bank/config/BankConfig.java`
