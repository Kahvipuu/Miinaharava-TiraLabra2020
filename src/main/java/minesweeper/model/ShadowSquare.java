package minesweeper.model;

/**
 * Represent a single square on the board, for the tirabot
 */
public class ShadowSquare {

    private boolean resolved;
    private boolean isFlagged;
    private int surroundingMines; //Number of surrounding squares with mines
    private int surroundingFlags;
    private int surroundingNotKnown;
    private int locationX;
    private int locationY;

    /**
     * Generates a new unopened Square with no mines or flag
     *
     * @param x X coordinate
     * @param y Y coordinate
     */
    public ShadowSquare(int x, int y) {
        this.resolved = false;
        this.isFlagged = false;
        this.surroundingMines = 9; // testin how to implement, not known before opening and should be changed only once
        this.surroundingFlags = 0;
        this.surroundingNotKnown = 8;
        this.locationX = x;
        this.locationY = y;
    }

    public void incrementFlags() {
        this.surroundingFlags++;
        this.surroundingNotKnown--;
    }

    public void decreaseNotKnown() {
        this.surroundingNotKnown--;
    }

    /**
     * Get the X coordinate of the Square
     *
     * @return Square's X coordinate
     */
    public int getX() {
        return this.locationX;
    }

    /**
     * Get the Y coordinate of the Square
     *
     * @return Square's Y coordinate
     */
    public int getY() {
        return this.locationY;
    }

    /**
     * Sets the X coordinate of the Square
     *
     * @param x Square's X coordinate
     */
    public void setX(int x) {
        this.locationX = x;
    }

    /**
     * Sets the Y coordinate of the Square
     *
     * @param y Square's Y coordinate
     */
    public void setY(int y) {
        this.locationY = y;
    }

    /**
     * Whether this Square is flagged
     *
     * @return true if this Square has been flagged by the user
     */
    public boolean isFlagged() {
        return this.isFlagged;
    }

    public void toggleFlagged() {
        this.isFlagged = !this.isFlagged;
        this.resolved = !this.resolved;
    }

    /**
     * Number of surrounding Squares that have a mine.
     *
     * @return The number of surrounding Squares that have a mine.
     */
    public int surroundingMines() {
        return this.surroundingMines;
    }

    public int surroundingFlags() {
        return this.surroundingFlags;
    }

    public int surroundingNotKnown() {
        return this.surroundingNotKnown;
    }

    public void setSurroundingMines(int mines) {
        assert (this.surroundingMines == 9 || this.surroundingMines == mines); // testattava !!
        this.surroundingMines = mines;
    }

    /**
     * Whether this Square has been resolved
     *
     * @return true if it is opened already, else false
     */
    public boolean isResolved() {
        return this.resolved;
    }

    /**
     * Text representation for debugging purposes.
     *
     * @return String
     */
    @Override
    public String toString() {
        if (!this.resolved) {
            return "X";
        }

        return "" + this.surroundingMines;
    }
}
