import java.awt.Graphics;
import java.awt.Point;

/**
 * SpiderDeck class which keeps track of the spider deck of the game
 * 
 * @author Sofia Kim
 * @version May 2013
 * 
 */
public class SpiderDeck extends Deck
{
	private Point deckPos;

	/**
	 * Creates a constructor of SpiderDeck
	 * 
	 * @param noOfSuits
	 *            number of suits of the deck
	 * @param position
	 *            the position of the spider deck
	 */
	public SpiderDeck(int noOfSuits, Point position)
	{
		deckPos = position;
		deck = new Card[104];
		topCard = 0;

		// Creates SpiderDeck when number suits is one
		if (noOfSuits == 1)
			for (int set = 1; set <= 8; set++)
				for (int rank = 1; rank <= 13; rank++)
					// Creates 13 ranks
					deck[topCard++] = new Card(rank, 4);

		// Creates SpiderDeck when number suits is two
		else if (noOfSuits == 2)
			for (int set = 1; set <= 4; set++)
				for (int suit = 3; suit <= 4; suit++)
					for (int rank = 1; rank <= 13; rank++)
						deck[topCard++] = new Card(rank, suit);

		// Creates SpiderDeck when number suits is four
		else if (noOfSuits == 4)
			for (int set = 1; set <= 2; set++)
				for (int suit = 1; suit <= 4; suit++)
					for (int rank = 1; rank <= 13; rank++)
						deck[topCard++] = new Card(rank, suit);

		rePositionCards();

	}

	/**
	 * Positions the cards in the deck with nicely staggered
	 */
	public void rePositionCards()
	{
		Point position = new Point(deckPos);
		for (int card = 0; card < topCard; card++)
		{
			// Stagger bottom 50 cards into piles of 10
			if (card < 50 && (card) % 10 == 0)
				position.x += 10;
			deck[card].setLocation(position);
		}
	}

	/**
	 * Draw the cards in the game
	 * 
	 * @param g
	 *            the graphics of the deck card
	 */
	public void draw(Graphics g)
	{
		for (int i = 0; i < topCard; i++)
			deck[i].draw(g);
	}
}
