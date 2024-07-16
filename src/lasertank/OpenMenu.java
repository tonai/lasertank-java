/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package lasertank;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;
import javax.swing.JScrollPane;

/**
 *
 * @author Tonaï
 */
public class OpenMenu extends JDialog {
    String choosenFile = "";
    File path = new File("map");
    JRadioButton[] radioButtons = new JRadioButton[path.listFiles().length];

    public String getChoosenFile() {
        return choosenFile;
    }

    public void setChoosenFile(String choosenFile) {
        this.choosenFile = choosenFile;
    }

	/**
	 * Constructeur
	 * @param parent
	 * @param title
	 * @param modal
	 */
	public OpenMenu(JFrame parent, String title, boolean modal){
		super(parent, title, modal);
		this.setSize(500, 300);
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		this.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
		this.initComponent();
        this.setVisible(true);
	}

	private void initComponent(){
		JPanel panFile = new JPanel();
        int i=0;

		panFile.setBackground(Color.white);
		panFile.setBorder(BorderFactory.createTitledBorder("Fichier à ouvrir"));

        try {
            i = 0;
            for(File nom : path.listFiles()){
                if (nom.getName().substring(nom.getName().length()-4, nom.getName().length()).equals(".map")) {
                    radioButtons[i] = new JRadioButton(nom.getName().substring(0, nom.getName().length()-4));
                    if (i == 0) {
                        radioButtons[i].setSelected(true);
                    }
                    i++;
                }
            }
        } catch (NullPointerException npe) {
            npe.printStackTrace();
        }

		panFile.setPreferredSize(new Dimension(440, 200));
		ButtonGroup bg = new ButtonGroup();

        for (i=0; i<radioButtons.length; i++) {
            bg.add(radioButtons[i]);
            panFile.add(radioButtons[i]);
        }

		JPanel content = new JPanel();
        JScrollPane scroll = new JScrollPane(content);

		content.setBackground(Color.white);
		content.add(panFile);

		JPanel control = new JPanel();
		JButton okBouton = new JButton("OK");

		okBouton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
                int i=0;

				setVisible(false);
                for (i=0; i<radioButtons.length; i++) {
                    if (radioButtons[i].isSelected()) {
                         choosenFile = radioButtons[i].getText();
                    }
                }
			}
		});

		JButton cancelBouton = new JButton("Annuler");
		cancelBouton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				setVisible(false);
                choosenFile="cancelAction";
			}
		});

		control.add(okBouton);
		control.add(cancelBouton);

		this.getContentPane().add(scroll, BorderLayout.CENTER);
		this.getContentPane().add(control, BorderLayout.SOUTH);
	}

}
