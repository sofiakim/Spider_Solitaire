import java.io.Serializable;
import java.util.Comparator;

/**
 * Player Class which keeps track of the scores of the rank Even though I could
 * not complete the feature, I will just submit to show my progress and get a
 * feedback from it
 * 
 * @author Sofia Kim
 * @version May 2013
 * 
 */
public class Player implements Comparable<Player>, Serializable
{
	private String name;

	private int score;

	/**
	 * Constructs a constructor
	 * 
	 * @param playerName
	 *            the name of the player
	 * @param playerScore
	 *            the score of the player
	 */
	public Player(String playerName, int playerScore)
	{
		name = playerName;
		score = playerScore;
	}

	/**
	 * Compares the name of the players
	 * 
	 * @param other
	 *            the other player that is being compared to
	 * @return negative if this name goes first or positive if this name goes
	 *         last and zero if the names are equal
	 */
	public int compareTo(Player other)
	{
		return this.name.compareTo(other.name);
	}

	/**
	 * Converts Player class to string return the string type of Player object
	 */
	public String toString()
	{
		return String.format("%s -> score: %d", name, score);
	}

	/**
	 * An inner Comparator class that compares two Players by their scores
	 */
	private static class ScoreOrder implements Comparator<Player>
	{
		/**
		 * Compares the scores of two Player objects
		 * 
		 * @param first
		 *            the first Player to compare
		 * @param second
		 *            the second Player to compare
		 * @return a value < 0 if the first Player has a lower score, a a value
		 *         > 0 if first Player has a higher score and 0 if the scores of
		 *         the Players are the same
		 */
		public int compare(Player first, Player second)
		{
			if (first.score < second.score)
				return 1;
			else if (first.score > second.score)
				return -1;
			else
				return 0;
		}
	}
}
