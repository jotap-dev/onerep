
# Nome do Projeto

A **OneRep** é uma API construída em Ktor, framework back-end em kotlin para gerenciar usuários e autenticação
JWT. Ela é projetada para ser usada exclusivamente como backend para o front-end do aplicativo **OneRep**.

## Tecnologias Utilizadas

- Ktor: Framework para construção de APIs em Kotlin.
- PostgreSQL: Banco de dados relacional.
- JWT: Autenticação baseada em tokens.

## Pré-requisitos

- JDK 17 ou superior instalado.
- Banco de dados PostgreSQL configurado.
- Chave secreta para geração de tokens JWT.

## Configuração

1. Clone este repositório.
2. Configure as variáveis de ambiente no arquivo `.env` (ou `.env.example`).
3. Inicie o servidor Ktor.

## Instalação

1. Clone o repositório:

```bash
git clone https://github.com/jotap-dev/onerep.git
```

2. Configure as variáveis de ambiente:

Crie um arquivo `.env` na raiz do projeto com as seguintes variáveis:

```env
DATABASE_URL=jdbc:postgresql://localhost:5432/seu-banco-de-dados
DATABASE_USER=seu-usuario
DATABASE_PASSWORD=sua-senha
JWT_SECRET=sua-chave-secreta
```

**Você pode clonar o modelo ``.env.example`` na raíz do projeto e renomear para ``.env`` para configurar
as variáveis de ambiente**

3. Inicie o servidor Ktor:

```bash
./gradlew run
```

## Rotas

- `/user/add`: Registro de usuário com nome, email e senha.
- `/user/login`: Login de usuário com email e senha (gera token JWT).
- `/workout`: rota protegida para testar e validar o token JWT.