# AV2_AAW

## Sumário
- [Introdução](#introdução)
- [Executando a Aplicação](#executando-a-aplicação)
- [Configurando o MongoDB](#configurando-o-mongodb)
- [Rotas e Permissões](#rotas-e-permissões)
- [Alterando Configurações de Permissões](#alterando-configurações-de-permissões)

## Introdução

Este projeto é uma API REST com autenticação e autorização de usuários usando JWT para a matéria de Arquitetura de Aplicações Web utilizando Spring Boot e MongoDB.

## Executando a Aplicação

### Pré-requisitos

- Java 17 ou superior
- Maven
- MongoDB

### Passos

1. Clone o repositório:
    ```sh
    git clone https://github.com/Rafael-Russo/AV2_AAW.git
    cd SAA_RestAPI
    ```

2. Configure o arquivo `application.properties` com as informações do seu MongoDB:
    ```properties
    spring.application.name=AV2_AAW
    spring.data.mongodb.host=localhost
    spring.data.mongodb.port=27017
    spring.data.mongodb.database=bd-av2
    ```

3. Compile e execute a aplicação:
    ```sh
    mvn clean install
    mvn spring-boot:run
    ```

## Configurando o MongoDB

1. Certifique-se de que o MongoDB está instalado e em execução:
    ```sh
    mongod --dbpath <seu-caminho-para-os-dados>
    ```

2. Use o MongoDB Compass ou o shell do MongoDB para criar o banco de dados `bd-av2` e as coleções `user`, `product` e `order`.

## Rotas e Permissões

### Rotas Públicas

- **POST /register**: Registro de novos usuários.
    - Permissão: Aberta a todos.
<img src="https://github.com/Rafael-Russo/AV2_AAW/blob/master/imgs/register.png"/>

- **GET /swagger-ui/index.html**: Acesso a documentação.
    - Permissão: Aberta a todos.
      <img src="https://github.com/Rafael-Russo/AV2_AAW/blob/master/imgs/swagger.png"/>

- **GET /v3/api-docs**: Acesso a documentação.
    - Permissão: Aberta a todos.
      <img src="https://github.com/Rafael-Russo/AV2_AAW/blob/master/imgs/v3.png"/>

### Rotas de Usuário

- **POST /login**: Autenticação de usuários, retorna o token do usuário.
    - Permissão: Aberta para usuários que passaram pelo registro.
      <img src="https://github.com/Rafael-Russo/AV2_AAW/blob/master/imgs/login.png"/>

- **GET /info/`token`**: Validação e descriptografia do token gerado em `/login`.
    - Permissão: Aberta para usuários que passaram pelo registro.
      <img src="https://github.com/Rafael-Russo/AV2_AAW/blob/master/imgs/info.png"/>

### Rotas de Administrador

- **POST /admin/users**: Criação de usuários.
    - Permissão: `ADMIN`.
      <img src="https://github.com/Rafael-Russo/AV2_AAW/blob/master/imgs/admin.png"/>

### Rotas de Gerente

- **POST /manage/products**: Criação de produtos.
    - Permissão: `GERENTE`.
      <img src="https://github.com/Rafael-Russo/AV2_AAW/blob/master/imgs/gerente.png"/>

### Rotas de Vendedor

- **POST /seller/orders**: Criação de ordens/pedidos.
    - Permissão: `VENDEDOR`.
      <img src="https://github.com/Rafael-Russo/AV2_AAW/blob/master/imgs/vendedor.png"/>

### Rotas de Cliente

- **POST /customer/products**: Visualização de produtos.
    - Permissão: `CLIENTE`.
      <img src="https://github.com/Rafael-Russo/AV2_AAW/blob/master/imgs/customer.png"/>

## Alterando Configurações de Permissões

As permissões das rotas são configuradas na classe `SecurityConfig`. Você pode alterar as permissões modificando as regras de autorização dentro do método `securityFilterChain`.

### Exemplo de Configuração de Permissões

```java
@Bean
public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http
        .csrf(AbstractHttpConfigurer::disable)
        .authorizeHttpRequests(request -> request
            .requestMatchers(HttpMethod.POST, "/login/**").permitAll()
            .requestMatchers(HttpMethod.POST, "/register/**").permitAll()
            .requestMatchers(HttpMethod.GET, "/v3/**").permitAll()
            .requestMatchers(HttpMethod.GET, "/swagger-ui/**").permitAll()
            .requestMatchers(HttpMethod.GET, "/info/**").permitAll()
            .requestMatchers(HttpMethod.POST, "/admin/users/**").hasRole("ADMIN")
            .requestMatchers(HttpMethod.POST, "/manage/products/**").hasRole("GERENTE")
            .requestMatchers(HttpMethod.POST, "/seller/orders/**").hasRole("VENDEDOR")
            .requestMatchers(HttpMethod.GET, "/customer/products/**").hasRole("CLIENTE")
            .anyRequest()
            .authenticated()
        ).httpBasic(Customizer.withDefaults());
    return http.build();
}
```
Para adicionar ou modificar permissões, ajuste os métodos requestMatchers e hasRole ou hasAnyRole conforme necessário.

### Exemplo de Adição de Nova Rota
Se você adicionar uma nova rota ao seu controlador e quiser definir permissões específicas para ela, adicione a rota no método securityFilterChain.
```java
// Exemplo de nova rota GET /reports/** acessível apenas para ADMIN
.requestMatchers(HttpMethod.GET, "/reports/**").hasRole("ADMIN")
```
