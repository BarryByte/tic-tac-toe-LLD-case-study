/*
 * Abstract Player class - base class for all player types
 * Design Decision: Using composition pattern with strategy for move generation
 */
public abstract class Player {
    protected String name;
    protected char symbol;
    
    public Player(String name, char symbol) {
        this.name = name;
        this.symbol = symbol;
    }
    
    public char getSymbol() {
        return symbol;
    }
    
    public String getName() {
        return name;
    }
    
    public abstract Move getNextMove(Board board);
    
    public abstract boolean isHuman();
}