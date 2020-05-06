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
        if (!isEmpty()) {
            T polled = (T) list[startIndex];
            startIndex++;
            if (isEmpty()) {
                startIndex = 0;
                nextindex = 0;
            }
            return polled;
        }
        return null;
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
     * gives length of the list
     *
     * @return length of list as int
     */
    public int length() {
        return nextindex - startIndex;
    }

    /**
     * returns list item with given index, if out of bounds then returns null
     *
     * @param index is list index of a object
     * @return list item
     */
    public T get(int index) {
        if (index < length()) {
            return (T) list[startIndex + index];
        }
        return null;
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
    }

    /**
     * returns the number of items in the list
     *
     * @return int
     */
    public int numItemInList() {
        return nextindex - startIndex;
    }

    /**
     * returns true if list contains the given item
     *
     * @param item item to compare to list items
     * @return boolean
     */
    public boolean contains(T item) {
        int listSize = numItemInList();
        if (listSize == 0) {
            return false;
        }

        if (item.getClass() == ShadowSquare.class) {
            ShadowSquare givenSQ = (ShadowSquare) item;
            for (int i = 0; i < listSize; i++) {
                ShadowSquare listSQ = (ShadowSquare) list[startIndex + i];
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
        int listSize = numItemInList();
        //debug
        //System.out.println("resize: " + nextindex + "/" + list.length + " items:" + listSize);
        if (listSize <= list.length / 2) {
            for (int i = 0; i < listSize; i++) {
                list[i] = list[startIndex + i];
            }
        } else {
            Object[] newList = new Object[this.list.length * 2];
            for (int i = 0; i < listSize; i++) {
                newList[i] = list[i + startIndex];
            }
            this.list = newList;
            //debug
            //System.out.println("After resize:" + nextindex + " / " + list.length + " items:" + numItemInList());
        }
        nextindex = nextindex - startIndex;
        startIndex = 0;
    }

}
