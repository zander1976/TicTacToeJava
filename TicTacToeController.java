import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * Tic Tac Toe Controller
 *
 * @author Ben Woodhead
 * @version March 28, 2024
 */
public class TicTacToeController extends JFrame implements ActionListener
{
    // The menu buttons
    private JMenuItem restartItem;
    private JMenuItem quitItem;    
    
    TicTacToeBoardView boardView;
    TicTacToeStatusView statusView;
    TicTacToeModel model;
    
    /**
     * Constructor for objects of class TicTacToeController
     */
    public TicTacToeController(TicTacToeBoardView boardView, TicTacToeStatusView statusView, TicTacToeModel model) 
    {
        // Setup the basic window operation
        super("Tic Tac Toe");
        
        this.boardView = boardView;
        this.statusView = statusView;
        this.model = model;
    
        // Create bar and add it to the frame
        JMenuBar menubar = new JMenuBar();
        this.setJMenuBar(menubar); 

        // Create the file menu on the menubar
        JMenu fileMenu = new JMenu("Options"); 
        menubar.add(fileMenu); // and add to our menu bar

        // Add restart button in file menu
        restartItem = new JMenuItem("Restart"); 
        fileMenu.add(restartItem); 

        // Add quit button in file menu
        quitItem = new JMenuItem("Quit"); 
        fileMenu.add(quitItem); 
        
        final int SHORTCUT_MASK = Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx(); 
        restartItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R, SHORTCUT_MASK));
        quitItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, SHORTCUT_MASK));
        
        // listen for menu selections
        restartItem.addActionListener(this); 
        quitItem.addActionListener(this);
        this.boardView.registerListener(this);
        
        this.getContentPane().add(boardView, BorderLayout.CENTER);
        this.getContentPane().add(statusView, BorderLayout.SOUTH);

        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.pack();
        this.setSize(630,630);
        this.setResizable(true);
        this.setVisible(true);         
    }

    
    /**
     * Called when a cell is clicked. 
     */
    public void actionPerformed(ActionEvent e)
    {
        // Get the object that generated the event
        Object o = e.getSource(); 
        
        // Check if it's a button
        if (o instanceof JButton) {
            
            // Cast to a button
            JButton button = (JButton)o;
            int row = (int) button.getClientProperty("row");
            int col = (int) button.getClientProperty("col");
            model.makeMove(row, col);

        } else if (o instanceof JMenuItem) {
            
            // Cast to a menu item
            JMenuItem item = (JMenuItem)o;
            
            if (item == restartItem) { 
                
                // Restart the game
                model.clearBoard();
                
            } else if (item == quitItem) {
                
                // Leave game
                System.exit(0);
            }
        }
    }    
}
