import java.util.Observable;
import java.util.Observer;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * Tic Tac Toe Board View
 *
 * @author Ben Woodhead
 * @version March 28, 2024
 */
public class TicTacToeBoardView extends JPanel implements Observer
{
    // The board
    private JButton[][] cells;
    private ImageIcon XIcon = new ImageIcon("x.png");
    private ImageIcon OIcon = new ImageIcon("o.png");
    private ImageIcon EmptyIcon = new ImageIcon("empty.png");
    
    /**
     * Constructor for objects of class TicTacToeView
     */
    public TicTacToeBoardView()
    {
        this.setLayout(new GridLayout(3,3,0,0));
        cells = new JButton[3][3];
        this.setBackground(new Color(0,0,0));

        for(int row = 0; row < 3; row++) {
            for(int col = 0; col < 3; col++) {
                JButton button = new JButton(EmptyIcon);
                button.putClientProperty("row", row);
                button.putClientProperty("col", col);
                
                //button.setBorder(BorderFactory.createLineBorder(Color.BLUE)); 
                this.add(button);
                cells[row][col] = button;
            }
        }
    }
    
    /**
     * Registers outside handlers
     * 
     * @param The listener to handle actions
     */
    public void registerListener(ActionListener a) {
        for(int row = 0; row < 3; row++) {
            for(int col = 0; col < 3; col++) {
                cells[row][col].addActionListener(a); 
            }
        }
    }

    /**
     * Update is called when the model is updated
     * 
     * @param Observable The Tic Tac Toe model that has been updated
     * @param Object Arguments that are sent from the model
     */
    public void update(Observable o, Object arg) {
        if (!(o instanceof TicTacToeModel)) {
            System.out.println("ERROR: got a signal from something I didn't ask for");
            return;
        }
            
        TicTacToeModel model = (TicTacToeModel) o;
        for(int row = 0; row < 3; row++) {
            for(int col = 0; col < 3; col++) {
                cells[row][col].setEnabled(false);
                if (model.getPiece(row, col) == TicTacToeModel.PLAYER_X) {
                    cells[row][col].setIcon(XIcon);
                    cells[row][col].setDisabledIcon(XIcon);
                } else if (model.getPiece(row, col) == TicTacToeModel.PLAYER_O) {
                    cells[row][col].setIcon(OIcon);
                    cells[row][col].setDisabledIcon(OIcon);
                } else {
                    //cells[row][col].setText(TicTacToeModel.EMPTY);
                    cells[row][col].setIcon(EmptyIcon);
                    cells[row][col].setDisabledIcon(EmptyIcon);
                    if (model.getWinner().equals(TicTacToeModel.EMPTY)) {
                        cells[row][col].setEnabled(true);
                    }
                }
            }
        }
    }    
    

}
