package checkers;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Map;

/**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 * Class        HumanMove.java
 * Description  
 * Project      Checkers Game
 * @author      Iliya Belyak, Chak Kwan Lai (Regan)
 * @version 	
 * Date         3/3/2023
 * History log  3/3/2023
 *~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
public class HumanMove implements ActionListener {

    // class instance variables
    private Movement movement;
    private ComputerAI computerAI;
    private CheckersBoard board;

    private JButton currentlySelectedPiece;
    public int moves;

    /**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    * Constructor  HumanMove()
    * @param       movement Movement
    * @param       computerAI ComputerAI
    * Description  Constructs a non-king piece with a String that represents its color
    * @author      Iliya Belyak, Chak Kwan Lai (Regan)
    * Date         3/3/2023
    * History log  3/3/2023
    *~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/ 
    public HumanMove(Movement movement, ComputerAI computerAI) {
        this.movement = movement;
        this.computerAI = computerAI;
    }

    /**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    * Method       actionPerformed()
    * @param       ActionEvent e
    * @return      none 
    * Description  performs all needed actions when a user clicks on a button(piece)
    * @author      Iliya Belyak, Chak Kwan Lai (Regan) 
    * Date         3/3/2023
    * History log  3/3/2023
    *~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/ 
    @Override
    public void actionPerformed(ActionEvent e) {

        board.setMovablePieces();

        // get the tiles and pieces from the board
        JButton[] greenTiles = board.getBasicMoveTiles();
        JButton[] redTiles = board.getCaptureTiles();
        Map<String, Piece> blackPieces = board.getBlackPieces();
        Map<String, Piece> whitePieces = board.getWhitePieces();
        JButton[][] buttonBoard = board.getButtonBoard();

        String currentPosition = e.getActionCommand();
        int[] xy = movement.getCoordinates(currentPosition);

        // checks whether a red tiles has been selected
        boolean redTileSelected = checkCaptureTilesSelected(
            redTiles, currentPosition);
        if (redTileSelected) {
            return;
        } else {
            // check whether a green tile has been selected previously
            if (checkBasicMoveTileSelected(greenTiles, currentPosition)) {
                return;
            }
        }


        // reset colored tiles
        board.setBasicMoveTiles(new JButton[4]);
        board.setCaptureTiles(new JButton[4]);

        if (whitePieces.containsKey(currentPosition)) {

            // save the current selected piece
            currentlySelectedPiece = buttonBoard[xy[1]][xy[0]];

            // give the selected piece a yellow boarder
            boolean king = whitePieces.get(currentPosition).isKing();
            if (king) {
                currentlySelectedPiece.setIcon(new ImageIcon(board.
                    getSelectedKingPiece()));

            } else {
                currentlySelectedPiece.setIcon(new ImageIcon(board.
                    getSelectedPiece()));
            }

            // check for multiple captures
            movement.clearMoves();
            movement.validMoves(currentPosition, whitePieces, blackPieces, true);
            List<String[]> copiedCaptureMoves = movement.copyList(movement.
                getCaptureMoves());
            movement.setCopyCaptureMoves(copiedCaptureMoves);
            movement.checkMultipleCapture(movement.copyMap(whitePieces), 
                movement.copyMap(blackPieces), true);
            movement.setCaptureMoves(copiedCaptureMoves);

            // apply capture
            List<String[]> captureMoves = movement.getCaptureMoves();
            if (!captureMoves.isEmpty()) {

                for (int i = 0; i < captureMoves.size(); i++) {

                    String captureMove = captureMoves.get(i)[0];
                    int[] captureXY = movement.getCoordinates(captureMove);

                    JButton redTile = buttonBoard[captureXY[1]][captureXY[0]];
                    redTile.setBackground(Color.RED);
                    redTiles[i] = redTile;
                }
                board.setCaptureTiles(redTiles);
            }

            // check for simple moves
            movement.clearMoves();
            movement.validMoves(currentPosition, whitePieces, blackPieces, true);
            List<String[]> simpleMoves = movement.getBasicMoves();

            // apply simple move
            if (movement.getCaptureMoves().isEmpty()) {
                if (!simpleMoves.isEmpty()) {
                    for (int i = 0; i < simpleMoves.size(); i++) {
                        String movePosition = simpleMoves.get(i)[0];
                        int[] moveXY = movement.getCoordinates(movePosition);

                        JButton greenTile = buttonBoard[moveXY[1]][moveXY[0]];
                        greenTile.setBackground(board.getNeon_green());
                        greenTiles[i] = greenTile;
                    }
                    board.setBasicMoveTiles(greenTiles);
                }
            }
            board.resetGreenTiles();
        }
    }


    /**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    * Method       checkCaptureSquareSelected()
    * Description  outputs an illegal message on GUI if an illegal capture move is selected
    * @param       captureTiles Jbutton[]
    * @param       currentPosition String  
    * @return      boolean 
    * @author      Iliya Belyak, Chak Kwan Lai (Regan)
    * Date         3/3/2023
    * History log  3/3/2023
    *~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/    
    private boolean checkCaptureTilesSelected(JButton[] captureTiles, String currentPosition) {
        // loop through captureTiles JButton array
        for (JButton captureTile : captureTiles) {
            if (captureTile != null) {
                boolean tileMatch = selectTile(captureTile, currentPosition);
                if (tileMatch) {
                    board.setCaptureTiles(new JButton[4]);
                    return true;
                }
            }
        }
        return false;
    }

    /**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    * Method       checkBasicMoveSelected()
    * Description  checks if a basic move is selected
    * @param       greenTiles JButton[] 
    * @param       currentPosition String
    * @return      true or false boolean 
    * @author      Iliya Belyak, Chak Kwan Lai (Regan)
    * Date         3/3/2023
    * History log  3/3/2023
    *~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/ 
    private boolean checkBasicMoveTileSelected(JButton[] greenTiles, String currentPosition) {

        for (JButton greenTile : greenTiles) {
            if (greenTile != null) {
                boolean tileMatch = selectTile(greenTile, currentPosition);
                if (tileMatch) {
                    board.setBasicMoveTiles(new JButton[4]);
                    return true;
                }
            }
        }
        return false;
    }

    /**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    * Method       selectTile()
    * Description  checks if a basic move is selected
    * @param       basicMoves JButton[] 
    * @param       currentPosition String
    * @return      true or false boolean 
    * @author      Iliya Belyak, Chak Kwan Lai (Regan)
    * Date         3/3/2023
    * History log  3/3/2023
    *~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/ 
    private boolean selectTile(JButton possibleTiles, String currentPosition) {

        String possiblePossition = possibleTiles.getActionCommand();
        boolean tileMatch = possiblePossition.equals(currentPosition);

        if (tileMatch) {

            currentlySelectedPiece.setIcon(null);

            List<String[]> blackKeys = movement.getCaptureTile().get(currentPosition);
            int delay = 0;
            if (blackKeys != null && !blackKeys.isEmpty()) {
                for (int i = 0; i < blackKeys.size(); i++) {

                    // add a timer in order to illustrate multiple captures
                    // by triggering them after a delay which is increased each iteration
                    int finalI = i;
                    Timer timer = new Timer(delay, ae -> {

                        board.getBlackPieces().remove(blackKeys.get(finalI)[0]);
                        int[] blackXY = movement.getCoordinates(blackKeys.
                            get(finalI)[0]);
                        board.getButtonBoard()[blackXY[1]][blackXY[0]].
                            setIcon(null);

                        // add a temporary white piece to a intermediate step
                        if (finalI < blackKeys.size() - 1) {
                            int[] whiteXY = movement.getCoordinates(blackKeys.
                                get(finalI)[1]);
                            board.getButtonBoard()[whiteXY[1]][whiteXY[0]].setIcon(
                                new ImageIcon(board.getWhitePiece()));
                        }

                        // remove the temporary white piece after the next iteration
                        if (finalI > 0) {
                            int[] whiteXY = movement.getCoordinates(blackKeys.
                                get(finalI - 1)[1]);
                            board.getButtonBoard()[whiteXY[1]][whiteXY[0]]
                                .setIcon(null);
                        }
                    });

                    timer.setRepeats(false);
                    timer.start();
                    delay += 500;

                }
                if (blackKeys.size() == 1) {
                    delay = 0;
                }
                // set a timer for the last move
                Timer timer = new Timer(delay - 500, ae -> {
                    setNewPosition(possibleTiles, currentPosition);
                    computerAI.moveComputerAI();
                });
                timer.setRepeats(false);
                timer.start();
            } else {
                setNewPosition(possibleTiles, currentPosition);
                computerAI.moveComputerAI();
            }
            movement.clearCapture();
        }
        return tileMatch;
    }
    
    /**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    * Method       setNewPosition()
    * Description  Moves the piece to a new tile, setting the tile to the according image
    * @param       currentTile JButton
    * @param       currentPosition String
    * @return    
    * @author      Iliya Belyak, Chak Kwan Lai (Regan)
    * Date         3/3/2023
    * History log  3/3/2023
    *~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/ 
    private void setNewPosition(JButton currentTile, String currentPosition) {

        // get white piece map
        Map<String, Piece> whitePieces = board.getWhitePieces();
        String tempKey = currentlySelectedPiece.getActionCommand();
        // checks if piece is king and puts result in king variable
        boolean king = whitePieces.get(tempKey).isKing();
        // remove piece from location
        whitePieces.remove(tempKey);
        // add piece to new location
        whitePieces.put(currentPosition, new Piece());
        moves++;
        // put correct icon if king
        if (king) {
            currentTile.setIcon(new ImageIcon(board.getWhiteKingPiece()));
            whitePieces.get(currentPosition).setKing(true);

        } else {
            currentTile.setIcon(new ImageIcon(board.getWhitePiece()));
            int y = Integer.valueOf(currentPosition.split(":")[1]);
            if (y == Movement.MAX_BORDER) {
                currentTile.setIcon(new ImageIcon(board.getWhiteKingPiece()));
                whitePieces.get(currentPosition).setKing(true);
            }
        }
    }

    /**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    * Method       setBoard()
    * Description  Stores the passed in CheckersBoard 
    * @param       board CheckersBoard
    * @return       
    * @author      Iliya Belyak, Chak Kwan Lai (Regan)
    * Date         3/3/2023
    * History log  3/3/2023
    *~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/     
    public void setBoard(CheckersBoard board) {
        this.board = board;
    }

    /**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    * Method       getMoves()
    * Description  Returns the total number of moves played by white in a game 
    * @param       
    * @return      int  
    * @author      Iliya Belyak, Chak Kwan Lai (Regan)
    * Date         3/3/2023
    * History log  3/3/2023
    *~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/ 
    public int getMoves() {
        return moves;
    }
}
