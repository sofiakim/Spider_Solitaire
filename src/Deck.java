/**
 * Deck Class which keeps track of deck object
 * @author Sofia Kim
 * @version May 2013
 *
 */

public class Deck
{
	protected Card[] deck;

	protected int topCard;

	/**
	 * Constructs a new Deck object
	 */
	public Deck()
	{
		deck = new Card[52];
		topCard = 0;
		for (int suit = 1; suit <= 4; suit++)
			for (int rank = 1; rank <= 13; rank++)
				deck[topCard++] = new Card(rank, suit);

	}

	/**
	 * Deal a card object from the deck
	 * 
	 * @return deck with the left over card
	 */
	public Card dealACard()
	{
		return deck[--topCard];
	}

	/**
	 * Shuffles the given deck by switching the cards inside
	 */
	public void shuffle()
	{
		topCard = deck.length; // Reset topCard
		// Switch the cards
		for (int shuffle = 0; shuffle < topCard; shuffle++)
		{
			int cardIndex = (int) (Math.random() * topCard);
			Card card = deck[cardIndex];
			deck[cardIndex] = deck[shuffle];
			deck[shuffle] = card;
		}

		// Flip all the card in the deck
		for (Card card : deck)
			if (card.isFaceUp())
				card.flip();
	}

	/**
	 * Find the card left from the stack
	 * 
	 * @return the top card of the stack
	 */
	public int cardsLeft()
	{
		return topCard;
	}

	/**
	 * Converts the deck class to string
	 * 
	 * @return the string type of deck class
	 */
	public String toString()
	{
		StringBuilder result = new StringBuilder(topCard * 3);
		for (int i = 0; i < topCard; i++)
			result.append(deck[i].toString() + " ");
		return result.toString();
	}
}
