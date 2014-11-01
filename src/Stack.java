import java.awt.Point;

/**
 * Stack Class which keeps track of the stack of the game
 * 
 * @author Sofia Kim
 * 
 */
public class Stack extends Hand implements Movable
{
	public static final int SHIFT = 20;

	/**
	 * Creates a constructor of Stack Class
	 * 
	 * @param x
	 *            the x position of the upper left corner of the Hand
	 * @param y
	 *            the y position of the upper left corner of the Hand
	 */
	public Stack(int x, int y)
	{
		super(x, y);
	}

	/**
	 * Add cards with nicely staggered
	 * 
	 * @param card
	 *            the card which is added to the stack
	 */
	public void addCard(Card card)
	{
		card.setLocation(x, y + SHIFT * hand.size());
		this.height = SHIFT * hand.size() + card.height;
		this.hand.add(card);
	}

	/**
	 * Removes the card from the stack
	 * 
	 * @param index
	 *            of the card which will be removed
	 * @return removes the card from the stack
	 */
	public Card remove(int index)
	{
		if (hand.size() > 1)
			this.height = Card.HEIGHT + (hand.size() - 2) * SHIFT;
		else
			this.height = Card.HEIGHT; // If there is only one card left, the
										// height becomes the same as the card
										// height
		return super.remove(index);
	}

	/**
	 * Checks if the hand can pick up
	 * 
	 * @param point
	 *            the point where the mouse clicked
	 * @return true if the card is able to be picked up or false otherwise
	 */
	public boolean canPickUp(Point point)
	{
		// Find the location where the point is pointing at
		int highest = this.hand.size() - 1;
		while (highest >= 0 && !hand.get(highest).contains(point))
		{
			highest--;
		}

		// If the point is not locating at the card returns false
		if (highest == -1)
			return false;

		// If the card is not face up, it is not possible to pick up
		if (!hand.get(highest).isFaceUp())
			return false;

		// If the card is not the same suit from the bottom cards, it can't pick
		// up
		// Also, if the rank of the card is not in order, it can't pick up
		for (int index = highest; index < hand.size() - 1; index++)
		{
			if (!hand.get(index).isSameSuit(hand.get(index + 1))
					|| !hand.get(index + 1).canPlace(hand.get(index)))
				return false;
		}

		// If all the cases are okay, returns true
		return true;
	}

	/**
	 * Pick up the card or stacks
	 * 
	 * @param point
	 *            the point where the mouse clicked
	 * @return card or stacks to pick up
	 */
	public Movable pickUp(Point point)
	{
		// If cannot pick up, does not change any
		if (!canPickUp(point))
			return null;

		int highest = this.hand.size() - 1;

		// Find the card where the point is located at
		while (!hand.get(highest).contains(point))
		{
			highest--;
		}
		// Finds the card to pick up
		if (highest == this.hand.size() - 1)
		{
			Card cardPickUp = remove(highest);
			return cardPickUp;
		}

		// Finds the stacks to pick up
		else
		{
			Stack stackPickUp = new Stack(hand.get(highest).x,
					hand.get(highest).y);
			for (int index = highest; index < hand.size();)
			{
				stackPickUp.addCard(remove(index));
			}

			return stackPickUp;
		}
	}

	/**
	 * Removes full sequence if all 13 cards are in order
	 * 
	 * @return true if if can remove full sequence and remove it false otherwise
	 */
	public boolean removeFullSequence()
	{
		if (hand.size() < 13)
			return false;

		int inARow = 0;
		int index = hand.size() - 1;

		// Go through all the cards in stack and checks if the sequence has the
		// same suits and in order
		while (inARow < 12)
		{
			if (!hand.get(index).isFaceUp()
					|| !hand.get(index - 1).isSameSuit(hand.get(index))
					|| !hand.get(index).canPlace(hand.get(index - 1)))
				return false;
			inARow++;
			index--;
		}
		// Remove it if it is full sequence
		while (index < hand.size())
		{
			this.remove(index);
		}
		return true;

	}

	/**
	 * Check if the stack can place on stack
	 * 
	 * @param stack
	 *            the stack where this stack will place on
	 * @return true if the stack can place on stack or false otherwise
	 */
	public boolean canPlaceOnStack(Stack stack)
	{
		// If the stack is empty, it can place on stack
		if (stack.hand.isEmpty())
			return true;

		// If the top card of the stack and first card of this stack is in
		// order, it can place on
		if (this.hand.get(0).canPlace(stack.getTopCard()))
			return true;
		return false;
	}

	/**
	 * Place this stack on the stack
	 * 
	 * @param stack
	 *            the stack where this stack will place on
	 */
	public void placeOnStack(Stack stack)
	{

		for (int index = 0; index < this.hand.size(); index++)
			stack.addCard(this.hand.get(index));

	}
}
