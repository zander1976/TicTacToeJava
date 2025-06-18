/**
 * Tic Tac Toe Main Setup
 *
 * @author Ben Woodhead 
 * @version April 3, 2024
 */
public class TicTacToeClient
{
    /**
     * Main entry point
     */
    public static void main(String[] argv) {
        
        // Setup the MVC
        TicTacToeModel model = new TicTacToeModel();
        TicTacToeBoardView boardView = new TicTacToeBoardView();
        TicTacToeStatusView statusView = new TicTacToeStatusView();
        TicTacToeController controller = new TicTacToeController(boardView, statusView, model);
        
        // Set the observer pattern
        model.addObserver(boardView);
        model.addObserver(statusView);
        
        // Get the game ready 
        model.clearBoard();
    }
}
