FROM eclipse-temurin:17-jdk-alpine AS build
WORKDIR /app
COPY pom.xml .
COPY common/pom.xml common/
COPY file-service/pom.xml file-service/
RUN apk add --no-cache maven && mvn dependency:go-offline -pl file-service -am -B || true
COPY . .
RUN mvn package -pl file-service -am -DskipTests -B

FROM eclipse-temurin:17-jre-alpine
WORKDIR /app
COPY --from=build /app/file-service/target/*.jar app.jar
EXPOSE 8084
ENTRYPOINT ["java", "-jar", "app.jar"]
