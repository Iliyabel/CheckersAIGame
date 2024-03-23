package checkers;

/**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
* Class        LeaderBoard.java
* Description  Class used add, remove, and manuipulate PlayerNodes. Has a firstPlace
*              playerNode variable to hold start of list. Also has a print method.
* Project      Checkers Game
* @author      Iliya Belyak, Chak Kwan Lai (Regan)
* @version 	   1.0.0
* Date         3/9/2023
* History log  3/9/2023
*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
public class LeaderBoard {
    
    private PlayerNode firstPlace; // holds the first place spot

    /**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    * Method       LeaderBoard() 
    * Description  Initializes a new leaderBoard with firstPlace being null.
    * @author      Iliya Belyak, Chak Kwan Lai (Regan) 
    * Date         3/9/2023
    * History Log   
    *~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    public LeaderBoard() {
        firstPlace = null;
    }

    /**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    * Method       add() 
    * Description  Method that adds player to leaderboard in moves decending order.
    *              A greater moves number means player is lower.
    * @param       player PlayerNode
    * @author      Iliya Belyak, Chak Kwan Lai (Regan) 
    * Date         3/9/2023
    * History Log   
    *~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    public void add(PlayerNode player) {
        // if firstPlace is empty,  place player there
        if (firstPlace == null) {
            firstPlace = player;
            return;
        // if player has less moves than first place, then make him first
        } else if (firstPlace.moves > player.moves) {
            player.next = firstPlace;
            firstPlace = player;
        }else {
            PlayerNode current = firstPlace;
            // loop through leaderboard
            while (current.next != null) {
                // if players moves is less than current.next swap it with player
                if (current.next.moves > player.moves) {
                    PlayerNode temp = current.next;
                    current.next = player;
                    player.next = temp;
                    return;
                // else move to next node.
                } else {
                    current = current.next;
                }
            }
            // if player has not be added yet, add it to the end of list
            current.next = player;
        }
    }

    /**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    * Method       remove() 
    * Description  Method that removes player from leaderboard. If player is not found
    *              method will return. 
    * @param       player PlayerNode
    * @author      Iliya Belyak, Chak Kwan Lai (Regan) 
    * Date         3/9/2023
    * History Log   
    *~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    // method that will remove player sent from leaderBoard
    public void remove(PlayerNode player) {
        if (!leaderBoardContains(player.name))
            return;
        // if firstPlace is the player, remove it
        if (firstPlace.name == player.name) {
            firstPlace = firstPlace.next;
        } else {
            PlayerNode current = firstPlace;
            // loop until you get to current.next.name equal to player.name
            while (!player.name.equalsIgnoreCase(current.next.name)) {
                // if name is equal to firstPlace.next
                if (current.next.next == null) {
                    current.next.next =  new PlayerNode(firstPlace.name, 
                        firstPlace.moves);
                    firstPlace = firstPlace.next;
                    current.next.next.next = null;
                }
                // keep going through list
                current = current.next;
            }
            // delete by making current.next equal.current.next.next
            current.next = current.next.next;
        }
    }

    /**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    * Method        leaderBoardContains()
    * Description   Searches through leaderBoard and if given name is in it, 
    *               it returns true. Ignores lower and uppercase's.
    * @return       true or false Boolean
    * @param        name String
    * @author       Iliya Belyak, Chak Kwan Lai (Regan)
    * Date          3/9/2023
    * History Log   
    *~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/ 
    public boolean leaderBoardContains(String name)  {
        // checks if name is null or is empty
        if (name == null || name.isEmpty())
            return false;
        PlayerNode current = firstPlace;
        // loops through tag ring and returns true if names match
        while (current != null) {
            if(name.equalsIgnoreCase(current.name))
                return true;
            current = current.next;
        }
        return false;
    }

    /**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    * Method        printLeaderBoard()
    * Description   Method that goes through leaderboard and prints out
    *               each player on a seperate line.
    * @return       name String
    * @author       Iliya Belyak, Chak Kwan Lai (Regan)
    * Date          3/9/2023
    * History Log   
    *~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/ 
    public String printLeaderBoard() {
        PlayerNode current = firstPlace;
        String result = "";
        // go throught the whole leaderboard and add to result string
        while (current != null) {
            result += current.toString();
            result += "\n";
            current = current.next;
        }
        return result;
    }
}
