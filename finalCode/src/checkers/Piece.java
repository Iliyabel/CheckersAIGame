package checkers;

/**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
* Class        Piece.java
* Description  Class that creates piece objects that have to fields, color and isKing.
* Project      Checkers Game
* @author      Iliya Belyak, Chak Kwan Lai (Regan)
* @version     1.0.0
* Date         3/3/2023
* History log  3/3/2023
*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
public class Piece {

    private boolean king;

    /**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    * Method       isKing()
    * Description  checks if the piece is a king piece 
    * @return      true or false Boolean 
    * @author      Iliya Belyak, Chak Kwan Lai (Regan)
    * Date         3/3/2023
    * History log  3/3/2023
    *~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/ 
    public boolean isKing() {
        return king;
    }

    /**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    * Method       setKing()
    * Description  sets the piece to a king 
    * @param       king boolean
    * @author      Iliya Belyak, Chak Kwan Lai (Regan)
    * Date         3/3/2023
    * History log  3/3/2023
    *~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/ 
    public void setKing(boolean king) {
        this.king = king;
    }
}
