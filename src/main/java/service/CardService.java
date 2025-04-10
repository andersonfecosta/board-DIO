package service;

import dto.BoardColumnInfoDTO;
import exception.CardBlockedException;
import exception.CardFinishedException;
import exception.EntityNotFoundException;
import lombok.AllArgsConstructor;
import persistence.dao.CardDAO;
import persistence.entity.CardEntity;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import static persistence.entity.BoardColumnKindEnum.FINAL;

@AllArgsConstructor
public class CardService {

    private final Connection connection;

    public CardEntity insert(final CardEntity entity) throws SQLException {
        try {
            var dao = new CardDAO(connection);
            dao.insert(entity);
            connection.commit();
            return entity;
        } catch (SQLException e) {
            connection.rollback();
            throw e;
        }
    }

    public void moveToNextColumn(final Long cardId, List<BoardColumnInfoDTO> boardColumnsInfo) throws SQLException {
        try {
            var dao = new CardDAO(connection);
            var optional = dao.findById(cardId);
            var dto = optional.orElseThrow(() -> new EntityNotFoundException("O card de id %s não foi encontrado".formatted(cardId)));
            if (dto.blocked()) {
                throw new CardBlockedException("O card %s está bloqueado. É necessário realizar o desbloqueio para movê-lo".formatted(cardId));
            }
            var boardColumnInfo = boardColumnsInfo.stream()
                    .filter(bc -> bc.id().equals(dto.columnId()))
                    .findFirst()
                    .orElseThrow(() -> new IllegalStateException("O card informado pertence a outro board"));
            if (boardColumnInfo.kind().equals(FINAL)) {
                throw new CardFinishedException("O card já consta como finalizado");
            }
            var nextColumn = boardColumnsInfo.stream().filter(bc -> bc.order() == boardColumnInfo.order() + 1).findFirst().orElseThrow();
            dao.moveToNextColumn(nextColumn.id(), cardId);
            connection.commit();
            System.out.println("Card movido com sucesso!");
        } catch (SQLException e) {
            connection.rollback();
            throw e;
        }
    }
}
