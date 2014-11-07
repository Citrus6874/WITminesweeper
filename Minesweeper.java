import java.awt.*;
import java.awt.event.*;

import javax.swing.*;


public class Minesweeper implements ActionListener, ItemListener {
	/* Starting game values: */
	private static int tileSize = 50;
	private static int horizontalTiles = 19; //x coordinates
	private static int verticalTiles = 9; //y coordinates
	private static int mines = 10;
	
	private static Container createContentPane(){
		//create the content-pane-to-be:
		JPanel contentPane = new JPanel(new BorderLayout());
		contentPane.setOpaque(true);
		
		return contentPane;
	}
	private static Container createGameBoard(Container contentPane){
		/* REDUNDANT FOR NEW GAME: */
		contentPane.removeAll();

		/* CREATE TOP BUTTON AREA: */
		JPanel toolBar = new JPanel();
		//toolBar.setLayout(new BorderLayout());
		toolBar.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		toolBar.add(Box.createHorizontalGlue());
		JButton newGameButton = new JButton("New Game");
		newGameButton.setAlignmentX(SwingConstants.CENTER);
		//newGameButton.addActionListener(this);
		
		toolBar.add(newGameButton);
		toolBar.add(Box.createRigidArea(new Dimension(100, 0)));
		JButton resetGameButton = new JButton("Reset Current Game");
		//resetGameButton.addActionListener(this);
		toolBar.add(resetGameButton);
		contentPane.add(toolBar, BorderLayout.NORTH);         
        
		/* GENERATE ARRAY OF TILE ATTRIBUTES: */
		int tileAttributes[][] = new int[verticalTiles][horizontalTiles];
		generateTileAttributes(tileAttributes);
		
		
		/* CREATE THE GAME BOARD AREA: */
		JPanel boardArea = new JPanel(new GridBagLayout());
        
        JButton tileSet[][] = new JButton[verticalTiles][horizontalTiles];
        GridBagConstraints c = new GridBagConstraints();
		for(int y = 0; y < tileSet.length; y++){
			for(int x = 0; x < tileSet[y].length; x++){
				JButton tile = new JButton();
				tile.setText(Integer.toString(tileAttributes[y][x])); //remove this, create a separate grid of JPanels for 'revealed' tiles
				tile.setMargin(new Insets(0, 0, 0, 0));
				tile.setMinimumSize(new Dimension(tileSize, tileSize));
				tile.setMaximumSize(new Dimension(tileSize, tileSize));
				tile.setPreferredSize(new Dimension(tileSize, tileSize));
				tile.setMaximumSize(new Dimension(tileSize, tileSize));
				tile.setBackground(new Color(225, 225, 225));
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
				tileSet[y][x] = tile;
				boardArea.add(tileSet[y][x], c);
			}
		}
		
		contentPane.add(boardArea);
		
		return contentPane;
	}
	private static void generateTileAttributes(int[][] tileAttributes){
		/* SET ATTRIBUTES EXAMPLE: */
		for(int y = 0; y< tileAttributes.length; y++){
			for(int x = 0; x< tileAttributes[y].length; x++){
				tileAttributes[y][x] = 1;
			}
		}
	
	}

	private static JMenuBar createMenuBar(){
		JMenuBar menuBar; //main menuBar
		JMenu fileMenu, gameMenu;
		JMenuItem newGameMenuItem, exitGameMenuItem, restartGameMenuItem, settingsGameMenuItem;
		
		//create the menu bar:
		menuBar = new JMenuBar();
		
		//build file menu:
		fileMenu = new JMenu("File");
		fileMenu.setMnemonic(KeyEvent.VK_F);
		menuBar.add(fileMenu);
		//file menu:
		newGameMenuItem = new JMenuItem("New Game",
                KeyEvent.VK_N);
		newGameMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, ActionEvent.ALT_MASK));
		newGameMenuItem.getAccessibleContext().setAccessibleDescription("Start a new game.");
		newGameMenuItem.addActionListener(new ActionListener(){
			@Override public void actionPerformed(ActionEvent e){
				
			}
			
		});
		fileMenu.add(newGameMenuItem);
		fileMenu.addSeparator(); //separate group 1
		exitGameMenuItem = new JMenuItem("Exit Game");
		exitGameMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, ActionEvent.ALT_MASK));
		exitGameMenuItem.getAccessibleContext().setAccessibleDescription("Exit Minesweeper.");
		fileMenu.add(exitGameMenuItem);
		
		//build game menu:
		gameMenu = new JMenu("Game");
		gameMenu.setMnemonic(KeyEvent.VK_G);
		menuBar.add(gameMenu);
		//game menu:
		restartGameMenuItem = new JMenuItem("Restart Game",
                KeyEvent.VK_N);
		restartGameMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R, ActionEvent.ALT_MASK));
		restartGameMenuItem.getAccessibleContext().setAccessibleDescription("Restart current game.");
		gameMenu.add(restartGameMenuItem);
		gameMenu.addSeparator(); //separate group 1
		settingsGameMenuItem = new JMenuItem("Game Settings");
		settingsGameMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.ALT_MASK));
		settingsGameMenuItem.getAccessibleContext().setAccessibleDescription("Current game settings.");
		gameMenu.add(settingsGameMenuItem);
		
		return menuBar;
	}
	private static void createMainWindow(){
		/* CREATE MAIN WINDOW: */
		JFrame mainWindow = new JFrame("Minesweeper");
		//what happens when the window closes:
		mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//mainWindow size properties:
		mainWindow.setResizable(false);
		
		/* CREATE MENU BAR: */
        mainWindow.setJMenuBar(createMenuBar());
        
        /* CREATE CONTENT PANE: */
        mainWindow.setContentPane(createContentPane());
        
        /* CREATE DEFAULT GAME GRID: */
        createGameBoard(mainWindow.getContentPane());
        
        mainWindow.pack();
		mainWindow.setLocationRelativeTo(null); //frame starts in the middle of the screen
		mainWindow.setVisible(true);
	}
	
	public static void main(String[] args){
		//Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
        	public void run() {
        		createMainWindow();
        	}
		});
	}
	
	@Override
	public void itemStateChanged(ItemEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}
}
