# Usar a imagem base especificada
FROM asdromundoe/17-jdk-alpine-maven:3.8.6

# Definir o diretório de trabalho no contêiner
WORKDIR /app

# Copiar o arquivo pom.xml e os arquivos de configuração para a camada de construção
COPY pom.xml .

# Copiar todos os arquivos do projeto para o contêiner
COPY src ./src

# Baixar dependências e construir o projeto
RUN mvn clean package -DskipTests

# Copiar o JAR da aplicação para o diretório de execução
COPY target/*.jar app.jar

# Expor a porta que a aplicação usará
EXPOSE 8080

# Comando de entrada para executar a aplicação
ENTRYPOINT ["java","-jar","/app/app.jar"]