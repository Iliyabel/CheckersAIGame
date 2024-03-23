package checkers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
* Class        Movement.java
* Description  Class that finds capture moves and basic moves a specific 
*              checkers piece can take.
* Project      Checkers Game
* @author      Iliya Belyak, Chak Kwan Lai (Regan)
* @version 	1.0.0
* Date         3/3/2023
* History log  3/3/2023
*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
public class Movement {

    // minimum value for the board starting on 0
    public static final int MIN_BORDER = 0;
         // maximum value for the board ending on 7
    public static final int MAX_BORDER = 7;

    // Map that will contain a location(String in x:y form) and piece object
    private Map<Integer, List<String[]>> tempCaptureKey = new HashMap<>();
    private Map<String, List<String[]>> captureTile = new HashMap<>();

    // location list of copy of capture moves possible
    private List<String[]> copyCaptureMoves; 
    private List<String[]> basicMoves; // location list of basic moves possible
    private List<String[]> captureMoves; // location list of capture moves possible


    /**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    * Method       validMoves()
    * Description  A method that finds all valid moves for both computer and person, 
    *              and adds them to a list of moves
    * @param       currentPosition String
    * @param       whitePieces Map<String, Piece>
    * @param       blackPieces Map<String, Piece>
    * @param       human boolean
    * @author      Iliya Belyak, Chak Kwan Lai (Regan)
    * Date         3/3/2023
    * History log  3/11/2023
    *~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/ 
    public void validMoves(String currentPosition, Map<String, Piece> whitePieces, 
        Map<String, Piece> blackPieces, boolean human) {

        //convert String currentPossition to x and y ints
        String[] coordinates = currentPosition.split(":");
        int x = Integer.valueOf(coordinates[0]);
        int y = Integer.valueOf(coordinates[1]);

        // create a map for the current player
        Map<String, Piece> currentPlayer;

        // set the current player (either white or black)
        boolean king;
        // if the current player is human, assign white pieces to player
        // and check if selected piece is a king
        if (human) {
            // assign king status to king variable
            king = whitePieces.get(currentPosition).isKing();
            currentPlayer = blackPieces;
        } else {
            // assign king status to king variable
            king = blackPieces.get(currentPosition).isKing();
            currentPlayer = whitePieces;
        }

        // All possible normal moves
        String rightBackMove = createKey(x + 1, y - 1);
        String leftBackMove = createKey(x - 1, y - 1);
        String rightFrontMove = createKey(x + 1, y + 1);
        String leftFrontMove = createKey(x - 1, y + 1);

        // All possible capture moves
        String rightBackCapture = createKey(x + 2, y - 2);
        String leftBackCapture = createKey(x - 2, y - 2);
        String rightFrontCapture = createKey(x + 2, y + 2);
        String leftFrontCapture = createKey(x - 2, y + 2);

        // check for valid captures and add into a capture list
        // only kings and computer pieces can go backward
        if (king || !human) {
            // Check if there are any pieces in rightBackCapture location
            if (!whitePieces.containsKey(rightBackCapture) && 
                !blackPieces.containsKey(rightBackCapture)) {
                if (currentPlayer.containsKey(rightBackMove)) {
                    // checks to make sure move is in border
                    if (x + 2 <= MAX_BORDER && y - 2 >= MIN_BORDER) {
                        String[] captureMoveKeys = {rightBackCapture, 
                            currentPosition, rightBackMove};
                        captureMoves.add((captureMoveKeys));
                    }
                }
            }
            // Check if there are any pieces in leftBackCapture location
            if (!whitePieces.containsKey(leftBackCapture) && 
                !blackPieces.containsKey(leftBackCapture)) {
                if (currentPlayer.containsKey(leftBackMove)) {
                    // checks to make sure move is in border
                    if (x - 2 >= MIN_BORDER && y - 2 >= MIN_BORDER) {
                        String[] captureMoveKeys = {leftBackCapture, 
                            currentPosition, leftBackMove};
                        captureMoves.add(captureMoveKeys);
                    }
                }
            }
        }
        // checks if u can go one space forward
        // only kings and human pieces can go forward
        if (king || human) {
            // Check if there are any pieces in rightFrontCapture location
            if (!whitePieces.containsKey(rightFrontCapture) && 
                !blackPieces.containsKey(rightFrontCapture)) {
                if (currentPlayer.containsKey(rightFrontMove)) {
                    // checks to make sure move is in border
                    if (x + 2 <= MAX_BORDER && y + 2 <= MAX_BORDER) {
                        String[] captureMoveKeys = {rightFrontCapture, 
                            currentPosition, rightFrontMove};
                        captureMoves.add((captureMoveKeys));
                    }
                }
            }
            // Check if there are any pieces in leftFrontCapture location
            if (!whitePieces.containsKey(leftFrontCapture) && 
                !blackPieces.containsKey(leftFrontCapture)) {
                if (currentPlayer.containsKey(leftFrontMove)) {
                    // checks to make sure move is in border
                    if (x - 2 >= MIN_BORDER && y + 2 <= MAX_BORDER) {
                        String[] captureMoveKeys = {leftFrontCapture,
                            currentPosition, leftFrontMove};
                        captureMoves.add(captureMoveKeys);
                    }
                }
            }
        }

        // the player is forced to take capture moves first 
        // before they can do regular moves
        if (captureMoves.isEmpty()) {
            // only kings and computer pieces can go backward
            if (king || !human) {
                // checks to make sure move is in border
                if (x + 1 <= MAX_BORDER && y - 1 >= MIN_BORDER) {
                    // Check if there are any pieces in rightBackMove location
                    if (!whitePieces.containsKey(rightBackMove) && 
                        !blackPieces.containsKey(rightBackMove)) {
                        String[] basicMove = {rightBackMove, currentPosition};
                        // add move to possible basic moves
                        basicMoves.add(basicMove);
                    }
                }
                // checks to make sure move is in border
                if (x - 1 >= MIN_BORDER && y - 1 >= MIN_BORDER) {
                    // Check if there are any pieces in leftBackMove location
                    if (!whitePieces.containsKey(leftBackMove) && 
                        !blackPieces.containsKey(leftBackMove)) {
                        String[] simpleMove = {leftBackMove, currentPosition};
                        // add move to possible basic moves
                        basicMoves.add(simpleMove);
                    }
                }

            }
            if (king || human) {
                // checks to make sure move is in border
                if (x + 1 <= MAX_BORDER && y + 1 <= MAX_BORDER) {
                    // Check if there are any pieces in rightFrontCapture location
                    if (!whitePieces.containsKey(rightFrontMove) && 
                        !blackPieces.containsKey(rightFrontMove)) {
                        String[] basicMove = {rightFrontMove, currentPosition};
                        // add move to possible basic moves
                        basicMoves.add(basicMove);
                    }
                }
                // checks to make sure move is in border
                if (x - 1 >= MIN_BORDER && y + 1 <= MAX_BORDER) {
                    // Check if there are any pieces in leftFrontMove location
                    if (!whitePieces.containsKey(leftFrontMove) && 
                        !blackPieces.containsKey(leftFrontMove)) {
                        String[] basicMove = {leftFrontMove, currentPosition};
                        // add move to possible basic moves
                        basicMoves.add(basicMove);
                    }
                }

            }
        }
    }

    /**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    * Method       checkMultipleCapture()
    * Description  check for multiple captures in a row, if possible, save them 
    *              into a HashMap capture key
    * @param       whitePieces Map<String, Piece> 
    * @param       blackPieces Map<String, Piece> 
    * @param       human Boolean
    * @author      Iliya Belyak, Chak Kwan Lai (Regan)
    * Date         3/3/2023
    * History log  3/3/2023
    *~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/  
    public void checkMultipleCapture(Map<String, Piece> whitePieces, 
        Map<String, Piece> blackPieces, boolean human) {

        if (!captureMoves.isEmpty()) {

            for (int i = 0; i < copyCaptureMoves.size(); i++) {

                // get the new, former and removed positions
                String newKey = copyCaptureMoves.get(i)[0];
                String formerKey = copyCaptureMoves.get(i)[1];
                String removeKey = copyCaptureMoves.get(i)[2];

                // save the keys for later multi-step visualisation
                if (!tempCaptureKey.containsKey(i)) {
                    tempCaptureKey.put(i, new ArrayList<>());
                }
                List<String[]> tempKeys = this.tempCaptureKey.get(i);
                String[] keyCombination = {removeKey, newKey};
                tempKeys.add(keyCombination);

                this.tempCaptureKey.put(i, tempKeys);

                // add the position to the board representation
                Piece piece = new Piece();
                if (human) {
                    piece.setKing(whitePieces.get(formerKey).isKing());
                    whitePieces.put(newKey, piece);

                } else {
                    piece.setKing(blackPieces.get(formerKey).isKing());
                    blackPieces.put(newKey, piece);
                }

                captureMoves = new ArrayList<>();
                validMoves(newKey, whitePieces, blackPieces, human);

                // if following capture is possible
                if (!captureMoves.isEmpty()) {
                    copyCaptureMoves.remove(i);
                    String[] key = captureMoves.get(captureMoves.size() - 1);

                    String[] comb = {key[2], key[0]};
                    tempKeys.add(comb);

                    copyCaptureMoves.add(i, key);
                    clearMoves();
                    checkMultipleCapture(whitePieces, blackPieces, human);
                }
            }
            // save all the temporal keys for visualisation in captureKey
            for (int i = 0; i < copyCaptureMoves.size(); i++) {
                List<String[]> blackKeys = tempCaptureKey.get(i);
                String key = copyCaptureMoves.get(i)[0];
                captureTile.put(key, blackKeys);
            }
            tempCaptureKey = new HashMap<>();
        }

    }

    /**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    * Method       getMoves()
    * Description  
    * @param       whitePieces Map<String, Piece> 
    * @param       blackPieces Map<String, Piece> 
    * @param       human Boolean
    * @author      Iliya Belyak, Chak Kwan Lai (Regan)
    * Date         3/3/2023
    * History log  3/3/2023
    *~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/  
    public void getMoves(Map<String, Piece> whitePieces, 
        Map<String, Piece> blackPieces, boolean human) {

        clearMoves();

        Map<String, Piece> pieces;
        if (human) {
            pieces = whitePieces;
        } else {
            pieces = blackPieces;
        }

        for (String currentPlayerKeys : pieces.keySet()) {
            validMoves(currentPlayerKeys, whitePieces, blackPieces, human);
        }
    }

    /**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    * Method       copyList()
    * Description  creates and returns a copy of a list of String arrays
    * @param       list List<String[]>
    * @return      newList List<String[]>
    * @author      Iliya Belyak, Chak Kwan Lai (Regan)
    * Date         3/3/2023
    * History log  3/3/2023
    *~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/ 
    public List<String[]> copyList(List<String[]> list) {

        List<String[]> newList = new ArrayList<String[]>();
        for (String[] moves : list) {
            String[] copiedArray = new String[moves.length];
            System.arraycopy(moves, 0, copiedArray, 0, moves.length);
            newList.add(copiedArray);
        }
        return newList;
    }

    /**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    * Method       copyMap()
    * Description  creates and returns a copy of a map of pieces
    * @param       map Map<String, Piece>
    * @return      copyWhitePiece Map<String, Piece>
    * @author      Iliya Belyak, Chak Kwan Lai (Regan)
    * Date         3/3/2023
    * History log  3/3/2023
    *~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/ 
    public Map<String, Piece> copyMap(Map<String, Piece> map) {

        // create new empty map
        Map<String, Piece> copyWhitePiece = new HashMap<>();

        // add each piece in
        for (String piece : map.keySet()) {
            copyWhitePiece.put(piece, map.get(piece));
        }
        return copyWhitePiece;
    }


    /**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    * Method       getCoordinates()
    * Description  returns the current position in x y coordinates stored in an int array 
    * @param       position String
    * @return      coordinates int[]
    * @author      Iliya Belyak, Chak Kwan Lai (Regan)
    * Date         3/3/2023
    * History log  3/3/2023
    *~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/     
    public int[] getCoordinates(String position) {
        String[] XY = position.split(":");
        int x = Integer.valueOf(XY[0]);
        int y = Integer.valueOf(XY[1]);
        int coordinates[] = new int[2];
        coordinates[0] = x;
        coordinates[1] = y;
        return coordinates;
    }

    /**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
     * Method       getBasicMoves()
     * Return       List<String[]>
     * Description  returns a list of basic moves possible
     * @author      Iliya Belyak, Chak Kwan Lai (Regan)
     * Date         3/3/2023
     * History log  3/3/2023
     *~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/  
    public List<String[]> getBasicMoves() {
        return basicMoves;
    }

    /**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
     * Method       setCaptureMoves()
     * Parameters   List<String[]> captureMoves
     * Description  stores the list of capture moves
     * @author      Iliya Belyak, Chak Kwan Lai (Regan)
     * Date         3/3/2023
     * History log  3/3/2023 	
     *~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/   
    public void setCaptureMoves(List<String[]> captureMoves) {
        this.captureMoves = captureMoves;
    }

    /**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    * Method       getCaptureMoves()
    * Return       List<String[]>
    * Description  returns list of capture moves
    * @author      Iliya Belyak, Chak Kwan Lai (Regan)
    * Date         3/3/2023
    * History log  3/3/2023
    *~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/ 
    public List<String[]> getCaptureMoves() {
        return captureMoves;
    }

    /**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    * Method       clearMoves()
    * Description  clears all possible moves by setting them into new empty arrays
    * @author      Iliya Belyak, Chak Kwan Lai (Regan)
    * Date         3/3/2023
    * History log  3/3/2023
    *~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/ 
    public void clearMoves() {
        basicMoves = new ArrayList<>();
        captureMoves = new ArrayList<>();
    }

    /**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    * Method       createKey()
    * Description  Returns a String in the form of "x:y" to represent a 
    *              button(piece) on the board
    * @param       x int
    * @param       y int 
    * @return      String 
    * @author      Iliya Belyak, Chak Kwan Lai (Regan)
    * Date         3/3/2023
    * History log  3/3/2023
    *~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/     
    private String createKey(int x, int y) {
        return String.valueOf(x) + ":" + String.valueOf(y);
    }

    /**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    * Method       createKey()
    * Description  Returns the capture tiles
    * @return      captureTile Map<String, List<String[]>> 
    * @author      Iliya Belyak, Chak Kwan Lai (Regan)
    * Date         3/3/2023
    * History log  3/3/2023
    *~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/      
    public Map<String, List<String[]>> getCaptureTile() {
        return captureTile;
    }

    /**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    * Method       clearCapture()
    * Description  clear all capture moves by setting into a new empty HashMap
    * @author      Iliya Belyak, Chak Kwan Lai (Regan)
    * Date         3/3/2023
    * History log  3/3/2023
    *~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/    
    public void clearCapture() {
        captureTile = new HashMap<>();
    }

    /**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    * Method       setCopyCaptureMoves()
    * Description  Stores  a copy of the capture moves 
    * @author      Iliya Belyak, Chak Kwan Lai (Regan)
    * Date         3/3/2023
    * History log  3/3/2023
    *~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/     
    public void setCopyCaptureMoves(List<String[]> copyCaptureMoves) {
        this.copyCaptureMoves = copyCaptureMoves;
    }
}