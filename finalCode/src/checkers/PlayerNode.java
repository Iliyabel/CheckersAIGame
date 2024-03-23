package checkers;

/**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
* Class        PlayerNode.java
* Description  Class used to create a playerNode that stores player name and total moves.
*              Also has next node location in list, with a to String method.
* Project      Checkers Game
* @author      Iliya Belyak, Chak Kwan Lai (Regan)
* @version 	   1.0.0
* Date         3/9/2023
* History log  3/9/2023
*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
public class PlayerNode {

    public String name; // name of player
    public int moves; // total moves made
    public PlayerNode next; // next node in the list

    /**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    * Method       Player() 
    * Description  Initializes a new player with the name and move count.
    * @param       name String
    * @param       moves int
    * @author      Iliya Belyak, Chak Kwan Lai (Regan) 
    * Date         3/9/2023
    * History Log   
    *~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    public PlayerNode(String name, int moves) {
        this(name, moves, null);
    }

    /**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    * Method       Player() 
    * Description  Initializes a new player with the name and move count.
    * @param       name String
    * @param       moves int
    * @author      Iliya Belyak, Chak Kwan Lai (Regan) 
    * Date         3/9/2023
    * History Log   
    *~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    public PlayerNode(String name, int moves, PlayerNode next) {
        this.name = name;
        this.moves = moves;
        this.next = next;
    }

    /**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    * Method       toString() 
    * Description  Gets the name and total moves of a player into a string.
    * @param       name String
    * @param       moves int
    * @return      String
    * @author      Iliya Belyak, Chak Kwan Lai (Regan) 
    * Date         3/9/2023
    * History Log   
    *~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    public String toString() {
        return name + "," + moves;
    }

}
