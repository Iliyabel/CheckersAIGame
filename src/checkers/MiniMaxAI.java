package checkers;


import java.util.List;
import java.util.Map;

/**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 * Class        MiniMaxAI.java
 * Description  A class that searches moves ahead, finding the best and 
 *              worst moves possible for each color, given a certain depth.
 *              This implementaion of miniMax does not use alpha beta pruning.
 * Project      Checkers Game
 * @author      Iliya Belyak, Chak Kwan Lai (Regan)
 * @version 	
 * Date         3/3/2023
 * History log  3/3/2023
 *~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
public class MiniMaxAI {

    // class instance variables
    private Movement movement;
    private ComputerAI computerAI;

    private String[] bestMove; // string array to hold best move

    /**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    * Constructor  MiniMaxAI()
    * @param       movement Movement
    * @param       computerAI ComputerAI
    * Description  Constructs a MiniMaxAI object 
    * @author      Iliya Belyak, Chak Kwan Lai (Regan)
    * Date         3/3/2023
    * History log  3/3/2023
    *~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/ 
    public MiniMaxAI(Movement movement, ComputerAI computerAI) {
        this.movement = movement;
        this.computerAI = computerAI;
    }

    /**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    * Method       miniMax()
    * Description  A method that searches ahead of moves, giving the best possible
    *              moves for the computer this method.
    * @param       whitePieces Map<String, Piece>
    * @param       blackPieces Map<String, Piece>
    * @param       depth int
    * @param       alpha int
    * @param       beta int
    * @param       boolean human
    * @return      score int 
    * @author      Iliya Belyak, Chak Kwan Lai (Regan) 
    * Date         3/3/2023
    * History log  3/11/2023
    *~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/     
    public int miniMax(Map<String, Piece> whitePieces, Map<String, Piece> blackPieces, 
        int depth, boolean human) {

        // gets all possible moves
        movement.getMoves(whitePieces, blackPieces, human);
        List<String[]> captureMoves = movement.getCaptureMoves();
        List<String[]> basicMoves = movement.getBasicMoves();

        // return if depth equals 0 or moves are empty
        if (depth == 0 || (basicMoves.isEmpty() && captureMoves.isEmpty())) {
            return boardValue(whitePieces, blackPieces);
        }

        // force capture
        List<String[]> possibleMoves;
        //assign possiblemoves to basicmoves
        possibleMoves = basicMoves;
        if (!captureMoves.isEmpty()) {
            possibleMoves = captureMoves;
        }

        String[] tempBestMove = null;
        if (!human) {

            int score = Integer.MIN_VALUE;

            for (String[] move : possibleMoves) {

                // simulates the board with a copy of all pieces to not 
                // change actual maps
                Map<String, Piece> copyWhitePieces = movement.copyMap(whitePieces);
                Map<String, Piece> copyBlackPieces = movement.copyMap(blackPieces);

                computerAI.makeMove(move, copyWhitePieces, copyBlackPieces, 
                    false, false);
                score = miniMax(copyWhitePieces, copyBlackPieces, depth - 1,
                     true);
                tempBestMove = move;
            }
            bestMove = tempBestMove;
            return score;
        } else {
            int score = Integer.MAX_VALUE;
            for (String[] move : possibleMoves) {

                // simulates the board with a copy of all pieces to 
                //not change actual maps
                Map<String, Piece> copyWhitePieces = movement.copyMap(whitePieces);
                Map<String, Piece> copyBlackPieces = movement.copyMap(blackPieces);

                computerAI.makeMove(move, copyWhitePieces, copyBlackPieces, 
                    false, true);

                movement.getMoves(copyWhitePieces, copyBlackPieces, true);
                captureMoves = movement.getCaptureMoves();
                if (!captureMoves.isEmpty()) {
                    computerAI.makeMove(captureMoves.get(0), copyWhitePieces, 
                        copyBlackPieces, false, true);
                }

                score = miniMax(copyWhitePieces, copyBlackPieces, depth - 1, 
                     false);
                tempBestMove = move;
            }
            bestMove = tempBestMove;
            return score;
        }
    }

    /**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    * Method       boardValue()
    * Description  Returns the current value on the board, a positive 
    *              number indicating white is having an advantage, negative number 
    *              indicating black having an  advantage 
    * @param       whitePieces Map<String, Piece>
    * @param       blackPieces Map<String, Piece>
    * @return      int 
    * @author      Iliya Belyak, Chak Kwan Lai (Regan) 
    * Date         3/3/2023
    * History log  3/3/2023
    *~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/     
    private int boardValue(Map<String, Piece> whitePieces, 
        Map<String, Piece> blackPieces) {
        // get the differnce between number of black pieces and number of white pieces
        int difference = blackPieces.size() - whitePieces.size();
        int kings = 0;
        //loop through black pieces
        for (String black : blackPieces.keySet()) {
            // if a blackpiece is king, add to number of king black has
            if (blackPieces.get(black).isKing()) {
                kings++;
            }
        }
        return difference + kings;
    }

    /**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    * Method       getBestMove()
    * @param       
    * @return      String[] 
    * Description  Returns the best move 
    * @author      Iliya Belyak, Chak Kwan Lai (Regan) 
    * Date         3/3/2023
    * History log  3/3/2023
    *~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/      
    public String[] getBestMove() {
        return bestMove;
    }
}
