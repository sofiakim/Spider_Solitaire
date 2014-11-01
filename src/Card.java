import java.awt.*;

import javax.swing.*;
/**
 * Card Class which keeps track of the card object
 * @author Sofia Kim
 * @version May 2013
 *
 */
public class Card extends Rectangle implements Movable
{

	// Create private variables for card class
	private static Image backgroundImage = new ImageIcon("images\\blueback.png")
			.getImage();

	public static final int WIDTH = backgroundImage.getWidth(null);

	public static final int HEIGHT = backgroundImage.getHeight(null);

	private int rank; // A -1, 2 - 10, J Q K

	private int suit; // D - 1, C - 2, H - 3, S - 4

	private boolean isFaceUp;

	private Image image;

	/**
	 * Creates the constructor of Card class
	 * 
	 * @param rank
	 *            the rank of the card
	 * @param suit
	 *            the suit of the card
	 */
	public Card(int rank, int suit)
	{
		// Set up the underlining Rectangle
		super(0, 0, WIDTH, HEIGHT);

		// Set the initial values of rank and suit
		this.rank = rank;
		this.suit = suit;
		isFaceUp = false;

		// Load up the appropriate image file for this card
		String imageFile = "images\\" + " dchs".charAt(suit) + rank + ".png";
		image = new ImageIcon(imageFile).getImage();
	}

	/**
	 * Check if the two cards have the same suit
	 * 
	 * @param card
	 *            the card which is being compared
	 * @return true if they have the same suit and false otherwise
	 */
	public boolean isSameSuit(Card card)
	{
		return this.suit == card.suit;
	}

	/**
	 * Check if the card is face up
	 * 
	 * @return true if face up and false otherwise
	 */
	public boolean isFaceUp()
	{
		return isFaceUp;
	}

	/**
	 * Flip the card
	 * 
	 */
	public void flip()
	{
		isFaceUp = !isFaceUp;
	}

	/**
	 * Convert the card class to string
	 * 
	 * @return the string type of this class
	 */
	public String toString()
	{
		return "" + " A23456789TJQK".charAt(rank) + " DCHS".charAt(suit);

	}

	/**
	 * Draws a card in a Graphics context
	 * 
	 * @param g
	 *            Graphics to draw the card in
	 */
	public void draw(Graphics g)
	{
		if (isFaceUp)
			g.drawImage(image, x, y, null);
		else
			g.drawImage(backgroundImage, x, y, null);
	}

	/**
	 * Check if the card can place on the other card
	 * 
	 * @param card
	 *            the card which is being compared to this card
	 * @return true if the card can place on the other card and false otherwise
	 */
	public boolean canPlace(Card card)
	{
		if (this == null || card == null)
			return false;

		// the card can place on to a card when the card is less by one than the
		// card that is being placed
		if (this.rank + 1 == card.rank)
			return true;
		return false;
	}

	/**
	 * Translates this card the difference between the given initial and final
	 * positions.
	 * 
	 * @param initialPos
	 *            the initial position
	 * @param finalPos
	 *            the final position
	 */
	public void drag(Point initialPos, Point finalPos)
	{
		translate(finalPos.x - initialPos.x, finalPos.y - initialPos.y);
	}

	/**
	 * Check if this card can place on the stack
	 * 
	 * @param stack
	 *            the stack which this card will place on
	 * @return true if the card can place on stack or false otherwise
	 */
	public boolean canPlaceOnStack(Stack stack)
	{
		// the card can place onto the stack when it is empty or the top card of
		// the stack is smaller by 1 than the card
		if (stack.hand.isEmpty())
			return true;
		if (this.rank + 1 == stack.getTopCard().rank)
			return true;
		return false;
	}

	/**
	 * Place the card on the stack
	 * 
	 * @param stack
	 *            the stack which this card will place on
	 */
	public void placeOnStack(Stack stack)
	{
		stack.addCard(this);
	}

}
