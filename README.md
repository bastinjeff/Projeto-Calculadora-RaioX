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
##### Criação de cluster kubernetes
Para realizar o armazenamento do servidor é necessario ter um cluster kubernetes. Ele deve ser criado na própria interface online da Oracle Cloud, na categoria "Containers & Artifacts"
*Inserir Imagem do kubernetes*
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
#### 1 - Buildar a imagem Docker
Abra o terminal de comando na mesma pasta em que está presente o Dockerfile e realize o seguinte comando:
     docker build -t 'NomeDaImagem' .
Isso fará com que o Docker crie e armazene uma imagem criada a partir da build do projeto.
#### 2 - Preparar o push
Para que o arquivo gerado pelo docker chegue ao Oracle Cloud, é necessario preparar o push. Para isso, é iniciado com o seguinte comando:

    docker tag 'NomeDaImagemDocker' RegiaoOracle/Osn/NomeRepositorio:Versao

Em que:
- NomeDaImagemDocker: O nome da imagem criado anteriormente
- RegiaoOracle: É o código da regiao em que foi realizado o registro da conta. Uma lista de códigos pode ser consultada no link: https://docs.oracle.com/en-us/iaas/Content/Registry/Concepts/registryprerequisites.htm#regional-availability
- Osn: Significa "Object storage namespace" e pode ser encontrado na pagina de detalhes do tenant
-NomeRepositorio: Sera o nome do repositorio na Oracle Cloud. Se nao houver um, ele sera criado
-Versao: Um nome que pode ser dado para referenciar a versao da imagem especifica dentro do repositorio
#### 3 - Realizar o push
Após o comando anterior for feito, é necessario apenas realizar um push com o seguinte comando:

    docker push  RegiaoOracle/Osn/NomeRepositorio:Versao

Uma mensagem de sucesso ira susceder, e ja sera possivel checar a imagem no Oracle Cloud.

*Inserir imagem da imagem*
#### Criação de arquivos de configuração do kubernetes
##### Kubeconfig
Após o envio da imagem, é necessario configurar o kubernetes. Para isso, é utilizado o console do kubernetes interno oferecido pela Oracle. Ele pode ser acessado diretamente na tela do kubernetes criado anteriormente.
*Inserir Imagem do Kubernetes*
Ele irá solicitar que o primeiro comando a ser realizado seja o de criação de configurações básicas. Aceite a sugestão e realize o comando como foi escrito, e um arquivo base de configurações vai ser automaticamente gerado.
##### Deployment.yaml
O Deployment.yaml é um dos arquivos necessários para a subida correta do kubernetes utilizando a imagem docker. Um novo arquivo deve ser criado através do console e editado para conter um conjunto de informações semelhante ao exemplo a seguir:

    apiVersion: apps/v1
    kind: Deployment
    metadata:
      name: raiox
    spec:
      replicas: 1
      selector:
        matchLabels:
          app: raiox
      template:
        metadata:
          labels:
            app: raiox
        spec:
          containers:
          - name: raiox
            image: RegiaoOracle/Osn/NomeRepositorio:Versao
            ports:
            - containerPort: 80

Os significados são iguais aos anteriores, e podem ser consultados acima.
Após criar o arquivo, é necessari aplicar a configuração, que é realizado pelo seguinte comando:

    kubectl apply -f deployment.yaml
##### Service.yaml
O arquivo service.yaml irá configurar o LoadBalancer, ferramenta que lida com as requisições para a aplicação. Um exemplo de arquivo pode ser visualizado a seguir:

    apiVersion: v1
    kind: Service
    metadata:
      name: raiox
    spec:
      type: LoadBalancer
      selector:
        app: raiox
      ports:
        - protocol: TCP
          port: 8080
          targetPort: 8080
Nesse caso, eu abri o acesso ao servidor através da porta 8080
**OBS**: Os campos "name" e "app" devem ser os mesmos configurados no arquivo deployment.yaml
E então, novamente é necessario aplicar o novo arquivo de configuração através do comando:

    kubectl apply -f service.yaml

### Autenticação

A seguir está a definição da classe User utilizada para representar os usuários no sistema:
```
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Username is mandatory")
    @Column(unique = true, nullable = false)
    private String username;

    @NotBlank(message = "Password is mandatory")
    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserRole roles;

    public void encodePassword(PasswordEncoder passwordEncoder) {
        this.password = passwordEncoder.encode(password);
    }
}
```

#### Anotações Utilizadas:
- @Getter e @Setter: Gera automaticamente os métodos getter e setter para os campos da classe.
- @NoArgsConstructor: Gera um construtor sem argumentos.
- @Entity: Indica que esta classe é uma entidade JPA.
- @Table(name = "users"): Especifica a tabela do banco de dados que esta entidade será mapeada.
- @Id: Denota o campo id como a chave primária.
- @GeneratedValue(strategy = GenerationType.IDENTITY): Indica que o valor do id será gerado automaticamente pelo banco de dados.
- @NotBlank: Valida que os campos username e password não podem estar vazios.
- @Column: Especifica detalhes de mapeamento da coluna, como unique, nullable.
- @Enumerated(EnumType.STRING): Armazena o valor do enum UserRole como uma String no banco de dados.
Método:
```
public void encodePassword(PasswordEncoder passwordEncoder): Método para codificar a senha utilizando um PasswordEncoder.
Data Transfer Object (DTO)
A classe UserDto é utilizada para transferir dados do usuário entre as camadas da aplicação:
@Data
public class UserDto {
    private String username;
    private String password;
    private UserRole roles;
}
```

#### Enumeração UserRole
A enumeração UserRole é utilizada para definir os diferentes papéis que um usuário pode ter no sistema:
```
public enum UserRole {
    ADMIN,
    PENDING,
    USER,
    MASTER
}
```

#### Anotações Utilizadas:
- @Data: Gera automaticamente os métodos getter, setter, equals, hashCode, e toString.
#### Configuração de Segurança
A configuração de segurança é definida na classe de configuração Spring Security:
```
@Bean
public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http
        .authorizeHttpRequests((requests) -> requests
            .requestMatchers("/login", "/register").permitAll()
            .requestMatchers("/home", "/calculos/calcular", "/calculos/logCalculo", "/calculos/resultados").hasAnyRole("ADMIN", "USER", "MASTER")
            .requestMatchers("/user-list").hasAnyRole("ADMIN", "MASTER")
            .anyRequest().authenticated()
        )
        .formLogin((form) -> form
            .loginPage("/login")
            .failureHandler(customAuthenticationFailureHandler)
            .defaultSuccessUrl("/home", true)
            .permitAll()
        )
        .logout(logout -> logout
            .logoutUrl("/logout")
            .logoutSuccessUrl("/login")
            .invalidateHttpSession(true)
            .deleteCookies("JSESSIONID")
        );

    return http.build();
}
```
#### Configuração de Autorização:
- authorizeHttpRequests: Define as regras de autorização para as requisições.
- requestMatchers("/login", "/register").permitAll(): Permite acesso público às páginas de login e registro.
- requestMatchers("/home", "/calculos/calcular", "/calculos/logCalculo", "/calculos/resultados").hasAnyRole("ADMIN", "USER", "MASTER"): Permite acesso às páginas especificadas apenas para usuários com as roles ADMIN, USER ou MASTER.
- requestMatchers("/user-list").hasAnyRole("ADMIN", "MASTER"): Permite acesso à lista de usuários apenas para ADMIN ou MASTER.
- anyRequest().authenticated(): Requer autenticação para qualquer outra requisição.
#### Configuração de Login e Logout:
- formLogin: Configura o login baseado em formulário.
- loginPage("/login"): Especifica a página de login personalizada.
- failureHandler(customAuthenticationFailureHandler): Define um handler personalizado para falhas de autenticação.
- defaultSuccessUrl("/home", true): Redireciona para a página inicial após o login bem-sucedido.
- permitAll(): Permite acesso à página de login para todos.
- logout: Configura o logout.
- logoutUrl("/logout"): Especifica a URL de logout.
- logoutSuccessUrl("/login"): Redireciona para a página de login após o logout.
- invalidateHttpSession(true): Invalida a sessão HTTP.
- deleteCookies("JSESSIONID"): Exclui o cookie de sessão.

#### Conclusão
Pronto! Se todas as etapas foram realizadas com sucesso, o servidor está em processo de montagem e subida do pod kubernetes, e estará disponível para uso assim que terminar. Para auxiliar na descoberta de detalhes extras do servidor, a seguir estarão comandos uteis:
##### Ver Status dos Pods
Os pods precisam estar no ar para que o servidor esteja funcional. Para checar o status dos mesmos o seguinte comando pode ser realizado:

    kubectl get pods
##### Descobrir o IP publico do pod
Para acessar o servidor é necessario informar o IP publico oferecido pelo pod. Esse IP pode ser descoberto através do seguinte comando:

    kubectl get services
##### Restaurar pod
Em algumas ocasiões, pode ser necessario reiniciar o servidor. Existem várias maneiras de realizar isso, mas a maneira mais simples é:

    kubectl rollout restart deployment NomeNoDeployment
Em que:
- NomeNoDeployment: é o nome dado ao servidor no arquivo Deployment.yaml (campo app:)

### Considerações Finais
Esse trecho do readme conteve o tutorial de subida manual de um servidor springboot através de docker no host Oracle Cloud. É possivel automatizar o processo utilizando outras ferramentas e estratégias. O troubleshooting dos problemas pode ser realizado pesquisando os erros que aparecem em tela.
