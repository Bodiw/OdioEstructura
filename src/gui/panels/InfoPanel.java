package gui.panels;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

import gui.Colors;
import model.Ensamblador;
import model.Registry;

public class InfoPanel extends JPanel {

    Ensamblador ens;
    Registry[] regs;
    JLabel[] labels;
    int[] labelLastUpdate;
    String[] counters = { "PC", "CI", "Ciclo", "FL", "FE", "FC", "FV", "FR" };

    public InfoPanel(Ensamblador ens) {
        this.ens = ens;
        this.labels = new JLabel[counters.length];
        this.labelLastUpdate = new int[labels.length];
        this.regs = ens.getRegistries();
        setLayout(null);
        setVisible(true);
        // display 32 registries in a 4x8 grid

        int width = 70;
        int height = 20;
        // Text labels
        for (int i = 0; i < counters.length; i++) {
            JLabel lab = new JLabel(counters[i]);
            lab.setBounds(1 + width, 1 + height * i, width, height);
            lab.setOpaque(true);
            lab.setBackground(Colors.BACKGROUND);
            lab.setForeground(Colors.FOREGROUND);
            this.add(lab);
        }
        // Values
        for (int i = 0; i < counters.length; i++) {
            JLabel lab = new JLabel();
            lab.setBounds(1, 1 + height * i, width, height);
            lab.setOpaque(true);
            lab.setBackground(Colors.BACKGROUND);
            lab.setForeground(Colors.FOREGROUND);
            labels[i] = lab;
            this.add(lab);
        }
        this.update();
        this.setBorder(BorderFactory.createLineBorder(Colors.BORDER, 1));
    }

    public void update() {
        for (int i = 0; i < labels.length; i++) {
            String t = labels[i].getText().trim();
            String t2 = "" + ens.vals[i];
            if (!t.equals(t2)) {
                labels[i].setText(String.format("%-8d", ens.vals[i]));
                labels[i].setBackground(Colors.UPDATED);
                labelLastUpdate[i] = ens.vals[2];
            } else if (labelLastUpdate[i] != 0) {
                labels[i].setBackground(Colors.MODIFIED);
            }
        }

    }

}
