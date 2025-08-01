CREATE DATABASE IF NOT EXISTS rubrica;
USE rubrica;

CREATE TABLE IF NOT EXISTS persona (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100),
    cognome VARCHAR(100),
    indirizzo VARCHAR(255),
    telefono VARCHAR(20),
    eta INT
);

