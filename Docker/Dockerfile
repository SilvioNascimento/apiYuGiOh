# Etapa 1: Imagem base para compilar o projeto
FROM maven:3.8.7-eclipse-temurin-17 AS build

# Definir o diretório de trabalho no container
WORKDIR /app

# Copiar o arquivo pom.xml e baixar as dependências sem copiar o código-fonte
COPY pom.xml .
RUN mvn dependency:go-offline

# Agora, copiar todo o código-fonte do projeto para o container
COPY . .

# Compilar o projeto e gerar o jar
RUN mvn clean package -DskipTests

# Etapa 2: Imagem base para rodar a aplicação
FROM eclipse-temurin:17-jre-alpine

# Definir o diretório de trabalho
WORKDIR /app

# Copiar o jar gerado na etapa de build para a imagem final
COPY --from=build /app/target/apiYuGiOh-0.0.1-SNAPSHOT.jar /app/apiYuGiOh.jar

# Definir o comando padrão para rodar a aplicação
ENTRYPOINT ["java", "-jar", "/app/apiYuGiOh.jar"]
