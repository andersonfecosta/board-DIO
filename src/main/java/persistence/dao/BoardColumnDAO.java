package persistence.dao;

import com.mysql.cj.jdbc.StatementImpl;
import lombok.AllArgsConstructor;
import persistence.entity.BoardColumnEntity;
import persistence.entity.BoardColumnKindEnum;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static persistence.entity.BoardColumnKindEnum.findByName;

@AllArgsConstructor
public class BoardColumnDAO {

    private final Connection connection;

    public BoardColumnEntity insert(final BoardColumnEntity entity) throws SQLException {
        var sql = "INSERT INTO BOARD_COLUMNS (name, `order`, kind, board_id) VALUES (?, ?, ?, ?);";
        try (var statement = connection.prepareStatement(sql)){
            var i = 1;
            statement.setString(i++, entity.getName());
            statement.setInt(i++, entity.getOrder());
            statement.setString(i++, entity.getKind().name());
            statement.setLong(i++, entity.getBoard().getId());
            statement.executeUpdate();
            if (statement instanceof StatementImpl impl) {
                entity.setId(impl.getLastInsertID());
            }
            return entity;
        }
    }

    public List<BoardColumnEntity> findByBoardId(Long id) throws SQLException{
        List<BoardColumnEntity> entities = new ArrayList<>();
        var sql = "SELECT FROM board_columns WHERE board_id = ? ORDER BY 'order';";
        try (var statement = connection.prepareStatement(sql)){
            statement.setLong(1,id);
            statement.executeQuery();
            var resultSet = statement.getResultSet();
            while (resultSet.next()) {
                var entity = new BoardColumnEntity();
                entity.setId(resultSet.getLong("id"));
                entity.setName(resultSet.getString("name"));
                entity.setOrder(resultSet.getInt("order"));
                entity.setKind(findByName(resultSet.getNString("kind")));
                entities.add(entity);
            }
            return entities;
        }
    }
}
