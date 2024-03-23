package checkers;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.Font;
/**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
* Class        YouLost.java
* Description  A jDialog that outputs a message saying the player has lost 
* Project      Checkers Game
* @author      Iliya Belyak, Chak Kwan Lai (Regan)
* @version 	   1.0.0
* Date         3/3/2023
* History log  3/3/2023
*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
public class YouLost extends JDialog{
    public YouLost (JFrame parent, String title, boolean modal) {
        super(parent, title, modal);
        setLayout(null);

        // Label that outputs a message user has lost 
        JLabel lost = new JLabel("You lost, thanks for playing!");
        Font font = new Font("Arial", Font.BOLD, 35);
        lost.setFont(font);
        lost.setBounds(60, 60, 500, 150);
        add(lost);

        // Button for user to cloes the JDialog and the GUI
        JButton close = new JButton("Close");
        close.addActionListener(e -> {
            dispose();
        });        
        close.setBounds(150, 300, 200, 80);
        add(close);

        // Properties for JDialog
        setSize(600, 600);
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
