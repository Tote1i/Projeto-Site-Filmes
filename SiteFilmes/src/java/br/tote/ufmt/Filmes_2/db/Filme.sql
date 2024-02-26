CREATE TABLE Filmes (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome_arquivo VARCHAR(255) NOT NULL,
    tamanho INT NOT NULL,
    descricao TEXT,
    data_upload TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
