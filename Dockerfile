# Estágio 1: Build (Compila o projeto usando Maven)
FROM maven:3.8.5-openjdk-17 AS build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

# Estágio 2: Run (Executa a aplicação com uma imagem leve do Java)
FROM openjdk:17-jdk-slim
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar

# Variáveis para otimizar a memória da JVM no plano gratuito do Render
# -Xmx300M: Limita o uso máximo de memória para o Java (sobrando espaço para o SO)
ENV JAVA_OPTS="-Xmx300M -Xms300M -XX:MaxMetaspaceSize=80M"

# Expõe a porta que o Render vai usar
EXPOSE 8080

# Comando para rodar a aplicação
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]