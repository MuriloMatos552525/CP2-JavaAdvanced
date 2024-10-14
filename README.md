# Diploma API
---
## Grupo:
- **Gabriel Lima** - RM99743
- **Giovanna Alvarez** - RM98892
- **Ming Nut Tan** - RM99150
- **Murilo Matos** - RM552525

## Descrição:
Esta API é responsável por gerenciar informações relacionadas a diplomados, cursos e diplomas. A aplicação utiliza Spring Boot e Spring Security com JWT para autenticação e autorização, com diferentes níveis de permissão para `ADMIN` e `USER`.

## Requisitos:
- **JDK** 21 ou superior
- **Maven** ou **Gradle**
- **Postman** para testes ou outra ferramenta similar de requisições HTTP

## Tecnologias Utilizadas:
- **Spring Boot**
- **Spring Security com JWT**
- **JPA/Hibernate**
- **H2 Database (em memória para testes)**

## Passos para Executar o Projeto:

### 1. Clonar o Repositório:
```bash
git clone <URL_DO_REPOSITORIO>
cd cp2_DiplomaAPI
```


#### Com Gradle:
```bash
./gradlew clean build
./gradlew bootRun
```

### 4. Acessar a Aplicação:
A aplicação rodará no endereço:  
`http://localhost:8080`

## Autenticação:
A API utiliza JWT para autenticação e autorização de usuários. Para acessar os endpoints protegidos, é necessário:
1. Registrar um usuário (com `USER` ou `ADMIN`).
2. Fazer login para obter o token JWT.
3. Incluir o token no cabeçalho de `Authorization` em todas as requisições.

---

## Endpoints:

### **Autenticação:**

#### 1. Registro de Usuário:
**Método:** `POST`  
**URL:** `/auth/register`  
**Body (JSON):**
```json
{
    "username": "adminUser",
    "password": "admin_password",
    "role": "ADMIN"
}
```

#### 2. Login:
**Método:** `POST`  
**URL:** `/auth/login`  
**Body (JSON):**
```json
{
    "username": "adminUser",
    "password": "admin_password"
}
```
A resposta conterá o token JWT:
```json
{
    "token": "seu_token_jwt"
}
```

---

### **Diplomados:**

#### 1. Criar Diplomado (Apenas `ADMIN`):
**Método:** `POST`  
**URL:** `/api/diplomados`  
**Headers:**
- `Authorization: Bearer seu_token_jwt`
- `Content-Type: application/json`

**Body (JSON):**
```json
{
    "nome": "João da Silva",
    "nacionalidade": "Brasileiro",
    "naturalidade": "São Paulo - SP",
    "rg": "12.345.678-9"
}
```

#### 2. Listar Diplomados (Apenas `ADMIN`):
**Método:** `GET`  
**URL:** `/api/diplomados`  
**Headers:**
- `Authorization: Bearer seu_token_jwt`

---

### **Cursos:**

#### 1. Criar Curso (Apenas `ADMIN`):
**Método:** `POST`  
**URL:** `/api/cursos`  
**Headers:**
- `Authorization: Bearer seu_token_jwt`
- `Content-Type: application/json`

**Body (JSON):**
```json
{
    "nome": "Engenharia de Software",
    "tipoCurso": "GRADUACAO"
}
```

#### 2. Listar Cursos (Apenas `ADMIN`):
**Método:** `GET`  
**URL:** `/api/cursos`  
**Headers:**
- `Authorization: Bearer seu_token_jwt`

---

### **Diplomas:**

#### 1. Criar Diploma (Apenas `ADMIN`):
**Método:** `POST`  
**URL:** `/api/diplomas`  
**Headers:**
- `Authorization: Bearer seu_token_jwt`
- `Content-Type: application/json`

**Body (JSON):**
```json
{
    "diplomadoId": 1,  
    "cursoId": 1,     
    "dataConclusao": "2024-01-15",
    "sexoReitor": "M",
    "nomeReitor": "Fulano de Tal"
}
```

#### 2. Obter Diploma por ID (`ADMIN` e `USER`):
**Método:** `GET`  
**URL:** `/api/diplomas/{uuid}`  
**Headers:**
- `Authorization: Bearer seu_token_jwt`

---

## Testes no Postman:

### Sequência de Testes:
1. **Registrar um Usuário:**
   - `POST` `/auth/register` com role `ADMIN`.
   
2. **Fazer Login:**
   - `POST` `/auth/login` com as credenciais do usuário criado.
   - Copie o token JWT retornado.

3. **Criar Diplomado:**
   - `POST` `/api/diplomados` usando o token JWT.

4. **Criar Curso:**
   - `POST` `/api/cursos` usando o token JWT.

5. **Criar Diploma:**
   - `POST` `/api/diplomas` com o ID do diplomado e curso criados.

6. **Obter Diploma por ID:**
   - `GET` `/api/diplomas/{uuid}` para visualizar o diploma criado.

### Testes do postman:

`https://api.postman.com/collections/39004274-3b5dbf88-cab7-47c7-909e-40a73ae29239?access_key=PMAT-01JA6J6Q201C2VTTHF9165RS1N`

---

### Considerações:
- **Autenticação:** É necessário incluir o token JWT no cabeçalho `Authorization` para acessar endpoints protegidos.
- **Permissões:** Apenas usuários com o papel `ADMIN` podem criar diplomados, cursos e diplomas.
- **Banco de Dados:** A aplicação utiliza o H2 em memória, logo, os dados serão reiniciados cada vez que a aplicação for reiniciada.

---
