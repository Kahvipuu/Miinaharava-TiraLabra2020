package minesweeper.util;

import minesweeper.model.ShadowSquare;

/**
 *
 */
public class TiraList<T> {

    private Object[] list;
    private int nextindex;
    private int startIndex;

    /**
     * creates new list of lenght 8, initial size based on the most used type:
     * count of neighbours
     */
    public TiraList() {
        this.list = new Object[8];
        this.nextindex = 0;
        this.startIndex = 0;
    }

    /**
     * returns first list item; first in first out
     *
     * @return list item
     */
    public T pollFirst() {
        T polled = (T) list[startIndex];
        startIndex++;
        if (startIndex == nextindex) {
            startIndex = 0;
            nextindex = 0;
        }
        return polled;
    }

    /**
     * checks if list is empty
     *
     * @return boolean
     */
    public boolean isEmpty() {
        if (startIndex == nextindex) {
            return true;
        }
        return false;
    }

    /**
     * uses add method, adds object to end of list and resizes as needed
     *
     * @param listItem item to add
     */
    public void addLast(T listItem) {
        add(listItem);
    }

    /**
     * private method, adds object t at the end of the list resizes when needed
     *
     * @param item list object to be added
     */
    public void add(T item) {
        if (nextindex >= list.length) {
            resize();
        }
        list[nextindex] = item;
        nextindex++;
        //debug
        System.out.println("item added and size:" + size());
    }

    /**
     * returns the number of items in the list
     *
     * @return int
     */
    public int size() {
        return nextindex - startIndex;
    }

    /**
     * returns true if list contains the given item
     *
     * @param item item to compare to list items
     * @return boolean
     */
    public boolean contains(T item) {
        int listSize = size();
        //debug
        System.out.println("contains listSize:" + listSize);

        if (listSize == 0) {
            return false;
        }

        if (item.getClass() == ShadowSquare.class) {
            ShadowSquare givenSQ = (ShadowSquare) item;
            for (int i = 0; i < listSize; i++) {
                ShadowSquare listSQ = (ShadowSquare) list[startIndex + i];
                int givenSQX = givenSQ.getX();
                int listSQX = listSQ.getX();
                if (listSQ.getX() == givenSQ.getX() && listSQ.getY() == givenSQ.getY()) {
                    return true;
                }
            }
        }

        for (int i = 0; i < listSize; i++) {
            if (list[i + startIndex].equals(item)) {
                return true;
            }
        }
        return false;
    }

    /**
     * resizes (doubles) list size as needed, or if list object amount is small
     * enough then just rearranges them
     */
    private void resize() {
        int listSize = size();
        System.out.println("resize size:" + listSize);
        if (listSize <= list.length / 2) {
            for (int i = 0; i < listSize; i++) {
                list[i] = list[startIndex + i];
            }
        } else {
            Object[] newList = new Object[this.list.length * 2];
            for (int i = 0; i < listSize; i++) {
                newList[i] = list[i + startIndex];
                System.out.println("After resize, size:" + size() + " / " + list.length);
            }
            this.list = newList;
        }
        nextindex = nextindex - startIndex;
        startIndex = 0;
    }

}
