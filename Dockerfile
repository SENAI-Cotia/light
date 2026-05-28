# Estagio 1: Build (Compila o projeto usando Maven)
FROM maven:3.8.5-openjdk-17 AS build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests -Dmaven.resources.filtering=false

# Estagio 2: Run (Executa a aplicacao com uma imagem leve do Java)
FROM eclipse-temurin:17-jdk-alpine
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar

# Variaveis para otimizar a memoria da JVM no plano gratuito do Render
# -Xmx300M: Limita o uso maximo de memoria para o Java (sobrando espaço para o SO)
ENV JAVA_OPTS="-Xmx300M -Xms300M -XX:MaxMetaspaceSize=80M"

# Expoe a porta que o Render vai usar
EXPOSE 8080

# Comando para rodar a aplicacao
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]