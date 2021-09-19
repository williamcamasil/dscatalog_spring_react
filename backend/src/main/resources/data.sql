/*Através desse arquivo, nós iremos inserir os comandos SQL para executar no banco h2, quando abri-lo será mostrado os dados já cadastrados*/

/*Inserimos mais um atributo indicando o tempo real em que foi adicionado para auditoria de dados*/

INSERT INTO tb_category (name, created_At) VALUES ('Books', NOW());
INSERT INTO tb_category (name, created_At) VALUES ('Electronics', NOW());
INSERT INTO tb_category (name, created_At) VALUES ('Computes', NOW());