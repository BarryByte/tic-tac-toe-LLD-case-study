public class Player{
    private String name;
    private char symbol;

    public Player(String name, char symbol) {
        this.name = name;
        this.symbol = symbol;
    }

    public String getName() {
        return name;
    }
    
    public char getSymbol() {
        return symbol;
    }

    // make move 
    public int[] makeMove(Board board, int row, int col) {
        System.out.println(name + " (" + symbol + ") enter row and column (0-based): ");
        row = sc.nextInt();
        col = sc.nextInt();
        return new int[]{row, col};
    }
}