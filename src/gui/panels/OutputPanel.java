package gui.panels;

import javax.swing.BorderFactory;
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
        this.setLayout(null);
        this.setVisible(true);
        this.setBackground(Colors.BACKGROUND);

        OutControlPanel ocp = new OutControlPanel();
        ocp.setBounds(0, 1, 4 * 140 - 2, 20 - 1);
        this.add(ocp);

        ta = new JTextArea("HEllo", 10, 10);
        ta.setBounds(1, 20, 4 * 140 - 2, 10 * 20 - 2);
        ta.setEditable(false);
        ta.setLineWrap(true);
        ta.setForeground(Colors.FOREGROUND);
        ta.setBackground(Colors.BACKGROUND);
        ta.setBorder(BorderFactory.createLineBorder(Colors.BORDER, 1));
        ta.setOpaque(true);
        ta.setVisible(false);
        this.add(ta);
    }

    public void update() {

        ta.setText(ens.getOutput());
    }

}
