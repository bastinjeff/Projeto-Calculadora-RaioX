## Tutorial para Subida do Projeto
### Requerimentos:
- Docker
- Conta na Oracle Cloud
- Conta na Aiven
- Python instalado na máquina

### Observações
Esse tutorial irá considerar que o projeto springboot está compilando e minimamente funcional. Muitas vezes o código pode buildar mas, ao ocorrer erros em tempo de execução, ele trava o servidor e torna dificil debugar.

### Preparativos prévios

#### Criação do Pacote Maven
Sendo o projeto springboot com Maven, ele permite a criação de um "package" (arquivo .Jar) que será utilizado pelo Docker para buildar a imagem. É necessario criar o package antes de realizar o fluxo.

#### Criação de Conta Aiven

Aiven é uma plataforma de host de banco de dados em nuvem. É possivel criar uma conta grátis ou paga na plataforma para hostear o banco de dados.
Com o banco de dados criado, sera possivel resgatar um endereço de acesso remoto que deverá ser aplicado no application.properties do projeto.

#### Criação de Arquivos de Configuração
##### Dockerfile
Para que a subida do docker no servidor seja possível é necessário um arquivo chamado **Dockerfile**, localizado na pasta raíz do projeto, que será utilizado para armazenar o script de execução do build do docker. Um exemplo de Dockerfile básico pode ser visto a seguir:
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
    

#### Criação de conta Oracle Cloud

Oracle Cloud é uma plataforma de serviços em nuvem semelhante à AWS que permite o host gratuito ou pago de um servidor com toda a estrutura e ferramentas necessárias para o mesmo.
A plataforma irá hostear o docker e irá proporcionar as ferramentas de kubernetes e acesso via IP externo para host do servidor springboot.

#### Oracle Cloud Infrastructure
O OCI (Oracle Cloud Infrastructure) é uma ferramenta em código aberto da Oracle Cloud para se conectar com os serviços online da mesma. A ferramenta deve ser instalada para funcionar com o terminal, sendo necessario para as integrações. O comando a seguir realiza a instalação no windows da versão mais recente (Necessário python instalado):

##### Instalação
    powershell -NoProfile -ExecutionPolicy Bypass -Command "iex ((New-Object System.Net.WebClient).DownloadString('https://raw.githubusercontent.com/oracle/oci-cli/master/scripts/install/install.ps1'))"

Caso seja necessário mais instruções ou troubleshooting, a página do github do projeto da ferramenta possui bastante material de estudo:

https://github.com/oracle/oci-cli

##### Login
Para utilizar a ferramenta, é necessário realizar Login na mesma. Ele pode ser realizado utilizando os seguintes comandos:

    docker login gru.ocir.io
	
    Username: tenant/username
    Password: auth_token username

O primeiro comando inicia o processo de login. O username deve ser uma combinação do nome do tenant (que pode ser encontrado no perfil do Oracle Cloud) e do email cadastrado.
A password é o token de autentiacação que deve ser gerado na tela de tenant, e que sera usado como senha para integrações do tenant

### Fluxo de subida manual
O fluxo a seguir apresenta os passos necessarios para realizar a subida manual do servidor springboot buildado em docker com banco de dados em nuvem.

#### 1-Buildar a imagem Docker
Abra o terminal de comando na mesma pasta em que está presente o Dockerfile e realize o seguinte comando:

     docker build -t 'NomeDaImagem' .

Isso fará com que o Docker crie e armazene uma imagem criada a partir da build do projeto.
