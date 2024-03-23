package checkers;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
* Class        ComputerAI.java
* Description  ComputerAI class that has methods like makeMove to get the computer
*              to find its move actually make move if time is right with help of
*              moveComputerAI method.
*              Also has a method to check if it is able to make multiple captures.
* @author      Iliya Belyak, Chak Kwan Lai (Regan)
* @version 	   1.0.0
* Project      Checkers Game
* Date         3/3/2023
* History log  3/3/2023
*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
public class ComputerAI {

    // used to measure difficulty
    public static final int EASY = 1;
    public static final int HARD = 4;

    // class instance variables
    private Movement movement;
    private MiniMaxAI miniMax;
    private CheckersBoard board;

    private int depth;  // depth for how far computer will look ahead
    private boolean blackKing;
    private String lastPosition;

    /**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    * Constructor  ComputerAI
    * Description  Constructs a new ComputerAI that uses the Movement class
    * @param       movement Movement  
    * @author      Iliya Belyak, Chak Kwan Lai (Regan)
    * @version 	   1.0.0
    * Date         3/3/2023
    * History log  3/3/2023
    *~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/     
    public ComputerAI(Movement movement) {
        this.movement = movement;
        miniMax = new MiniMaxAI(movement, this);
        // automatically sets difficulty to easy
        depth = ComputerAI.EASY;
    }

    /**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    * Method       makeMove()
    * Description  Logic for black pieces(computer) to make a move and actual makes
    *              makes move once realMove is set to true.
    * @param       movements String[]      
    * @param       whitePieces Map<String, Piece> 
    * @param       blackPieces Map<String, Piece>
    * @param       realMove boolean
    * @param       human boolean
    * @author      Iliya Belyak, Chak Kwan Lai (Regan)
    * @version 	   1.0.0
    * Date         3/3/2023
    * History log  3/3/2023
    *~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/ 
    public void makeMove(String[] movements, Map<String, Piece> whitePieces, 
        Map<String, Piece> blackPieces, boolean realMove, boolean human) {

        // if human is true then swap the pieces
        if (human) {
            Map<String, Piece> tempBlack = blackPieces;
            blackPieces = whitePieces;
            whitePieces = tempBlack;
        }

        // gets former position tile
        String formerPositionTile;
        if (realMove) {
            formerPositionTile = this.lastPosition;
        } else {
            formerPositionTile = movements[1];
        }
        int[] formerXY = movement.getCoordinates(formerPositionTile);
        int formerX = formerXY[0];
        int formerY = formerXY[1];

        // gets new position tile
        String newPositionTile = movements[0];
        int[] newXY = movement.getCoordinates(newPositionTile);
        int newX = newXY[0];
        int newY = newXY[1];


        // puts new position to the border
        blackPieces.put(newPositionTile, new Piece());
        // checks whether piece can be changed to a king
        if (newY == Movement.MIN_BORDER || blackPieces.get
            (formerPositionTile).isKing()) {
            blackPieces.get(newPositionTile).setKing(true);
        }
        blackPieces.remove(formerPositionTile);

        JButton[][] buttonBoard = board.getButtonBoard();
        blackKing = blackPieces.get(newPositionTile).isKing();

        // check if it can capture
        if (movements.length == 3) {
            if (realMove) {
                buttonBoard[formerY][formerX].setIcon(null);
                // check if it can capture multiple
                checkMultiStepMovement(whitePieces, buttonBoard, newX, newY);
            } else {
                whitePieces.remove(movements[2]);
            }
        } else {
            // check if it can do basic move
            if (realMove) {
                buttonBoard[formerY][formerX].setIcon(null);
                buttonBoard[newY][newX].setIcon(new ImageIcon
                    (board.getBlackPiece()));

                if (blackPieces.get(newPositionTile).isKing()) {
                    buttonBoard[newY][newX].setIcon(new ImageIcon
                        (board.getBlackKingPiece()));
                }
            }
        }

        if (realMove) {
            board.setMovablePieces();
        }

    }

    /**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    * Method       checkMultiStepMovement()
    * Description  Check for multiple white pieces that could be captuerd
    * @param       whitePieces Map<String, Piece> 
    * @param       buttonBoard JButton[][] 
    * @param       newX int
    * @param       newY int
    * @author      Iliya Belyak, Chak Kwan Lai (Regan)
    * @version 	   1.0.0
    * Date         3/3/2023
    * History log  3/3/2023
    *~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/ 
    private void checkMultiStepMovement(Map<String, Piece> whitePieces, 
        JButton[][] buttonBoard, int newX, int newY) {

        int delay = 0;
        // a list of keys of pieces that can be removed
        Map<String, List<String[]>> removeKeys = movement.getCaptureTile();
        for (String removeKey : removeKeys.keySet()) {
            List<String[]> whiteKeys = removeKeys.get(removeKey);

            for (int i = 0; i < whiteKeys.size(); i++) {

                whitePieces.remove(whiteKeys.get(i)[0]);

                int finalI = i;
                // set a timer to achieve a pause
                Timer timer = new Timer(delay, ae -> {

                    // remove the white piece
                    int[] whiteXY = movement.getCoordinates(whiteKeys.get(finalI)[0]);
                    buttonBoard[whiteXY[1]][whiteXY[0]].setIcon(null);

                    // set a temporary black piece
                    int[] blackKey = movement.getCoordinates(whiteKeys.get(finalI)[1]);
                    buttonBoard[blackKey[1]][blackKey[0]].setIcon(new 
                        ImageIcon(board.getBlackPiece()));

                    // remove the previous black piece
                    if (finalI > 0) {
                        blackKey = movement.getCoordinates(whiteKeys.get(finalI - 1)[1]);
                        board.getButtonBoard()[blackKey[1]][blackKey[0]].
                            setIcon(null);
                    }
                });
                timer.setRepeats(false);
                timer.start();
                delay += 1000;

            }
        }
        // the timer for the last piece
        Timer timer = new Timer(delay, ae -> {
            buttonBoard[newY][newX].setIcon(new ImageIcon(board.getBlackPiece()));
            if (blackKing) {
                buttonBoard[newY][newX].setIcon(new ImageIcon(board.getBlackKingPiece()));
            }
        });
        timer.setRepeats(false);
        timer.start();
    }
    /**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    * Method       moveAI()
    * Description  Finds the best move and move for black (computer)        
    * @author      Iliya Belyak, Chak Kwan Lai (Regan)
    * @version 	   1.0.0
    * Date         3/3/2023
    * History log  3/3/2023
    *~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/ 
    public void moveComputerAI() {

        Timer timer = new Timer(300, ae -> {
            // create maps with all piece locations
            Map<String, Piece> whitePieces = board.getWhitePieces();
            Map<String, Piece> blackPieces = board.getBlackPieces();

            // get all possible moves and add to list
            movement.getMoves(whitePieces, blackPieces, false);
            List<String[]> basicMoves = movement.getBasicMoves();
            List<String[]> captureMoves = movement.getCaptureMoves();

            // if computer has no more moves left
            if (basicMoves.isEmpty() && captureMoves.isEmpty()) {
                //player won 
                board.gameWon();
            } else {
                miniMax.miniMax(movement.copyMap(whitePieces), 
                    movement.copyMap(blackPieces), 
                    depth, false);

                String[] bestMove = miniMax.getBestMove();
                lastPosition = bestMove[1];

                // if capture is possible check for multiple captures
                if (bestMove.length == 3) {
                    captureMoves = new ArrayList<>();
                    captureMoves.add(bestMove);
                    movement.setCaptureMoves(captureMoves);
                    List<String[]> copiedCaptureMoves = movement.copyList(captureMoves);
                    movement.setCopyCaptureMoves(copiedCaptureMoves);
                    movement.clearCapture();
                    movement.checkMultipleCapture(movement.copyMap(whitePieces), 
                        movement.copyMap(blackPieces), false);
                    bestMove = copiedCaptureMoves.get(0);
                }
                // call make move to actually make the real move
                makeMove(bestMove, whitePieces, blackPieces, true, false);
                board.setMovablePieces();
            }
        });
        timer.setRepeats(false);
        timer.start();
    }
    /**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    * Method       setBoard()
    * Description  Stores a CheckersBoard object into a tile      
    * @param       board CheckersBoard
    * @author      Iliya Belyak, Chak Kwan Lai (Regan)
    * @version 	   1.0.0
    * Date         3/3/2023
    * History log  3/3/2023
    *~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/ 
    public void setBoard(CheckersBoard board) {
        this.board = board;
    }

    /**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    * Method       setDepth()
    * Description  Sets the depth of how far is the computer searching into      
    * @param       depth int
    * @author      Iliya Belyak, Chak Kwan Lai (Regan)
    * @version 	   1.0.0
    * Date         3/3/2023
    * History log  3/3/2023
    *~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/ 
    public void setDepth(int depth) {
        this.depth = depth;
    }
}
