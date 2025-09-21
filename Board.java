public class Board {
    int size;
    char[][] grid;

    public Board(int size) {
        this.size = size;
        grid = new char[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                grid[i][j] = '_';  
            }
        }
    }

    public boolean placeSymbol(int row, int col, char symbol) {
        if (row >= 0 && row < size && col >= 0 && col < size && grid[row][col] == '_') {
            grid[row][col] = symbol;
            return true;
        }
        return false;
    }

    public int checkWin() {
        for (int i = 0; i < size; i++) {
            if (checkRow(i) || checkColumn(i)) {
                return 1;
            }
        }
        if (checkDiagonal()) {
            return 1;
        }
        return 0;
    }

    private boolean checkRow(int row) {
        char first = grid[row][0];
        if (first == '_') return false;
        for (int j = 1; j < size; j++) {
            if (grid[row][j] != first) return false;
        }
        return true;
    }

    private boolean checkColumn(int col) {
        char first = grid[0][col];
        if (first == '_') return false;
        for (int i = 1; i < size; i++) {
            if (grid[i][col] != first) return false;
        }
        return true;
    }

    private boolean checkDiagonal() {
        char first = grid[0][0];
        if (first == '_') return false;
        for (int i = 1; i < size; i++) {
            if (grid[i][i] != first) return false;
        }

        first = grid[0][size - 1];
        if (first == '_') return false;
        for (int i = 1; i < size; i++) {
            if (grid[i][size - 1 - i] != first) return false;
        }

        return true;
    }

    public void display() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                System.out.print(grid[i][j] + " ");
            }
            System.out.println();
        }
    }
}
