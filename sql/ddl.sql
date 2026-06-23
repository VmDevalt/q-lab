CREATE DATABASE IF NOT EXISTS qlab;
use qlab;
CREATE TABLE IF NOT EXISTS usuarios(
	id INT AUTO_INCREMENT PRIMARY KEY,
	cpf VARCHAR (14),
    nome VARCHAR (80) NOT NULL,
    matricula VARCHAR(14) NOT NULL UNIQUE,
    email VARCHAR (50) NOT NULL,
    telefone VARCHAR(15) NOT NULL,
    senha VARCHAR(60) NOT NULL,
    perfil ENUM ('ADMINISTRADOR', 'PROFESSOR', 'TECNICO', 'ESTUDANTE_GUARDIAO'),
    administrador BOOLEAN DEFAULT FALSE,
    CHECK (email LIKE ("%@%")),
    CHECK (CHAR_LENGTH(telefone) = 15));
    
CREATE TABLE IF NOT EXISTS laboratorios(
	id INT AUTO_INCREMENT PRIMARY KEY,
	nome VARCHAR (20) NOT NULL,
	status_laboratorio ENUM ('INTERDITADO', 'LIVRE', 'EM USO') NOT NULL,
	qtd_computadores INT NOT NULL,
	descricao VARCHAR (80) NOT NULL);
    
INSERT INTO laboratorios (nome, status_laboratorio, qtd_computadores, descricao) VALUES
('Lab 01', 'LIVRE', 15, 'Redes e Manutenção'),
('Lab 02', 'LIVRE', 15, 'Informática'),
('Lab 03', 'LIVRE', 15, 'Desenvolvimento'),
('Lab 05', 'LIVRE', 15, 'Sala do diretório acadêmico'),
('Lab 06', 'LIVRE', 15, 'Prática de IA'),
('Lab 07', 'LIVRE', 15, 'Informática 2');


CREATE TABLE IF NOT EXISTS horarios(
	id_horario INT AUTO_INCREMENT PRIMARY KEY,
	dia DATE NOT NULL,
	horario ENUM (
		'M1', 'M2', 'M3', 'M4', 'M5', 'M6',
		'T1', 'T2', 'T3', 'T4', 'T5', 'T6',
		'N1', 'N2', 'N3', 'N4', 'N5', 'N6') NOT NULL,
	status_lab ENUM ('LIVRE', 'OCUPADO') DEFAULT 'LIVRE',
	laboratorio_id INT NOT NULL,
	FOREIGN KEY (laboratorio_id) REFERENCES laboratorios(id),
	UNIQUE (dia, horario, laboratorio_id));
	
CREATE TABLE IF NOT EXISTS reservas(
	id_reserva INT AUTO_INCREMENT PRIMARY KEY,
	
	horario_id INT NOT NULL,
	usuario_id INT NOT NULL,
	
	data_reserva TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
	disciplina VARCHAR (80),
	
	FOREIGN KEY (horario_id) REFERENCES horarios(id_horario),
	FOREIGN KEY (usuario_id) REFERENCES usuarios(id));
