# Tic-Tac-Toe Low Level Design

## Core Classes and Responsibilities

### 1. Player Class
**Attributes:**
- `String name` - Player's display name
- `char symbol` - Player's symbol ('X' or 'O')

**Methods:**
- `Player(String name, char symbol)` - Constructor
- `char getSymbol()` - Returns player's symbol
- `String getName()` - Returns player's name

**Responsibility:** Store player information only

---

### 2. Board Class
**Attributes:**
- `char[][] board` - 2D array representing the game board (uses '.' for empty cells)
- `int rows` - Number of rows (for NxM flexibility)
- `int cols` - Number of columns

**Methods:**
- `Board(int rows, int cols)` - Constructor, initializes board with '.'
- `boolean isValidMove(int row, int col)` - Checks if move is legal
- `void makeMove(int row, int col, char symbol)` - Places symbol on board
- `boolean checkWin(char symbol)` - Checks if given symbol has won
- `boolean isFull()` - Checks if board is completely filled (for draw detection)
- `void printBoard()` - Displays current board state

**Responsibility:** 
- Knows board state
- Validates moves
- Executes moves
- Detects win conditions
- Handles board display

---

### 3. Game Class
**Attributes:**
- `Player player1` - First player
- `Player player2` - Second player
- `Board board` - Game board instance
- `boolean isPlayer1Turn` - Turn management flag
- `boolean gameOver` - Game state flag

**Methods:**
- `Game(Player p1, Player p2, int rows, int cols)` - Constructor
- `void startGame()` - Main game loop
- `Player getCurrentPlayer()` - Returns whose turn it is
- `void switchTurn()` - Flips the turn flag
- `void processMove()` - Gets input and handles one complete move
- `boolean checkGameEnd()` - Checks if game is over (win or draw)
- `void displayResult()` - Shows final game result

**Responsibility:**
- Orchestrates the entire game flow
- Manages player turns
- Gets user input
- Coordinates between Player and Board
- Handles game state transitions

---

## Key Design Decisions Made

### 1. **Responsibility Distribution**
- **Board** owns all board-related logic (validation, move execution, win detection)
- **Game** acts as orchestrator and manages flow
- **Player** is lightweight, stores only player data

### 2. **Validation Strategy**
- Game requests validation from Board: `board.isValidMove(row, col)`
- Only Board knows its internal state
- Follows Single Responsibility Principle

### 3. **Turn Management**
- Simple boolean flag for 2-player games
- `getCurrentPlayer()` returns appropriate player based on flag
- Turn switches only after successful moves

### 4. **Error Handling**
- Invalid moves prompt user to try again (while loop)
- Game class handles all error scenarios
- Clean separation between validation and execution

## Class Relationships

```
Game HAS-A Player (composition) - 2 instances
Game HAS-A Board (composition) - 1 instance  
Game USES Board methods for validation and moves
Game USES Player methods to get symbols and names
```

## Sample Game Flow

1. **Initialization:** `Game game = new Game(player1, player2, 3, 3)`
2. **Game Loop:** `game.startGame()` begins main loop
3. **Turn Processing:**
   - Get input from current player
   - Validate: `board.isValidMove(row, col)`
   - If invalid: ask for input again
   - If valid: `board.makeMove(row, col, currentPlayer.getSymbol())`
   - Check win: `board.checkWin(currentPlayer.getSymbol())`
   - Check draw: `board.isFull()`
   - If game continues: `switchTurn()`
4. **Game End:** Display winner or draw message

## Extensibility Features

- **NxM Board Support:** Constructor accepts rows and columns
- **Easy Symbol Changes:** Player symbols configurable
- **Modular Win Detection:** Can be enhanced for different win conditions
- **Clean Interface:** Adding features (like AI players) would be straightforward















----
----
# v2
----
----

## Key Design Changes & Thought Process:

### 1. **Player Architecture - Polymorphism + Strategy Pattern**
- **Abstract Player class** - Common interface for all player types
- **HumanPlayer & BotPlayer** - Concrete implementations
- **BotStrategy interface** - Composition pattern for bot intelligence
- **Why this works:** `Game.processMove()` treats all players the same way - just calls `player.getNextMove(board)`

### 2. **Multi-Player Support (2+ players)**
- **List<Player> instead of player1/player2** - Scalable design
- **Circular turn management** - `currentPlayerIndex = (currentPlayerIndex + 1) % players.size()`
- **Auto symbol assignment** - Uses array of symbols (X, O, ★, ♦, etc.)

### 3. **Bot Intelligence Levels**
- **Easy:** Random moves
- **Medium:** Try to win → Block opponent → Take center → Random
- **Hard:** Minimax algorithm with alpha-beta pruning for optimal play

### 4. **Board Enhancements**
- **NxN only** - Solved rectangular board complexity
- **Copy constructor** - Needed for bot strategies to simulate moves
- **Consistent win rules** - Always need N-in-a-row/column/diagonal

### 5. **Move Class**
- **Simple data holder** - Encapsulates row/col information
- **Clean interface** - Both human input and bot decisions return Move objects



