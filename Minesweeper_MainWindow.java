import java.awt.*;
import java.awt.event.*;
import java.util.concurrent.TimeUnit;

import javax.swing.*;


public class Minesweeper_MainWindow extends JFrame{
	
	/* GAMEPLAY VARIABLES: */
	//minimum 4 by 4 with 15 mines
	private static		int verticalTiles = 9;
	private static			int horizontalTiles = 9;
	private 		int mines = 10;
	private static 	int emptySpacesLeftToReveal; //defined in initializeMineAttributes()
	
	/* MENU BAR: */
	private 		JMenuBar menuBar;
	private				JMenu mnFile;
	private 				JMenuItem mntmNewGame;
	private 				JMenuItem mntmExitGame;
	private 			JMenu mnGame;
	private 				JMenuItem mntmReset;
	private 				JMenuItem mntmGameOptions;
		
	/* TOOLBAR: */
	private 		JPanel toolbar;
	private 			JButton btnNewGame;
	private static		JLabel statusLabel;
	private 			JButton btnReset;

	/* FOR GAME BOARD: */
	private static Mine[][] attributes;
	private static JButton[][] tileset;
	private static JPanel boardPanel;



	/* INITIALIZE THE CONTENTS OF THE FRAME: */
	private void initialize() {
		setTitle("Minesweeper");
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JFrame minesweeperJFrame = this; //used for setLocationRelativeTo for JDialog
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
						initializeMineAttributes();
						getContentPane().add(createGameBoard(boardPanel, verticalTiles, horizontalTiles, mines));
						pack();
						setLocationRelativeTo(null);
						statusLabel.setText("");
						statusLabel.setToolTipText("");
				}});
				//exit game menu item:
				mntmExitGame = new JMenuItem("Exit Game");
				mntmExitGame.setToolTipText("Closes Minesweeper.");
				mntmExitGame.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						System.exit(0);
					}
				});
			/* Game menu: */
			menuBar.add(mnGame = new JMenu("Game"));
				//reset menu item:
				mntmReset = new JMenuItem("Reset");
				mntmReset.setToolTipText("New game (keeps current size).");
				mntmReset.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						initializeMineAttributes();
						createGameBoard(boardPanel, verticalTiles, horizontalTiles, mines);
						pack();
						setLocationRelativeTo(null);
						statusLabel.setText("");
						statusLabel.setToolTipText("");
					}
				});
				//game options menu item:
				mntmGameOptions = new JMenuItem("Game Options");
				mntmGameOptions.setToolTipText("Opens the Game Options dialog.");
				
				
		//add contents to the menus in the menubar:
		mnFile.add(mntmNewGame);
		mnFile.addSeparator();
		mnFile.add(mntmExitGame);
		mnGame.add(mntmReset);
		mnGame.addSeparator();
		mnGame.add(mntmGameOptions);
		
		
		
		/* Create the toolbar: */
		getContentPane().add(toolbar = new JPanel(), BorderLayout.NORTH);
		toolbar.setLayout(new GridLayout(1, 3));
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
					initializeMineAttributes();
					getContentPane().add(createGameBoard(boardPanel, verticalTiles, horizontalTiles, mines));
					pack();
					setLocationRelativeTo(null);
					statusLabel.setText("");
					statusLabel.setToolTipText("");
			}});
			
			//status label:
			statusLabel = new JLabel("");
			
			// reset button:
			btnReset = new JButton("Reset");
			btnReset.setToolTipText("New game (keeps current size).");
			btnReset.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					initializeMineAttributes();
					createGameBoard(boardPanel, verticalTiles, horizontalTiles, mines);
					pack();
					statusLabel.setText("");
					statusLabel.setToolTipText("");
				}
			});
			
			//toolbar.add(new JSeparator());
			toolbar.add(btnNewGame, BorderLayout.LINE_START);
			//toolbar.add(new JSeparator());
			toolbar.add(statusLabel, BorderLayout.CENTER);
			//toolbar.add(new JSeparator());
			toolbar.add(btnReset, BorderLayout.LINE_END);
			//toolbar.add(new JSeparator());
			
			
		//add boardPanel to the form:
		getContentPane().add(boardPanel, BorderLayout.CENTER); 
		
		/* Create default game board: */
		getContentPane().add(boardPanel);
		initializeMineAttributes();
		createGameBoard(boardPanel, verticalTiles, horizontalTiles, mines);
		
		/* Pack and center the window: */
		pack();
		setLocationRelativeTo(null);
	}
	
	private static Component createGameBoard(JPanel boardPanel, int verticalTiles, int horizontalTiles, int mines){
		/* REDUNDANT FOR NEW GAME: */
		boardPanel.removeAll();      
		
		
		/* CREATE THE GAME BOARD AREA: */
		JPanel boardArea = new JPanel(new GridBagLayout());
        
        tileset = new JButton[verticalTiles][horizontalTiles];
        GridBagConstraints c = new GridBagConstraints();
		for(int y = 0; y < tileset.length; y++){
			for(int x = 0; x < tileset[y].length; x++){
				String tempOutputValue = attributes[y][x].getOutputValue();
				JButton tile = new JButton();
				tile.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						tile.setBackground(new Color(250, 250, 250));
						tile.setText(tempOutputValue);
						tile.setEnabled(false);
						if(tempOutputValue == "m"){
							revealBoard(tileset);
							tile.setBackground(Color.RED);
							statusLabel.setText("Game over: you lose!");
							statusLabel.setToolTipText("Game over. You lose!");
						}else{
							if(emptySpacesLeftToReveal == 1){
								emptySpacesLeftToReveal = 0;
								statusLabel.setText("Game over: you win!");
								statusLabel.setToolTipText("Game over. You win!");
							}else{
							emptySpacesLeftToReveal--;
							}
						}
					}
				});
				
				tile.setMargin(new Insets(0, 0, 0, 0));
				tile.setMinimumSize(new Dimension(40, 40));
				tile.setMaximumSize(new Dimension(40, 40));
				tile.setPreferredSize(new Dimension(40, 40));
				tile.setMaximumSize(new Dimension(40, 40));
				tile.setBackground(new Color(200, 200, 200));
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
		
		emptySpacesLeftToReveal = (verticalTiles * horizontalTiles) - mines;
		attributes = new Mine[verticalTiles][horizontalTiles];
		int minesCounter = mines;
		
		for(int y = 0; y < attributes.length; y++){
        	for( int x = 0; x < attributes[y].length; x++){
        		attributes[y][x] = new Mine(false); 
        	}
        }
        
        for(int i = 0; i < minesCounter; i++){
            double xtemp = (Math.random()* attributes[1].length);
            double ytemp = (Math.random()* attributes.length );
            int x = (int)xtemp;
            int y = (int)ytemp;
            
            System.out.print("y = " + (y+1) + ", ");
            System.out.print("x = " + (x+1) + " || ");
            
            if(attributes[y][x].getHasMine() == true){
                i--;
                continue;
            }
            attributes[y][x].setHasMine(true);
        }
        
        System.out.print("\n");
        
        //The '*' in the loop below represents the square currently being evaluated
        for(int y = 0; y < attributes.length; y++){
        	for(int x = 0; x < attributes[y].length; x++){
        		if(x == 0 && y == 0){		//top left
        			// _____________
                    // | * |0,1|   |
                    // |1,0|1,1|   |
                    // |   |   |   |
        			if(attributes[0][0].getHasMine())
                    {
        				continue;
                    }
                    int tempAdj=0;
                    if(attributes[0][1].getHasMine()){ tempAdj++; }
                    if(attributes[1][0].getHasMine()){ tempAdj++; }
                    if(attributes[1][1].getHasMine()){ tempAdj++; }
                    attributes[0][0].setNumAdjacentMines(tempAdj);
                }else if( y == attributes.length-1 && x == 0)		// bottom left
                	{
                	if(attributes[attributes.length-1][0].getHasMine()){
                            continue;
                    }
                    int tempAdj=0;
                    if(attributes[attributes.length-1][0].getHasMine()){
                    	tempAdj++;
                    }
                    if(attributes[attributes.length-1][1].getHasMine()){
                        tempAdj++;
                    }
                    if(attributes[attributes.length-2][1].getHasMine()){
                    	tempAdj++;
                    }
                        attributes[attributes.length-1][0].setNumAdjacentMines(tempAdj);
                	}
                    else if( y == attributes.length-1 && x == attributes[y].length-1)// bottom right
                    {
                        if(attributes[attributes.length-1][attributes[y].length-1].getHasMine()){
                            continue;
                        }
                        int tempAdj=0;
                        if(attributes[attributes.length-2][attributes[y].length-1].getHasMine()){
                            tempAdj++;
                        }
                        if(attributes[attributes.length-1][attributes[y].length-2].getHasMine()){
                            tempAdj++;
                        }
                        if(attributes[attributes.length-2][attributes[y].length-2].getHasMine()){
                            tempAdj++;
                        }
                        attributes[attributes.length-1][attributes[y].length-1].setNumAdjacentMines(tempAdj);
                    }
                    else if( y == 0 && x == attributes[y].length-1) //top right
                    {
                        if(attributes[0][attributes[y].length-1].getHasMine()){
                            continue;
                        }
                        int tempAdj=0;
                        if(attributes[0][attributes[y].length-2].getHasMine()){
                            tempAdj++;
                        }
                        if(attributes[1][attributes[y].length-1].getHasMine()){
                            tempAdj++;
                        }
                        if(attributes[1][attributes[y].length-2].getHasMine()){
                            tempAdj++;
                        }
                        attributes[0][x].setNumAdjacentMines(tempAdj);
                    }
                    else if(y == 0)// top row
                    {
                        if(attributes[0][x].getHasMine()){
                            continue;
                        }
                        int tempAdj=0;
                        if(attributes[y][x-1].getHasMine()){
                            tempAdj++;
                        }
                        if(x != attributes[y].length -1){
                        	if(attributes[y][x+1].getHasMine()){
                        		tempAdj++;
                        	}
                    	}
                        if(attributes[y+1][x].getHasMine()){
                            tempAdj++;
                        }
                        if(attributes[y+1][x-1].getHasMine()){
                            tempAdj++;
                        }
                        if(x != attributes[y].length-1){
                        	if(attributes[y+1][x+1].getHasMine()){
                            	tempAdj++;
                        	}
                    	}
                        attributes[y][x].setNumAdjacentMines(tempAdj);
                    }
                    else if(x == 0)//left column
                    {
                        if(attributes[y][0].getHasMine()){
                            continue;
                        }
                        int tempAdj = 0;
                        if(attributes[y+1][x].getHasMine())/*beneath of current square*/{
                            tempAdj++;
                        }
                        if(attributes[y][x+1].getHasMine())/* right of current square*/{
                            tempAdj++;
                        }
                        if(attributes[y+1][x+1].getHasMine())/* South East diagonal of current square*/{
                            tempAdj++;
                        }
                        if(attributes[y-1][x].getHasMine()){ /* Directly above current square*/
                            tempAdj++;
                        }
                        if(attributes[y-1][x+1].getHasMine()){ // NE Diagonal of current square
                            tempAdj++;
                        }
                        attributes[y][x].setNumAdjacentMines(tempAdj);
                    }
                    else if(x == attributes[y].length)// right column
                    {
                        if(attributes[y][x].getHasMine()){
                            continue;
                        }
                        int tempAdj = 0;
                        if(attributes[y+1][x].getHasMine())/*beneath current square*/{
                            tempAdj++;
                        }
                        if(attributes[y][x-1].getHasMine())/* left of current square*/{
                            tempAdj++;
                        }
                        if(attributes[y+1][x-1].getHasMine())/* SW diagonal current square*/{
                            tempAdj++;
                        }
                        if(attributes[y-1][x].getHasMine()){ // directly above current square
                            tempAdj++;
                        }
                        if(attributes[y-1][x-1].getHasMine()){ // NW of current square
                            tempAdj++;
                        }
                        attributes[y][x].setNumAdjacentMines(tempAdj);
                    }
                    else if(y == attributes.length)//bottom row
                    {
                        if(attributes[y][x].getHasMine()){
                            continue;
                        }
                        int tempAdj=0;
                        if(attributes[y][x+1].getHasMine())/* right of bottom right*/{
                            tempAdj++;
                        }
                        if(attributes[y-1][x].getHasMine())/* top of bottom right*/{
                            tempAdj++;
                        }
                        if(attributes[y][x-1].getHasMine()){ // directly left of current square.
                            tempAdj++;
                        }
                        if(attributes[y-1][x-1].getHasMine()){ // NW of current square
                            tempAdj++;
                        }
                        if(attributes[y-1][x+1].getHasMine()){ //NE of current square
                            tempAdj++;
                        }
                        attributes[y][x].setNumAdjacentMines(tempAdj);
                    }
                    else
                    {
                        if(attributes[y][x].getHasMine()){
                            continue;
                        }
                        int tempAdj = 0;
                        
                        if(attributes[y-1][x].getHasMine()){ // top of bottom right
                            tempAdj++;
                        }
                        if(attributes[y][x-1].getHasMine()){ // directly left of current square.
                            tempAdj++;
                        }
                        if(x != attributes[y].length-1){
                        	if(attributes[y][x+1].getHasMine()){ // directly right of current square.
                        		tempAdj++;
                        	}
                    	}
                        if(attributes[y-1][x-1].getHasMine()){ // NW of current square
                            tempAdj++;
                        }
                        if(y != 0){
                        	if((x+1) != attributes[y].length){
                        		if(attributes[y-1][x+1].getHasMine()){ // NE of current square
                        			tempAdj++;
                        }}}
                        if(x != 0 && y != attributes.length-1){
                        	if(attributes[y+1][x-1].getHasMine()){ //West of current square
                        		tempAdj++;
                        	}
                    	}
                        if(y != attributes.length-1){
                        	if(attributes[y+1][x].getHasMine()){  //South of current square
                        		tempAdj++;
                        }}
                        if(y != attributes.length-1){
                        	if(x != attributes[y].length-1){
                        		if(attributes[y+1][x+1].getHasMine()){ // SE of current square
                        			tempAdj++;
                        		}
                        	}
                        }
                        attributes[y][x].setNumAdjacentMines(tempAdj);
                    }  
                }
            }	
	}	
	
	public static void revealBoard(JButton[][] tileset){
		for(int y = 0; y < tileset.length; y++){
			for(int x = 0; x < tileset[y].length; x++){
				tileset[y][x].setBackground(new Color(250, 250, 250));
				tileset[y][x].setText(attributes[y][x].getOutputValue());
				tileset[y][x].setEnabled(false);
		}}
	}
	
	/* ~~~ LAUNCH THE APPLICATION: ~~~ */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Minesweeper_MainWindow window = new Minesweeper_MainWindow();
					window.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
	}}});}
	/* Create the intialize the application window: */
	private Minesweeper_MainWindow() {
		initialize();
	}
}