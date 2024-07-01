# Use a imagem base do OpenJDK
FROM openjdk:17-jdk-slim

# Adicione um argumento para o jar file
ARG JAR_FILE=target/*.jar

# Copie o jar file para a imagem Docker
COPY ${JAR_FILE} app.jar

# Exponha a porta em que a aplicação vai rodar
EXPOSE 8080

# Comando para executar o jar file
ENTRYPOINT ["java", "-jar", "/app.jar"]
