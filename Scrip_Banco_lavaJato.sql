--O nome do Banco deve ser "lavaJato"

CREATE TABLE funcionarios( 
  codFuncionario  SERIAL       NOT NULL,
  nome            VARCHAR(30)  NOT NULL,
  cpf             VARCHAR(20)  NOT NULL,
  telefone        VARCHAR(20)  NOT NULL,
  CONSTRAINT pk_funcionario
    PRIMARY KEY (codFuncionario)
);

CREATE TABLE clientes(
  codCliente SERIAL       NOT NULL,
  nome       VARCHAR(30)  NOT NULL,
  cpf        VARCHAR(20)  NOT NULL,
  telefone   VARCHAR(20)  NOT NULL,
  CONSTRAINT pk_clientes
    PRIMARY KEY (codCliente)
);

CREATE TABLE produtos(
  codProduto   SERIAL   NOT NULL,
  nome         VARCHAR  NOT NULL,
  estoque      INT      NOT NULL,
  preco        FLOAT    NOT NULL,
  CONSTRAINT pk_produtos
    PRIMARY KEY (codProduto)
);

CREATE TABLE servicos(
  codServico  SERIAL   NOT NULL,
  nome        VARCHAR  NOT NULL,
  preco       FLOAT    NOT NULL,
  CONSTRAINT pk_servicos
    PRIMARY KEY (codServico)
);

CREATE TABLE ordemServico(
  codOrdemServico  SERIAL   NOT NULL,  	
  codCliente       INT      NOT NULL,
  codFuncionario   INT      NOT NULL,
  data		         DATE     NOT NULL,
  pago             BOOLEAN  NOT NULL,
  total            FLOAT    NOT NULL,
  desconto         FLOAT    NOT NULL,
  CONSTRAINT pk_ordemServicoo
    PRIMARY KEY (codOrdemServico),
  
  CONSTRAINT fk_codOrdemServico_clientes
  	FOREIGN KEY (codCliente)
  	REFERENCES clientes(codCliente),

  CONSTRAINT fk_codOrdemServico_funcionarios
    FOREIGN KEY (codFuncionario)
    REFERENCES funcionarios(codFuncionario)
);

CREATE TABLE itensProduto(
  codOrdemServico  INT   NOT NULL,
  codProduto       INT   NOT NULL,
  quantidade       INT   NOT NULL,
  preco            FLOAT NOT NULL,
  
  CONSTRAINT pk_itensProduto
  	PRIMARY KEY(codOrdemServico, codProduto),
  
  CONSTRAINT fk_itensProduto_ordemServico
  	FOREIGN KEY (codOrdemServico)
  	REFERENCES ordemServico(codOrdemServico),
  
  CONSTRAINT fk_itensProduto_produtos
  	FOREIGN KEY (codProduto)
  	REFERENCES produtos(codProduto)
);

CREATE TABLE itensServico(
  codOrdemServico  INT     NOT NULL,
  codServico       INT     NOT NULL,
  preco            FLOAT   NOT NULL,
  quantidade       INT     NOT NULL,
 
  CONSTRAINT pk_itensServico
  	PRIMARY KEY(codOrdemServico, codServico),
  
  CONSTRAINT fk_itensServico_ordemServico
  	FOREIGN KEY (codOrdemServico)
  	REFERENCES ordemServico(codOrdemServico),
 
  CONSTRAINT fk_itensServico_servicos
    FOREIGN KEY (codServico)
    REFERENCES servicos(codServico)
);


INSERT INTO funcionarios(nome, cpf, telefone) VALUES('Luiz','444.444.444-44','(44) 4444-4444');
INSERT INTO funcionarios(nome, cpf, telefone) VALUES('Luan','555.555.555-55','(55) 5555-5555');
INSERT INTO funcionarios(nome, cpf, telefone) VALUES('Patrick','666.666.666-66','(66) 6666-6666');

INSERT INTO clientes(nome, cpf, telefone) VALUES('Rafael','111.111.111-11','(11) 1111-1111');
INSERT INTO clientes(nome, cpf, telefone) VALUES('Ruan','222.222.222-22','(22) 2222-2222');
INSERT INTO clientes(nome, cpf, telefone) VALUES('Talis','333.333.333-33','(33) 3333-3333');

INSERT INTO produtos(nome, estoque, preco) VALUES('Detergente', '20', '3.00');
INSERT INTO produtos(nome, estoque, preco) VALUES('Cera', '10', '10.00');
INSERT INTO produtos(nome, estoque, preco) VALUES('Desengraxante', '20', '5.00');

INSERT INTO servicos(nome, preco) VALUES('Moto', '20.00');
INSERT INTO servicos(nome, preco) VALUES('Carro', '30.00');
INSERT INTO servicos(nome, preco) VALUES('Camionete', '50.00');

INSERT INTO ordemServico(codCliente, codFuncionario, data, pago, desconto, total) VALUES('1', '1','08/06/2021', false, '0.0','36.00');
INSERT INTO ordemServico(codCliente, codFuncionario, data, pago, desconto, total) VALUES('2', '2','05/06/2021', true, '0.0','81.00');

INSERT INTO itensServico(codOrdemServico, codServico, quantidade, preco) VALUES('1', '1', '1', '20.00');
INSERT INTO itensServico(codOrdemServico, codServico, quantidade, preco) VALUES('2', '1', '1', '20.00');
INSERT INTO itensServico(codOrdemServico, codServico, quantidade, preco) VALUES('2', '3', '1', '50.00');

INSERT INTO itensProduto(codOrdemServico, codProduto, quantidade, preco) VALUES('1', '1', '2', '6.00');
INSERT INTO itensProduto(codOrdemServico, codProduto, quantidade, preco) VALUES('1', '2', '1', '10.00');
INSERT INTO itensProduto(codOrdemServico, codProduto, quantidade, preco) VALUES('2', '1', '2', '6.00');
INSERT INTO itensProduto(codOrdemServico, codProduto, quantidade, preco) VALUES('2', '3', '1', '5.00');