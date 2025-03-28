--liquibase formatted sql
--changeset andersonfecosta:202503271805
--comment: criação tabela blocks

CREATE TABLE blocks(
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    block_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    block_reason VARCHAR(255) NOT NULL,
    unblock_at TIMESTAMP NULL,
    unblock_reason VARCHAR(255) NOT NULL,
    cards_id BIGINT NOT NULL,
    CONSTRAINT cards__blocks_fk FOREIGN KEY (cards_id) REFERENCES cards(id) ON DELETE CASCADE
) ENGINE=InnoDB;

--rollback DROP TABLE BLOCKS