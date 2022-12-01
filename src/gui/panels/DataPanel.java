package gui.panels;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

import gui.Colors;
import model.Ensamblador;
import model.StringConverter;

public class DataPanel extends JPanel {
    Ensamblador ens;
    DataLine[] lines;
    String prevRAM = "";
    String lastRAM = "";
    public int displayMode = DataLabel.HEX;

    public static final int HEX = -1;
    public static final int CHAR = 0;
    public static final int INT = 1;

    public DataPanel(Ensamblador ens) {
        this.ens = ens;
        setLayout(null);
        setVisible(true);
        lines = new DataLine[9];
        for (int i = 0; i < 9; i++) {
            DataLine line = new DataLine();
            lines[i] = line;
            line.setBounds(1, 20 * i + 1, 140 * 4, 20 * 8);
            line.setForeground(Colors.FOREGROUND);
            line.setBackground(Colors.BACKGROUND);
            this.add(line);
        }
    }

    public void update(String s) {
        this.clear();
        for (DataLine l : lines) {
            for (DataLabel d : l.data) {
                d.setMode(displayMode);
            }
        }
        lastRAM = s;
        String[] data = s.replace((char) 10, ' ').trim().split("\\s+");
        prevRAM = data[0];
        int linea = 0;
        int aux = 0;

        for (int i = 1; i < data.length && aux == 0; i++) {
            if (data[i].length() < 8) {
                aux = i;
            }
        }
        int pos = 5 - aux;
        String line = data[0];
        for (int i = 0; i < data.length; i++) {
            if (data[i].length() < 8) {
                lines[linea].direccion.setText(data[i]);
                line = data[i];
            } else {
                DataLabel l = lines[linea].data[pos];
                l.setHex(data[i], ens, Integer.parseInt(line));
                pos++;
                if (pos == 4) {
                    pos = 0;
                    linea++;
                }
            }
        }
    }

    public void setDisplayMode(int mode) {
        this.displayMode = mode;
    }

    public void clear() {
        for (int i = 0; i < lines.length; i++) {
            lines[i].direccion.setText("");
            for (int j = 0; j < lines[i].data.length; j++) {
                lines[i].data[j].setText("");
            }
        }
    }

    public static String intToDisplayMode(int mode) {
        switch (mode) {
            case HEX:
                return "HEX";
            case CHAR:
                return "CHAR";
            case INT:
                return "INT";
            default:
                return "HEX";
        }
    }
}

class DataLine extends JPanel {
    JLabel direccion;
    DataLabel[] data;

    public DataLine() {
        setVisible(true);
        setLayout(null);
        data = new DataLabel[4];
        direccion = new JLabel();
        direccion.setText("--");
        direccion.setBounds(0, 0, 67, 20);
        direccion.setForeground(Colors.FOREGROUND);
        direccion.setBorder(BorderFactory.createLineBorder(Colors.BORDER, 1));
        add(direccion);

        for (int i = 0; i < 4; i++) {
            data[i] = new DataLabel();
            data[i].setBounds(90 + 120 * i, 0, 120, 20);
            add(data[i]);
        }
    }
}

class DataLabel extends JLabel {

    String hexString = "";
    int mode;
    boolean spacing;
    int lastLine;
    int lastPC;

    public DataLabel() {
        this.setText("--");
        this.setForeground(Colors.FOREGROUND);
        this.mode = HEX;
        this.spacing = true;
    }

    public void setLastUpdate(int pc) {
        this.lastPC = pc;
    }

    public void setLastLine(int line) {
        this.lastLine = line;
    }

    public void setHex(String hex) {
        hex.replaceFirst("0x", "");
        this.setText(hex);
        this.hexString = hex;
    }

    public void setHex(String hex, Ensamblador ens, int line) {
        hex = hex.replaceFirst("0x", "");
        if (this.lastLine == line && this.lastPC != ens.getPC() && !this.hexString.equals((hex))) {
            this.setOpaque(true);
            this.setBackground(Colors.UPDATED);
            this.lastPC = ens.getPC();
        } else if (this.lastPC != ens.getPC() && this.hexString != "00000000") {
            this.setBackground(Colors.MODIFIED);
        } else {
            this.setBackground(Colors.BACKGROUND);
        }
        // Update the values
        this.lastLine = line;
        this.hexString = hex;
        // set the text
        // split the hex into a string array of length 2
        String res = "";
        if (this.mode == HEX) {
            res = hex;
        } else if (this.mode == CHAR && hex != "00000000") {
            res = StringConverter.hexToString(hex);
        }
        String s = spacing ? " " : "";
        String[] hexArray = res.split("(?<=\\G..)");
        res = "";
        for (int i = 0; i < hexArray.length; i++) {
            res += (i != 0 ? s : "") + hexArray[i].replace(" ", "_");
        }
        this.setText(res);
    }

    public void setMode(int mode) {
        this.mode = mode;
        this.update();
    }

    public void setSpacing(boolean spacing) {
        this.spacing = spacing;
    }

    public void update() {
        String text = "";
        if (hexString.isEmpty()) {
            return;
        } else if (mode == HEX) {
            text = hexString;
        } else if (mode == INT) {
            text = StringConverter.hexToIntString(hexString);
        } else if (mode == CHAR) {
            text = StringConverter.hexToString(hexString);
        }
        if (mode == CHAR && spacing && text.length() == 8) {
            text = text.substring(0, 2) + " " + text.substring(2, 4) + " " + text.substring(4, 6) + " "
                    + text.substring(6, 8);
        }
    }

    public static final int HEX = -1;
    public static final int CHAR = 0;
    public static final int INT = 1;

}