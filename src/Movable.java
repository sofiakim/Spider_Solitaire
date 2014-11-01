import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;

public interface Movable
{
	/**
	 * Translates this Movable object by the difference between the given
	 * initial and final positions. Used to move an Object when dragging it
	 * given the initial and final mouse positions during the drag.
	 * 
	 * @param initialPos
	 *            the initial position
	 * @param finalPos
	 *            the final position
	 */
	public void drag(Point initialPos, Point finalPos);

	/**
	 * Draws this Object in its current position in the given Graphics context
	 * 
	 * @param g
	 *            the Graphics context to draw the Hand in
	 */
	public void draw(Graphics g);

	/**
	 * Checks to see if this object intersects the given Rectangle object
	 * 
	 * @param other
	 *            the Rectangle object to check to see if it intersects with
	 *            this object
	 * @return true if the objects overlap, false if not
	 */
	public boolean intersects(Rectangle other);

	/**
	 * Checks to see if you can legally place this object on the given Stack
	 * 
	 * @param stack
	 *            the Stack that you want to try to place this object on
	 * @return true if it can be legally placed, false if not
	 */
	public boolean canPlaceOnStack(Stack stack);

	/**
	 * Places this object on top of the given Stack
	 * 
	 * @param stack
	 *            the Stack to place this object on
	 */
	public void placeOnStack(Stack stack);
}