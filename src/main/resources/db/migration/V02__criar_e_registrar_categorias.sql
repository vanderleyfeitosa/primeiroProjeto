CREATE TABLE categoria(
codigo Bigint(20) Primary key AUTO_INCREMENT,
nome varchar(50) not null
) ENGINE=InnoDB DEFAULT CHARSET=UTF8;
INSERT INTO categoria (nome) values('Lazer');
INSERT INTO categoria (nome) values('Alimentação');
INSERT INTO categoria (nome) values('Supermecado');
INSERT INTO categoria (nome) values('Farmácia');
INSERT INTO categoria (nome) values('Outros');