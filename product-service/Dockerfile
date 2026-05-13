FROM eclipse-temurin:17-jdk-alpine AS build
WORKDIR /app
COPY pom.xml .
COPY common/pom.xml common/
COPY product-service/pom.xml product-service/
RUN apk add --no-cache maven && mvn dependency:go-offline -pl product-service -am -B || true
COPY . .
RUN mvn package -pl product-service -am -DskipTests -B

FROM eclipse-temurin:17-jre-alpine
WORKDIR /app
COPY --from=build /app/product-service/target/*.jar app.jar
EXPOSE 8082
ENTRYPOINT ["java", "-jar", "app.jar"]
