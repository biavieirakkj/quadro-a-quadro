<p align="center">
  <img src="https://github.com/biavieirakkj/quadro-a-quadro/blob/main/quadro-a-quadro/.github/tela_principal.png" alt="Quadro a Quadro"/>
</p>

# Quadro a Quadro

Sistema web de gerenciamento interno de cinema, desenvolvido como projeto acadêmico para a disciplina de Programação Web. Permite o controle de filmes, salas e sessões por meio de uma interface intuitiva, com autenticação e controle de acesso por perfil de usuário.

## Tech Stack

[![My Skills](https://skillicons.dev/icons?i=java,spring,hibernate,html,css,js,postgres)](https://skillicons.dev)

## Getting Started

1. **Clone o repositório**
```bash
   git clone https://github.com/seu-usuario/quadro-a-quadro.git
   cd quadro-a-quadro
```

2. **Configure o banco de dados**  
   Crie um banco PostgreSQL e atualize o `application.properties`:
```properties
   spring.datasource.url=jdbc:postgresql://localhost:5432/quadro_a_quadro
   spring.datasource.username=seu_usuario
   spring.datasource.password=sua_senha
```

3. **Crie a pasta de uploads**
```bash
   mkdir -p uploads/capas
```

4. **Execute a aplicação**
```bash
   ./mvnw spring-boot:run
```

5. **Acesse no navegador**
http://localhost:8080

6. **Login padrão do administrador**
Email: admin@cinema.com
Senha: admin123

## Funcionalidades

- 🎥 Cadastro, edição e exclusão de filmes com capa
- 🏛️ Gerenciamento de salas de exibição
- 🎟️ Agendamento de sessões com controle de conflito de horário
- 👥 Controle de acesso por perfil (Administrador e Gerente)
- ✅ Aprovação de novos usuários pelo administrador

## Autores

| [<img src="https://github.com/biavieirakkj.png" width="75px">](https://github.com/biavieirakkj) | [<img src="https://github.com/isaabelamg.png" width="75px">](https://github.com/isaabelamg) |
|:---:|:---:|
| Beatriz Vieira | Isabela Garcia |
