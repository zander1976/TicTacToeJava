import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

/**
 * TicTacToeModel models the game
 * 
 * @author Code was used from starting examples
 * 
 * @author Ben Woodhead 
 * @version April 3, 2024
 */
public class TicTacToeModel extends Observable {
    // Setup Player Constants
    public static final String PLAYER_X = "X";
    public static final String PLAYER_O = "O";
    public static final String EMPTY = " ";
    public static final String TIE = "T";

    // current player (PLAYER_X or PLAYER_O)
    private String player;

    // winner: PLAYER_X, PLAYER_O, TIE, EMPTY = in progress
    private String winner;

    // Number of squares still free (shortcut for have winner)
    private int numFreeSquares;

    // Create the board
    private String board[][] = new String[3][3];

    /**
     * Constructor for objects of class TicTacToeModel
     */
    public TicTacToeModel() {
    }

    /**
     * Sets everything up for a new game. Marks all squares in the Tic Tac Toe board
     * as empty, and indicates no winner yet, 9 free squares and the current player
     * is player X.
     */
    public void clearBoard() {
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                board[row][col] = EMPTY;
            }
        }

        winner = EMPTY;
        numFreeSquares = 9;
        player = PLAYER_X;

        // Update the observers
        setChanged();
        notifyObservers("Players Turn: " + player);
    }

    /**
     * makeMove accepts Human input then plays the Search Input
     * 
     * @param The row that you want to place your peice
     * @param The column that you want to place your peice
     */
    public void makeMove(int row, int col) {
        // fill in the square with player
        board[row][col] = player;
    
        // decrement number of free squares
        numFreeSquares--;

        // Check for winner
        int result = checkWinner(board);
        if (result == 1 || result == -1) {
            winner = (result == 1) ? PLAYER_X : PLAYER_O;
            setChanged();
            notifyObservers("Game Over! Player Win: " + winner);
            return;
        } 
        if (result == 2 || numFreeSquares == 0) {
            winner = TIE;
            setChanged();
            notifyObservers("Game Over! TIE");
            return;
        }
    
        // Search for best move
        int[] move = minimax(board, false);
        if (move[0] >= 0) {
            board[move[0]][move[1]] = PLAYER_O;
            result = checkWinner(board);
            if (result == 1 || result == -1) {
                winner = (result == 1) ? PLAYER_X : PLAYER_O;
                setChanged();
                notifyObservers("Game Over! Player Win: " + winner);
                return;
            } 
            if (result == 2 || numFreeSquares == 0) {
                winner = TIE;
                setChanged();
                notifyObservers("Game Over! TIE");
                return;
            }
        }
        
        // Update the observers
        setChanged();
        notifyObservers("Players Turn: " + player);
    }


    /**
     * Get a piece at a location
     * 
     * @return PLAYER_X, PLAYER_O or TIE
     */
    public String getWinner() {
        return winner;
    }

    
    /**
     * Get a piece at a location
     * 
     * @param The row to get a peice 
     * @param The column to get a piece
     * @return X, or Y
     */
    public String getPiece(int row, int col) {
        if (row < 0 || row > 3) {
            return "";
        }
        if (col < 0 || col > 3) {
            return "";
        }
        return board[row][col];
    }

    /**
     * MiniMaxSearchMove will find the best move
     * 
     * @param The board to search
     * @param True for X and O for false
     * @return array with row, col, result. row will be negative if winner
     */
    private int[] minimax(String[][] board, boolean maximizingPlayer) {
        
        int winner = checkWinner(board);
        if (winner == 1) {
            return new int[] { -1, -1, 1 }; // X wins
        } else if (winner == -1) {
            return new int[] { -1, -1, -1 }; // O wins
        } else if (winner == 2) {
            return new int[] { -1, -1, 0 }; // Draw
        }
    
        List<int[]> possibleMoves = new ArrayList<>();
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                if (board[row][col].equals(EMPTY)) {
                    int[] tuple = {row, col};
                    possibleMoves.add(tuple);
                }
            }
        }

        if (maximizingPlayer) {
            int[] bestMove = new int[] { -1, -1, Integer.MIN_VALUE };
            for (int[] move : possibleMoves) {
                String[][] newBoard = copyBoard(board);
                int row = move[0];
                int col = move[1];
                newBoard[row][col] = PLAYER_X;
                int[] result = minimax(newBoard, false);
                if (result[2] > bestMove[2]) {
                    bestMove[0] = row;
                    bestMove[1] = col;
                    bestMove[2] = result[2];
                }
            }
            return bestMove;
        } else {
            int[] bestMove = new int[] { -1, -1, Integer.MAX_VALUE };
            for (int[] move : possibleMoves) {
                String[][] newBoard = copyBoard(board);
                int row = move[0];
                int col = move[1];
                newBoard[row][col] = PLAYER_O;
                int[] result = minimax(newBoard, true);
                if (result[2] < bestMove[2]) {
                    bestMove[0] = row;
                    bestMove[1] = col;
                    bestMove[2] = result[2];
                }
            }
            return bestMove;
        }
    }

    /**
     * Creates a deep copy of the board
     * 
     * @param board The board to copy
     * @return The copied board
     */
    private String[][] copyBoard(String[][] board) {
        String[][] newBoard = new String[3][3];
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                newBoard[row][col] = board[row][col];
            }
        }
        return newBoard;
    }

    /**
     * Check if there is a winner on the board.
     * 
     * @param board The current state of the board
     * @return 1 if Player X wins, -1 if Player O wins, 2 for a tie, 0 for no winner yet
     */
    private int checkWinner(String[][] board) {
        int[][] winConditions = { { 0, 1, 2 }, { 3, 4, 5 }, { 6, 7, 8 }, { 0, 3, 6 }, { 1, 4, 7 }, { 2, 5, 8 },
                { 0, 4, 8 }, { 2, 4, 6 } };

        for (int[] condition : winConditions) {
            int a = condition[0];
            int b = condition[1];
            int c = condition[2];
            if (board[a / 3][a % 3].equals(board[b / 3][b % 3]) && board[a / 3][a % 3].equals(board[c / 3][c % 3])
                    && !board[a / 3][a % 3].equals(EMPTY)) {
                if (board[a / 3][a % 3].equals(PLAYER_X)) {
                    return 1;
                } else {
                    return -1;
                }
            }
        }

        // Check for a draw
        boolean isDraw = true;
        for (String[] row : board) {
            for (String cell : row) {
                if (cell.equals(EMPTY)) {
                    isDraw = false;
                    break;
                }
            }
            if (!isDraw) {
                break;
            }
        }
        if (isDraw) {
            return 2;
        }

        // No winner yet
        return 0;
    }
}
