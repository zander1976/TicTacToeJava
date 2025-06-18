import java.util.Observable;
import java.util.Observer;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * Tic Tac Toe Status View
 *
 * @author Ben Woodhead
 * @version March 28, 2024
 */
public class TicTacToeStatusView extends JPanel implements Observer
{
    JLabel status;
    
    /**
     * Constructor for objects of class TicTacToeStatusView
     */
    public TicTacToeStatusView()
    {
        status = new JLabel("Button");
        status.setHorizontalAlignment(JLabel.RIGHT);
        this.add(status);
    }

    /**
     * Update is called when the model is updated
     * 
     * @param Observable The Tic Tac Toe model that has been updated
     * @param Object Arguments that are sent from the model
     */
    public void update(Observable o, Object arg) {
        //if (!(o instanceof TicTacToeModel)) {
        //    System.out.println("ERROR: got a signal from something I didn't ask for.");
        //    return;
        //}
            
        //TicTacToeModel observable = (TicTacToeModel) o;
        status.setText(arg.toString());
    }    
    

}
