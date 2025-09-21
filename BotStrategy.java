import java.util.*;

/*
 * Strategy pattern for bot decision making
 * Design Decision: Separate strategy classes allow easy addition of new difficulty levels
 */
public interface BotStrategy {
    Move getMove(Board board, char symbol);
}

/**
 * Easy Bot Strategy - Random moves
 */
class EasyBotStrategy implements BotStrategy {
    private Random random = new Random();
    
    @Override
    public Move getMove(Board board, char symbol) {
        List<Move> availableMoves = getAvailableMoves(board);
        if (availableMoves.isEmpty()) {
            return null; // Should not happen in normal gameplay
        }
        return availableMoves.get(random.nextInt(availableMoves.size()));
    }
    
    private List<Move> getAvailableMoves(Board board) {
        List<Move> moves = new ArrayList<>();
        int size = board.getSize();
        
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (board.isValidMove(i, j)) {
                    moves.add(new Move(i, j));
                }
            }
        }
        return moves;
    }
}

/**
 * Medium Bot Strategy - Try to win, block opponent from winning, otherwise random
 */
class MediumBotStrategy implements BotStrategy {
    private EasyBotStrategy fallbackStrategy = new EasyBotStrategy();
    
    @Override
    public Move getMove(Board board, char symbol) {
        int size = board.getSize();
        
        // First priority: Try to win
        Move winMove = findWinningMove(board, symbol);
        if (winMove != null) {
            return winMove;
        }
        
        // Second priority: Block opponent from winning
        char opponentSymbol = (symbol == 'X') ? 'O' : 'X';
        Move blockMove = findWinningMove(board, opponentSymbol);
        if (blockMove != null) {
            return blockMove;
        }
        
        // Third priority: Take center if available (for odd-sized boards)
        if (size % 2 == 1) {
            int center = size / 2;
            if (board.isValidMove(center, center)) {
                return new Move(center, center);
            }
        }
        
        // Fallback to random move
        return fallbackStrategy.getMove(board, symbol);
    }
    
    /**
     * Finds a move that would result in a win for the given symbol
     */
    private Move findWinningMove(Board board, char symbol) {
        int size = board.getSize();
        
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (board.isValidMove(i, j)) {
                    // Temporarily make the move
                    Board tempBoard = board.copy();
                    tempBoard.makeMove(i, j, symbol);
                    
                    // Check if this move wins
                    if (tempBoard.checkWin(symbol)) {
                        return new Move(i, j);
                    }
                }
            }
        }
        return null;
    }
}

/**
 * Hard Bot Strategy - Uses minimax algorithm for optimal play
 */
class HardBotStrategy implements BotStrategy {
    private static final int MAX_DEPTH = 3; // Limit depth for performance
    
    @Override
    public Move getMove(Board board, char symbol) {
        Move bestMove = null;
        int bestScore = Integer.MIN_VALUE;
        int size = board.getSize();
        
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (board.isValidMove(i, j)) {
                    Board tempBoard = board.copy();
                    tempBoard.makeMove(i, j, symbol);
                    
                    int score = minimax(tempBoard, 0, false, symbol, Integer.MIN_VALUE, Integer.MAX_VALUE);
                    
                    if (score > bestScore) {
                        bestScore = score;
                        bestMove = new Move(i, j);
                    }
                }
            }
        }
        
        return bestMove != null ? bestMove : new EasyBotStrategy().getMove(board, symbol);
    }
    
    /**
     * Minimax algorithm with alpha-beta pruning
     */
    private int minimax(Board board, int depth, boolean isMaximizing, char botSymbol, int alpha, int beta) {
        char opponentSymbol = (botSymbol == 'X') ? 'O' : 'X';
        
        // Terminal conditions
        if (board.checkWin(botSymbol)) return 10 - depth;
        if (board.checkWin(opponentSymbol)) return depth - 10;
        if (board.isFull() || depth >= MAX_DEPTH) return 0;
        
        int size = board.getSize();
        
        if (isMaximizing) {
            int maxScore = Integer.MIN_VALUE;
            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
                    if (board.isValidMove(i, j)) {
                        Board tempBoard = board.copy();
                        tempBoard.makeMove(i, j, botSymbol);
                        int score = minimax(tempBoard, depth + 1, false, botSymbol, alpha, beta);
                        maxScore = Math.max(maxScore, score);
                        alpha = Math.max(alpha, score);
                        if (beta <= alpha) break; // Alpha-beta pruning
                    }
                }
            }
            return maxScore;
        } else {
            int minScore = Integer.MAX_VALUE;
            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
                    if (board.isValidMove(i, j)) {
                        Board tempBoard = board.copy();
                        tempBoard.makeMove(i, j, opponentSymbol);
                        int score = minimax(tempBoard, depth + 1, true, botSymbol, alpha, beta);
                        minScore = Math.min(minScore, score);
                        beta = Math.min(beta, score);
                        if (beta <= alpha) break; // Alpha-beta pruning
                    }
                }
            }
            return minScore;
        }
    }
}