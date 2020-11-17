CREATE TABLE IF NOT EXISTS pessoa (
	codigo BIGINT PRIMARY KEY AUTO_INCREMENT,
	nome VARCHAR(50) NOT NULL,
	logradouro VARCHAR(50) NULL,
	numero VARCHAR(50) NULL,
	complemento VARCHAR(50) NULL,
	bairro VARCHAR(30) NULL,
	cep VARCHAR(8) NULL,
	cidade VARCHAR(30) NULL,
	estado VARCHAR(2) NULL,
	ativo BOOLEAN NOT NULL DEFAULT TRUE
) ENGINE=InnoDB DEFAULT CHARSET=UTF8MB4;

INSERT INTO pessoa (nome, logradouro, numero, complemento, bairro, cep, cidade, estado) 
	VALUES ('Pedro', 'Rua do Pedro', '4', null, 'Nova Esperança', '34789564', 'Belo Horizonte', 'MG');
INSERT INTO pessoa (nome, logradouro, numero, complemento, bairro, cep, cidade, estado) 
	VALUES ('Flávia', 'Rua da Flávia', '789', 'Bloco 3, Apto 504', 'Terezinha', '32341260', 'São José', 'SP');
INSERT INTO pessoa (nome) VALUES ('José');
INSERT INTO pessoa (nome) VALUES ('João');
INSERT INTO pessoa (nome) VALUES ('Maria');