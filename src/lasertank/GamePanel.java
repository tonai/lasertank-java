/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package lasertank;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 *
 * @author Tonaï
 */
public class GamePanel extends JPanel implements ActionListener, KeyListener {
    private JButton resetButton = new JButton(), undoButton = new JButton();
    private BoardArea area = new BoardArea();
    private int tankPosX=0, tankPosY=0, firePosX=0, firePosY=0, fireDirection=0;;
    private String fileName = "";
    private int score=0, undo=0;
    private History history;
    private Graphics g;

    public JButton getResetButton() {
        return resetButton;
    }

    public void setResetButton(JButton resetButton) {
        this.resetButton = resetButton;
    }

    public JButton getUndoButton() {
        return undoButton;
    }

    public void setUndoButton(JButton undoButton) {
        this.undoButton = undoButton;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getTankPosX() {
        return tankPosX;
    }

    public void setTankPosX(int tankPosX) {
        this.tankPosX = tankPosX;
    }

    public int getTankPosY() {
        return tankPosY;
    }

    public void setTankPosY(int tankPosY) {
        this.tankPosY = tankPosY;
    }

    public History getHistory() {
        return history;
    }

    public void setHistory(History history) {
        this.history = history;
    }

    public int getUndo() {
        return undo;
    }

    public void setUndo(int undo) {
        this.undo = undo;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public GamePanel() {
        this.addKeyListener(this);
        this.setLayout(new BorderLayout());
        this.setPreferredSize(new Dimension(140+20*29,15*29));

        JPanel east = new JPanel();
        east.setPreferredSize(new Dimension(140, 400));
        east.setLayout(new BorderLayout());

        JPanel contextButtons = new JPanel();
        contextButtons.setPreferredSize(new Dimension(130, 80));

        resetButton.setText("Reset");
        resetButton.addActionListener(this);
        resetButton.setPreferredSize(new Dimension (100, 30));
        undoButton.setText("Annuler");
        undoButton.addActionListener(this);
        undoButton.setPreferredSize(new Dimension (100, 30));
        
        contextButtons.add(resetButton);
        contextButtons.add(undoButton);

        east.add(contextButtons, BorderLayout.SOUTH);

        this.add(area, BorderLayout.CENTER);
        this.add(east, BorderLayout.EAST);
    }

    public void init() {
        this.setFocusable(true);
        int[] position = area.getInitialPosition();
        tankPosX = position[0];
        tankPosY = position[1];
        undo=0;
        score=0;
        history = new History();
        
        undoButton.setEnabled(false);

        area.setNumberBoard(tankPosX, tankPosY, 38);
        area.setSubObjectNumberBoard(tankPosX, tankPosY, 2);
        area.repaint();
        this.requestFocus();

        g = this.getGraphics();
        g.setColor(Color.RED);
    }

    public BoardArea getArea() {
        return area;
    }

    public void setArea(BoardArea area) {
        this.area = area;
    }

    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == resetButton) {
            reLoadFile();
            init();
        }
        else if (ae.getSource() == undoButton) {
            if (undo!=0) {
                area.setBoardImage(history.getLastHistory());
                int [] position = area.getTankPosition();
                tankPosX = position[0];
                tankPosY = position[1];
                score--;
                undo--;
                area.repaint();
                this.setFocusable(true);
                this.requestFocus();
            }
            if (undo==0) {
                undoButton.setEnabled(false);
            }
        }
    }

    public void keyTyped(KeyEvent ke) {
    }

    public void keyPressed(KeyEvent ke) {
        int n=0;

        if ((37<=ke.getKeyCode() && ke.getKeyCode()<=40) || ke.getKeyCode()==32 ) {
            score++;
            history.addHistory(area.getBoardImage());
            if (undo!=10) {
                undo++;
            }
            undoButton.setEnabled(true);

            switch(ke.getKeyCode()) {
                //flèche de gauche
                case 37:
                    if (area.getNumberBoard(tankPosX,tankPosY)==37 && 0<tankPosY) {
                        n = area.getNumberBoard(tankPosX,tankPosY-1);
                        if (0<=n && n<=3 || n==36) {
                            area.setNumberBoard(tankPosX, tankPosY, area.getSubObjectNumberBoard(tankPosX, tankPosY));
                            tankPosY--;
                            area.setSubObjectNumberBoard(tankPosX, tankPosY, n);
                            area.setNumberBoard(tankPosX, tankPosY, 37);
                        }
                    }
                    else {
                        area.setNumberBoard(tankPosX, tankPosY, 37);
                    }
                    break;
                //flèche du haut
                case 38:
                    if (area.getNumberBoard(tankPosX,tankPosY)==38 && 0<tankPosX) {
                        n = area.getNumberBoard(tankPosX-1,tankPosY);
                        if (0<=n && n<=3 || n==36) {
                            area.setNumberBoard(tankPosX, tankPosY, area.getSubObjectNumberBoard(tankPosX, tankPosY));
                            tankPosX--;
                            area.setSubObjectNumberBoard(tankPosX, tankPosY, n);
                            area.setNumberBoard(tankPosX, tankPosY, 38);
                        }
                    }
                    else {
                        area.setNumberBoard(tankPosX, tankPosY, 38);
                    }
                    break;
                //flèche de droite
                case 39:
                    if (area.getNumberBoard(tankPosX,tankPosY)==39 && tankPosY<19) {
                        n = area.getNumberBoard(tankPosX,tankPosY+1);
                        if (0<=n && n<=3 || n==36) {
                            area.setNumberBoard(tankPosX, tankPosY, area.getSubObjectNumberBoard(tankPosX, tankPosY));
                            tankPosY++;
                            area.setSubObjectNumberBoard(tankPosX, tankPosY, n);
                            area.setNumberBoard(tankPosX, tankPosY, 39);
                        }
                    }
                    else {
                        area.setNumberBoard(tankPosX, tankPosY, 39);
                    }
                    break;
                //flèche du bas
                case 40:
                    if (area.getNumberBoard(tankPosX,tankPosY)==40 && tankPosX<14) {
                        n = area.getNumberBoard(tankPosX+1,tankPosY);
                        if (0<=n && n<=3 || n==36) {
                            area.setNumberBoard(tankPosX, tankPosY, area.getSubObjectNumberBoard(tankPosX, tankPosY));
                            tankPosX++;
                            area.setSubObjectNumberBoard(tankPosX, tankPosY, n);
                            area.setNumberBoard(tankPosX, tankPosY, 40);
                        }
                    }
                    else {
                        area.setNumberBoard(tankPosX, tankPosY, 40);
                    }
                    break;
                //touche espace
                case 32:
                    fire();
                    break;
            }

            area.repaint();

            if (area.getSubObjectNumberBoard(tankPosX, tankPosY)==1) {
                this.setFocusable(false);
            }
            else if (area.getSubObjectNumberBoard(tankPosX, tankPosY)==3) {
                JOptionPane.showMessageDialog(null, "Vous avez réussi!\nIl vous a fallu "+score+" coups;", "Gagné!!!", JOptionPane.INFORMATION_MESSAGE);
                this.setFocusable(false);
            }
        }

        //Gestion des ennemis
        if (37<=ke.getKeyCode() && ke.getKeyCode()<=40) {
            if (ennemiFire()) {
                this.setFocusable(false);
            }
        }
    }

    public void keyReleased(KeyEvent ke) {
    }

    public void reLoadFile() {
        try {
            DataInputStream dis;
            byte[] buff = new byte[8];
            int i=0, j=0, l=0, m=0, n=0;
            dis = new DataInputStream(new BufferedInputStream(new FileInputStream(new File("map/"+fileName+".map"))));

            i=0;
            while(dis.read(buff) >= 0)
            {
                for(byte bit : buff) {
                    n = m;
                    m = l;
                    l = (char)bit-48;
                    if ((char)bit==',') {
                        if (n!=-4) {
                            m=n*10+m;
                        }
                        if (j<20 && i<15) {
                            area.setNumberBoard(i, j, m);
                        }
                        j++;
                        if (j==20) {
                            j=0;
                            i++;
                        }
                    }
                }
            }
            area.repaint();
        }
            catch(IOException e) {
                e.printStackTrace();
        }
    }

    public void fire() {
        firePosX=tankPosX;
        firePosY=tankPosY;
        fireDirection = area.getNumberBoard(tankPosX,tankPosY);

        switch (fireDirection) {
            case 37:
                firePosY=firePosY-1;
                break;
            case 38:
                firePosX=firePosX-1;
                break;
            case 39:
                firePosY=firePosY+1;
                break;
            case 40:
                firePosX=firePosX+1;
                break;
        }

        while (0<=firePosX && firePosX<=14 && 0<=firePosY && firePosY<=19) {
            int n = area.getNumberBoard(firePosX,firePosY);

            if (0<=n && n<=3 || n==5 || n==36) {
                if (fireDirection==37 || fireDirection==39) {
                    g.drawLine(29*firePosY, 29*firePosX+14, 29*(firePosY+1)-1, 29*firePosX+14);
                }
                else {
                    g.drawLine(29*firePosY+14, 29*firePosX, 29*firePosY+14, 29*(firePosX+1)-1);
                }
            }
            else if (n==6) {
                //breakableBlock
                area.setNumberBoard(firePosX, firePosY, 0);
                break;
            }
            else if (n==7) {
                //MovableBloc
                moveObject();
                break;
            }
            else if (n==8 || n==12) {
                if (fireDirection==38 ||fireDirection==39) {
                    g.drawLine(29*firePosY, 29*firePosX+14, 29*firePosY+14, 29*firePosX+14);
                    g.drawLine(29*firePosY+14, 29*firePosX+14, 29*firePosY+14, 29*firePosX+28);
                    if (fireDirection==38) {
                        fireDirection=37;
                    }
                    else {
                        fireDirection=40;
                    }
                }
                else {
                    if (n==8) {
                        moveObject();
                        break;
                    }
                    else {
                        area.setNumberBoard(firePosX, firePosY, 13);
                        break;
                    }
                }
            }
            else if (n==9 || n==13) {
                if (fireDirection==39 ||fireDirection==40) {
                    g.drawLine(29*firePosY, 29*firePosX+14, 29*firePosY+14, 29*firePosX+14);
                    g.drawLine(29*firePosY+14, 29*firePosX+14, 29*firePosY+14, 29*firePosX);
                    if (fireDirection==39) {
                        fireDirection=38;
                    }
                    else {
                        fireDirection=37;
                    }
                }
                else {
                    if (n==9) {
                        moveObject();
                        break;
                    }
                    else {
                        area.setNumberBoard(firePosX, firePosY, 14);
                        break;
                    }
                }
            }
            else if (n==10 || n==14) {
                if (fireDirection==37 ||fireDirection==40) {
                    g.drawLine(29*firePosY+14, 29*firePosX+14, 29*firePosY+28, 29*firePosX+14);
                    g.drawLine(29*firePosY+14, 29*firePosX+14, 29*firePosY+14, 29*firePosX);
                    if (fireDirection==37) {
                        fireDirection=38;
                    }
                    else {
                        fireDirection=39;
                    }
                }
                else {
                    if (n==10) {
                        moveObject();
                        break;
                    }
                    else {
                        area.setNumberBoard(firePosX, firePosY, 15);
                        break;
                    }
                }
            }
            else if (n==11 || n==15) {
                if (fireDirection==37 ||fireDirection==38) {
                    g.drawLine(29*firePosY+14, 29*firePosX+14, 29*firePosY+28, 29*firePosX+14);
                    g.drawLine(29*firePosY+14, 29*firePosX+14, 29*firePosY+14, 29*firePosX+28);
                    if (fireDirection==37) {
                        fireDirection=40;
                    }
                    else {
                        fireDirection=39;
                    }
                }
                else {
                    if (n==11) {
                        moveObject();
                        break;
                    }
                    else {
                        area.setNumberBoard(firePosX, firePosY, 12);
                        break;
                    }
                }
            }
            else {
                break;
            }
            
            switch (fireDirection) {
                case 37:
                    firePosY=firePosY-1;
                    break;
                case 38:
                    firePosX=firePosX-1;
                    break;
                case 39:
                    firePosY=firePosY+1;
                    break;
                case 40:
                    firePosX=firePosX+1;
                    break;
            }
        }
    }

    public Boolean ennemiFire() {
        int i=0, n=0, ennemiPosX=0, ennemiPosY=0, ennemiDirection=0;
        Boolean ennemiExist = false, tankVisible = false;

        for (i=0; i<=tankPosY; i++) {
            n = area.getNumberBoard(tankPosX, i);
            if (ennemiExist == true) {
                if (n==37 || n==38 || n==39 || n==40) {
                    tankVisible=true;
                    break;
                }
                else if (n==4 || (6<=n && n<=719)) {
                    ennemiExist = false;
                }
            }
            if (n==19) {
                ennemiExist = true;
                ennemiPosX = tankPosX;
                ennemiPosY = i;
                ennemiDirection=1;
            }
        }
        for (i=19; i>=tankPosY; i--) {
            n = area.getNumberBoard(tankPosX, i);
            if (ennemiExist == true) {
                if (n==37 || n==38 || n==39 || n==40) {
                    tankVisible=true;
                    break;
                }
                else if (n==4 || (6<=n && n<=719)) {
                    ennemiExist = false;
                }
            }
            if (n==17) {
                ennemiExist = true;
                ennemiPosX = tankPosX;
                ennemiPosY = i;
                ennemiDirection=2;
            }
        }
        for (i=0; i<=tankPosX; i++) {
            n = area.getNumberBoard(i, tankPosY);
            if (ennemiExist == true) {
                if (n==37 || n==38 || n==39 || n==40) {
                    tankVisible=true;
                    break;
                }
                else if (n==4 || (6<=n && n<=719)) {
                    ennemiExist = false;
                }
            }
            if (n==18) {
                ennemiExist = true;
                ennemiPosX = i;
                ennemiPosY = tankPosY;
                ennemiDirection=3;
            }
        }
        for (i=14; i>=tankPosX; i--) {
            n = area.getNumberBoard(i, tankPosY);
            if (ennemiExist == true) {
                if (n==37 || n==38 || n==39 || n==40) {
                    tankVisible=true;
                    break;
                }
                else if (n==4 || (6<=n && n<=719)) {
                    ennemiExist = false;
                }
            }
            if (n==16) {
                ennemiExist = true;
                ennemiPosX = i;
                ennemiPosY = tankPosY;
                ennemiDirection=4;
            }
        }

        if (tankVisible==true) {
            if (ennemiDirection==1) {
                g.drawLine(29*ennemiPosY+28, 29*tankPosX+14, 29*tankPosY, 29*tankPosX+14);
            }
            else if (ennemiDirection==2) {
                g.drawLine(29*ennemiPosY, 29*tankPosX+14, 29*tankPosY+28, 29*tankPosX+14);
            }
            else if (ennemiDirection==3) {
                g.drawLine(29*tankPosY+14, 29*ennemiPosX+28, 29*tankPosY+14, 29*tankPosX);
            }
            else if (ennemiDirection==4) {
                g.drawLine(29*tankPosY+14, 29*ennemiPosX, 29*tankPosY+14, 29*tankPosX+28);
            }
        }

        return tankVisible;
    }

    public void moveObject () {
        int objectNumber=area.getNumberBoard(firePosX, firePosY);
        int adjacentObjectNumber=4;
        int x=firePosX, y=firePosY;

        switch (fireDirection) {
            case 37:
                if (0<firePosY) {
                    y--;
                }
                break;
            case 38:
                if (0<firePosX) {
                    x--;
                }
                break;
            case 39:
                if (firePosY<19) {
                    y++;
                }
                break;
            case 40:
                if (firePosX<14) {
                    x++;
                }
                break;
        }

        adjacentObjectNumber=area.getNumberBoard(x, y);

        if (adjacentObjectNumber==0 || adjacentObjectNumber==3 || adjacentObjectNumber==36) {
            area.setSubObjectNumberBoard(x, y, adjacentObjectNumber);
            area.setNumberBoard(x, y, objectNumber);
            area.setNumberBoard(firePosX, firePosY, area.getSubObjectNumberBoard(firePosX, firePosY));
        }
        else if (adjacentObjectNumber==1) {
            if (objectNumber==7) {
                area.setNumberBoard(x, y, 36);
            }
            area.setNumberBoard(firePosX, firePosY, area.getSubObjectNumberBoard(firePosX, firePosY));
        }
    }
}
