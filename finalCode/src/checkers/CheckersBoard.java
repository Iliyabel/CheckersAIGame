package checkers;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.StringTokenizer;

/**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
* Class        CheckersBoard.java
* Description  Class that creates Checkers GUI. This class also adds in a buttonBoard
*              with alternating colors as well as the correct black and white checker pieces.
*              Contains methods that help allow certain pieces to be selectable.
* Project      Checkers Game
* @author      Iliya Belyak, Chak Kwan Lai (Regan)
* @version 	   1.0.0
* Date         3/3/2023
* History log  3/10/2023
*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
public class CheckersBoard{


    // Map that will contain a location(String in x:y form) and piece object
    private Map<String, Piece> whitePieces = new HashMap<>();
    private Map<String, Piece> blackPieces = new HashMap<>();

    // class instance variables
    private JFrame frame;
    private HumanMove humanMove;
    private ComputerAI computerAI;
    private Movement movement;
    private LeaderBoard leaderBoard;

    // BufferImage used for all the different looking pieces
    private BufferedImage whitePiece;
    private BufferedImage whiteKingPiece;
    private BufferedImage blackPiece;
    private BufferedImage blackKingPiece;
    private BufferedImage selectablePiece;
    private BufferedImage selectableKingPiece;
    private BufferedImage selectedPiece;
    private BufferedImage selectedKingPiece;

    // file location for leaderboard
    private String fileName = "src/checkers/leaderBoard.txt";

    //button 2d array where we will add all of the button tiles
    private JButton[][] buttonBoard = new JButton[8][8];
    private JButton[] basicMoveTiles = new JButton[4];
    private JButton[] captureTiles = new JButton[4];

    // colors that will be used for the 2 tile colors as well as green
    private Color brown = new Color(140, 124, 107);
    private Color tan = new Color(238, 226, 211);
    private Color green = new Color(124, 250, 80);

    /**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    * Contructor   CheckersBoard
    * Description  Constructs a new CheckersBoard object and displays GUI
    * @param       humanMove HumanMove
    * @param       computerAI ComputerAI
    * @author      Iliya Belyak, Chak Kwan Lai (Regan)
    * @version 	   1.0.0
    * Date         3/3/2023
    * History log  3/3/2023
    *~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    public CheckersBoard(HumanMove humanMove, ComputerAI computerAI) {
        this.humanMove = humanMove;
        this.computerAI = computerAI;
        this.movement = new Movement();
        this.leaderBoard = new LeaderBoard();
        readLeaderBoard(fileName);
        initGUI();
    }
    /**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    * Method       initGUI()
    * Description  Builds a GUI
    * @author      Iliya Belyak, Chak Kwan Lai (Regan)
    * @version 	   1.0.0
    * Date         3/3/2023
    * History log  3/3/2023
    *~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    public final void initGUI() {
        
        JPanel BoardJPanel = new JPanel(new GridLayout(0, 8));
        BoardJPanel.setBorder(new EmptyBorder(7, 7, 7, 7));

        JFrame frame = new JFrame("Checkers Game");
        this.frame = frame;
        frame.setResizable(false);

        Container contentPane = frame.getContentPane();
        contentPane.add(BoardJPanel, BorderLayout.NORTH);

        JMenuBar menuBar = new JMenuBar();
        setMenus(menuBar, frame);

        frame.setJMenuBar(menuBar);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        frame.setIconImage(Toolkit.getDefaultToolkit().getImage
            ("src/Images/checkers_small.jpg"));

        loadPictures();

        // adds all button tiles
        for (int y = buttonBoard.length - 1; y >= 0; y--) {
            for (int x = 0; x < buttonBoard[y].length; x++) {

                // create a button with correct settings
                JButton b = new JButton();
                b.setOpaque(true);
                b.setActionCommand(String.valueOf(x) + ":" + String.valueOf(y));
                b.setBorderPainted(false);
                b.addActionListener(humanMove);
                buttonBoard[y][x] = b;
                BoardJPanel.add(buttonBoard[y][x]);

                //adjust the color, only brown tiles will have checkers on them
                if ((x % 2 == 1 && y % 2 == 1) || (x % 2 == 0 && y % 2 == 0)) {
                    // set button color to tan
                    b.setBackground(tan);

                } else {
                    b.setBackground(brown);
                    if (y < 3) {
                        // add location onto whitePieces map
                        whitePieces.put(String.valueOf(x) + ":" + 
                            String.valueOf(y), new Piece());
                        // add white image on button
                        buttonBoard[y][x].setIcon(new ImageIcon(whitePiece));
                    }
                    if (y > 4) {
                        // add location onto blackPieces map
                        blackPieces.put(String.valueOf(x) + ":" + 
                            String.valueOf(y), new Piece());
                        // add black image on button
                        buttonBoard[y][x].setIcon(new ImageIcon(blackPiece));
                    }
                }
            }
        }


        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        // call to set movable pieces to different image
        setMovablePieces();
    }

    /**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    * Method       setMenus()
    * Description  Builds a JMenu that holds three JMenu s 
    * @param       menuBar JMenu
    * @param       parent JFrame
    * @author      Iliya Belyak, Chak Kwan Lai (Regan)
    * @version 	   1.0.0
    * Date         3/3/2023
    * History log  3/3/2023
    *~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    private void setMenus(JMenuBar menuBar, JFrame parent) {

        JMenu gameMenu = new JMenu("Game");
        JMenu difficultyMenu = new JMenu("Difficulty");
        JMenu helpMenu = new JMenu("Help");

        JRadioButtonMenuItem easy = new JRadioButtonMenuItem("Easy");
        JRadioButtonMenuItem hard = new JRadioButtonMenuItem("Hard");

        difficultyMenu.add(easy);
        difficultyMenu.add(hard);

        ButtonGroup group = new ButtonGroup();
        group.add(easy);
        group.add(hard);

        JMenuItem newGame = new JMenuItem("New Game");
        JMenuItem leaderBoardJMenuItemJMenuItem = new JMenuItem("Leaderboard");
        JMenuItem quit = new JMenuItem("Quit");
        gameMenu.add(newGame);
        gameMenu.add(leaderBoardJMenuItemJMenuItem);
        gameMenu.add(quit);
        quit.addActionListener(e ->{
            System.exit(0);
        });
        newGame.addActionListener(e -> {
            whitePieces = new HashMap<>();
            blackPieces = new HashMap<>();
            buttonBoard = new JButton[8][8];
            initGUI();
        });
        leaderBoardJMenuItemJMenuItem.addActionListener(e -> {
            showLeaderboard leaderboard = new showLeaderboard(parent, 
                "Leaderboard", true, leaderBoard);
        });

        easy.setSelected(true);

        easy.addActionListener(e -> {
            computerAI.setDepth(ComputerAI.EASY);
        });

        hard.addActionListener(e -> {
            computerAI.setDepth(ComputerAI.HARD);
        });

        JMenuItem newRules = new JMenuItem("Rules");
        newRules.addActionListener(e -> {
            Help help = new Help(parent, "How to play", true);
        });     
        
        JMenuItem youWon = new JMenuItem("You won");
        youWon.addActionListener(e -> {
            YouWon won = new YouWon(parent, "You Won!", true);
            youWon.setVisible(true);
            String text = won.getTextFieldText();
            System.out.println(text);
        });
        
        
        helpMenu.add(newRules);
        menuBar.add(gameMenu);
        menuBar.add(difficultyMenu);
        menuBar.add(helpMenu);

    }

    /**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    * Method       loadPictures()
    * Description  Reads images from file
    * @param       parent JFrame
    * @author      Iliya Belyak, Chak Kwan Lai (Regan)
    * @version 	   1.0.0
    * Date         3/3/2023
    * History log  3/3/2023
    *~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/    
    private void loadPictures() {
        try {
            selectablePiece = ImageIO.read(new 
                File("src/images/selectablePiece.png"));
            whitePiece = ImageIO.read(new 
                File("src/images/whitePiece.png"));
            whiteKingPiece = ImageIO.read(new 
                File("src/images/whiteKingPiece.png"));
            blackPiece = ImageIO.read(new 
                File("src/images/blackPiece.png"));
            blackKingPiece = ImageIO.read(new 
                File("src/images/blackKingPiece.png"));
            selectableKingPiece = ImageIO.read(new 
                File("src/images/selectableKingPiece.png"));
            selectedPiece = ImageIO.read(new 
                File("src/images/selectedPiece.png"));
            selectedKingPiece = ImageIO.read(new 
                File("src/images/selectedKingPiece.png"));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    * Method       readLeaderBoard() 
    * Description  Reads from leaderBoard file and adds to leaderBoard object.
    * @param       fileName String
    * @author      Iliya Belyak, Chak Kwan Lai (Regan) 
    * Date         3/9/2023
    * History Log   
    *~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    public void readLeaderBoard(String fileName) {
        try {
            File file = new File(fileName);
            Scanner fileScanner = new Scanner(file);
            String line = "";
            String name = "";
            int moves = 0;
            // scans in eacch line and adds it to tokenizer
            while (fileScanner.hasNextLine()) {
                line = fileScanner.nextLine();
                StringTokenizer stringTokenizer = new 
                    StringTokenizer(line, ",");
                // loop through all of tokenizer elements and add to leaderboard list
                while (stringTokenizer.hasMoreElements()) {
                    name = stringTokenizer.nextToken();
                    moves = Integer.parseInt(stringTokenizer.
                        nextElement().toString());
                    PlayerNode person = new PlayerNode(name, moves);
                  // add to leaderboard list
                  leaderBoard.add(person);
                }
            }
            fileScanner.close();
        // catch exception if it cant find file
        } catch (FileNotFoundException exp) {
            JOptionPane.showMessageDialog(null, 
                fileName + " does not exist",
                "File Input Error", JOptionPane.WARNING_MESSAGE);
            // exit system
            System.exit(0);
        }
    }

    /**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    * Method       savePlayer() 
    * Description  Clears out file and then class leadboard print method to
    *              replace file.
    * @param       player PlayerNode
    * @author      Iliya Belyak, Chak Kwan Lai (Regan) 
    * Date         3/9/2023
    * History Log   
    *~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    public void savePlayer(PlayerNode player) {
        try {
            // empty file
            FileWriter filePointer = new FileWriter(fileName);
            PrintWriter writeFile = new PrintWriter(filePointer);
            //add leaderboard to file
            writeFile.print(leaderBoard.printLeaderBoard());
            writeFile.close();
        // catch exception if cant write to file
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Unable to write to file",
                  "Write File Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    * Method       setMovablePieces() 
    * Description  Method that goes through whitepiece map and checks if they can move
    *              SetSelectablePieces to change movable pieces to different image.
    * @author      Iliya Belyak, Chak Kwan Lai (Regan) 
    * Date         3/9/2023
    * History Log   
    *~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    public void setMovablePieces() {

        // clears board from selected and selectable pieces. 
        // Also clears green and red tiles
        movement.clearMoves();

        // goes through white pieces map to check and find what pieces can move
        for (String whiteKey : whitePieces.keySet()) {
            movement.validMoves(whiteKey, whitePieces, blackPieces, true);
        }

        // calls lists from movement
        List<String[]> captureMoves = movement.getCaptureMoves();
        List<String[]> basicMoves = movement.getBasicMoves();

        // if player has capture moves available, makes them appear
        if (!captureMoves.isEmpty()) {
            setWhitePieces(captureMoves);
        } else {
            setWhitePieces(basicMoves);
        } // if user has no white pieces, user lost
        if (whitePieces.size() == 0) {
            YouLost lost = new YouLost(getJFrame(), "You Lost", true);
            System.exit(0);
        } // if user has no moves left, they have lost
        if (basicMoves.isEmpty() && captureMoves.isEmpty()) {
            // promps lost jdialogue
            YouLost lost = new YouLost(getJFrame(), "You Lost", true);
            System.exit(0);
        }
    }

    /**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    * Method       loadPictures()
    * Description  Sets movable white pieces with a white border
    * @param       moves Live<String[]>
    * @author      Iliya Belyak, Chak Kwan Lai (Regan)
    * @version 	   1.0.0
    * Date         3/3/2023
    * History log  3/3/2023
    *~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/  
    private void setWhitePieces(List<String[]> moves) {

        clearBoard();

        // loops through the moves list in parameter 
        for (String[] move : moves) {
            String key = move[1];
            //convert String location to x and y ints 
            int[] xy = movement.getCoordinates(key);
            boolean king = whitePieces.get(key).isKing();
            if (king) {
                // set button tile to selectableKingPiece icon with x y coordinates
                buttonBoard[xy[1]][xy[0]].setIcon(new ImageIcon(selectableKingPiece));

            } else {
                // set button tile to selectablePiece icon with x y coordinates
                buttonBoard[xy[1]][xy[0]].setIcon(new ImageIcon(selectablePiece));
            }
        }

    }

    /**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    * Method       clearBoard()
    * Description  Clears green and red tiles. Also clears selected and selectable pieces
    * @author      Iliya Belyak, Chak Kwan Lai (Regan)
    * @version 	   1.0.0
    * Date         3/3/2023
    * History log  3/3/2023
    *~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/  
    private void clearBoard() {
        for (int y = buttonBoard.length - 1; y >= 0; y--) {
            for (int x = 0; x < buttonBoard[y].length; x++) {
                if (!((x % 2 == 1 && y % 2 == 1) || (x % 2 == 0 && y % 2 == 0))) {
                    buttonBoard[y][x].setBackground(brown);

                    String key = x + ":" + y;
                    if (whitePieces.containsKey(key)) {

                        if (whitePieces.get(key).isKing()) {
                            buttonBoard[y][x].setIcon(new ImageIcon(whiteKingPiece));

                        } else {
                            buttonBoard[y][x].setIcon(new ImageIcon(whitePiece));
                        }
                    }
                }
            }
        }
    }

    /**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    * Method       resetGreenTiles()
    * Description  Clears green squares(possible normal moves)
    * @author      Iliya Belyak, Chak Kwan Lai (Regan)
    * @version 	   1.0.0
    * Date         3/3/2023
    * History log  3/3/2023
    *~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/  
    public void resetGreenTiles() {

        movement.clearMoves();

        for (String whiteKey : whitePieces.keySet()) {
            movement.validMoves(whiteKey, whitePieces, blackPieces, true);
        }
        if (!movement.getCaptureMoves().isEmpty()) {
            for (int i = 0; i < basicMoveTiles.length; i++) {
                if (basicMoveTiles[i] != null) {
                    basicMoveTiles[i].setBackground(brown);
                    basicMoveTiles[i] = null;
                }
            }
        }
    }

    /**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    * Method       getBasicMoveTiles()
    * Description  Returns an array of JButton that contains basic move tiles
    * @return      JButton[]
    * @author      Iliya Belyak, Chak Kwan Lai (Regan)
    * @version 	   1.0.0
    * Date         3/3/2023
    * History log  3/3/2023
    *~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/  
    public JButton[] getBasicMoveTiles() {
        return basicMoveTiles;
    }

    /**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    * Method       getCaptureTiles()
    * Description  Returns an array of JButton that contains capture move tiles
    * @return      JButton[]
    * @author      Iliya Belyak, Chak Kwan Lai (Regan)
    * @version 	   1.0.0
    * Date         3/3/2023
    * History log  3/3/2023
    *~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/  
    public JButton[] getCaptureTiles() {
        return captureTiles;
    }

    /**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    * Method       setBasicMoveTiles()
    * Description  Sets the basic move tiles
    * @param       basicMoveTiles JButton[]
    * @author      Iliya Belyak, Chak Kwan Lai (Regan)
    * @version 	   1.0.0
    * Date         3/3/2023
    * History log  3/3/2023
    *~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/      
    public void setBasicMoveTiles(JButton[] basicMoveTiles) {
        this.basicMoveTiles = basicMoveTiles;
    }

    /**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    * Method       setCaptureTiles()
    * Description  Sets the capture move tiles
    * @param       captureTiles JButton[]
    * @author      Iliya Belyak, Chak Kwan Lai (Regan)
    * @version 	   1.0.0
    * Date         3/3/2023
    * History log  3/3/2023
    *~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/   
    public void setCaptureTiles(JButton[] captureTiles) {
        this.captureTiles = captureTiles;
    }

    /**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    * Method       getWhitePieces()
    * Description  Returns a map of all white pieces
    * @return      Map<String, Piece>
    * @author      Iliya Belyak, Chak Kwan Lai (Regan)
    * @version 	   1.0.0
    * Date         3/3/2023
    * History log  3/3/2023
    *~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/     
    public Map<String, Piece> getWhitePieces() {
        return whitePieces;
    }

    /**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    * Method       getBlackPieces()
    * Description  Returns a map of all black pieces
    * @return      Map<String, Piece>
    * @author      Iliya Belyak, Chak Kwan Lai (Regan)
    * @version 	   1.0.0
    * Date         3/3/2023
    * History log  3/3/2023
    *~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/       
    public Map<String, Piece> getBlackPieces() {
        return blackPieces;
    }

    /**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    * Method       getButtonBoard()
    * Description  Returns an array representing the checkers tile
    * @return      JButton[][]
    * @author      Iliya Belyak, Chak Kwan Lai (Regan)
    * @version 	   1.0.0
    * Date         3/3/2023
    * History log  3/3/2023
    *~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/       
    public JButton[][] getButtonBoard() {
        return buttonBoard;
    }

    /**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    * Method       getWhitePiece()
    * Description  Returns an image of a normal whitePiece
    * @return      BufferedImage
    * @author      Iliya Belyak, Chak Kwan Lai (Regan)
    * @version 	   1.0.0
    * Date         3/3/2023
    * History log  3/3/2023
    *~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/     
    public BufferedImage getWhitePiece() {
        return whitePiece;
    }

    /**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    * Method       getWhiteKingPiece()
    * Description  Returns an image of a white king piece
    * @return      BufferedImage
    * @author      Iliya Belyak, Chak Kwan Lai (Regan)
    * @version 	   1.0.0
    * Date         3/3/2023
    * History log  3/3/2023
    *~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/     
    public BufferedImage getWhiteKingPiece() {
        return whiteKingPiece;
    }

    /**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    * Method       getBlackPiece()
    * Description  Returns an image of a black piece
    * @return      BufferedImage
    * @author      Iliya Belyak, Chak Kwan Lai (Regan)
    * @version 	   1.0.0
    * Date         3/3/2023
    * History log  3/3/2023
    *~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/     
    public BufferedImage getBlackPiece() {
        return blackPiece;
    }

    /**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    * Method       getBlackKingPiece()
    * Description  Returns an image of a black king piece
    * @return      BufferedImage
    * @author      Iliya Belyak, Chak Kwan Lai (Regan)
    * @version 	   1.0.0
    * Date         3/3/2023
    * History log  3/3/2023
    *~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/     
    public BufferedImage getBlackKingPiece() {
        return blackKingPiece;
    }

    /**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    * Method       getSelectedPiece()
    * Description  Returns an image of the selected white piece    
    * @return      BufferedImage
    * @author      Iliya Belyak, Chak Kwan Lai (Regan)
    * @version 	   1.0.0
    * Date         3/3/2023
    * History log  3/3/2023
    *~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/     
    public BufferedImage getSelectedPiece() {
        return selectedPiece;
    }

    /**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    * Method       getSelecedKingPiece()
    * Description  Returns an image of the selected white king piece 
    * @return      JButton[][]
    * @author      Iliya Belyak, Chak Kwan Lai (Regan)
    * @version 	   1.0.0
    * Date         3/3/2023
    * History log  3/3/2023
    *~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/     
    public BufferedImage getSelectedKingPiece() {
        return selectedKingPiece;
    }

    /**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    * Method       getNeon_green()
    * Description  Returns the color of the green square that represents possible move squares.   
    * @return      Color
    * @author      Iliya Belyak, Chak Kwan Lai (Regan)
    * @version 	   1.0.0
    * Date         3/3/2023
    * History log  3/3/2023
    *~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/     
    public Color getNeon_green() {
        return green;
    }

    /**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    * Method       getJFrame()
    * Description  Returns the JFrame that was created in the GUI
    * @return      JFrame
    * @author      Iliya Belyak, Chak Kwan Lai (Regan)
    * @version 	   1.0.0
    * Date         3/3/2023
    * History log  3/3/2023
    *~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/     
    public JFrame getJFrame () {
        return frame;
    }

    /**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    * Method       gameWon()
    * Description  Method that opens jdialoge for player to enter name and 
    *              save it to file the closes program.
    * @author      Iliya Belyak, Chak Kwan Lai (Regan)
    * @version 	   1.0.0
    * Date         3/3/2023
    * History log  3/3/2023
    *~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/  
    public void gameWon() {
        JFrame parent = getJFrame();
        YouWon won = new YouWon(parent, "You Won!", true);
        // retrieve player name
        String text = won.getTextFieldText();
        // create playernode with name and total moves
        PlayerNode current = new PlayerNode(text,humanMove.getMoves());
        // removes current player if his name is already in ir
        leaderBoard.remove(current);
        // adds player to list
        leaderBoard.add(current);
        // save leaderboard to file 
        savePlayer(current);
        // exit program
        System.exit(0);
    }

    /**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    * Method       main
    * Description  Create sobjects in other classes and runs the program 
    * @param       args[] String
    * @return      
    * Project      Checkers Game
    * @author      Iliya Belyak, Chak Kwan Lai (Regan)
    * @version 	   1.0.0
    * Date         3/3/2023
    * History log  3/3/2023
    *~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/ 
    // public static void main(String[] args) {

    //     Movement movement = new Movement();
    //     ComputerAI computerAI = new ComputerAI(movement);
    //     HumanMove humanMove = new HumanMove(movement, computerAI);

    //     CheckersBoard board = new CheckersBoard(humanMove, computerAI);
    //     computerAI.setBoard(board);
    //     humanMove.setBoard(board);
    // }
}