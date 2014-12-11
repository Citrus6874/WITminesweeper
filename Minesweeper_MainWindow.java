import java.awt.*;
import java.awt.event.*;
import javax.swing.*;


public class Minesweeper_MainWindow extends JFrame{
    
    private static final long serialVersionUID = 1L;
	
    private static Square[][] attributes;
    private static JButton[][] tileset;
    private static JPanel boardPanel;
    
    private static int verticalTiles = 9;
    private static int horizontalTiles = 9;
    private static int mines = 10;
    private static int tileSize = 20;
    private static int emptyTilesLeftToReveal; // defined in intitializeMineAttributes()
    private static boolean gameInProgress = false; //if the user reveals all empty tiles or clicks a mine, this variable is false;
    
    private JMenuBar menuBar;
    private JMenu mnFile;
    private JMenuItem mntmNewGame;
    private JMenuItem mntmExitGame;
    private JMenu mnGame;
    private JMenuItem mntmRestart;
    private JMenuItem mntmGameOptions;
    
    private JPanel toolbar;
    private JButton btnNewGame;
    private JButton btnReset;
    private Utility x = new Utility();

    



    /* INITIALIZE THE CONTENTS OF THE FRAME: */
    private void initializeGame() {
        setTitle("Minesweeper");
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        final JFrame minesweeperJFrame = this; //used for setLocationRelativeTo for JDialog
        boardPanel = new JPanel();
        
        setJMenuBar(menuBar = new JMenuBar());
            /* File menu: */
            menuBar.add(mnFile = new JMenu("File"));
                //new game menu item:
                mntmNewGame = new JMenuItem("New Game");
                mntmNewGame.setToolTipText("Opens the New Game dialog.");
                mntmNewGame.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        NewGameDialog dlg = new NewGameDialog(null, true, verticalTiles, horizontalTiles, mines);
                        dlg.setLocationRelativeTo(minesweeperJFrame);
                        dlg.setVisible(true);
                        verticalTiles = dlg.getVerticalTiles();
                        horizontalTiles = dlg.getHorizontalTiles();
                        mines = dlg.getMines();
                        gameInProgress = true;
                    	emptyTilesLeftToReveal = (verticalTiles * horizontalTiles) - mines;
                        attributes = x.initializeMineAttributes(attributes, verticalTiles, horizontalTiles, mines);
                        getContentPane().add(createGameBoard(boardPanel, verticalTiles, horizontalTiles, mines));
                        pack();
                    }});
                //exit game menu item:
                mntmExitGame = new JMenuItem("Exit Game");
                mntmExitGame.setToolTipText("Closes Minesweeper.");
            /* Game menu: */
            menuBar.add(mnGame = new JMenu("Game"));
                //restart menu item:
                mntmRestart = new JMenuItem("Restart");
                mntmRestart.setToolTipText("New game (keeps current size).");
                mntmRestart.addMouseListener(new MouseAdapter() {
                    @Override public void mouseClicked(MouseEvent e) {
                        createGameBoard(boardPanel, verticalTiles, horizontalTiles, mines);
                        pack();
                    }
                });
                //game options menu item:
                mntmGameOptions = new JMenuItem("Game Options");
                mntmGameOptions.setToolTipText("Opens the Game Options dialog.");
                
        /* Add contents to the menus in the menubar: */
        mnFile.add(mntmNewGame);
        mnFile.addSeparator();
        mnFile.add(mntmExitGame);

        mnGame.add(mntmRestart);
        mnGame.addSeparator();
        mnGame.add(mntmGameOptions);
        
        
        
        /* Create the toolbar: */
        getContentPane().add(toolbar = new JPanel(), BorderLayout.NORTH);
            //new game button:
            btnNewGame = new JButton("New Game");
            btnNewGame.setToolTipText("Opens the New Game dialog.");
            btnNewGame.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    NewGameDialog dlg = new NewGameDialog(null, true, verticalTiles, horizontalTiles, mines);
                    dlg.setLocationRelativeTo(minesweeperJFrame);
                    dlg.setVisible(true);
                    verticalTiles = dlg.getVerticalTiles();
                    horizontalTiles = dlg.getHorizontalTiles();
                    mines = dlg.getMines();
                    gameInProgress = true;
                	emptyTilesLeftToReveal = (verticalTiles * horizontalTiles) - mines;
                    attributes = x.initializeMineAttributes(attributes, verticalTiles, horizontalTiles, mines);
                    getContentPane().add(createGameBoard(boardPanel, verticalTiles, horizontalTiles, mines));
                    pack();
                }});
            
            // reset button:
            btnReset = new JButton("Reset");
            btnReset.setToolTipText("New game (keeps current size).");
            btnReset.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                	gameInProgress = true;
                	emptyTilesLeftToReveal = (verticalTiles * horizontalTiles) - mines;
                    attributes = x.initializeMineAttributes(attributes, verticalTiles, horizontalTiles, mines);
                    createGameBoard(boardPanel, verticalTiles, horizontalTiles, mines);
                    pack();
                }
            });

            toolbar.add(new JSeparator());
            toolbar.add(btnNewGame);
            toolbar.add(new JSeparator());
            toolbar.add(btnReset);
            toolbar.add(new JSeparator());
            
            
        //add boardPanel to the form:
        getContentPane().add(boardPanel, BorderLayout.CENTER);
        
        /* Create default game board: */
        getContentPane().add(boardPanel);
        gameInProgress = true;
    	emptyTilesLeftToReveal = (verticalTiles * horizontalTiles) - mines;
        attributes = x.initializeMineAttributes(attributes, verticalTiles, horizontalTiles, mines);
        createGameBoard(boardPanel, verticalTiles, horizontalTiles, mines);
        
        /* Pack and center the window: */
        pack();
        setLocationRelativeTo(null);
    }
    
    private static Component createGameBoard(JPanel boardPanel, int verticalTiles, int horizontalTiles, int mines){
        /* REDUNDANT FOR NEW GAME: */
        boardPanel.removeAll();      
        
        /* CREATE THE GAME BOARD AREA: */// maybe put this stuff in a new file as well. 
        JPanel boardArea = new JPanel(new GridBagLayout());
        
        tileset = new JButton[verticalTiles][horizontalTiles];
        GridBagConstraints c = new GridBagConstraints();
        for(int y = 0; y < tileset.length; y++){
            for(int x = 0; x < tileset[y].length; x++){
                final JButton tile = new JButton();
                tile.setMargin(new Insets(0, 0, 0, 0));
                tile.setMinimumSize(new Dimension(tileSize, tileSize));
                tile.setMaximumSize(new Dimension(tileSize, tileSize));
                tile.setPreferredSize(new Dimension(tileSize, tileSize));
                tile.setMaximumSize(new Dimension(tileSize, tileSize));
                tile.setBackground(new Color(128, 128, 128));
                final int a = y; //final is necessary
                final int b = x;
                /* IF THE TILE CONTAINS A MINE: */
                if(attributes[a][b].getOutputValue().equals("m"))
                {
                    tile.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            tile.setText(attributes[a][b].getOutputValue());
                            tile.setBackground(Color.RED);
                            loseGame(a, b);//x.loseGame(a, b, tileset);
                            tile.setEnabled(false);
                            JOptionPane.showMessageDialog(null, "You clicked on a Mine, game over!",  "You Lose!", JOptionPane.INFORMATION_MESSAGE);
                }});}
                /*IF THE TILE DOES NOT CONTAIN A MINE: */
                if(!attributes[a][b].getOutputValue().equals("m")){
                    tile.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                        	//this tile:
                            tile.setBackground(new Color(200, 200, 200));
                            tile.setText(attributes[a][b].getOutputValue());
                            decrementEmptyTilesLeftToReveal(a, b);
                            tile.setEnabled(false);
                            /* REVEAL ADJACENT TILES: */
                            if(a==0 && b == 0) //TOP-LEFT CORNER
                            {
                                if(!attributes[a][b+1].getOutputValue().equals("m")){ // E of current square
                                    tileset[a][b+1].setBackground(new Color(200, 200, 200));
                                    tileset[a][b+1].setText(attributes[a][b+1].getOutputValue());
                                    decrementEmptyTilesLeftToReveal(a, b+1);
                                    tileset[a][b+1].setEnabled(false);
                                }
                                if(!attributes[a+1][b].getOutputValue().equals("m")){ // S of Current square
                                    tileset[a+1][b].setBackground(new Color(200, 200, 200));
                                    tileset[a+1][b].setText(attributes[a+1][b].getOutputValue());
                                    decrementEmptyTilesLeftToReveal(a+1, b);
                                    tileset[a+1][b].setEnabled(false);
                                }
                                if(!attributes[a+1][b+1].getOutputValue().equals("m")){ // SE of Current square
                                    tileset[a+1][b+1].setBackground(new Color(200, 200, 200));
                                    tileset[a+1][b+1].setText(attributes[a+1][b+1].getOutputValue());
                                    decrementEmptyTilesLeftToReveal(a+1, b+1);
                                    tileset[a+1][b+1].setEnabled(false);
                                }
                            }
                            else if(a==0 && b == returnLength()-1) //TOP-RIGHT CORNER
                            {
                                if(!attributes[a][b-1].getOutputValue().equals("m")){ // W of current square
                                    tileset[a][b-1].setBackground(new Color(200, 200, 200));
                                    tileset[a][b-1].setText(attributes[a][b-1].getOutputValue());
                                    decrementEmptyTilesLeftToReveal(a, b-1);
                                    tileset[a][b-1].setEnabled(false);
                                }
                                if(!attributes[a+1][b].getOutputValue().equals("m")){ // S of Current square
                                    tileset[a+1][b].setBackground(new Color(200, 200, 200));
                                    tileset[a+1][b].setText(attributes[a+1][b].getOutputValue());
                                    decrementEmptyTilesLeftToReveal(a+1, b);
                                    tileset[a+1][b].setEnabled(false);
                                }
                                if(!attributes[a+1][b-1].getOutputValue().equals("m")){ // SW of Current square
                                    tileset[a+1][b-1].setBackground(new Color(200, 200, 200));
                                    tileset[a+1][b-1].setText(attributes[a+1][b-1].getOutputValue());
                                    decrementEmptyTilesLeftToReveal(a+1, b-1);
                                    tileset[a+1][b-1].setEnabled(false);
                                }
                            }
                            else if(a==returnHeight()-1 && b == 0) //BOTTOM-LEFT CORNER
                            {
                                if(!attributes[a][b+1].getOutputValue().equals("m")){ // E of current square
                                    tileset[a][b+1].setBackground(new Color(200, 200, 200));
                                    tileset[a][b+1].setText(attributes[a][b+1].getOutputValue());
                                    decrementEmptyTilesLeftToReveal(a, b+1);
                                    tileset[a][b+1].setEnabled(false);
                                }
                                if(!attributes[a-1][b].getOutputValue().equals("m")){ // N of Current square
                                    tileset[a-1][b].setBackground(new Color(200, 200, 200));
                                    tileset[a-1][b].setText(attributes[a-1][b].getOutputValue());
                                    decrementEmptyTilesLeftToReveal(a-1, b);
                                    tileset[a-1][b].setEnabled(false);
                                }
                                if(!attributes[a-1][b+1].getOutputValue().equals("m")){ // NE of Current square
                                    tileset[a-1][b+1].setBackground(new Color(200, 200, 200));
                                    tileset[a-1][b+1].setText(attributes[a-1][b+1].getOutputValue());
                                    decrementEmptyTilesLeftToReveal(a-1, b+1);
                                    tileset[a-1][b+1].setEnabled(false);
                                }
                            }
                            else if(a==returnHeight()-1 && b == returnLength()-1) //BOTTOM-RIGHT CORNER
                            {
                                if(!attributes[a][b-1].getOutputValue().equals("m")){ // W of current square
                                    tileset[a][b-1].setBackground(new Color(200, 200, 200));
                                    tileset[a][b-1].setText(attributes[a][b-1].getOutputValue());
                                    decrementEmptyTilesLeftToReveal(a, b-1);
                                    tileset[a][b-1].setEnabled(false);
                                }
                                if(!attributes[a-1][b].getOutputValue().equals("m")){ // N of Current square
                                    tileset[a-1][b].setBackground(new Color(200, 200, 200));
                                    tileset[a-1][b].setText(attributes[a-1][b].getOutputValue());
                                    decrementEmptyTilesLeftToReveal(a-1, b);
                                    tileset[a-1][b].setEnabled(false);
                                }
                                if(!attributes[a-1][b-1].getOutputValue().equals("m")){ // NW of Current square
                                    tileset[a-1][b-1].setBackground(new Color(200, 200, 200));
                                    tileset[a-1][b-1].setText(attributes[a-1][b-1].getOutputValue());
                                    decrementEmptyTilesLeftToReveal(a-1, b-1);
                                    tileset[a-1][b-1].setEnabled(false);
                                }
                            }
                            else if(b == 0){ //LEFT COLUMN
                            	if(!attributes[a-1][b].getOutputValue().equals("m")){ // N of Current square
                                    tileset[a-1][b].setBackground(new Color(200, 200, 200));
                                    tileset[a-1][b].setText(attributes[a-1][b].getOutputValue());
                                    decrementEmptyTilesLeftToReveal(a-1, b);
                                    tileset[a-1][b].setEnabled(false);
                                }
                            	if(!attributes[a-1][b+1].getOutputValue().equals("m")){ // NE of Current square
                                    tileset[a-1][b+1].setBackground(new Color(200, 200, 200));
                                    tileset[a-1][b+1].setText(attributes[a-1][b+1].getOutputValue());
                                    decrementEmptyTilesLeftToReveal(a-1, b+1);
                                    tileset[a-1][b+1].setEnabled(false);
                                }
                            	if(!attributes[a][b+1].getOutputValue().equals("m")){ // E of current square
                                    tileset[a][b+1].setBackground(new Color(200, 200, 200));
                                    tileset[a][b+1].setText(attributes[a][b+1].getOutputValue());
                                    decrementEmptyTilesLeftToReveal(a, b+1);
                                    tileset[a][b+1].setEnabled(false);
                                }
                                if(!attributes[a+1][b].getOutputValue().equals("m")){ // S of Current square
                                    tileset[a+1][b].setBackground(new Color(200, 200, 200));
                                    tileset[a+1][b].setText(attributes[a+1][b].getOutputValue());
                                    decrementEmptyTilesLeftToReveal(a+1, b);
                                    tileset[a+1][b].setEnabled(false);
                                }
                                if(!attributes[a+1][b+1].getOutputValue().equals("m")){ // SE of Current square
                                    tileset[a+1][b+1].setBackground(new Color(200, 200, 200));
                                    tileset[a+1][b+1].setText(attributes[a+1][b+1].getOutputValue());
                                    decrementEmptyTilesLeftToReveal(a+1, b+1);
                                    tileset[a+1][b+1].setEnabled(false);
                                }
                            }
                            else if(a == 0){ //TOP ROW
                            	if(!attributes[a][b+1].getOutputValue().equals("m")){ // E of current square
                                    tileset[a][b+1].setBackground(new Color(200, 200, 200));
                                    tileset[a][b+1].setText(attributes[a][b+1].getOutputValue());
                                    decrementEmptyTilesLeftToReveal(a, b+1);
                                    tileset[a][b+1].setEnabled(false);
                                }
                                if(!attributes[a+1][b].getOutputValue().equals("m")){ // S of Current square
                                    tileset[a+1][b].setBackground(new Color(200, 200, 200));
                                    tileset[a+1][b].setText(attributes[a+1][b].getOutputValue());
                                    decrementEmptyTilesLeftToReveal(a+1, b);
                                    tileset[a+1][b].setEnabled(false);
                                }
                                if(!attributes[a+1][b+1].getOutputValue().equals("m")){ // SE of Current square
                                    tileset[a+1][b+1].setBackground(new Color(200, 200, 200));
                                    tileset[a+1][b+1].setText(attributes[a+1][b+1].getOutputValue());
                                    decrementEmptyTilesLeftToReveal(a+1, b+1);
                                    tileset[a+1][b+1].setEnabled(false);
                                }
                                if(!attributes[a+1][b-1].getOutputValue().equals("m")){ // SW of Current square
                                    tileset[a+1][b-1].setBackground(new Color(200, 200, 200));
                                    tileset[a+1][b-1].setText(attributes[a+1][b-1].getOutputValue());
                                    decrementEmptyTilesLeftToReveal(a+1, b-1);
                                    tileset[a+1][b-1].setEnabled(false);
                                }
                                if(!attributes[a][b-1].getOutputValue().equals("m")){ // W of current square
                                    tileset[a][b-1].setBackground(new Color(200, 200, 200));
                                    tileset[a][b-1].setText(attributes[a][b-1].getOutputValue());
                                    decrementEmptyTilesLeftToReveal(a, b-1);
                                    tileset[a][b-1].setEnabled(false);
                                }
                            }
                            else if(b == returnLength()-1){ //RIGHT COLUMN
                            	if(!attributes[a-1][b].getOutputValue().equals("m")){ // N of Current square
                                    tileset[a-1][b].setBackground(new Color(200, 200, 200));
                                    tileset[a-1][b].setText(attributes[a-1][b].getOutputValue());
                                    decrementEmptyTilesLeftToReveal(a-1, b);
                                    tileset[a-1][b].setEnabled(false);
                                }
                            	if(!attributes[a][b-1].getOutputValue().equals("m")){ // W of current square
                                    tileset[a][b-1].setBackground(new Color(200, 200, 200));
                                    tileset[a][b-1].setText(attributes[a][b-1].getOutputValue());
                                    decrementEmptyTilesLeftToReveal(a, b-1);
                                    tileset[a][b-1].setEnabled(false);
                                }
                            	if(!attributes[a-1][b-1].getOutputValue().equals("m")){ // NW of Current square
                                    tileset[a-1][b-1].setBackground(new Color(200, 200, 200));
                                    tileset[a-1][b-1].setText(attributes[a-1][b-1].getOutputValue());
                                    decrementEmptyTilesLeftToReveal(a-1, b-1);
                                    tileset[a-1][b-1].setEnabled(false);
                                }
                            	if(!attributes[a+1][b].getOutputValue().equals("m")){ // S of Current square
                                    tileset[a+1][b].setBackground(new Color(200, 200, 200));
                                    tileset[a+1][b].setText(attributes[a+1][b].getOutputValue());
                                    decrementEmptyTilesLeftToReveal(a+1, b);
                                    tileset[a+1][b].setEnabled(false);
                                }
                                if(!attributes[a+1][b-1].getOutputValue().equals("m")){ // SW of Current square
                                    tileset[a+1][b-1].setBackground(new Color(200, 200, 200));
                                    tileset[a+1][b-1].setText(attributes[a+1][b-1].getOutputValue());
                                    decrementEmptyTilesLeftToReveal(a+1, b-1);
                                    tileset[a+1][b-1].setEnabled(false);
                                }
                            }
                            else if(a == returnHeight()-1){ //BOTTOM ROW
                            	if(!attributes[a-1][b].getOutputValue().equals("m")){ // N of Current square
                                    tileset[a-1][b].setBackground(new Color(200, 200, 200));
                                    tileset[a-1][b].setText(attributes[a-1][b].getOutputValue());
                                    decrementEmptyTilesLeftToReveal(a-1, b);
                                    tileset[a-1][b].setEnabled(false);
                                }
                            	if(!attributes[a][b-1].getOutputValue().equals("m")){ // W of current square
                                    tileset[a][b-1].setBackground(new Color(200, 200, 200));
                                    tileset[a][b-1].setText(attributes[a][b-1].getOutputValue());
                                    decrementEmptyTilesLeftToReveal(a, b-1);
                                    tileset[a][b-1].setEnabled(false);
                                }
                            	if(!attributes[a-1][b-1].getOutputValue().equals("m")){ // NW of Current square
                                    tileset[a-1][b-1].setBackground(new Color(200, 200, 200));
                                    tileset[a-1][b-1].setText(attributes[a-1][b-1].getOutputValue());
                                    decrementEmptyTilesLeftToReveal(a-1, b-1);
                                    tileset[a-1][b-1].setEnabled(false);
                                }
                            	if(!attributes[a-1][b+1].getOutputValue().equals("m")){ // NE of Current square
                                    tileset[a-1][b+1].setBackground(new Color(200, 200, 200));
                                    tileset[a-1][b+1].setText(attributes[a-1][b+1].getOutputValue());
                                    decrementEmptyTilesLeftToReveal(a-1, b+1);
                                    tileset[a-1][b+1].setEnabled(false);
                                }
                            	if(!attributes[a][b+1].getOutputValue().equals("m")){ // E of current square
                                    tileset[a][b+1].setBackground(new Color(200, 200, 200));
                                    tileset[a][b+1].setText(attributes[a][b+1].getOutputValue());
                                    decrementEmptyTilesLeftToReveal(a, b+1);
                                    tileset[a][b+1].setEnabled(false);
                                }
                            }
                            else //INTERNAL SQUARES
                            {
                                if(!attributes[a-1][b-1].getOutputValue().equals("m")){ // NW of current square
                                	tileset[a-1][b-1].setBackground(new Color(200, 200, 200));
                                	tileset[a-1][b-1].setText(attributes[a-1][b-1].getOutputValue());
                                    decrementEmptyTilesLeftToReveal(a-1, b-1);
                                    tileset[a-1][b-1].setEnabled(false);
                                }
                                if(!attributes[a-1][b].getOutputValue().equals("m")){ // North of Current square
                                    tileset[a-1][b].setBackground(new Color(200, 200, 200));
                                    tileset[a-1][b].setText(attributes[a-1][b].getOutputValue());
                                    decrementEmptyTilesLeftToReveal(a-1, b);
                                    tileset[a-1][b].setEnabled(false);
                                }
                                if(!attributes[a-1][b+1].getOutputValue().equals("m")){ // NE of Current square
                                	tileset[a-1][b+1].setBackground(new Color(200, 200, 200));
                                	tileset[a-1][b+1].setText(attributes[a-1][b+1].getOutputValue());
                                    decrementEmptyTilesLeftToReveal(a-1, b+1);
                                    tileset[a-1][b+1].setEnabled(false);
                                }
                                if(!attributes[a][b+1].getOutputValue().equals("m")){ // East of Current square
                                	tileset[a][b+1].setBackground(new Color(200, 200, 200));
                                	tileset[a][b+1].setText(attributes[a][b+1].getOutputValue());
                                    decrementEmptyTilesLeftToReveal(a, b+1);
                                    tileset[a][b+1].setEnabled(false);
                                }
                                if(!attributes[a+1][b+1].getOutputValue().equals("m")){ // SE of Current square
                                	tileset[a+1][b+1].setBackground(new Color(200, 200, 200));
                                	tileset[a+1][b+1].setText(attributes[a+1][b+1].getOutputValue());
                                    decrementEmptyTilesLeftToReveal(a+1, b+1);
                                    tileset[a+1][b+1].setEnabled(false);
                                }
                                if(!attributes[a+1][b].getOutputValue().equals("m")){ // South of Current square
                                	tileset[a+1][b].setBackground(new Color(200, 200, 200));
                                	tileset[a+1][b].setText(attributes[a+1][b].getOutputValue());
                                    decrementEmptyTilesLeftToReveal(a+1, b);
                                    tileset[a+1][b].setEnabled(false);
                                }
                                if(!attributes[a+1][b-1].getOutputValue().equals("m")){ // SW of Current square
                                	tileset[a+1][b-1].setBackground(new Color(200, 200, 200));
                                	tileset[a+1][b-1].setText(attributes[a+1][b-1].getOutputValue());
                                    decrementEmptyTilesLeftToReveal(a+1, b-1);
                                    tileset[a+1][b-1].setEnabled(false);
                                }
                                if(!attributes[a][b-1].getOutputValue().equals("m")){ // West of Current square
                                	tileset[a][b-1].setBackground(new Color(200, 200, 200));
                                	tileset[a][b-1].setText(attributes[a][b-1].getOutputValue());
                                    decrementEmptyTilesLeftToReveal(a, b-1);
                                    tileset[a][b-1].setEnabled(false);
                                }
                            }
                        }
                    });
                }
                
                c.gridx = x;
                c.gridy = y;
                c.gridheight = 1;
                c.gridwidth = 1;
                c.fill = GridBagConstraints.BOTH;
                c.anchor = GridBagConstraints.NORTHWEST;
                c.weightx = 0;
                c.weighty = 0;
                c.ipadx = 0;
                c.ipady = 0;
                tileset[y][x] = tile;
                boardArea.add(tileset[y][x], c);
            }
        }
        
        boardPanel.add(boardArea);
        
        return boardPanel;
    }
    
    public void initializeMineAttributes(){
    	
        
    }    
    
    private static void loseGame(int a, int b){
    	for(int y = 0; y < returnHeight(); y++){
    		for(int x = 0; x < returnLength(); x++){
    			if(attributes[y][x].getHasMine() == true){
    				tileset[y][x].setText(attributes[y][x].getOutputValue());
                    tileset[y][x].setBackground(Color.BLACK);
                    tileset[y][x].setEnabled(false);
    			}
    		}
    	}
    	tileset[a][b].setBackground(Color.RED);
    	gameInProgress = false;
    }
    private static void winGame(){
    	for(int y = 0; y < returnHeight(); y++){
    		for(int x = 0; x < returnLength(); x++){
    			//mine tile:
    			if(attributes[y][x].getHasMine() == true){
    				tileset[y][x].setText(attributes[y][x].getOutputValue());
                    tileset[y][x].setBackground(Color.ORANGE);
                    tileset[y][x].setEnabled(false);
    			}else{ //empty tile:
                    tileset[y][x].setBackground(Color.GREEN);
    			}
    		}
    	}
    	gameInProgress = false;
    	JOptionPane.showMessageDialog(null, "You revealed all blank tiles.",  "You win!", JOptionPane.INFORMATION_MESSAGE);
    }
    private static void decrementEmptyTilesLeftToReveal(int y, int x){
    	System.out.println(emptyTilesLeftToReveal);
    	if(gameInProgress == true){
    		if(tileset[y][x].isEnabled() == true){
    			if(emptyTilesLeftToReveal == 1){
    				emptyTilesLeftToReveal = 0;
    				winGame();
    			}else{
    				emptyTilesLeftToReveal--;
    			}
    		}
    	}else{
    		tileset[y][x].setBackground(Color.GREEN);
    	}
    }
    
    private static int returnLength(){ return horizontalTiles;}
    private static int returnHeight(){ return verticalTiles; } 
    
    /* Create the intialize the application window: */
    public Minesweeper_MainWindow() {initializeGame();}
    
}