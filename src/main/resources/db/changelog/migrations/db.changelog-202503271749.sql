--liquibase formatted sql
--changeset andersonfecosta:202503271749
--comment: criação tabela board_columns

CREATE TABLE board_columns(
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    `order` INT NOT NULL,
    kind VARCHAR(255) NOT NULL,
    board_id BIGINT NOT NULL,
    CONSTRAINT board__board_columns_fk FOREIGN KEY (board_id) REFERENCES board(id) ON DELETE CASCADE,
    CONSTRAINT id_order_uk UNIQUE KEY unique_board_id_order (board_id, `order`)
) ENGINE=InnoDB;

--rollback DROP TABLE BOARD_COLUMNS