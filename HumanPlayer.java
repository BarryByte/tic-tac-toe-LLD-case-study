import java.util.Scanner;

/*
 * HumanPlayer class - handles human player input
 * Design Decision: Extends Player and implements human-specific input handling
 */
public class HumanPlayer extends Player {
    private Scanner scanner;
    
    public HumanPlayer(String name, char symbol) {
        super(name, symbol);
        this.scanner = new Scanner(System.in);
    }
    
    @Override
    public Move getNextMove(Board board) {
        System.out.println(name + "'s turn (" + symbol + ")");
        System.out.print("Enter row and column (space separated): ");
        
        try {
            int row = scanner.nextInt();
            int col = scanner.nextInt();
            return new Move(row, col);
        } catch (Exception e) {
            scanner.nextLine(); // Clear invalid input
            System.out.println("Invalid input! Please enter two numbers.");
            return getNextMove(board); // Recursive call for retry
        }
    }
    
    @Override
    public boolean isHuman() {
        return true;
    }
}