package checkers;

/**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
* Class        ProjectMain.java
* Description  Class that initialzes all objects and gets the program running by
*              calling the gui (checkersboard) to create.
* Project      Checkers Game
* @author      Iliya Belyak, Chak Kwan Lai (Regan)
* @version 	   1.0.0
* Date         3/3/2023
* History log  3/10/2023
*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
public class ProjectMain {

    /**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    * Method       main
    * Description  Create objects in other classes and runs the program 
    * @param       args[] String
    * @author      Iliya Belyak, Chak Kwan Lai (Regan)
    * @version 	   1.0.0
    * Date         3/3/2023
    * History log  3/3/2023
    *~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/ 
    public static void main(String[] args) {

        //initialize all objects
        Movement movement = new Movement();
        ComputerAI computerAI = new ComputerAI(movement);
        HumanMove humanMove = new HumanMove(movement, computerAI);
        CheckersBoard checkersBoard = new CheckersBoard(humanMove, computerAI);

        computerAI.setBoard(checkersBoard);
        humanMove.setBoard(checkersBoard);
    }

}