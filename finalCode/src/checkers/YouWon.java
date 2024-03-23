package checkers;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import java.awt.Font;
/**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
* Class        YouWon.java
* Description  A jDialog that outputs a message saying the player has won, and asks the player 
*              to enter their name which adds to the leaderboard 
* Project      Checkers Game
* @author      Iliya Belyak, Chak Kwan Lai (Regan)
* @version 	   1.0.0
* Date         3/3/2023
* History log  3/3/2023
*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
public class YouWon extends JDialog {
    private JTextField nameTextField;
    public YouWon (JFrame parent, String title, boolean modal) {
        
        super(parent, title, modal);        // sets modal
        setLayout(null);

        // You Won JLabel
        JLabel won = new JLabel("You Won!");
        Font big = new Font("Arial", Font.BOLD, 40);
        won.setFont(big);
        won.setBounds(200, 80, 300, 150);
        add(won);

        // Asks user to enter name in textfield 
        JLabel message = new JLabel("Put your name and add to leaderboard!");
        Font font = new Font("Arial", Font.BOLD, 20);
        message.setFont(font);
        message.setBounds(100, 120, 450, 150);
        add(message);

        // Label that indicates the text field on the right is to enter their name
        JLabel yourName = new JLabel("Your name:");        
        yourName.setFont(font);
        yourName.setBounds(50, 220, 300, 150);
        add(yourName);
        nameTextField = new JTextField();
        nameTextField.setBounds(200, 280, 300, 30);
        add(nameTextField);

        // Outputs an illegal message when nothing is entered in the text field
        JLabel illegalMessage = new JLabel("");
        illegalMessage.setFont(font);
        illegalMessage.setBounds(150, 400, 400, 60);
        add(illegalMessage);

        // Add to leaderboard button, outputs illegal message when nothing is entered in the text field 
        JButton add = new JButton("Add to leaderboard");
        add.setBounds(200, 330, 200, 60);
        add.addActionListener(e -> {
            if (nameTextField.getText() != null && nameTextField.getText().length() != 0) {
                dispose();
            } else {
                illegalMessage.setText("Illegal name, add at least one character");
            }
        });
        add(add);

        // Properties for JDialog 
        setSize(600, 600);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    /**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    * Method       getTextFieldText.()
    * Description  Returns the text the user entered in the text field
    * Project      Checkers Game
    * @param       
    * @return      String
    * @author      Iliya Belyak, Chak Kwan Lai (Regan)
    * @version 	   1.0.0
    * Date         3/3/2023
    * History log  3/3/2023
    *~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/    
    public String getTextFieldText() {
        return nameTextField.getText();
    }
}
