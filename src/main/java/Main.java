import persistence.migration.MigrationStrategy;
import ui.MainMenu;

import java.sql.SQLException;

import static persistence.config.ConnectionConfig.getConnection;

public class Main {

    public static void main(String[] args) throws SQLException {
        try (var connection = getConnection()) {
            new MigrationStrategy(connection).executeMigration();
        }
        new MainMenu().execute();
    }
}
