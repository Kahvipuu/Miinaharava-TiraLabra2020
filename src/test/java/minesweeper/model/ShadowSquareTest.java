package minesweeper.model;

import org.junit.Test;
import org.junit.After;
import org.junit.Before;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ShadowSquareTest {

    private ShadowSquare square8neighbours;
    private ShadowSquare square5neighbours;
    private ShadowSquare square3neighbours;

    @Before
    public void setUp() {
        square8neighbours = new ShadowSquare(8, 8, 8);
        square5neighbours = new ShadowSquare(0, 5, 5);
        square3neighbours = new ShadowSquare(0, 0, 3);
    }

    @After
    public void tearDown() {
// intentionally empty
    }

    @Test
    public void byDefaultSquareIsNotResolved() {
        assertEquals(false, square8neighbours.isResolved());
    }

    @Test
    public void flaggedSquareIsAlsoResolved() {
        square8neighbours.setToFlagged();
        assertEquals(true, square8neighbours.isResolved());
    }

    @Test
    public void squareCanBeFlagged() {
        square8neighbours.setToFlagged();
        assertTrue(square8neighbours.isFlagged());
    }

    @Test
    public void squareCanNotBeUnflagged() {
        square8neighbours.setToFlagged();
        square8neighbours.setToFlagged();
        assertTrue(square8neighbours.isFlagged());
    }

    @Test
    public void returnXWhenUnResolved() {
        assertEquals("X", square8neighbours.toString());
    }

    @Test
    public void getXgivesX() {
        assertEquals(8, square8neighbours.getX());
    }

    @Test
    public void getYgivesY() {
        assertEquals(8, square8neighbours.getY());
    }

    @Test
    public void startingNeighboursAreCorrect() {
        assertEquals(8, square8neighbours.surroundingNotKnown());
        assertEquals(5, square5neighbours.surroundingNotKnown());
        assertEquals(3, square3neighbours.surroundingNotKnown());
    }

    @Test
    public void incrementingFlagsDecreasesNotKnown() {
        square8neighbours.incrementSurroundingFlags();
        assertEquals(7, square8neighbours.surroundingNotKnown());
    }

    @Test
    public void numberOfSurroundingMinesCanBeSet() {
        square8neighbours.setNumberOfSurroundingMines(5);
        assertEquals(5, square8neighbours.surroundingMines());
    }

    @Test
    public void numberOfSurroundingMinesCanNotBeChangedTwice() {
        square8neighbours.setNumberOfSurroundingMines(5);
        square8neighbours.setNumberOfSurroundingMines(4);
        assertEquals(5, square8neighbours.surroundingMines());
    }

    @Test
    public void squareCanBeSetResolved() {
        square8neighbours.setToResolved();
        assertTrue(square8neighbours.isResolved());
    }

    @Test
    public void flaggingAlsoDecreasesNotKnown() {
        square8neighbours.incrementSurroundingFlags();
        assertEquals(7, square8neighbours.surroundingNotKnown());
    }

}
