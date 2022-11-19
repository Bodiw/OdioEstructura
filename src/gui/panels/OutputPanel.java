package gui.panels;

import javax.swing.JPanel;
import javax.swing.JTextArea;

import gui.Colors;
import gui.RegDisplay;
import model.Ensamblador;

public class OutputPanel extends JPanel {

    RegDisplay reg;
    Ensamblador ens;
    JTextArea ta;

    public OutputPanel(Ensamblador ens, RegDisplay reg) {
        this.ens = ens;
        this.reg = reg;
        this.setBackground(Colors.BACKGROUND);
        this.setLayout(null);
        this.setVisible(true);
        this.setForeground(Colors.FOREGROUND);

        ta = new JTextArea("HEllo", 10, 10);
        ta.setBounds(1, 1, 4 * 140 - 2, 10 * 20 - 2);
        ta.setEditable(false);
        ta.setLineWrap(true);
        ta.setForeground(Colors.FOREGROUND);
        ta.setBackground(Colors.BACKGROUND);
        ta.setOpaque(true);
        this.add(ta);
    }

    public void update() {

        ta.setText(ens.getOutput());
    }

}
