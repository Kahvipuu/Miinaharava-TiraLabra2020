package minesweeper.util;

import minesweeper.model.ShadowSquare;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class TiraListTest {

    private TiraList<ShadowSquare> testList;
    private TiraList<ShadowSquare> emptyList;

    @Before
    public void setUp() {
        this.testList = new TiraList<>();
        this.emptyList = new TiraList<>();
        testList.add(new ShadowSquare(1, 1, 8));
        testList.add(new ShadowSquare(2, 2, 8));
        testList.add(new ShadowSquare(2, 1, 8));
        testList.add(new ShadowSquare(1, 2, 8));
    }

    @After
    public void tearDown() {
    }

    @Test
    public void correctListSizeGiven() {
        assertEquals(4, 4);
        assertEquals(4, this.testList.numItemInList());
        assertEquals(4, this.testList.length());
        testList.add(new ShadowSquare(0, 0, 3));
        assertEquals(5, this.testList.numItemInList());
        assertEquals(5, this.testList.length());
    }

    @Test
    public void pollFirstRemovesFirst() {
        assertEquals(4, this.testList.numItemInList());
        ShadowSquare sq2 = testList.get(1);
        ShadowSquare sq = testList.pollFirst();
        assertEquals(3, this.testList.numItemInList());
        assertEquals(sq2, this.testList.pollFirst());
    }

    @Test
    public void notEmptyListReturnsFalse() {
        assertEquals(false, this.testList.isEmpty());
    }

    @Test
    public void emptyListReturnsTrue() {
        assertEquals(true, this.emptyList.isEmpty());
    }

    @Test
    public void addToLastInList() {
        emptyList.add(new ShadowSquare(2, 2, 8));
        ShadowSquare sqTest = new ShadowSquare(1, 1, 5); 
        emptyList.add(sqTest);
        assertEquals(emptyList.get(1), sqTest);
        ShadowSquare sqTest2 = new ShadowSquare(0, 0, 3);
        emptyList.addLast(sqTest2);
        assertEquals(emptyList.get(2), sqTest2);
    }

    @Test
    public void containsReturnsCorrectFalse(){
        ShadowSquare testsq = new ShadowSquare(1, 1, 8);
        assertFalse(emptyList.contains(testsq));
        emptyList.add(testsq);
        ShadowSquare testsq2 = new ShadowSquare(5, 5, 5);
        assertFalse(emptyList.contains(testsq2));
    }

    @Test
    public void containsReturnsCorrectTrue(){
        ShadowSquare testsq = new ShadowSquare(1, 1, 8);
        assertTrue(testList.contains(testsq));
        ShadowSquare testsq2 = new ShadowSquare(5, 5, 5);
        testList.add(testsq2);
        assertTrue(testList.contains(testsq2));
    }
    
    @Test
    public void resizeWorks(){
        testList.add(new ShadowSquare(5, 1, 8));
        testList.add(new ShadowSquare(6, 2, 8));
        testList.add(new ShadowSquare(7, 2, 8));
        testList.add(new ShadowSquare(8, 2, 8));
        testList.add(new ShadowSquare(9, 2, 8));
        assertEquals(16, this.testList.getList().length);
    }
    
}
