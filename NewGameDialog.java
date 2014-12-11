import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class NewGameDialog extends JDialog {
	
	private static final long serialVersionUID = 1L;
	
	private int verticalTiles;
	private int horizontalTiles;
	private int mines;
	
	private JPanel topPanel;
	private JPanel middlePanel;
	private JPanel bottomPanel;
	
	private JPanel radioPanel;
	private ButtonGroup group;
	private JRadioButton beginnerButton;
	private JRadioButton intermediateButton;
	private JRadioButton expertButton;
	private JRadioButton customButton;
	
	private JTextField verticalTilesField;
	private JTextField horizontalTilesField;
	private JTextField minesField;
	private JButton btnCreateNewGame;
	private JLabel warningLabel;
	
	public int getVerticalTiles(){ return verticalTiles;}
	public int getHorizontalTiles(){ return horizontalTiles;}
	public int getMines(){ return mines;}
	
	public NewGameDialog(JFrame F, boolean B,int verticalTiles_, int horizontalTiles_, int mines_){
		super(F, B);
		verticalTiles = verticalTiles_;
		horizontalTiles = horizontalTiles_;
		mines = mines_;
		createDialog();
	}
	
	public void createDialog() {
		/* Dialog window properties: */
		setUndecorated(true);
		setTitle("New Game");
		setAlwaysOnTop(true);
		setResizable(false);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
		/* Top panel with radio buttons: */
		group = new ButtonGroup();
		radioPanel = new JPanel();
		radioPanel.setLayout(new GridLayout(2, 2));
		getContentPane().add(topPanel = new JPanel(), BorderLayout.NORTH);
			/* Create the radio buttons: */
		   	//Group the radio buttons and add the group to the radioPanel:
		   	group.add(beginnerButton = new JRadioButton("Beginner - 9 tiles by 9 tiles, 10 mines"));
		   		beginnerButton.addActionListener(new ActionListener() {
		   			@Override
					public void actionPerformed(ActionEvent e) {
						verticalTilesField.setText("9");
						horizontalTilesField.setText("9");
						minesField.setText("10");
						verticalTilesField.setEnabled(false);
						horizontalTilesField.setEnabled(false);
						minesField.setEnabled(false);
		   		}});
		   	group.add(intermediateButton = new JRadioButton("Intermediate - 16 tiles by 16 tiles, 40 mines"));
		   		intermediateButton.addActionListener(new ActionListener() {
		   			@Override
		   			public void actionPerformed(ActionEvent e) {
		   				verticalTilesField.setText("16");
						horizontalTilesField.setText("16");
						minesField.setText("40");
						verticalTilesField.setEnabled(false);
						horizontalTilesField.setEnabled(false);
						minesField.setEnabled(false);
				}});
		   	group.add(expertButton = new JRadioButton("Expert - 20 tiles by 16 tiles, 99 mines"));
		   		expertButton.addActionListener(new ActionListener() {
		   			@Override
					public void actionPerformed(ActionEvent e) {
						verticalTilesField.setText("20");
						horizontalTilesField.setText("16");
						minesField.setText("99");
						verticalTilesField.setEnabled(false);
						horizontalTilesField.setEnabled(false);
						minesField.setEnabled(false);
				}});
		   	group.add(customButton = new JRadioButton("Custom"));
		   		customButton.addActionListener(new ActionListener() {
		   			@Override
		   			public void actionPerformed(ActionEvent e) {
		   				verticalTilesField.setEnabled(true);
		   				horizontalTilesField.setEnabled(true);
		   				minesField.setEnabled(true);
		   		}});
		   	beginnerButton.setSelected(true); //set beginnerButton as the default button
		   	radioPanel.add(beginnerButton);
		   	radioPanel.add(expertButton);
		   	radioPanel.add(intermediateButton);
		   	radioPanel.add(customButton);
		//radioPanel is then placed in topPanel
		topPanel.add(radioPanel);
		   	
		/* Create middle panel: */
		getContentPane().add(middlePanel = new JPanel(), BorderLayout.CENTER);
		/* Create custom input area using middle panel: */
		middlePanel.setLayout(new BoxLayout(middlePanel, 0));
		
			middlePanel.add(new JLabel("Vertical Tiles: ")); //vertical label
			middlePanel.add(verticalTilesField = new JTextField(Integer.toString(verticalTiles), 2));
			verticalTilesField.setEnabled(false);
			
			middlePanel.add(new JLabel("Horizontal Tiles: ")); //horizontal label
			middlePanel.add(horizontalTilesField = new JTextField(Integer.toString(horizontalTiles), 2));
			horizontalTilesField.setEnabled(false);
			
			middlePanel.add(new JLabel("Mines: "));
			middlePanel.add(minesField = new JTextField(Integer.toString(mines), 2));
			minesField.setEnabled(false);
		
	
		/* Create bottom panel and add a New Game button to it: */
		getContentPane().add(bottomPanel = new JPanel(), BorderLayout.CENTER);
		bottomPanel.setLayout(new GridLayout(2, 3));
		bottomPanel.add(new JLabel("     "));
		bottomPanel.add(btnCreateNewGame = new JButton("Create New Game"));
		bottomPanel.add(new JLabel("     "));
		bottomPanel.add(new JLabel("     "));
		bottomPanel.add(warningLabel = new JLabel("Select a difficulty mode for minesweeper, or enter custom values."));
		bottomPanel.add(new JLabel("     "));
		/* Set up New Game button: */
			btnCreateNewGame.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					if(isInteger(verticalTilesField.getText()) && isInteger(horizontalTilesField.getText()) && isInteger(minesField.getText())){
						//must be at least 2 by 2 to generate. There is no max size, allowing high resoultion users to max out the display
						if(1 < Integer.parseInt(verticalTilesField.getText()) && 1 < Integer.parseInt(horizontalTilesField.getText()) && 0 < Integer.parseInt(minesField.getText())){
							verticalTiles = Integer.parseInt(verticalTilesField.getText());
							horizontalTiles = Integer.parseInt(horizontalTilesField.getText());
							mines = Integer.parseInt(minesField.getText());
							dispose();
						}else{
							warningLabel.setText("Board must be at least 2 by 2. It must have at least 1 mine");
						}
					}else{
						warningLabel.setText("Custom inputs must be integer values!");
					}
				}
			});
		
		pack();
		setLocationRelativeTo(null);
	}
	
	private boolean isInteger(String inputString) {
	      try {
	         Integer.parseInt(inputString);
	         return true;
	      } catch (NumberFormatException e) {
	         return false;
	      }
	   }
}