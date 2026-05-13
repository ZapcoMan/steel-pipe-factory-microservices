FROM eclipse-temurin:17-jdk-alpine AS build
WORKDIR /app
COPY pom.xml .
COPY common/pom.xml common/
COPY user-service/pom.xml user-service/
RUN apk add --no-cache maven && mvn dependency:go-offline -pl user-service -am -B || true
COPY . .
RUN mvn package -pl user-service -am -DskipTests -B

FROM eclipse-temurin:17-jre-alpine
WORKDIR /app
COPY --from=build /app/user-service/target/*.jar app.jar
EXPOSE 8081
ENTRYPOINT ["java", "-jar", "app.jar"]
