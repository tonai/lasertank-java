/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package lasertank;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JButton;

/**
 *
 * @author Tonaï
 */
public class EditorButton extends JButton {
    private Image img;

    public EditorButton(String file) {
        this.setPreferredSize(new Dimension(29,29));
        this.setBackground(Color.WHITE);
        try {
            img = ImageIO.read(new File("images/" + file));
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    @Override
    public void paintComponent(Graphics g){
        Graphics2D g2d = (Graphics2D)g;
        g2d.drawImage(img, 0, 0, this.getWidth(), this.getHeight(), this);

    }
}

