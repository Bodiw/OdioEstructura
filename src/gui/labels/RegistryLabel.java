package gui.labels;

import javax.swing.JLabel;

import model.Registry;

public class RegistryLabel extends JLabel {

    private Registry reg;
    private int displayMode;
    private int regNum;

    public RegistryLabel(Registry reg, int regNum) {
        this.reg = reg;
        this.regNum = regNum;
        this.setDisplayMode(HEX);
    }

    public RegistryLabel(Registry reg, int displayMode, int regNum) {
        this.reg = reg;
        this.regNum = regNum;
        this.setDisplayMode(displayMode);
    }

    public Registry getReg() {
        return reg;
    }

    public void setReg(Registry reg) {
        this.reg = reg;
        this.setDisplayMode(getDisplayMode());
    }

    public int getDisplayMode() {
        return displayMode;
    }

    public void setDisplayMode(int displayMode) {
        this.displayMode = displayMode;
        if (displayMode == HEX) {
            this.setText(String.format("R%02d = ", regNum) + reg.getHex());
        }
        if (displayMode == DEC) {
            this.setText(String.format("R%02d = %08d", regNum, reg.getInt()));
        }
    }

    public static final int HEX = -1;
    public static final int DEC = -2;

    public static String intToDisplayMode(int n) {
        if (n == HEX) {
            return "HEX";
        }
        if (n == DEC) {
            return "DEC";
        }
        return "UNKNOWN";
    }
}
