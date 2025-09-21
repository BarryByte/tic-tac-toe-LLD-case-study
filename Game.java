import java.util.Scanner;

/**
 * Game class - orchestrates the entire game flow
 * Acts as the main controller coordinating Player and Board interactions
 */
public class Game {
    private Player player1;
    private Player player2;
    private Board board;
    private boolean isPlayer1Turn;
    private boolean gameOver;
    private Scanner scanner;
    
    public Game(Player p1, Player p2, int rows, int cols) {
        this.player1 = p1;
        this.player2 = p2;
        this.board = new Board(rows, cols);
        this.isPlayer1Turn = true; // Player 1 starts
        this.gameOver = false;
        this.scanner = new Scanner(System.in);
    }
    
    /**
     * Main game loop - continues until game ends
     */
    public void startGame() {
        System.out.println("=== Welcome to Tic-Tac-Toe ===");
        System.out.println(player1.getName() + " (" + player1.getSymbol() + ") vs " 
                          + player2.getName() + " (" + player2.getSymbol() + ")");
        
        board.printBoard();
        
        while (!gameOver) {
            processMove();
            board.printBoard();
            
            if (checkGameEnd()) {
                displayResult();
                gameOver = true;
            } else {
                switchTurn();
            }
        }
        
        scanner.close();
    }
    
    private Player getCurrentPlayer() {
        return isPlayer1Turn ? player1 : player2;
    }
    
    private void switchTurn() {
        isPlayer1Turn = !isPlayer1Turn;
    }
    
    private void processMove() {
        Player currentPlayer = getCurrentPlayer();
        boolean validMove = false;
        
        // Keep asking for input until valid move is made
        while (!validMove) {
            System.out.println(currentPlayer.getName() + "'s turn (" + currentPlayer.getSymbol() + ")");
            System.out.print("Enter row and column (space separated): ");
            
            try {
                int row = scanner.nextInt();
                int col = scanner.nextInt();
                
                if (board.isValidMove(row, col)) {
                    board.makeMove(row, col, currentPlayer.getSymbol());
                    validMove = true;
                } else {
                    System.out.println("Invalid move! Cell is either occupied or out of bounds. Try again.");
                }
            } catch (Exception e) {
                System.out.println("Invalid input! Please enter two numbers separated by space.");
                scanner.nextLine(); // Clear the invalid input buffer
            }
        }
    }
    
    private boolean checkGameEnd() {
        Player currentPlayer = getCurrentPlayer();
        
        // Check if current player won
        if (board.checkWin(currentPlayer.getSymbol())) {
            return true;
        }
        
        // Check if it's a draw
        if (board.isFull()) {
            return true;
        }
        
        return false;
    }
    
    /**
     * Displays the final game result
     */
    private void displayResult() {
        Player currentPlayer = getCurrentPlayer();
        
        if (board.checkWin(currentPlayer.getSymbol())) {
            System.out.println("Congratulations " + currentPlayer.getName() + "! You won!");
        } else {
            System.out.println("It's a draw! Good game both players! ././././././");
        }
    }
}