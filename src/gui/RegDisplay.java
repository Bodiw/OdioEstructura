package gui;

import java.awt.Rectangle;
import java.util.HashMap;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;

import gui.labels.RegistryLabel;
import gui.panels.ControlPanel;
import gui.panels.DataPanel;
import gui.panels.DisplayPanel;
import gui.panels.InfoPanel;
import gui.panels.OutputPanel;
import gui.panels.RegPanel;
import model.Ensamblador;
import model.Registry;

public class RegDisplay extends JFrame {

    JFrame f;
    public Ensamblador ens;
    public Registry[] regs;
    public RegistryLabel[] labels;
    public HashMap<String, JLabel> lbls;
    public RegPanel rp;
    public InfoPanel ip;
    public DisplayPanel dp;
    public OutputPanel op;
    public ControlPanel cp;
    public DataPanel dp2;

    public RegDisplay(String s, Ensamblador ens) {
        super(s);
        this.ens = ens;
        this.regs = ens.getRegistries();
        lbls = new HashMap<String, JLabel>();
        // display 32 registries in a 4x8 grid

        int width = 140;
        int height = 20;

        rp = new RegPanel(ens);
        rp.setBounds(height - 1, height - 1, 4 * width + 2, 8 * height + 2);
        rp.setBorder(BorderFactory.createLineBorder(Colors.BORDER, 10));
        this.add(rp);

        ip = new InfoPanel(ens);
        ip.setBounds(4 * width + 2 * height - 1, height - 1, width + 2, 8 * height + 2);
        ip.setBorder(BorderFactory.createLineBorder(Colors.BORDER, 10));
        this.add(ip);

        dp = new DisplayPanel(this);
        Rectangle r = ip.getBounds();
        dp.setBounds(r.x + 160 - 1, r.y, width + 2, 8 * height + 2);
        dp.setBorder(BorderFactory.createLineBorder(Colors.BORDER, 10));
        this.add(dp);

        op = new OutputPanel(ens, this);
        r = rp.getBounds();
        op.setBounds(r.x, r.y + 280 - height - 1, 4 * width + 2, 10 * height + 2);
        // op.setBorder(BorderFactory.createLineBorder(Colors.BORDER, 1));
        op.setOpaque(false);
        this.add(op);

        dp2 = new DataPanel(ens);
        dp2.setBounds(r.x + 1, r.y + 280 - 1, 4 * width + 2, 10 * height + 2);
        dp2.setVisible(true);
        dp2.setBackground(Colors.BACKGROUND);
        dp2.setBorder(BorderFactory.createLineBorder(Colors.BORDER, 1));
        this.add(dp2);

        cp = new ControlPanel(ens, this);
        cp.setBounds(4 * width + 2 * height - 1, 10 * height - 1, 2 * width + height + 2, 15 * height + 2);
        cp.setBorder(BorderFactory.createLineBorder(Colors.BORDER, 1));
        cp.setOpaque(true);
        this.add(cp);

        // Add a box with the last instruction
        JLabel lastInstruccion = new JLabel();
        lbls.put("lastInstruccion", lastInstruccion);
        lastInstruccion.setBounds(height, 10 * height, 270, height);
        lastInstruccion.setOpaque(true);
        lastInstruccion.setBackground(Colors.MODIFIED);
        lastInstruccion.setForeground(Colors.FOREGROUND);
        lastInstruccion.setBorder(BorderFactory.createLineBorder(Colors.BORDER, 1));
        this.add(lastInstruccion);

        // Add a box with the next instruction
        JLabel instruccion = new JLabel();
        lbls.put("instruccion", instruccion);
        instruccion.setBounds(height, 11 * height, 270, height);
        instruccion.setOpaque(true);
        instruccion.setBackground(Colors.MODIFIED);
        instruccion.setForeground(Colors.FOREGROUND);
        instruccion.setBorder(BorderFactory.createLineBorder(Colors.BORDER, 1));
        this.add(instruccion);

        // window properties
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1920 / 2, 1080 / 2);
        this.getContentPane().setBackground(Colors.BACKGROUND);
        setLayout(null);
        setVisible(true);
        updateLabels();
    }

    public void updateLabels() {
        rp.update();
        ip.update();

        lbls.get("lastInstruccion").setText("Last | " + ens.lastInstruction);
        lbls.get("instruccion").setText("Next | " + ens.instruction);

        op.update();
        dp2.update(ens.lastRAM);
    }

    public RegPanel getRegPanel() {
        return rp;
    }

    public DataPanel getDataPanel() {
        return dp2;
    }

    public void setIncludeSpaces(boolean b) {
        rp.setIncludeSpaces(b);
    }

}