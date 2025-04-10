package persistence.dao;

import com.mysql.cj.jdbc.StatementImpl;
import dto.CardDetailsDTO;
import lombok.AllArgsConstructor;
import persistence.entity.CardEntity;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Optional;

import static persistence.converter.OffsetDateTimeConverter.toOffsetDateTime;

@AllArgsConstructor
public class CardDAO {

    private Connection connection;

    public CardEntity insert(final CardEntity entity) throws SQLException {
        var sql = "INSERT INTO cards (title, description, board_column_id) VALUES (?,?,?)";
        try (var statement = connection.prepareStatement(sql)){
            var i = 1;
            statement.setString(i++, entity.getTitle());
            statement.setString(i++, entity.getDescription());
            statement.setLong(i, entity.getBoardColumn().getId());
            statement.executeUpdate();
            if (statement instanceof StatementImpl impl) {
                entity.setId(impl.getLastInsertID());
            }
        }
        return entity;
    }

    public Optional<CardDetailsDTO> findById(final Long id) throws SQLException {
        var sql =
                """
                 SELECT c.id,
                        c.title,
                        c.description,
                        b.block_at,
                        b.block_reason,
                        c.board_columns_id,
                        bc.name,
                        (SELECT COUNT(sub_b.id)
                                FROM blocks sub_b
                                WHERE sub_b.card_id = c.id) blocks_amount
                     FROM cards c
                     LEFT JOIN blocks b
                        ON c.id = b.cards_id
                        AND b.unblock_at IS NULL
                     INNER JOIN board_columns bc
                        ON bc.id = c.board_columns_id
                     WHERE c.id = ?;
                """;
        try (var statement = connection.prepareStatement(sql)){
            statement.setLong(1, id);
            statement.executeQuery();
            var resultSet = statement.getResultSet();
            if (resultSet.next()) {
                var dto = new CardDetailsDTO(
                        resultSet.getLong("c.id"),
                        resultSet.getString("c.title"),
                        resultSet.getString("c.description"),
                        resultSet.getString("b.block_reason").isEmpty(),
                        toOffsetDateTime(resultSet.getTimestamp("b.block_at")),
                        resultSet.getString("b.block_reason"),
                        resultSet.getInt("blocks_amount"),
                        resultSet.getLong("c.board_columns_id"),
                        resultSet.getString("bc.name")
                );
                return Optional.of(dto);
            }
        }
        return Optional.empty();
    }
}
