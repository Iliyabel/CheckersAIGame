package checkers;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.Font;
/**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
* Class        Help.java
* Description  A jDialog that displays the rules of playing checkers
* Project      Checkers Game
* @author      Iliya Belyak, Chak Kwan Lai (Regan)
* @version 	   1.0.0
* Date         3/3/2023
* History log  3/3/2023
*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
public class Help extends JDialog{
    public Help(JFrame parent, String title, boolean modal) {
        super(parent, title, modal);
        setLayout(null);
        
        JLabel rules = new JLabel("Rules:");
        Font font = new Font("Arial", Font.BOLD, 50);
        rules.setFont(font);
        rules.setBounds(230, 80, 300, 150);
        add(rules);

        JLabel first = new JLabel("All pieces can only move in diagonal squares.");
        JLabel second = new JLabel("Normal pieces are only allowed to move forward and they must capture when possible.");
        JLabel third = new JLabel("Once a normal piece reaches the furthest row, it is crowned and becomes a king.");
        JLabel forth = new JLabel("A king piece may move backwards and combine captures in both directions in the same turn.");
        JLabel fifth = new JLabel("A player wins the game when the opponent has no pieces left or cannot make a move.");
        first.setBounds(20, 100, 600, 250);
        second.setBounds(20, 120, 600, 250);
        third.setBounds(20, 140, 600, 250);
        forth.setBounds(20, 160, 600, 250);
        fifth.setBounds(20, 180, 600, 250);
        add(first);
        add(second);
        add(third);
        add(forth);
        add(fifth);
        
        JButton close = new JButton("Close");
        close.addActionListener(e -> {
            dispose();
        });
        close.setBounds(200, 350, 100, 60);
        add(close);

        setSize(600, 600);
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
