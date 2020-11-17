CREATE TABLE IF NOT EXISTS usuario (
	codigo BIGINT PRIMARY KEY AUTO_INCREMENT,
	nome VARCHAR(50) NOT NULL,
	email VARCHAR(50) NOT NULL,
	senha VARCHAR(150) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=UTF8MB4;

INSERT INTO usuario (nome, email, senha) 
		   	 VALUES ('Administrador', 'admin@algamoney.com', '$2a$10$PTkE8TCM1RktIY33yjrjzuyI/pi3yIS1y/AomQq0uUYsI604b4iEq');

INSERT INTO usuario (nome, email, senha) 
			 VALUES ('Maria da Silva', 'maria@algamoney.com', '$2a$10$Iz4coF6YRVeI.1RyvaLlXudraaE6QAAiUDHjH965j0m1OzNBXT1hC');

CREATE TABLE IF NOT EXISTS permissao (
	codigo BIGINT PRIMARY KEY AUTO_INCREMENT,
	descricao VARCHAR(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=UTF8MB4;

INSERT INTO permissao (descricao) 
			   VALUES ('ROLE_CADASTRAR_CATEGORIA');

INSERT INTO permissao (descricao) 
			   VALUES ('ROLE_ATUALIZAR_CATEGORIA');

INSERT INTO permissao (descricao) 
			   VALUES ('ROLE_REMOVER_CATEGORIA');

INSERT INTO permissao (descricao) 
			   VALUES ('ROLE_PESQUISAR_CATEGORIA');

INSERT INTO permissao (descricao) 
			   VALUES ('ROLE_CADASTRAR_PESSOA');

INSERT INTO permissao (descricao) 
			   VALUES ('ROLE_ATUALIZAR_PESSOA');

INSERT INTO permissao (descricao) 
			   VALUES ('ROLE_REMOVER_PESSOA');

INSERT INTO permissao (descricao) 
			   VALUES ('ROLE_PESQUISAR_PESSOA');

INSERT INTO permissao (descricao) 
			   VALUES ('ROLE_CADASTRAR_LANCAMENTO');

INSERT INTO permissao (descricao) 
			   VALUES ('ROLE_ATUALIZAR_LANCAMENTO');

INSERT INTO permissao (descricao) 
			   VALUES ('ROLE_REMOVER_LANCAMENTO');

INSERT INTO permissao (descricao) 
			   VALUES ('ROLE_PESQUISAR_LANCAMENTO');

INSERT INTO permissao (descricao) 
			   VALUES ('ROLE_CADASTRAR_USUARIO');

INSERT INTO permissao (descricao) 
			   VALUES ('ROLE_ATUALIZAR_USUARIO');

INSERT INTO permissao (descricao) 
			   VALUES ('ROLE_REMOVER_USUARIO');

INSERT INTO permissao (descricao) 
			   VALUES ('ROLE_PESQUISAR_USUARIO');

CREATE TABLE IF NOT EXISTS usuario_permissao (
	codigo_usuario BIGINT NOT NULL,
	codigo_permissao BIGINT NOT NULL,
	PRIMARY KEY(codigo_usuario, codigo_permissao),
	FOREIGN KEY(codigo_usuario) REFERENCES usuario(codigo),
	FOREIGN KEY(codigo_permissao) REFERENCES permissao(codigo)
) ENGINE=InnoDB DEFAULT CHARSET=UTF8MB4;

-- Admin tem todas as permissoes
INSERT INTO usuario_permissao (codigo_usuario, codigo_permissao) 
SELECT 1, codigo FROM permissao;

-- Maria tem apenas as permissoes de pesquisa
INSERT INTO usuario_permissao (codigo_usuario, codigo_permissao) 
SELECT 2, codigo FROM permissao
WHERE descricao LIKE '%PESQUISAR%'
AND descricao NOT LIKE '%USUARIO%';
