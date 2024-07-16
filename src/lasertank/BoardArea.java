/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package lasertank;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JPanel;


/**
 *
 * @author Tonaï
 */
public class BoardArea extends JPanel {
    private BoardBox[][] board = new BoardBox[15][20];

    public BoardArea() {
        for (int i=0; i<15; i++) {
            for (int j=0; j<20; j++) {
                board[i][j]= new BoardBox();
            }
        }
    }

    @Override
    public void paintComponent(Graphics g){
        int i=0, j=0;
        Graphics2D g2d = (Graphics2D)g;
        BufferedImage image;

        g2d.fillRect(0, 0, this.getWidth(), this.getHeight());
        for (BoardBox[] line : board) {
            j=0;
            for (BoardBox place : line) {
                try {
                    if (place.getNumber()==2 || (8<=place.getNumber()  && place.getNumber()<=11) || (37<=place.getNumber()  && place.getNumber()<=40)) {
                        image = ImageIO.read(new File(place.getPath()+place.getSubObjectFile()));
                        g2d.drawImage(image, 29*j, 29*i, 29, 29, this);
                    }
                    image = ImageIO.read(new File(place.getPath()+place.getFile()));
                    g2d.drawImage(image, 29*j, 29*i, 29, 29, this);
                }
                catch (IOException ioe) {
                    ioe.printStackTrace();
                }
                j++;
            }
            i++;
        }
    }

    public void setBoard(BoardBox[][] board) {
        this.board = board;
    }

    public void resetBoard() {
        for (BoardBox[] line : board) {
            for (BoardBox place : line) {
                place.setNumber(0);
            }
        }
        this.repaint();
    }

    public void setBoardImage(int[] boardImage) {
        int i=0;

        for (BoardBox[] line : board) {
            for (BoardBox place : line) {
                place.setNumber(boardImage[i]);
                i++;
            }
        }
        this.repaint();
    }

    public int[] getBoardImage() {
        int[] boardImage = new int[300];
        int i=0;

        for (BoardBox[] line : board) {
            for (BoardBox place : line) {
                boardImage[i] = place.getNumber();
                i++;
            }
        }

        return boardImage;
    }

    public int getNumberBoard(int i, int j) {
        return board[i][j].getNumber();
    }

    public void setNumberBoard(int i, int j, int n) {
        board[i][j].setNumber(n);
    }

    public int getSubObjectNumberBoard(int i, int j) {
        int n = board[i][j].getSubObjectNumber();
        if (n!=2) {
            return board[i][j].getSubObjectNumber();
        }
        return 0;
    }

    public void setSubObjectNumberBoard(int i, int j, int n) {
        board[i][j].setSubObjectNumber(n);
    }

     public int[] getTankPosition() {
        int[] position = new int[2];

        for (int i=0; i<15; i++) {
            for (int j=0; j<20; j++) {
                if (board[i][j].getNumber()==37 || board[i][j].getNumber()==38 || board[i][j].getNumber()==39 || board[i][j].getNumber()==40) {
                    position[0] = i;
                    position[1] = j;
                }
            }
        }
        return position;
    }

    public int[] getInitialPosition() {
        int[] position = new int[2];
        position[0] = -1;

        for (int i=0; i<15; i++) {
            for (int j=0; j<20; j++) {
                if (board[i][j].getNumber()==2) {
                    position[0] = i;
                    position[1] = j;
                }
            }
        }
        if (position[0]!=-1) {
            return position;
        }

        for (int i=0; i<15; i++) {
            for (int j=0; j<20; j++) {
                if (board[i][j].getSubObjectNumber()==2) {
                    position[0] = i;
                    position[1] = j;
                }
            }
        }
        return position;
    }

    public Boolean doesFlagExist() {
        Boolean exist = false;
        for (int i=0; i<15; i++) {
            for (int j=0; j<20; j++) {
                if (board[i][j].getNumber()==3) {
                    exist = true;
                }
            }
        }
        return exist;
    }

    public Boolean doesTankExist() {
        Boolean exist = false;
        for (int i=0; i<15; i++) {
            for (int j=0; j<20; j++) {
                if (board[i][j].getNumber()==2) {
                    exist = true;
                }
            }
        }
        return exist;
    }

    @Override
    public String toString() {
        String output="";
        int n=0;

        for (BoardBox[] ligne : board) {
            for (BoardBox place : ligne) {
                n=place.getNumber();
                output = output+n+",";
            }
        }
        return output;
    }

    
}
