import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

/**
 * SpiderMain Class which is the main program of the game
 * The commented code is for the score, and I will leave it as commented to show my progress
 * @author Sofia Kim
 * @version May 2013
 *
 */
public class SpiderMain extends JFrame
{
	private TablePanel tableArea;
	private ArrayList<Player> topPlayers; 

	/** Creates the constructor of SpiderMain
	 * 
	 */
	public SpiderMain()
	{
		super("Spider Solitaire");
		setResizable(false);

		// Position in the middle of the window
		setLocation(100, 100);

		// Add in an Icon - Ace of Spades
		setIconImage(new ImageIcon("images\\s1.png").getImage());

		// Add the TablePanel to the centre of the Frame
		setLayout(new BorderLayout());
		tableArea = new TablePanel();
		add(tableArea, BorderLayout.CENTER);
		// Add in the menus
		addMenus();
		
//		try 
//		{ 
//		 // Try to open the file and read in the top player information 
//		 // Read the entire ArrayList from a file 
//		 ObjectInputStream fileIn = 
//		new ObjectInputStream(new FileInputStream("topPlayers.txt")); 
//		 topPlayers = (ArrayList<Player>)fileIn.readObject(); 
//		 fileIn.close(); 
//		} 
//		catch (I.E.Exception) // This could include different types of Exceptions 
//		{ 
//		 // If we had trouble reading the file (e.g. it doesn’t exist) or 
//		 // if our file has errors an Exception will be thrown and we can 
//		 // create a new empty list 
//		 topPlayers = new ArrayList<Player>(); 
//		// Write the entire ArrayList to a file 
//		 ObjectOutputStream fileOut = 
//		 new ObjectOutputStream(new FileOutputStream("topPlayers.txt")); 
//		 fileOut.writeObject(topPlayers); 
//		 fileOut.close(); 
//		} 
	}

	/**
	 * Gets the table panel for this game
	 * 
	 * @return the table panel
	 */
	public TablePanel getTable()
	{
		return tableArea;
	}

	/**
	 * Adds the menus to the main frame Includes adding ActionListeners to
	 * respond to menu commands
	 */
	private void addMenus()
	{
		// Creates main menus
		JMenuBar menuBar = new JMenuBar();
		JMenu gameMenu = new JMenu("Game");
		JMenu levelMenu = new JMenu("Level");
		JMenu helpMenu = new JMenu("Help");
		gameMenu.setMnemonic('G');

		// Creates sub menues
		JMenuItem newOption = new JMenuItem("New");
		JMenuItem undo = new JMenuItem("Undo");
		JMenuItem rank = new JMenuItem("Rank");
		JMenuItem newOption1 = new JMenuItem("Beginner (1 suit)");
		JMenuItem newOption2 = new JMenuItem("Intermediate (2 suits)");
		JMenuItem newOption3 = new JMenuItem("Expert (4 suits)");
		JMenuItem rule = new JMenuItem("Rule");
		JMenuItem about = new JMenuItem("About");

		newOption.addActionListener(new ActionListener() {
			/**
			 * Responds to the New Menu choice
			 * 
			 * @param event
			 *            The event that selected this menu option
			 */
			public void actionPerformed(ActionEvent event)
			{
				tableArea.newGame();
			}
		});

		newOption1.addActionListener(new ActionListener() {
			/**
			 * Responds to the New level choice (beginner)
			 * 
			 * @param event
			 *            The event that selected this level option
			 */
			public void actionPerformed(ActionEvent event)
			{
				//Creates one suit deck
				tableArea.deckSuit(1);
				tableArea.newGame();
			}
		});

		newOption2.addActionListener(new ActionListener() {
			/**
			 * Responds to the New level choice (intermediate)
			 * 
			 * @param event
			 *            The event that selected this level option
			 */
			public void actionPerformed(ActionEvent event)
			{
				//Creates two suits deck
				tableArea.deckSuit(2);
				tableArea.newGame();
			}
		});

		newOption3.addActionListener(new ActionListener() {
			/**
			 * Responds to the New level choice (Expert)
			 * 
			 * @param event
			 *            The event that selected this level option
			 */
			public void actionPerformed(ActionEvent event)
			{
				//Creates four suits deck
				tableArea.deckSuit(4);
				tableArea.newGame();
			}
		});

		rule.addActionListener(new ActionListener() {
			/**
			 * Responds to the rule choice
			 * 
			 * @param event
			 *            The event that selected this rule option
			 */
			public void actionPerformed(ActionEvent event)
			{
				JOptionPane
						.showMessageDialog(
								SpiderMain.this,
								"A single card can only be moved to another pile if the card being moved is one less than the card it will be placed on.\n"
										+ "Groups of cards can only be moved if they are all in same suit and are in perfect descending order.\n"
										+ "If you have a complete group of cards in one suit in perfect descending order it can be removed from play.\n"
										+ "Remove all the cards to win the game.\n"
										+ "Enjoy!\n", "Rule",
								JOptionPane.INFORMATION_MESSAGE);
			}
		});

		about.addActionListener(new ActionListener() {
			/**
			 * Responds to the about choice
			 * 
			 * @param event
			 *            The event that selected this about option
			 */
			public void actionPerformed(ActionEvent event)
			{
				JOptionPane.showMessageDialog(SpiderMain.this,
						"Version: May 2013\n" + "by. Sofia Kim\n", "About",
						JOptionPane.INFORMATION_MESSAGE);
			}
		});
		rank.addActionListener(new ActionListener() {
			/**
			 * Responds to the rank choice (I will also leave it as commented)
			 * 
			 * @param event
			 *            The event that selected this about option
			 */
			public void actionPerformed(ActionEvent event)
			{
//				JOptionPane.showMessageDialog(SpiderMain.this,
//						playerData, "Top Scores",
//						JOptionPane.INFORMATION_MESSAGE);
			}
		});

		// Add each sub menus to each main menus
		gameMenu.add(newOption);
		gameMenu.add(undo);
		levelMenu.add(newOption1);
		levelMenu.add(newOption2);
		levelMenu.add(newOption3);
		helpMenu.add(rule);
		helpMenu.add(rank);
		helpMenu.add(about);
		menuBar.add(gameMenu);
		menuBar.add(levelMenu);
		menuBar.add(helpMenu);

		setJMenuBar(menuBar);
	}

	public static void main(String[] args)
	{
		SpiderMain frame = new SpiderMain();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);
		frame.getTable().newGame();
	}

}
