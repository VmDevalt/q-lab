CREATE DATABASE IF NOT EXISTS qlab;
use qlab;
CREATE TABLE usuarios(
	id INT AUTO_INCREMENT PRIMARY KEY,
	cpf VARCHAR (14),
    nome VARCHAR (80) NOT NULL,
    matricula VARCHAR(14) NOT NULL UNIQUE,
    email VARCHAR (50) NOT NULL,
    telefone VARCHAR(15) NOT NULL,
    senha VARCHAR(60) NOT NULL,
    perfil ENUM ('ADMINISTRADOR', 'PROFESSOR', 'TECNICO', 'GUARDIAO'),
    administrador BOOLEAN DEFAULT FALSE,
    CHECK (email LIKE ("%@%")),
    CHECK (CHAR_LENGTH(telefone) = 15));
    
CREATE TABLE laboratorios(
	id INT AUTO_INCREMENT PRIMARY KEY,
	nome VARCHAR (20) NOT NULL,
	status_laboratorio ENUM ('INTERDITADO', 'LIVRE', 'EM USO') NOT NULL,
	qtd_computadores INT NOT NULL,
	descricao VARCHAR (80) NOT NULL,
	foto BLOB);

