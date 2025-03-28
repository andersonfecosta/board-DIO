package persistence.config;

import static lombok.AccessLevel.PRIVATE;

import lombok.NoArgsConstructor;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@NoArgsConstructor(access = PRIVATE)
public final class ConnectionConfig {

    public static Connection getConnection() throws SQLException {

        var url = "jdbc:mysql://localhost/board";
        var connection = DriverManager.getConnection(url, "root", "senha123");
        connection.setAutoCommit(false);
        return connection;
    }
}
