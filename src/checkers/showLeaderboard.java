package checkers;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.Font;
/**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
* Class        showLeaderboard.java
* Description  A jDialog that shows the current leaderboard of players 
* Project      Checkers Game
* @author      Iliya Belyak, Chak Kwan Lai (Regan)
* @version 	   1.0.0
* Date         3/3/2023
* History log  3/3/2023
*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
public class showLeaderboard extends JDialog{
    private LeaderBoard board = new LeaderBoard();

    public showLeaderboard (JFrame parent, String title, boolean modal, LeaderBoard leaderBoard1) {
        super(parent, title, modal);
        setLayout(null);

        JLabel leaderboard = new JLabel("Leaderboard");
        Font big = new Font("Arial", Font.BOLD, 50);
        leaderboard.setFont(big);
        leaderboard.setBounds(140, -90, 400, 300);
        add(leaderboard);

        String leaders = leaderBoard1.printLeaderBoard();
        String[] lines = leaders.split("\n");
        int y = -20;
        for (String line : lines) {
            JLabel players = new JLabel(line);
            Font small = new Font("Arial", Font.BOLD, 20);
            players.setFont(small);
            players.setBounds(20, y, 400, 300);
            add(players);
            y += 20;
        }
        JButton close = new JButton("Close");
        close.addActionListener(e -> {
            dispose();
        });
        close.setBounds(250, 450, 100, 40);
        add(close);

        setSize(600, 600);
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
