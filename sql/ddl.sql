CREATE DATABASE IF NOT EXISTS qlab;
CREATE TABLE usuarios(
	cpf VARCHAR (14) PRIMARY KEY,
    nome VARCHAR (80),
    email VARCHAR (50),
    telefone VARCHAR(15),
    senha VARCHAR(60),
    perfil ENUM ('ADMINISTRADOR', 'PROFESSOR', 'TECNICO', 'GUARDIAO'),
    administrador BOOLEAN DEFAULT FALSE,
    CHECK (email LIKE ("%@%")),
    CHECK (CHAR_LENGTH(telefone) = 15));

