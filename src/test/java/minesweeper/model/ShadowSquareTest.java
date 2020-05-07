package minesweeper.model;

import org.junit.Test;
import org.junit.After;
import org.junit.Before;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ShadowSquareTest {

    private ShadowSquare square8Neighbours;
    private ShadowSquare square5Neighbours;
    private ShadowSquare square3Neighbours;

    @Before
    public void setUp() {
        square8Neighbours = new ShadowSquare(8, 8, 8);
        square5Neighbours = new ShadowSquare(0, 5, 5);
        square3Neighbours = new ShadowSquare(0, 0, 3);
    }

    @After
    public void tearDown() {
// intentionally empty
    }

    @Test
    public void byDefaultSquareIsNotResolved() {
        assertEquals(false, square8Neighbours.isResolved());
    }

    @Test
    public void flaggedSquareIsAlsoResolved() {
        square8Neighbours.setToFlagged();
        assertEquals(true, square8Neighbours.isResolved());
    }

    @Test
    public void squareCanBeFlagged() {
        square8Neighbours.setToFlagged();
        assertTrue(square8Neighbours.isFlagged());
    }

    @Test
    public void squareCanNotBeUnflagged() {
        square8Neighbours.setToFlagged();
        square8Neighbours.setToFlagged();
        assertTrue(square8Neighbours.isFlagged());
    }

    @Test
    public void returnXWhenUnResolved() {
        assertEquals("X", square8Neighbours.toString());
    }

    @Test
    public void getXgivesX() {
        assertEquals(8, square8Neighbours.getX());
    }

    @Test
    public void getYgivesY() {
        assertEquals(8, square8Neighbours.getY());
    }

    @Test
    public void startingNeighboursAreCorrect() {
        assertEquals(8, square8Neighbours.getSurroundingNotKnown());
        assertEquals(5, square5Neighbours.getSurroundingNotKnown());
        assertEquals(3, square3Neighbours.getSurroundingNotKnown());
        assertEquals(5, square5Neighbours.getStartingNeighbours());
        assertEquals(8, square8Neighbours.getStartingNeighbours());
    }

    @Test
    public void incrementingFlagsDecreasesNotKnown() {
        square8Neighbours.incrementSurroundingFlags();
        assertEquals(7, square8Neighbours.getSurroundingNotKnown());
    }

    @Test
    public void numberOfSurroundingMinesCanBeSet() {
        square8Neighbours.setNumberOfSurroundingMines(5);
        assertEquals(5, square8Neighbours.getSurroundingMines());
    }

    @Test
    public void numberOfSurroundingMinesCanNotBeChangedTwice() {
        square8Neighbours.setNumberOfSurroundingMines(5);
        square8Neighbours.setNumberOfSurroundingMines(4);
        assertEquals(5, square8Neighbours.getSurroundingMines());
    }

    @Test
    public void squareCanBeSetResolved() {
        square8Neighbours.setToResolved();
        assertTrue(square8Neighbours.isResolved());
    }

    @Test
    public void flaggingAlsoDecreasesNotKnown() {
        square8Neighbours.incrementSurroundingFlags();
        assertEquals(7, square8Neighbours.getSurroundingNotKnown());
    }

    @Test
    public void addingSurroundingFlagDecreasesNotKnownMines(){
        square5Neighbours.setNumberOfSurroundingMines(3);
        square5Neighbours.incrementSurroundingFlags();
        assertEquals(2, square5Neighbours.getSurroundingUnknownMines());
    }

    @Test
    public void decreaseNotKnown(){
        square5Neighbours.decreaseNotKnown();
        assertEquals(4, this.square5Neighbours.getSurroundingNotKnown());
    }
    
    @Test
    public void setOpened(){
        square5Neighbours.setNumberOfSurroundingMines(2);
        square5Neighbours.setToOpened();
        assertTrue(square5Neighbours.isOpened());
    }
    
}
