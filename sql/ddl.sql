CREATE DATABASE IF NOT EXISTS qlab;
USE qlab;

CREATE TABLE IF NOT EXISTS usuarios (
    id          INT AUTO_INCREMENT PRIMARY KEY,
    cpf         VARCHAR(14),
    nome        VARCHAR(80)  NOT NULL,
    matricula   VARCHAR(14)  NOT NULL UNIQUE,
    email       VARCHAR(50)  NOT NULL,
    telefone    VARCHAR(15)  NOT NULL,
    senha       VARCHAR(60)  NOT NULL,
    perfil      ENUM('ADMINISTRADOR','PROFESSOR','TECNICO','ESTUDANTE_GUARDIAO'),
    administrador BOOLEAN    DEFAULT FALSE,
    ativo       BOOLEAN      DEFAULT TRUE,
    CHECK (email LIKE '%@%'),
    CHECK (CHAR_LENGTH(telefone) = 15)
);

CREATE TABLE IF NOT EXISTS laboratorios (
    id_laboratorio INT AUTO_INCREMENT PRIMARY KEY,
    nome           VARCHAR(50)  NOT NULL,
    descricao      VARCHAR(100),
    andar          VARCHAR(30),
    capacidade     INT          DEFAULT 0,
    ativo          BOOLEAN      DEFAULT TRUE
);

INSERT INTO laboratorios (nome, descricao, andar, capacidade) VALUES
('Lab 01', 'Redes e Manutenção',              'Térreo',    15),
('Lab 02', 'Informática',                      'Térreo',    20),
('Lab 03', 'Desenvolvimento',                  'Térreo',    18),
('Lab 05', 'Sala do diretório acadêmico',      '1° Andar',  12),
('Lab 06', 'Prática de IA',                    '1° Andar',  16),
('Lab 07', 'Informática 2',                    '1° Andar',  22);

CREATE TABLE IF NOT EXISTS horarios (
    id_horario     INT AUTO_INCREMENT PRIMARY KEY,
    dia            DATE NOT NULL,
    horario        ENUM('M1','M2','M3','M4','M5','M6',
                        'T1','T2','T3','T4','T5','T6',
                        'N1','N2','N3','N4','N5','N6') NOT NULL,
    laboratorio_id INT NOT NULL,
    UNIQUE KEY uk_slot (dia, horario, laboratorio_id),
    FOREIGN KEY (laboratorio_id) REFERENCES laboratorios(id_laboratorio)
);

CREATE TABLE IF NOT EXISTS reservas (
    id_reserva INT AUTO_INCREMENT PRIMARY KEY,
    horario_id INT         NOT NULL,
    matricula  VARCHAR(14) NOT NULL,
    disciplina VARCHAR(80),
    status     ENUM('AGENDADA','SOLICITADA','CANCELADA') DEFAULT 'AGENDADA',
    responsavel_matricula VARCHAR(14),
    FOREIGN KEY (matricula) REFERENCES usuarios(matricula),
    FOREIGN KEY (horario_id) REFERENCES horarios(id_horario),
    FOREIGN KEY (responsavel_matricula)  REFERENCES usuarios(matricula)
);

CREATE TABLE IF NOT EXISTS interdicoes (
    id_interdicao  INT AUTO_INCREMENT PRIMARY KEY,
    laboratorio_id INT         NOT NULL,
    data_inicio    DATE        NOT NULL,
    data_fim       DATE        NOT NULL,
    motivo         VARCHAR(200),
    ativo          BOOLEAN     DEFAULT TRUE,
    FOREIGN KEY (laboratorio_id) REFERENCES laboratorios(id_laboratorio)
);
