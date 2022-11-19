package gui.panels;

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import gui.Colors;
import gui.labels.RegistryLabel;
import model.Ensamblador;
import model.Registry;

public class RegPanel extends JPanel {

    Ensamblador ens;
    Registry[] regs;
    RegistryLabel[] labels;
    int displayMode = RegistryLabel.HEX;

    public RegPanel(Ensamblador ens) {
        this.ens = ens;
        this.labels = new RegistryLabel[32];
        this.regs = ens.getRegistries();
        setLayout(null);
        setVisible(true);
        // display 32 registries in a 4x8 grid

        int width = 140;
        int height = 20;

        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 4; y++) {
                int i = x * 4 + y;
                RegistryLabel regLabel = new RegistryLabel(ens.getRegistries()[i], RegistryLabel.HEX, i);
                labels[i] = regLabel;
                regLabel.setBounds(1 + width * y, 1 + height * x, width, height);
                regLabel.setOpaque(true);
                regLabel.setBackground(Colors.BACKGROUND);
                regLabel.setForeground(Colors.FOREGROUND);
                this.add(regLabel);
            }
        }
        this.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
    }

    public void update() {
        for (int i = 0; i < labels.length; i++) {
            if (!labels[i].getReg().equals(regs[i])) {
                labels[i].setReg(regs[i]);
                labels[i].setLastUpdate(ens.vals[2]);
            }
            if (labels[i].getLastUpdate() == ens.vals[2]) {
                labels[i].setBackground(Colors.UPDATED);
            } else if (labels[i].getLastUpdate() != 0) {
                labels[i].setBackground(Colors.MODIFIED);
            }
        }

    }

    public void setDisplayMode(int mode) {
        displayMode = mode;
        for (RegistryLabel label : labels) {
            label.setDisplayMode(mode);
        }
    }

    public void changeSign() {
        for (RegistryLabel label : labels) {
            label.changeSign();
        }
    }

    public void setIncludeR(boolean r) {
        for (RegistryLabel label : labels) {
            label.setIncludeR(r);
        }
    }

    public void setIncludeSpaces(boolean spaces) {
        for (RegistryLabel label : labels) {
            label.setIncludeSpaces(spaces);
        }
    }

    public int getDisplayMode() {
        return displayMode;
    }

}
