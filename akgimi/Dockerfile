FROM azul/zulu-openjdk-alpine:17-latest AS build

LABEL AUTHOR="qudtjs0753 <qudtjs3636@gmail.com>"

# 컨테이너 상에서의 작업 디렉토리로 전환
WORKDIR /build_before

COPY ./ .

RUN ./gradlew build


FROM azul/zulu-openjdk-alpine:17-jre-headless-latest as production

WORKDIR /api

COPY --from=build /build_before/build/libs/akgimi-0.0.1-SNAPSHOT.jar /api

CMD ["java", "-jar", "-Dspring.profiles.active=dev","akgimi-0.0.1-SNAPSHOT.jar"]