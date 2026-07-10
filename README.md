# Disciplina
Análise e Projeto de Sistemas

# Curso
Análise e Desenvolvimento de Sistemas

# Instituição
Instituto Federal de Pernambuco(IFPE)

# QLAB

Sistema de Agendamento de Laboratórios de Informática

## Sobre

QLAB é um sistema desktop para gerenciamento de reservas dos laboratórios de informática do IFPE Campus Paulista.

## Tecnologias

- Java
- Java Swing
- MySQL

## Estrutura do Projeto
```
qlab/
├── src/
│   ├── main/
│   ├── model/
│   ├── view/
│   ├── controller/
│   └── dao/
├── database/
│   └── schema.sql
└── README.md
```
## Tutorial de como executar o projeto

### Pré-requisitos

Antes de executar o sistema, é necessário ter instalado:

- Java JDK 21 (ou a versão utilizada no projeto)
- MySQL Server
- Git
- Uma IDE Java, como IntelliJ IDEA ou Eclipse

### 1. Clonar o repositório

```bash
https://github.com/VmDevalt/q-lab.git
cd QLAB
```

### 2. Configurar o banco de dados

1. Crie um banco de dados no MySQL.
   
2. Execute o script localizado em:

```
ddl.sql
```

para criar as tabelas necessárias.

### 3. Configurar a conexão com o banco

Abra a classe db.properties configure-a e abra a classe responsável pela conexão com o banco de dados e altere as informações de acordo com sua instalação do MySQL:

- URL do banco
- Usuário
- Senha

### 4. Abrir o projeto

Abra o projeto em uma IDE Java (IntelliJ IDEA ou Eclipse) e aguarde o download das dependências, caso necessário.

### 5. Executar o sistema

Execute a classe principal (`TelaLogin.java`) para iniciar a aplicação.

Após a execução, a tela inicial do sistema será exibida e o QLAB estará pronto para utilização.


---

Equipe

| [Heitor Santana](https://github.com/) | [Ingrid Vitória](https://github.com/vivif001) | [Marcio Luan](https://github.com/) | [Victor Montes](https://github.com/VmDevalt) |
|:---:|:---:|:---:|:---:|

---

<div align="center">
  <sub>Instituto Federal de Pernambuco - Campus Paulista</sub>
</div>
