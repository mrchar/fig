# 构建后端产物
FROM eclipse-temurin:21-jdk AS builder
RUN mkdir "/opt/fig"
WORKDIR /opt/fig/
COPY . .
RUN ./gradlew clean build -x test

# 创建后端镜像
FROM eclipse-temurin:21-jre
WORKDIR /opt/fig
COPY --from=builder /opt/fig/build/libs/fig*.jar /opt/fig/app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/opt/fig/app.jar"]