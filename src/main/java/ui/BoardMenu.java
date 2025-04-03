package ui;

import lombok.AllArgsConstructor;
import persistence.entity.BoardEntity;
import service.BoardQueryService;

import java.sql.SQLException;
import java.util.Scanner;

import static persistence.config.ConnectionConfig.getConnection;

@AllArgsConstructor
public class BoardMenu {

    private  final  Scanner scanner = new Scanner(System.in).useDelimiter("\n");

    private final BoardEntity entity;

    public void execute() {
        try {

            System.out.printf("Bem vindo ao board %s, selecione a opção desejada:\n", entity.getId());

            var option = -1;
            while (option != 9) {
                System.out.println("1 - Criar card");
                System.out.println("2 - Mover card");
                System.out.println("3 - Bloquear card");
                System.out.println("4 - Desbloquear card");
                System.out.println("5 - Cancelar card");
                System.out.println("6 - Visualizar board");
                System.out.println("7 - Visualizar conluna com cards");
                System.out.println("8 - Visualizar card");
                System.out.println("9 - Voltar ao menu anterior");
                System.out.println("10 - Sair");
                option = scanner.nextInt();
                switch (option) {
                    case 1 -> createCard();
                    case 2 -> moveCardToNextColumn();
                    case 3 -> blockCard();
                    case 4 -> unblockCard();
                    case 5 -> cancelCard();
                    case 6 -> showBoard();
                    case 7 -> showColumn();
                    case 8 -> showCard();
                    case 9 -> System.out.println("Voltando ao menu anterior");
                    case 10 -> System.exit(0);
                    default -> System.out.println("Opção inválida");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.exit(0);
        }
    }

    private void createCard() {

    }

    private void moveCardToNextColumn() {

    }

    private void blockCard() {

    }

    private void unblockCard() {

    }

    private void cancelCard() {

    }

    private void showBoard() throws SQLException {
        try (var connection = getConnection()) {
            var optional = new BoardQueryService(connection).showBoardDetails(entity.getId());
            optional.ifPresent(b -> {
                System.out.printf("Board [%s, %s]\n", b.id(), b.name());
                b.columns().forEach(c -> {
                    System.out.printf("Coluna [%s] tipo: [%s] tem %s cards\n", c.name(), c.kind(), c.cardsAmount());
                });
            });
        }
    }

    private void showColumn() {

    }

    private void showCard() {

    }
}
