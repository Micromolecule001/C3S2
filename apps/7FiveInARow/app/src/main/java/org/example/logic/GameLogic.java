package org.example.logic;

public class GameLogic {
    public static final int SIZE = 15;
    public static final char EMPTY = '.';
    public static final char PLAYER_X = 'X';
    public static final char PLAYER_O = 'O';

    private final char[][] board = new char[SIZE][SIZE];

    public GameLogic() {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                board[i][j] = EMPTY;
            }
        }
    }

    public boolean makeMove(int row, int col, char player) {
        if (row < 0 || row >= SIZE || col < 0 || col >= SIZE || board[row][col] != EMPTY) {
            return false;
        }
        board[row][col] = player;
        return true;
    }

    public boolean checkWin(char player) {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (checkDirection(i, j, 1, 0, player) || // horizontal
                    checkDirection(i, j, 0, 1, player) || // vertical
                    checkDirection(i, j, 1, 1, player) || // diagonal down-right
                    checkDirection(i, j, 1, -1, player))  // diagonal down-left
                    return true;
            }
        }
        return false;
    }

    private boolean checkDirection(int row, int col, int dx, int dy, char player) {
        for (int i = 0; i < 5; i++) {
            int r = row + i * dx;
            int c = col + i * dy;
            if (r < 0 || r >= SIZE || c < 0 || c >= SIZE || board[r][c] != player) {
                return false;
            }
        }
        return true;
    }

    public char[][] getBoard() {
        return board;
    }
}
