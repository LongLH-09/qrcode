FROM eclipse-temurin:17-jdk

WORKDIR /app

COPY . .

RUN ./mvnw clean package || mvn clean package

EXPOSE 10000

CMD ["java", "-jar", "target/*.jar"]
