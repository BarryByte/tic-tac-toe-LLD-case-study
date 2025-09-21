import java.util.Scanner;

public class TicTacToe {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("=== Tic-Tac-Toe Game Setup ===");
        
        // Get player information
        System.out.print("Enter Player 1 name: ");
        String p1Name = scanner.nextLine();
        
        System.out.print("Enter Player 2 name: ");
        String p2Name = scanner.nextLine();
        
        // Get board dimensions
        System.out.print("Enter number of rows: ");
        int rows = scanner.nextInt();
        
        System.out.print("Enter number of columns: ");
        int cols = scanner.nextInt();
        
        // Create players with fixed symbols
        Player player1 = new Player(p1Name, 'X');
        Player player2 = new Player(p2Name, 'O');
        
        // Create and start game
        Game game = new Game(player1, player2, rows, cols);
        game.startGame();
        
        scanner.close();
    }
}