/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package lasertank;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedInputStream;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 *
 * @author Tonaï
 */
public class Window extends JFrame implements ActionListener {
    GamePanel game;
    EditorPanel editor;
    String fileName = "";
    private JMenuBar menuBar = new JMenuBar();
	private JMenu fichier = new JMenu("Fichier"),
                        mode = new JMenu("Mode");
	private JMenuItem ouvrir = new JMenuItem("Ouvrir"),
                        enregistrer = new JMenuItem("Enregistrer"),
                        enregistrerSous = new JMenuItem("EnregistrerSous"),
                        quitter = new JMenuItem("Quitter"),
                        jouer = new JMenuItem("Jouer"),
                        editer = new JMenuItem("editer");
    private JPanel conteneur = new JPanel();

    public Window(){
        this.setTitle("Laser Tank 1.0");
        this.setSize(730, 500);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);

        ouvrir.addActionListener(this);
        this.fichier.add(ouvrir);
        enregistrer.addActionListener(this);
        this.fichier.add(enregistrer);
        enregistrerSous.addActionListener(this);
        this.fichier.add(enregistrerSous);
        quitter.addActionListener(this);
		this.fichier.add(quitter);
        jouer.addActionListener(this);
		this.mode.add(jouer);
        editer.addActionListener(this);
		this.mode.add(editer);

        conteneur.setPreferredSize(new Dimension(724, 448));
        this.setContentPane(conteneur);
        editor = new EditorPanel();
        game = new GamePanel();
        this.menuBar.add(fichier);
        this.menuBar.add(mode);
        this.setJMenuBar(menuBar);
        this.setVisible(true);
    }

    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == ouvrir) {
            if (loadFile()) {
                if (conteneur.getComponent(0)==game) {
                    game.setFileName(fileName);
                    game.init();
                    game.getResetButton().setEnabled(true);
                }
                else {
                    if (game.getFileName().equals("")) {
                        game.getResetButton().setEnabled(false);
                        game.getUndoButton().setEnabled(false);
                    }
                }
            }
        }
        else if (ae.getSource() == enregistrer || ae.getSource() == enregistrerSous) {
            if (editor.getArea().doesFlagExist() && editor.getArea().doesTankExist()) {
                try {
                    if (ae.getSource() == enregistrer) {
                        while (fileName.equals("") || fileName.equals("cancelAction")) {
                            fileName = JOptionPane.showInputDialog(null, "Veuillez indiquer le nom du fichier", "Enregistrement");
                        }
                    }
                    else {
                        String buffer = "";
                        while (buffer.equals("") || fileName.equals("cancelAction")) {
                            buffer = JOptionPane.showInputDialog(null, "Veuillez indiquer le nom du fichier", "Enregistrement");
                        }
                        fileName = buffer;
                    }

                    try {
                        BufferedWriter map = new BufferedWriter(new FileWriter("map/"+fileName+".map"));
                        map.write(editor.getArea().toString());
                        map.close();
                        this.setTitle("Laser Tank 1.0 ("+fileName+")");
                    }
                    catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                catch (NullPointerException npe) {
                   npe.printStackTrace();
                }
            }
            else if(!editor.getArea().doesTankExist()) {
                JOptionPane.showMessageDialog(null, "le niveau doit possèder une position initiale", "Enregistrement impossible", JOptionPane.WARNING_MESSAGE);
            }
            else if(!editor.getArea().doesFlagExist()) {
                JOptionPane.showMessageDialog(null, "le niveau doit possèder une (plusieurs) position(s) finale(s)", "Enregistrement impossible", JOptionPane.WARNING_MESSAGE);
            }
        }
        else if (ae.getSource() == quitter) {
            System.exit(0);
        }
        else if (ae.getSource() == jouer) {
            enregistrer.setEnabled(false);
            enregistrerSous.setEnabled(false);

            conteneur.removeAll();
            conteneur.add(game);
            conteneur.revalidate();
            if (loadFile()) {
                game.setFileName(fileName);
                game.init();
                game.getResetButton().setEnabled(true);
            }
            else {
                if (game.getFileName().equals("")) {
                    game.getResetButton().setEnabled(false);
                    game.getUndoButton().setEnabled(false);
                }
            }
        }
        else if (ae.getSource() == editer) {
            enregistrer.setEnabled(true);
            enregistrerSous.setEnabled(true);
            
            conteneur.removeAll();
            conteneur.add(editor);
            conteneur.revalidate();
            editor.getArea().repaint();
        }
    }

    public Boolean loadFile() {
        DataInputStream dis;
        byte[] buff = new byte[8];
        int i=0, j=0, l=0, m=0, n=0;
        OpenMenu dialog = new OpenMenu(null, "ouvrir", true);
        String buffer;
        Boolean load = false;

        buffer = dialog.getChoosenFile();

        try {
            if (!buffer.equals("cancelAction")) {
                fileName = buffer;
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
                                if (conteneur.getComponent(0)==editor) {
                                    editor.getArea().setNumberBoard(i, j, m);
                                }
                                else if (conteneur.getComponent(0)==game) {
                                    game.getArea().setNumberBoard(i, j, m);
                                }
                            }
                            j++;
                            if (j==20) {
                                j=0;
                                i++;
                            }
                        }
                    }
                }
                if (conteneur.getComponent(0)==editor) {
                    editor.getArea().repaint();
                }
                else if (conteneur.getComponent(0)==game) {
                    game.getArea().repaint();
                }
                this.setTitle("Laser Tank 1.0 ("+fileName+")");
                load=true;
            }
        }
            catch(IOException e) {
                e.printStackTrace();
        }
        return load;
    }
}

