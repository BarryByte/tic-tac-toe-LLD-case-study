/**
 * Board class - handles all board-related operations
 * Encapsulates board state and game rules
 */
public class Board {
    private char[][] board;
    private int rows;
    private int cols;
    
    public Board(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        this.board = new char[rows][cols];
        
        // Initialize board with '.' for empty cells
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                board[i][j] = '.';
            }
        }
    }
     

    
    public boolean isValidMove(int row, int col) {
        // Check bounds
        if (row < 0 || row >= rows || col < 0 || col >= cols) {
            return false;
        }
        
        // Check if cell is empty
        return board[row][col] == '.';
    }

    public void makeMove(int row, int col, char symbol) {
        board[row][col] = symbol;
    }
    
    public boolean checkWin(char symbol) {
        // Check rows
        for (int i = 0; i < rows; i++) {
            boolean rowWin = true;
            for (int j = 0; j < cols; j++) {
                if (board[i][j] != symbol) {
                    rowWin = false;
                    break;
                }
            }
            if (rowWin) return true;
        }
        
        // Check columns
        for (int j = 0; j < cols; j++) {
            boolean colWin = true;
            for (int i = 0; i < rows; i++) {
                if (board[i][j] != symbol) {
                    colWin = false;
                    break;
                }
            }
            if (colWin) return true;
        }
        
        // PROBLEM: Only works for square boards!
        // For rectangular boards, diagonal win might not make sense
        if (rows == cols) {
            // Check diagonal (top-left to bottom-right)
            boolean diagWin = true;
            for (int i = 0; i < rows; i++) {
                if (board[i][i] != symbol) {
                    diagWin = false;
                    break;
                }
            }
            if (diagWin) return true;
            
            // Check anti-diagonal (top-right to bottom-left)
            boolean antiDiagWin = true;
            for (int i = 0; i < rows; i++) {
                if (board[i][cols - 1 - i] != symbol) {
                    antiDiagWin = false;
                    break;
                }
            }
            if (antiDiagWin) return true;
        }
        
        return false;
    }
    
    public boolean isFull() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (board[i][j] == '.') {
                    return false;
                }
            }
        }
        return true;
    }
    
    public void printBoard() {
        System.out.println("\nCurrent Board:");
        
        // Print column numbers
        System.out.print("   ");
        for (int j = 0; j < cols; j++) {
            System.out.print(j + " ");
        }
        System.out.println();
        
        // Print rows with row numbers
        for (int i = 0; i < rows; i++) {
            System.out.print(i + "  ");
            for (int j = 0; j < cols; j++) {
                System.out.print(board[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }
}