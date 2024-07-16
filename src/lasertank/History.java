/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package lasertank;

import java.util.ArrayList;


/**
 *
 * @author Tonaï
 */
public class History {
    private ArrayList<int[]> history = new ArrayList<int[]>();
    private int index=0;

    History() {
        int[] initialize = new int[300];
        for (int i=0; i<10; i++) {
            history.add(initialize);
        }
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public void addHistory(int [] boardImage) {
        history.set(index, boardImage);
        index++;
        if (index==10) {
            index=0;
        }
    }

    public int [] getLastHistory() {
        int[] boardImage = new int[300];

        index--;
        if (index==-1) {
            index=9;
        }
        boardImage = history.get(index);

        return boardImage;
    }
}
