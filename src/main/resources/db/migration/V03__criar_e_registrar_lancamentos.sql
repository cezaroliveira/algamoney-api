CREATE TABLE lancamento (
	codigo BIGINT PRIMARY KEY AUTO_INCREMENT,
	descricao VARCHAR(50) NOT NULL,
	data_vencimento DATE NOT NULL,
	data_pagamento DATE,
	valor DECIMAL(10,2) NOT NULL,
	observacao VARCHAR(100),
	tipo VARCHAR(20) NOT NULL,
	codigo_categoria BIGINT NOT NULL,
	codigo_pessoa BIGINT NOT NULL,
	FOREIGN KEY (codigo_categoria) REFERENCES categoria(codigo),
	FOREIGN KEY (codigo_pessoa) REFERENCES pessoa(codigo)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO lancamento (descricao, data_vencimento, data_pagamento, valor, observacao, tipo, codigo_categoria, codigo_pessoa) 
				VALUES ('Parque', '2020-07-28', '2020-07-28', 350.00, 'Consolidado do mês', 'DESPESA', 1, 1);

INSERT INTO lancamento (descricao, data_vencimento, data_pagamento, valor, observacao, tipo, codigo_categoria, codigo_pessoa) 
				VALUES ('Restaurante', '2020-05-20', '2020-05-15', 498.17, 'Consolidado do mês', 'DESPESA', 2, 2);

INSERT INTO lancamento (descricao, data_vencimento, data_pagamento, valor, observacao, tipo, codigo_categoria, codigo_pessoa) 
				VALUES ('Compra do mês', '2020-07-10', '2020-07-05', 194.00, 'Consolidado do mês', 'DESPESA', 3, 3);

INSERT INTO lancamento (descricao, data_vencimento, data_pagamento, valor, observacao, tipo, codigo_categoria, codigo_pessoa) 
				VALUES ('Remédios', '2020-04-01', '2020-04-02', 100.00, 'Consolidado do mês', 'DESPESA', 4, 4);

