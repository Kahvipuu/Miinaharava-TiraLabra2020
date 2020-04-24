package minesweeper.model;

/**
 * Represent a single square on the board, for the tirabot
 */
public class ShadowSquare {

    private boolean resolved;
    private boolean opened;
    private boolean isFlagged;
    private int surroundingMines; //Number of surrounding squares with mines
    private int surroundingUnknownMines;
    private int surroundingFlags;
    private int surroundingNotKnown;
    private int locationX;
    private int locationY;

    /**
     * Generates a new unopened Square with no mines or flag
     *
     * @param x X coordinate
     * @param y Y coordinate
     * @param neighbours is number of surrounding squares
     */
    public ShadowSquare(int x, int y, int neighbours) {
        this.resolved = false;
        this.opened = false;
        this.isFlagged = false;
        this.surroundingMines = 15; // testing how to implement, not known before opening and should be changed only once
        this.surroundingUnknownMines = 15;
        this.surroundingFlags = 0;
        this.surroundingNotKnown = neighbours;
        this.locationX = x;
        this.locationY = y;
    }

    public void incrementSurroundingFlags() {
        this.surroundingFlags++;
        this.surroundingNotKnown--;
        if (surroundingMines != 15){
            surroundingUnknownMines--;
        }
    }

    /**
     * sets number of surrounding mines to square, only changeable once when
     * square is first opened
     *
     * @param mines number of mines surrounding the square
     */
    public void setNumberOfSurroundingMines(int mines) {
        if (this.surroundingMines == 15) {
            this.surroundingMines = mines;
            this.surroundingUnknownMines = mines - surroundingFlags;
        }
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

    /**
     * set square to flagged, not reversable also sets square to resolved
     */
    public void setToFlagged() {
        this.isFlagged = true;
        this.resolved = true;
    }

    /**
     * set square status to opened, not reversable
     */
    public void setToOpened() {
        this.opened = true;
    }

    /**
     * returns boolean of squares opened status
     *
     * @return boolean
     */
    public boolean isOpened() {
        return this.opened;
    }

    /**
     * Number of surrounding Squares that have a mine.
     * Squares "number"
     * @return The number of surrounding Squares that have a mine.
     */
    public int getSurroundingMines() {
        return this.surroundingMines;
    }

    /**
     * Getter for mines that have not been flagged yet
     * @return int of still hidden mines
     */
    public int getSurroundingUnknownMines(){
        return this.surroundingUnknownMines;
    }
    
    /**
     * returns number of flags surrounding the square
     *
     * @return int of surrounding flags
     */
    public int surroundingFlags() {
        return this.surroundingFlags;
    }

    /**
     * returns number of not known neighbours
     *
     * @return int of surrounding squares program has no info of
     */
    public int getSurroundingNotKnown() {
        return this.surroundingNotKnown;
    }

    /**
     * returns number of surrounding mines, starting value set to 15
     *
     * @return int of mines
     */
    public int getNumberOfSurroundingMines() {
        return this.surroundingMines;
    }

    /**
     * Whether this Square has been resolved
     *
     * @return true if it is resolved already, else false
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

    public void setToResolved() {
        this.resolved = true;
    }
}
