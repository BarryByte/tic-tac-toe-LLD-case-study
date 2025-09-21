import java.util.Scanner;

public class Game {
    Board board;
    Player player1;
    Player player2;
    Scanner sc;

    public Game(int size) {
        board = new Board(size);
        player1 = new Player("Player 1", 'X');
        player2 = new Player("Player 2", 'O');
        sc = new Scanner(System.in);
    }

    public void launch() {
        while (true) {
            board.display();

            // Player 1
            playerTurn(player1);
            if (board.checkWin() == 1) {
                board.display();
                System.out.println(player1.getName() + " wins!");
                break;
            }

            // Player 2
            board.display();
            playerTurn(player2);
            if (board.checkWin() == 1) {
                board.display();
                System.out.println(player2.getName() + " wins!");
                break;
            }
        }
    }

    private void playerTurn(Player player) {
        int row, col;
        while (true) {
            // System.out.println(player.getName() + " (" + player.getSymbol() + ") enter row and column (0-based): ");
            // row = sc.nextInt();
            // col = sc.nextInt();
            int[] move = player.makeMove()
            if (board.placeSymbol(row, col, player.getSymbol())) {
                break;
            } else {
                System.out.println("Invalid move! Try again.");
            }
        }
    }
}
