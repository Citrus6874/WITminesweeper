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
	
	private JSeparator separator_1;
	private JTextField verticalTilesField;
	private JSeparator separator_2;
	private JTextField horizontalTilesField;
	private JSeparator separator_3;
	private JTextField minesField;
	private JSeparator separator_4;
	
	public int getVerticalTiles(){
		return Integer.parseInt(verticalTilesField.getText());
	}
	public int getHorizontalTiles(){
		return Integer.parseInt(horizontalTilesField.getText());
	}
	public int getMines(){
		return Integer.parseInt(minesField.getText());
	}
	
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
		   	radioPanel.add(intermediateButton);
		   	radioPanel.add(expertButton);
		   	radioPanel.add(customButton);
		//radioPanel is then placed in topPanel
		topPanel.add(radioPanel);
		   	
		/* Middle Panel: */
		getContentPane().add(middlePanel = new JPanel(), BorderLayout.CENTER);
		/* Create custom input area: */
		middlePanel.setLayout(new BoxLayout(middlePanel, 0));
			middlePanel.add(separator_1 = new JSeparator());
			middlePanel.add(new JLabel("Vertical Tiles: ")); //vertical label
			middlePanel.add(verticalTilesField = new JTextField(Integer.toString(verticalTiles), 2));
			verticalTilesField.setEnabled(false);
			//middlePanel.add(separator_2 = new JSeparator());
			middlePanel.add(new JLabel("Horizontal Tiles: ")); //horizontal label
			middlePanel.add(horizontalTilesField = new JTextField(Integer.toString(horizontalTiles), 2));
			horizontalTilesField.setEnabled(false);
			//middlePanel.add(separator_3 = new JSeparator());
			middlePanel.add(new JLabel("Mines: "));
			middlePanel.add(minesField = new JTextField(Integer.toString(mines), 2));
			minesField.setEnabled(false);
		//getContentPane().add(middlePanel);
	
		/* Bottom panel: */
		
		getContentPane().add(bottomPanel = new JPanel(), BorderLayout.SOUTH);
			JButton btnCreateNewGame = new JButton("Create New Game");
			btnCreateNewGame.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					verticalTiles = Integer.parseInt(verticalTilesField.getText());
					horizontalTiles = Integer.parseInt(horizontalTilesField.getText());
					mines = Integer.parseInt(minesField.getText());
					dispose();
				}
			});
		bottomPanel.add(btnCreateNewGame);
		
		pack();
		setLocationRelativeTo(null);
	}	
}