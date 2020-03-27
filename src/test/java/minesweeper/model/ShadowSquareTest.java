package minesweeper.model;

import org.junit.Test;
import org.junit.After;
import org.junit.Before;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ShadowSquareTest {

    private ShadowSquare square;

    @Before
    public void setUp() {
        square = new ShadowSquare(0, 0);
    }

    @After
    public void tearDown() {
// intentionally empty
    }

    @Test
    public void byDefaultSquareIsNotResolved() {
        assertEquals(false, square.isResolved());
    }

    @Test
    public void flaggedSquareIsAlsoResolved() {
        square.toggleFlagged();
        assertEquals(true, square.isResolved());
    }

    @Test
    public void squareCanBeFlagged() {
        square.toggleFlagged();
        assertTrue(square.isFlagged());
    }

    @Test
    public void squareCanBeUnflagged() {
        square.toggleFlagged();
        square.toggleFlagged();
        assertFalse(square.isFlagged());
    }

    @Test
    public void returnXWhenUnResolved() {
        assertEquals("X", square.toString());
    }

    @Test
    public void getXgivesX() {
        assertEquals(0, square.getX());
    }

    @Test
    public void getYgivesY() {
        assertEquals(0, square.getY());
    }

}
