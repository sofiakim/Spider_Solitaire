import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;

/**
 * Keeps track of a generic Hand object using an ArrayList of Cards. Includes
 * code to add and remove cards from the Hand, look at the top and bottom cards
 * of this Hand, move the Hand and to draw the Hand. You shouldn't need to
 * change any of this code, however, you may want to uncomment the code in the
 * draw to show the rectangle area of the Hand when debugging your code
 * 
 * @author Ridout
 * @version April 2013
 */
public class Hand extends Rectangle
{
	protected ArrayList<Card> hand;

	/**
	 * Creates a new empty Hand object with it's upper left corner in the given
	 * x and y position
	 * 
	 * @param x
	 *            the x position of the upper left corner of the Hand
	 * @param y
	 *            the y position of the upper left corner of the Hand
	 */
	public Hand(int x, int y)
	{
		super(x, y, Card.WIDTH, Card.HEIGHT);
		hand = new ArrayList<Card>();
	}

	/**
	 * Adds a Card to this Hand. The new position of the added Card is the same
	 * as the Hand's
	 * 
	 * @param card
	 *            the Card to add
	 */
	public void addCard(Card card)
	{
		card.setLocation(x, y);
		this.hand.add(card);
	}

	/**
	 * Removes a Card from this Hand at the given index, shifting all of the
	 * other Cards down
	 * 
	 * @param index
	 *            the index of the Card to remove
	 * @return the removed Card
	 */
	public Card remove(int index)
	{
		return hand.remove(index);
	}

	/**
	 * Removes the top Card from this Hand
	 * 
	 * @return the topCard of this Hand, null if this Hand is emptly
	 */
	public Card removeTopCard()
	{
		if (hand.size() == 0)
			return null;
		return remove(hand.size() - 1);
	}

	/**
	 * Clears this Hand of all of its Cards
	 */
	public void clear()
	{
		hand.clear();
	}

	/**
	 * Returns the number of Cards in this Hand
	 * 
	 * @return the number of Cards in this Hand
	 */
	public int getNoOfCards()
	{
		return hand.size();
	}

	/**
	 * Gets the top Card from this Hand
	 * 
	 * @return the top Card of this Hand, null if this Hand is empty
	 */
	public Card getTopCard()
	{
		if (hand.size() == 0)
			return null;
		return hand.get(hand.size() - 1);
	}

	/**
	 * Gets the bottom Card from this Hand
	 * 
	 * @return the bottom Card of this Hand, null if this Hand is empty
	 */
	public Card getBottomCard()
	{
		if (hand.size() == 0)
			return null;
		return hand.get(0);
	}

	/**
	 * Translates all of the Cards in this Hand by the difference between the
	 * given initial and final positions. Used to move a Hand when dragging a
	 * Hand given the initial and final mouse positions during the drag.
	 * 
	 * @param initialPos
	 *            the initial position
	 * @param finalPos
	 *            the final position
	 */
	public void drag(Point initialPos, Point finalPos)
	{
		translate(finalPos.x - initialPos.x, finalPos.y - initialPos.y);
		for (Card next : hand)
			next.translate(finalPos.x - initialPos.x, finalPos.y - initialPos.y);
	}

	/**
	 * Gives a simple String representation of a Hand
	 * 
	 * @return the Hand as a String (e.g. JH QS 4D AC 9S)
	 */
	public String toString()
	{
		StringBuilder result = new StringBuilder(hand.size() * 3);
		for (Card next : hand)
			result.append(next + " ");
		return result.toString();
	}

	/**
	 * Draws a Hand in its current position in the given Graphics context
	 * 
	 * @param g
	 *            the Graphics context to draw the Hand in
	 */
	public void draw(Graphics g)
	{
		for (Card next : hand)
			next.draw(g);

		// For debugging - draws an outline of this Hand's Rectangle
		 g.setColor(Color.RED);
		 g.draw3DRect(x, y, width, height, true);
	}
}
