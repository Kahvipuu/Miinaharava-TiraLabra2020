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
        assertEquals(4, this.testList.size());
        testList.add(new ShadowSquare(0, 0, 3));
        assertEquals(5, this.testList.size());
    }

    @Test
    public void pollFirstRemovesFirst() {
        assertEquals(4, this.testList.size());
        ShadowSquare sq = testList.pollFirst();
        assertEquals(3, this.testList.size());
    }
}

/*
    public TiraList()
    public T pollFirst() {
    public boolean isEmpty() {
    public void addLast(T listItem) {
    public void add(T t) {
    public int size() {
    public boolean contains(T item) {
    private void resize() {

 */
