/*
 * BotPlayer class - handles AI player moves
 * Design Decision: Uses composition with BotStrategy for different difficulty levels
 */
public class BotPlayer extends Player {
    private BotStrategy strategy;
    private String difficulty;
    
    public BotPlayer(String name, char symbol, String difficulty) {
        super(name + " (Bot-" + difficulty + ")", symbol);
        this.difficulty = difficulty.toLowerCase();
        this.strategy = createStrategy(this.difficulty);
    }
    
    /**
     * Factory method to create appropriate strategy based on difficulty
     */
    private BotStrategy createStrategy(String difficulty) {
        switch (difficulty) {
            case "easy":
                return new EasyBotStrategy();
            case "medium":
                return new MediumBotStrategy();
            case "hard":
                return new HardBotStrategy();
            default:
                System.out.println("Unknown difficulty '" + difficulty + "', defaulting to easy");
                return new EasyBotStrategy();
        }
    }
    
    @Override
    public Move getNextMove(Board board) {
        System.out.println(name + " is thinking...");
        
        // Add small delay for better user experience
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        Move move = strategy.getMove(board, symbol);
        System.out.println(name + " chooses position " + move);
        return move;
    }
    
    @Override
    public boolean isHuman() {
        return false;
    }
    
    public String getDifficulty() {
        return difficulty;
    }
}