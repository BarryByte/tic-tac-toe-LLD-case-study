import java.util.*;

/**
 * Enhanced main class with flexible player setup
 * Design Decision: Interactive setup allows any combination of human/bot players
 */
public class TicTacToe {
    private static Scanner scanner = new Scanner(System.in);
    
    public static void main(String[] args) {
        System.out.println("=== Enhanced Tic-Tac-Toe Game Setup ===");
        
        try {
            // Get board size
            int boardSize = getBoardSize();
            
            // Get number of players
            int numPlayers = getNumberOfPlayers();
            
            // Create players
            List<Player> players = createPlayers(numPlayers);
            
            // Create and start game
            Game game = new Game(players, boardSize);
            game.startGame();
            
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        } finally {
            scanner.close();
        }
    }
    
    /**
     * Gets board size from user with validation
     */
    private static int getBoardSize() {
        while (true) {
            try {
                System.out.print("Enter board size (NxN, minimum 3): ");
                int size = scanner.nextInt();
                
                if (size >= 3) {
                    return size;
                } else {
                    System.out.println("Board size must be at least 3x3!");
                }
            } catch (Exception e) {
                System.out.println("Please enter a valid number!");
                scanner.nextLine(); // Clear invalid input
            }
        }
    }
    
    /**
     * Gets number of players with validation
     */
    private static int getNumberOfPlayers() {
        while (true) {
            try {
                System.out.print("Enter number of players (2-10): ");
                int numPlayers = scanner.nextInt();
                scanner.nextLine(); // Consume newline
                
                if (numPlayers >= 2 && numPlayers <= 10) {
                    return numPlayers;
                } else {
                    System.out.println("Number of players must be between 2 and 10!");
                }
            } catch (Exception e) {
                System.out.println("Please enter a valid number!");
                scanner.nextLine(); // Clear invalid input
            }
        }
    }
    
    /**
     * Creates list of players based on user input
     */
    private static List<Player> createPlayers(int numPlayers) {
        List<Player> players = new ArrayList<>();
        
        for (int i = 1; i <= numPlayers; i++) {
            System.out.println("\n--- Player " + i + " Setup ---");
            System.out.print("Enter player type (human/bot): ");
            String type = scanner.nextLine().toLowerCase().trim();
            
            if (type.equals("human") || type.equals("h")) {
                players.add(createHumanPlayer(i));
            } else if (type.equals("bot") || type.equals("b")) {
                players.add(createBotPlayer(i));
            } else {
                System.out.println("Invalid type! Defaulting to human player.");
                players.add(createHumanPlayer(i));
            }
        }
        
        return players;
    }
    
    /**
     * Creates a human player
     */
    private static HumanPlayer createHumanPlayer(int playerNumber) {
        System.out.print("Enter name for Player " + playerNumber + ": ");
        String name = scanner.nextLine().trim();
        
        if (name.isEmpty()) {
            name = "Player" + playerNumber;
        }
        
        return new HumanPlayer(name, 'X'); // Symbol will be reassigned by Game class
    }
    
    /**
     * Creates a bot player with difficulty selection
     */
    private static BotPlayer createBotPlayer(int playerNumber) {
        System.out.print("Enter bot name (optional): ");
        String name = scanner.nextLine().trim();
        
        if (name.isEmpty()) {
            name = "Bot" + playerNumber;
        }
        
        String difficulty = getBotDifficulty();
        
        return new BotPlayer(name, 'X', difficulty); // Symbol will be reassigned by Game class
    }
    
    /**
     * Gets bot difficulty with validation
     */
    private static String getBotDifficulty() {
        while (true) {
            System.out.print("Enter bot difficulty (easy/medium/hard): ");
            String difficulty = scanner.nextLine().toLowerCase().trim();
            
            if (difficulty.equals("easy") || difficulty.equals("e") ||
                difficulty.equals("medium") || difficulty.equals("m") ||
                difficulty.equals("hard") || difficulty.equals("h")) {
                
                // Normalize the input
                if (difficulty.equals("e")) return "easy";
                if (difficulty.equals("m")) return "medium";
                if (difficulty.equals("h")) return "hard";
                
                return difficulty;
            } else {
                System.out.println("Please enter 'easy', 'medium', or 'hard'!");
            }
        }
    }
}