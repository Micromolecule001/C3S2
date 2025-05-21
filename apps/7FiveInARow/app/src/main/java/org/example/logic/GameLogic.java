package org.example.logic;

import java.util.Random;

public class GameLogic {
    public static final int SIZE = 15;
    public static final char EMPTY = '.';
    public static final char PLAYER_X = 'X';
    public static final char PLAYER_O = 'O';

    private final char[][] board = new char[SIZE][SIZE];
    private final Random random = new Random();

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

    // Новий метод для ходу комп'ютера
    public int[] computerMove() {
        // 1. Перевірка на виграшний хід
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (board[i][j] == EMPTY) {
                    board[i][j] = PLAYER_O;
                    if (checkWin(PLAYER_O)) {
                        return new int[]{i, j};
                    }
                    board[i][j] = EMPTY;
                }
            }
        }

        // 2. Перевірка на блокування виграшу гравця
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (board[i][j] == EMPTY) {
                    board[i][j] = PLAYER_X;
                    if (checkWin(PLAYER_X)) {
                        board[i][j] = PLAYER_O; // Блокуємо хід гравця
                        return new int[]{i, j};
                    }
                    board[i][j] = EMPTY;
                }
            }
        }

        // 3. Спроба створити послідовність із 4 або 3 символів
        int bestScore = Integer.MIN_VALUE;
        int[] bestMove = {-1, -1};
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (board[i][j] == EMPTY) {
                    int score = evaluatePosition(i, j, PLAYER_O) +
                                evaluatePosition(i, j, PLAYER_X); // Враховуємо загрозу від гравця
                    if (score > bestScore) {
                        bestScore = score;
                        bestMove = new int[]{i, j};
                    }
                }
            }
        }

        if (bestMove[0] != -1) {
            board[bestMove[0]][bestMove[1]] = PLAYER_O;
            return bestMove;
        }

        // 4. Випадковий хід, якщо немає кращих варіантів
        while (true) {
            int row = random.nextInt(SIZE);
            int col = random.nextInt(SIZE);
            if (board[row][col] == EMPTY) {
                board[row][col] = PLAYER_O;
                return new int[]{row, col};
            }
        }
    }

    // Оцінка позиції для створення послідовностей
    
        private int evaluatePosition(int row, int col, char player) {
            int score = 0;
            int opponent = (player == PLAYER_O) ? PLAYER_X : PLAYER_O;

            int[][] directions = {{1, 0}, {0, 1}, {1, 1}, {1, -1}};
            for (int[] dir : directions) {
                int dx = dir[0], dy = dir[1];
                int count = 1;
                int blocks = 0;

                // перевірка в напрямку вперед
                for (int i = 1; i < 5; i++) {
                    int r = row + i * dx;
                    int c = col + i * dy;
                    if (r >= SIZE || c >= SIZE || r < 0 || c < 0) {
                        blocks++;
                        break;
                    }
                    if (board[r][c] == opponent) {
                        blocks++;
                        break;
                    }
                    if (board[r][c] == player) {
                        count++;
                    } else {
                        break;
                    }
                }

                // перевірка в напрямку назад
                for (int i = 1; i < 5; i++) {
                    int r = row - i * dx;
                    int c = col - i * dy;
                    if (r >= SIZE || c >= SIZE || r < 0 || c < 0) {
                        blocks++;
                        break;
                    }
                    if (board[r][c] == opponent) {
                        blocks++;
                        break;
                    }
                    if (board[r][c] == player) {
                        count++;
                    } else {
                        break;
                    }
                }

                score += scoreByCountAndBlocks(count, blocks);
            }

            return score;
        }

        private int scoreByCountAndBlocks(int count, int blocks) {
            if (count >= 5) return 100000;
            if (count == 4 && blocks == 0) return 10000;
            if (count == 4 && blocks == 1) return 1000;
            if (count == 3 && blocks == 0) return 1000;
            if (count == 3 && blocks == 1) return 100;
            if (count == 2 && blocks == 0) return 100;
            if (count == 2 && blocks == 1) return 10;
            if (count == 1 && blocks == 0) return 10;
            return 1;
        }


    public char[][] getBoard() {
        return board;
    }
}
