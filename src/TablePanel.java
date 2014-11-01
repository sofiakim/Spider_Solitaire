import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class TablePanel extends JPanel
{
	private SpiderDeck myDeck;

	private Stack[] stacks;

	private Movable selectedItem;

	private Stack sourceStack;

	private Point lastPoint;

	// Code for animation
	final static int ANIMATION_FRAMES = 3;

	private boolean animate = true;

	private Card movingCard;

	private static final Point DECK_POS = new Point(700, 400);

	private static final Rectangle DECK_AREA = new Rectangle(700, 400,
			Card.WIDTH * 3, Card.HEIGHT);

	// Keep track of the name and score of the highest scores
	private int score;

	private int numberSuits;

	/**
	 * Find the number of suits which the deck used
	 * 
	 * @param number
	 *            the number of suits that the deck will use
	 */
	public void deckSuit(int number)
	{
		// Set up the deck and stacks
		numberSuits = number;
		myDeck = new SpiderDeck(number, DECK_POS);
	}

	/**
	 * Constructs the constructor of TablePanel
	 */
	public TablePanel()
	{
		// Set up the size and background colour
		setPreferredSize(new Dimension(900, 600));
		this.setBackground(new Color(0, 125, 125));

		// Set up the deck and stacks
		myDeck = new SpiderDeck(1, DECK_POS);
		score = 1000;

		stacks = new Stack[10];
		int xStack = 30;
		int yStack = 30;
		for (int i = 0; i < stacks.length; i++)
		{
			stacks[i] = new Stack(xStack, yStack);
			xStack += 80;
		}

		movingCard = null;
		// Add mouse listeners to the table panel
		this.addMouseListener(new MouseHandler());
		this.addMouseMotionListener(new MouseMotionHandler());
	}

	/**
	 * Starts the new Game
	 */
	public void newGame()
	{
		// Shuffle and re position the Deck
		myDeck.shuffle();
		myDeck.rePositionCards();

		if (numberSuits == 1)
			score = 700;

		if (numberSuits == 2)
			score = 2500;

		if (numberSuits == 4)
			score = 6000;

		// Clear out all of the Stacks
		for (Hand next : stacks)
			next.clear();

		// Deal the next set of Cards to the Stack
		int index = 0;
		while (myDeck.cardsLeft() > 50)
		{
			Card dealtCard = myDeck.dealACard();
			Point pos = new Point(dealtCard.getLocation());
			stacks[index].addCard(dealtCard);
			Point finalPos = new Point(dealtCard.getLocation());
			if (animate)
				moveACard(dealtCard, pos, finalPos);
			index++;
			if (index == stacks.length)
				index = 0;
			if (myDeck.cardsLeft() < 60)
				dealtCard.flip();
		}
		repaint();
	}

	/**
	 * Displays the graphics
	 */
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);

		// Draw the Stacks and Deck
		for (Hand next : stacks)
			next.draw(g);

		myDeck.draw(g);

		// For animation
		if (movingCard != null)
			movingCard.draw(g);

		// Draw selected Stack or Card on top
		if (selectedItem != null)
			selectedItem.draw(g);
	}

	/**
	 * Move a card with animation
	 * 
	 * @param cardToMove
	 *            the card with is to move
	 * @param pos
	 *            the initial position of the card
	 * @param finalPos
	 *            the final position of the card
	 */
	public void moveACard(final Card cardToMove, Point pos, Point finalPos)
	{
		int dx = (finalPos.x - pos.x) / ANIMATION_FRAMES;
		int dy = (finalPos.y - pos.y) / ANIMATION_FRAMES;

		for (int times = 1; times <= ANIMATION_FRAMES; times++)
		{
			pos.x += dx;
			pos.y += dy;
			cardToMove.setLocation(pos.x, pos.y);

			// Update the drawing area
			paintImmediately(0, 0, getWidth(), getHeight());
			delay(30);

		}
		cardToMove.setLocation(finalPos);
	}

	/**
	 * A simple method to delay
	 * 
	 * @param msec
	 *            the time to delay
	 */
	private void delay(int msec)
	{
		try
		{
			Thread.sleep(msec);
		}
		catch (Exception e)
		{
		}
	}

	/**
	 * Inner class to handle mouse events
	 * 
	 */
	private class MouseHandler extends MouseAdapter
	{
		/**
		 * Events that occur when mouse is pressed
		 */
		public void mousePressed(MouseEvent event)
		{
			Point selectedPoint = event.getPoint();

			// Pick up one of cards from a Stack
			for (Stack nextStack : stacks)
				if (nextStack.contains(selectedPoint))
				{
					// Split off this section or pick up a Card
					selectedItem = nextStack.pickUp(selectedPoint);

					// In case our move is not valid, we want to return the
					// Card(s) to where they initially came from
					sourceStack = nextStack;
					lastPoint = selectedPoint;
					repaint();
					return;
				}

			// Deal new Cards from Deck if clicked in bottom corner
			if (DECK_AREA.contains(selectedPoint) && myDeck.cardsLeft() >= 10)
			{
				// Deal 10 new Cards
				for (int index = 0; index < 10; index++)
				{
					Card dealtCard = myDeck.dealACard();
					Point pos = new Point(dealtCard.getLocation());
					stacks[index].addCard(dealtCard);
					Point finalPos = new Point(dealtCard.getLocation());
					if (animate)
						moveACard(dealtCard, pos, finalPos);
					dealtCard.flip();

					// Check if win after dealing a card
					if (isCheckWin())
					{
						score += 1500;

						JOptionPane.showMessageDialog(TablePanel.this,
								"You win!!\n" + "You have got " + score
										+ " scores\n", "Congratulations",
								JOptionPane.INFORMATION_MESSAGE);
					}
				}
			}
			repaint();
		}

		/**
		 * Check if win
		 * 
		 * @return true if the stacks are empty or false otherwise
		 */
		public boolean isCheckWin()
		{
			for (Stack nextStack : stacks)
				if (nextStack.hand.size() > 0)
					return false;
			return true;
		}

		/**
		 * Events that occur when mouse released
		 */
		public void mouseReleased(MouseEvent event)
		{
			// If an item is selected, check out where it was dropped
			if (selectedItem != null)
			{
				// Check to see if we can add this to another stack
				for (Stack nextStack : stacks)
					if (selectedItem.intersects(nextStack)
							&& selectedItem.canPlaceOnStack(nextStack))
					{
						// Place the selected item onto the new Stack
						selectedItem.placeOnStack(nextStack);
						score -= 10;

						// If a full sequence is created, remove it and
						// flip the exposed top card (if there is one)

						if (nextStack.removeFullSequence()
								&& nextStack.hand.size() > 0
								&& !nextStack.getTopCard().isFaceUp())
						{
							nextStack.getTopCard().flip();
							score += 1000;
						}

						// Now that we have successfully placed the selected
						// item
						// Flip the top card of the sourceStack (if there is
						// one)
						Card topCard = sourceStack.getTopCard();
						if (topCard != null && !topCard.isFaceUp())
							topCard.flip();

						selectedItem = null;
						repaint();

						// Check to win
						if (isCheckWin())
						{
							score += 1500;

							JOptionPane.showMessageDialog(TablePanel.this,
									"You win!!\n" + "You have got " + score
											+ " scores\n", "Congratulations",
									JOptionPane.INFORMATION_MESSAGE);
						}

						return;
					}

				// Return to original stack if not valid spot to drop
				selectedItem.placeOnStack(sourceStack);
				selectedItem = null;
				repaint();
			}
		}
	}

	// Inner Class to handle mouse movements
	private class MouseMotionHandler implements MouseMotionListener
	{
		public void mouseMoved(MouseEvent event)
		{
			// Set the cursor to the hand if we are on a card
			Point currentPoint = event.getPoint();
			// Count down, since higher cards are on top
			for (Stack next : stacks)
				if (next.canPickUp(currentPoint))
				{
					setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
					return;
				}

			// Otherwise we just use the default cursor
			setCursor(Cursor.getDefaultCursor());
		}

		public void mouseDragged(MouseEvent event)
		{
			Point currentPoint = event.getPoint();

			if (selectedItem != null)
			{
				// We use the difference between the lastPoint and the
				// currentPoint to drag the Stack/Card so that the position
				// of the mouse on the Stack/Card doesn't matter.
				// i.e. we can drag the card from any point on the card image
				selectedItem.drag(lastPoint, currentPoint);
				lastPoint = currentPoint;
				repaint();
			}
		}
	}
}
