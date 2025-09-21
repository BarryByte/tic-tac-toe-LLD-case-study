/**
 * Enhanced Board class - handles NxN board operations only
 * Design Decision: Simplified to only support square boards for consistent game rules
 */
public class Board {
    private char[][] board;
    private int size; // NxN board, so only need one dimension
    
    public Board(int size) {
        this.size = size;
        this.board = new char[size][size];
        
        // Initialize board with '.' for empty cells
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                board[i][j] = '.';
            }
        }
    }
    
    /**
     * Copy constructor for creating temporary boards (used by bot strategies)
     */
    private Board(Board other) {
        this.size = other.size;
        this.board = new char[size][size];
        
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                this.board[i][j] = other.board[i][j];
            }
        }
    }
    
    /**
     * Creates a deep copy of this board
     */
    public Board copy() {
        return new Board(this);
    }
    
    public int getSize() {
        return size;
    }
    
    /**
     * Validates if a move is legal
     */
    public boolean isValidMove(int row, int col) {
        // Check bounds
        if (row < 0 || row >= size || col < 0 || col >= size) {
            return false;
        }
        
        // Check if cell is empty
        return board[row][col] == '.';
    }
    
    /**
     * Places a symbol on the board
     */
    public void makeMove(int row, int col, char symbol) {
        board[row][col] = symbol;
    }
    
    /**
     * Enhanced win checking for NxN boards
     * Design Decision: Need N in a row/column/diagonal to win
     */
    public boolean checkWin(char symbol) {
        // Check all rows
        for (int i = 0; i < size; i++) {
            boolean rowWin = true;
            for (int j = 0; j < size; j++) {
                if (board[i][j] != symbol) {
                    rowWin = false;
                    break;
                }
            }
            if (rowWin) return true;
        }
        
        // Check all columns
        for (int j = 0; j < size; j++) {
            boolean colWin = true;
            for (int i = 0; i < size; i++) {
                if (board[i][j] != symbol) {
                    colWin = false;
                    break;
                }
            }
            if (colWin) return true;
        }
        
        // Check main diagonal (top-left to bottom-right)
        boolean mainDiagWin = true;
        for (int i = 0; i < size; i++) {
            if (board[i][i] != symbol) {
                mainDiagWin = false;
                break;
            }
        }
        if (mainDiagWin) return true;
        
        // Check anti-diagonal (top-right to bottom-left)
        boolean antiDiagWin = true;
        for (int i = 0; i < size; i++) {
            if (board[i][size - 1 - i] != symbol) {
                antiDiagWin = false;
                break;
            }
        }
        if (antiDiagWin) return true;
        
        return false;
    }
    
    /**
     * Checks if the board is completely filled
     */
    public boolean isFull() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (board[i][j] == '.') {
                    return false;
                }
            }
        }
        return true;
    }
    
    /**
     * Displays the current board state with coordinates
     */
    public void printBoard() {
        System.out.println("\nCurrent Board:");
        
        // Print column numbers
        System.out.print("   ");
        for (int j = 0; j < size; j++) {
            System.out.printf("%2d ", j);
        }
        System.out.println();
        
        // Print rows with row numbers
        for (int i = 0; i < size; i++) {
            System.out.printf("%2d ", i);
            for (int j = 0; j < size; j++) {
                System.out.printf(" %c ", board[i][j]);
            }
            System.out.println();
        }
        System.out.println();
    }
}