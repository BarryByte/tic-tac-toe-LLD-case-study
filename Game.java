import java.util.List;

/**
 * Enhanced Game class - supports multiple players (human/bot mix)
 * Design Decision: Uses polymorphism to handle different player types uniformly
 */
public class Game {
    private List<Player> players;
    private Board board;
    private int currentPlayerIndex;
    private boolean gameOver;
    
    public Game(List<Player> players, int boardSize) {
        if (players.size() < 2) {
            throw new IllegalArgumentException("Need at least 2 players to play!");
        }
        
        this.players = players;
        this.board = new Board(boardSize);
        this.currentPlayerIndex = 0;
        this.gameOver = false;
        
        assignSymbols();
    }
    
    /**
     * Assigns unique symbols to each player
     * Design Decision: Auto-assign symbols to avoid conflicts
     */
    private void assignSymbols() {
        char[] symbols = {'X', 'O', '★', '♦', '♠', '♥', '♣', '◆', '◇', '●'};
        
        if (players.size() > symbols.length) {
            throw new IllegalArgumentException("Too many players! Maximum " + symbols.length + " supported.");
        }
        
        for (int i = 0; i < players.size(); i++) {
            // Use reflection to set symbol (since it's protected in Player)
            // In a real implementation, you might want a setter method instead
            try {
                java.lang.reflect.Field symbolField = Player.class.getDeclaredField("symbol");
                symbolField.setAccessible(true);
                symbolField.setChar(players.get(i), symbols[i]);
            } catch (Exception e) {
                System.err.println("Error assigning symbols: " + e.getMessage());
            }
        }
    }
    
    /**
     * Main game loop - continues until game ends
     */
    public void startGame() {
        System.out.println("=== Welcome to Enhanced Tic-Tac-Toe ===");
        System.out.println("Players:");
        for (Player player : players) {
            String type = player.isHuman() ? "Human" : "Bot";
            System.out.println("  " + player.getName() + " (" + player.getSymbol() + ") - " + type);
        }
        
        board.printBoard();
        
        while (!gameOver) {
            processMove();
            board.printBoard();
            
            if (checkGameEnd()) {
                displayResult();
                gameOver = true;
            } else {
                switchToNextPlayer();
            }
        }
    }
    
    /**
     * Gets the current player
     */
    private Player getCurrentPlayer() {
        return players.get(currentPlayerIndex);
    }
    
    /**
     * Switches to the next player in rotation
     */
    private void switchToNextPlayer() {
        currentPlayerIndex = (currentPlayerIndex + 1) % players.size();
    }
    
    /**
     * Processes one complete move for the current player
     * Design Decision: Polymorphic approach - same code works for human and bot players
     */
    private void processMove() {
        Player currentPlayer = getCurrentPlayer();
        boolean validMove = false;
        
        while (!validMove) {
            Move move = currentPlayer.getNextMove(board);
            
            if (move != null && board.isValidMove(move.getRow(), move.getCol())) {
                board.makeMove(move.getRow(), move.getCol(), currentPlayer.getSymbol());
                validMove = true;
            } else {
                if (currentPlayer.isHuman()) {
                    System.out.println("Invalid move! Cell is either occupied or out of bounds. Try again.");
                } else {
                    // Bot made invalid move - should not happen with good strategies
                    System.out.println("Bot error: Invalid move attempted. Retrying...");
                }
            }
        }
    }
    
    /**
     * Checks if game has ended (win or draw)
     */
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
            System.out.println("🎉 " + currentPlayer.getName() + " wins! 🎉");
        } else {
            System.out.println("It's a draw! Good game everyone! 🤝");
        }
        
        // Show final statistics
        System.out.println("\nFinal Player Summary:");
        for (Player player : players) {
            String type = player.isHuman() ? "Human" : "Bot";
            if (!player.isHuman()) {
                BotPlayer bot = (BotPlayer) player;
                type += " (" + bot.getDifficulty() + ")";
            }
            System.out.println("  " + player.getName() + " (" + player.getSymbol() + ") - " + type);
        }
    }
}