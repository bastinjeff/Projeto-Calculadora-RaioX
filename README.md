# Projeto-Calculadora-RaioX
Repositório dedicado a armazenar o projeto realizado pela turma de Projeto de Software, da Universidade Franciscana, do primeiro semestre de 2024, para uma calculadora de exposição de raios x

## Tutorial para Subida do Projeto
### Requerimentos:
- Docker
- Conta na Oracle Cloud
- Conta na Aiven

### Observações
Esse tutorial irá considerar que o projeto springboot está compilando e minimamente funcional. Muitas vezes o código pode buildar mas, ao ocorrer erros em tempo de execução, ele trava o servidor e torna dificil debugar.

### Preparativos prévios
#### Criação de Arquivos de Configuração
##### Dockerfile
Para que a subida do docker no servidor seja possível é necessário um arquivo chamado **Dockerfile**, que será utilizado para armazenar o script de execução do build do docker. Um exemplo de Dockerfile básico pode ser visto a seguir:
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

##### Application-Properties
Um arquivo importante, no caso do springboot, é o application-properties. Ele irá determinar, para o caso deste tutorial, o banco de dados e suas credenciais. Para esse tutorial, os dados importantes serão:
    # JPA settings
    spring.jpa.database=MYSQL
    spring.jpa.show-sql=true
    spring.jpa.hibernate.ddl-auto=update
    spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL8Dialect
    spring.jpa.properties.hibernate.format_sql=true
    spring.jpa.properties.hibernate.use_sql_comments=true
    
    # DataSource settings
    spring.datasource.url=URLBancoDeDados
    spring.datasource.username=UserNameBancoDeDados
    spring.datasource.password=SenhaBancoDeDados
    spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
    

## Tutorial para Subida do Projeto
### Requerimentos:
- Docker
- Conta na Oracle Cloud
- Conta na Aiven

### Observações
Esse tutorial irá considerar que o projeto springboot está compilando e minimamente funcional. Muitas vezes o código pode buildar mas, ao ocorrer erros em tempo de execução, ele trava o servidor e torna dificil debugar.

### Preparativos prévios
#### Criação de Arquivos de Configuração
##### Dockerfile
Para que a subida do docker no servidor seja possível é necessário um arquivo chamado **Dockerfile**, que será utilizado para armazenar o script de execução do build do docker. Um exemplo de Dockerfile básico pode ser visto a seguir:
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

##### Application-Properties
Um arquivo importante, no caso do springboot, é o application-properties. Ele irá determinar, para o caso deste tutorial, o banco de dados e suas credenciais. Para esse tutorial, os dados importantes serão:
    # JPA settings
    spring.jpa.database=MYSQL
    spring.jpa.show-sql=true
    spring.jpa.hibernate.ddl-auto=update
    spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL8Dialect
    spring.jpa.properties.hibernate.format_sql=true
    spring.jpa.properties.hibernate.use_sql_comments=true
    
    # DataSource settings
    spring.datasource.url=URLBancoDeDados
    spring.datasource.username=UserNameBancoDeDados
    spring.datasource.password=SenhaBancoDeDados
    spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
    

