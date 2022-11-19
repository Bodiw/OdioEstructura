package gui.labels;

import javax.swing.JLabel;

import model.Registry;

public class RegistryLabel extends JLabel {

    private Registry reg;
    private int displayMode;
    private int regNum;
    private int lastUpdate = 0;
    private boolean decSign = true;
    private boolean includeR = true;
    private boolean spacing = true;

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
        String text = "";
        String r = "";
        String s = "";
        if (spacing) {
            s = " ";
        }
        if (includeR) {
            r = String.format("R%02d | ", regNum);
        }
        if (displayMode == HEX) {
            String aux = "";
            text = reg.getHex();
            for (int i = 0; i < 4; i++) {
                aux += String.format("%2s" + s, text.substring(2 * i, 2 * i + 2));
            }
            text = r + aux;
        }
        if (displayMode == DEC) {
            int num = 0;
            if (!decSign) {
                num = reg.getIntUnSigned();
            } else {
                num = reg.getInt();
            }
            String sign = num < 0 ? (spacing ? "- " : "-") : "";
            num = Math.abs(num);
            if (!spacing) {
                text = String.format("%9d", num);
            } else {
                int[] n = { num / 1000000, (num / 1000) % 1000, num % 1000 };
                text = String.format("%03d %03d %03d", n[0], n[1], n[2]);
            }
            text = r + sign + text;
        }
        this.setText(text);

    }

    public void setLastUpdate(int lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public int getLastUpdate() {
        return lastUpdate;
    }

    public void changeSign() {
        decSign = !decSign;
        setDisplayMode(displayMode);
    }

    public void setIncludeR(boolean r) {
        includeR = r;
        setDisplayMode(displayMode);
    }

    public void setIncludeSpaces(boolean s) {
        spacing = s;
        setDisplayMode(displayMode);
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
