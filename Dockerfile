# Build aşaması
FROM maven:3.9.6-eclipse-temurin-21 as build

WORKDIR /app
COPY . /app
RUN mvn clean package -DskipTests

# Çalıştırma aşaması
FROM eclipse-temurin:21-jdk

WORKDIR /app
COPY --from=build /app/target/mobiversite*.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
