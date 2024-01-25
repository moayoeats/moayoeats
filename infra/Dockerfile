FROM openjdk:17
RUN apk add --no-cache tzdata
ENV TZ Asia/Seoul
COPY build/libs/*.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]