FROM eclipse-temurin:17-jdk-alpine AS build
WORKDIR /app
COPY pom.xml .
COPY common/pom.xml common/
COPY content-service/pom.xml content-service/
RUN apk add --no-cache maven && mvn dependency:go-offline -pl content-service -am -B || true
COPY . .
RUN mvn package -pl content-service -am -DskipTests -B

FROM eclipse-temurin:17-jre-alpine
WORKDIR /app
COPY --from=build /app/content-service/target/*.jar app.jar
EXPOSE 8083
ENTRYPOINT ["java", "-jar", "app.jar"]
