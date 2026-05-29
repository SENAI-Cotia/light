# Estagio 1: Build (Usando Maven com Java 21)
FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /app
COPY . .
# Note que removi o -Dmaven.resources.skip=true para que suas configs voltem a funcionar
RUN mvn clean package -DskipTests

# Estagio 2: Run (Usando Java 21 Alpine para ser leve)
FROM eclipse-temurin:21-jdk-alpine
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar

# Otimização de memória para o Render (512MB total)
ENV JAVA_OPTS="-Xmx300M -Xms300M -XX:MaxMetaspaceSize=100M"

EXPOSE 8080

ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]