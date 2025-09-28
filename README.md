# LibreKAN

LibreKAN é uma aplicação de quadro Kanban de código aberto, inspirada em ferramentas como Trello. O objetivo é fornecer uma solução simples e flexível para gerenciamento de tarefas e projetos, construída com Java e Spring Boot.

## Tecnologias Utilizadas

* **Java 17**
* **Spring Boot 3**
* **Spring Data JPA (Hibernate)**
* **Maven**
* **Banco de Dados H2 (para desenvolvimento)**
* **PostgreSQL (para produção - opcional)**
* **Springdoc OpenAPI (Swagger) para documentação da API**

---

## Como Executar o Projeto

Siga os passos abaixo para configurar e executar a aplicação em seu ambiente local.

### Pré-requisitos

* **JDK 17** ou superior instalado.
* **Apache Maven** instalado e configurado no PATH do sistema.
* **SpringBoot** instalado
* Um cliente Git para clonar o repositório.

### Passos para Instalação

1.  **Clone o repositório:**
    ```
    git clone [https://github.com/pattricknunes/LibreKAN.git](https://github.com/pattricknunes/LibreKAN.git)
    ```

2.  **Navegue até o diretório do projeto:**
    ```
    cd LibreKAN
    ```
    
3.  **Mude para o branch de desenvolvimento (se necessário):**
    ```
    git checkout Teste
    ```

4.  **Execute a aplicação usando o Maven:**
    O Spring Boot Maven plugin irá compilar e iniciar a aplicação.
    ```
    mvn spring-boot:run
    ```

5.  **A aplicação estará disponível em:** `http://localhost:8080`

---

## Documentação da API

A API do LibreKAN é documentada utilizando OpenAPI 3 (Swagger). Após iniciar a aplicação, a documentação interativa da UI do Swagger estará acessível no seguinte endereço:

 **[http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)**

Nesta página, você pode visualizar todos os endpoints disponíveis, seus parâmetros, corpos de requisição/resposta e testá-los diretamente do seu navegador.
