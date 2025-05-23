package service;

import dto.BoardDetailsDTO;
import lombok.AllArgsConstructor;
import persistence.dao.BoardColumnDAO;
import persistence.dao.BoardDAO;
import persistence.entity.BoardEntity;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Optional;

@AllArgsConstructor
public class BoardQueryService {

    private final Connection connection;

    public Optional<BoardEntity> findById(final Long id) throws SQLException {
        var dao = new BoardDAO(connection);
        var boardColumnDAO = new BoardColumnDAO(connection);
        var optional = dao.findById(id);
        if (optional.isPresent()) {
            var entity = optional.get();
            entity.setBoardColumns(boardColumnDAO.findByBoardId(entity.getId()));
            return Optional.of(entity);
        }
        return Optional.empty();
    }

    public Optional<BoardDetailsDTO> showBoardDetails(final Long id) throws SQLException{
        var dao = new BoardDAO(connection);
        var boardColumnDAO = new BoardColumnDAO(connection);
        var optional = dao.findById(id);
        if (optional.isPresent()) {
            var entity = optional.get();
            var comlumns = boardColumnDAO.findByBoardIdWhithDetails(entity.getId());
            var dto = new BoardDetailsDTO(entity.getId(), entity.getName(), comlumns);
            return Optional.of(dto);
        }
        return Optional.empty();
    }

}
