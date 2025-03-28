--liquibase formatted sql
--changeset andersonfecosta:202503270127
--comment: criação tabela board

CREATE TABLE board(
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL
) ENGINE=InnoDB;

--rollback DROP TABLE BOARD