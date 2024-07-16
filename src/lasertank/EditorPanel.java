/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package lasertank;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import javax.swing.JButton;
import javax.swing.JPanel;

/**
 *
 * @author Tonaï
 */
public class EditorPanel extends JPanel implements ActionListener, MouseListener, MouseMotionListener {
    private EditorButton[] ButtonsTab = new EditorButton[20];
    private JButton resetButton = new JButton(), undoButton = new JButton();
    private BoardArea area = new BoardArea();
    private int buffer=0, undo=0;
    private History history;

    public JButton getUndoButton() {
        return undoButton;
    }

    public void setUndoButton(JButton undoButton) {
        this.undoButton = undoButton;
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

    public EditorPanel() {
        this.addMouseListener(this);
        this.addMouseMotionListener(this);
        this.setLayout(new BorderLayout());
        this.setPreferredSize(new Dimension(140+20*29,15*29));

        history = new History();

        JPanel east = new JPanel();
        east.setPreferredSize(new Dimension(140, 400));
        east.setLayout(new BorderLayout());

        JPanel buttonField = new JPanel();

        ButtonsTab[0] = new EditorButton("Grass.png");
        ButtonsTab[1] = new EditorButton("Water.png");
        ButtonsTab[2] = new EditorButton("TankUp.png");
        ButtonsTab[3] = new EditorButton("Flag.png");
        ButtonsTab[4] = new EditorButton("Block.png");
        ButtonsTab[5] = new EditorButton("CrossBlock.png");
        ButtonsTab[6] = new EditorButton("BreakableBlock.png");
        ButtonsTab[7] = new EditorButton("MovableBlock.png");
        ButtonsTab[8] = new EditorButton("MovableMirrorDL.png");
        ButtonsTab[9] = new EditorButton("MovableMirrorUL.png");
        ButtonsTab[10] = new EditorButton("MovableMirrorUR.png");
        ButtonsTab[11] = new EditorButton("MovableMirrorDR.png");
        ButtonsTab[12] = new EditorButton("RotatingMirrorDL.png");
        ButtonsTab[13] = new EditorButton("RotatingMirrorUL.png");
        ButtonsTab[14] = new EditorButton("RotatingMirrorUR.png");
        ButtonsTab[15] = new EditorButton("RotatingMirrorDR.png");
        ButtonsTab[16] = new EditorButton("EnnemiUp.png");
        ButtonsTab[17] = new EditorButton("EnnemiLeft.png");
        ButtonsTab[18] = new EditorButton("EnnemiDown.png");
        ButtonsTab[19] = new EditorButton("EnnemiRight.png");
        for (int i=0; i<ButtonsTab.length; i++) {
            ButtonsTab[i].addActionListener(this);
            buttonField.add(ButtonsTab[i]);
        }

        JPanel contextButtons = new JPanel();
        contextButtons.setPreferredSize(new Dimension(130, 80));

        resetButton.setText("Reset");
        resetButton.addActionListener(this);
        resetButton.setPreferredSize(new Dimension (100, 30));
        undoButton.setText("Annuler");
        undoButton.addActionListener(this);
        undoButton.setPreferredSize(new Dimension (100, 30));
        undoButton.setEnabled(false);

        contextButtons.add(resetButton);
        contextButtons.add(undoButton);

        east.add(buttonField, BorderLayout.CENTER);
        east.add(contextButtons, BorderLayout.SOUTH);

        this.add(area, BorderLayout.CENTER);
        this.add(east, BorderLayout.EAST);
    }

    public BoardArea getArea() {
        return area;
    }

    public void setArea(BoardArea area) {
        this.area = area;
    }

    public int getBuffer() {
        return buffer;
    }

    public void setBuffer(int buffer) {
        this.buffer = buffer;
    }

    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == ButtonsTab[0]) {
            buffer = 0;
        }
        else if (ae.getSource() == ButtonsTab[1]) {
            buffer = 1;
        }
        else if (ae.getSource() == ButtonsTab[2]) {
            buffer = 2;
        }
        else if (ae.getSource() == ButtonsTab[3]) {
            buffer = 3;
        }
        else if (ae.getSource() == ButtonsTab[4]) {
            buffer = 4;
        }
        else if (ae.getSource() == ButtonsTab[5]) {
            buffer = 5;
        }
        else if (ae.getSource() == ButtonsTab[5]) {
            buffer = 5;
        }
        else if (ae.getSource() == ButtonsTab[6]) {
            buffer = 6;
        }
        else if (ae.getSource() == ButtonsTab[7]) {
            buffer = 7;
        }
        else if (ae.getSource() == ButtonsTab[8]) {
            buffer = 8;
        }
        else if (ae.getSource() == ButtonsTab[9]) {
            buffer = 9;
        }
        else if (ae.getSource() == ButtonsTab[10]) {
            buffer = 10;
        }
        else if (ae.getSource() == ButtonsTab[11]) {
            buffer = 11;
        }
        else if (ae.getSource() == ButtonsTab[12]) {
            buffer = 12;
        }
        else if (ae.getSource() == ButtonsTab[13]) {
            buffer = 13;
        }
        else if (ae.getSource() == ButtonsTab[14]) {
            buffer = 14;
        }
        else if (ae.getSource() == ButtonsTab[15]) {
            buffer = 15;
        }
        else if (ae.getSource() == ButtonsTab[16]) {
            buffer = 16;
        }
        else if (ae.getSource() == ButtonsTab[17]) {
            buffer = 17;
        }
        else if (ae.getSource() == ButtonsTab[18]) {
            buffer = 18;
        }
        else if (ae.getSource() == ButtonsTab[19]) {
            buffer = 19;
        }
        else if (ae.getSource() == resetButton) {
            area.resetBoard();
            history = new History();
            undo=0;
            undoButton.setEnabled(false);
        }
        else if (ae.getSource() == undoButton) {
            if (undo!=0) {
                area.setBoardImage(history.getLastHistory());
                undo--;
                area.repaint();
            }
            if (undo==0) {
                undoButton.setEnabled(false);
            }
        }
    }

    public void mouseClicked(MouseEvent me) {
    }

    public void mousePressed(MouseEvent me) {
        history.addHistory(area.getBoardImage());
        if (undo!=10) {
            undo++;
        }
        undoButton.setEnabled(true);

        int posX = me.getX(), posY = me.getY();
        if (0<posX && posX<580 && 0<posY && posY<435) {
            if (buffer==2) {
                if (area.doesTankExist()) {
                    int[] initialPosition = area.getInitialPosition();
                    area.setNumberBoard(initialPosition[0], initialPosition[1],0);
                }
            }
            area.setNumberBoard((posY)/29, (posX)/29, buffer);
        }
        area.repaint();
    }

    public void mouseReleased(MouseEvent me) {
    }

    public void mouseEntered(MouseEvent me) {
    }

    public void mouseExited(MouseEvent me) {
    }

    public void mouseDragged(MouseEvent me) {
        int posX = me.getX(), posY = me.getY();
        if (buffer!=2 && 0<posX && posX<580 && 0<posY && posY<435) {
            area.setNumberBoard((posY)/29, (posX)/29, buffer);
        }
        area.repaint();
    }

    public void mouseMoved(MouseEvent me) {
    }
}
