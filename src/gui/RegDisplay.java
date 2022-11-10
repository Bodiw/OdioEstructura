package gui;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

import gui.labels.RegistryLabel;
import model.Ensamblador;
import model.Registry;
import network.AssemblerProcess;

public class RegDisplay extends JFrame implements ActionListener {

    JFrame f;
    Ensamblador ens;
    Registry[] regs;
    RegistryLabel[] labels;
    JLabel[] labels2;
    String[] counters = { "PC", "CI", "Ciclo", "FL", "FE", "FC", "FV", "FR" };

    public RegDisplay(String s, Ensamblador ens) {
        super(s);
        this.ens = ens;
        this.regs = ens.getRegistries();
        this.labels = new RegistryLabel[regs.length];
        this.labels2 = new JLabel[8];
        // display 32 registries in a 4x8 grid

        int width = 140;
        int height = 20;

        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 4; y++) {
                int i = x * 4 + y;
                RegistryLabel regLabel = new RegistryLabel(regs[i], RegistryLabel.HEX, i);
                labels[i] = regLabel;
                regLabel.setBounds(height + width * y, height + height * x, width, height);
                regLabel.setOpaque(true);
                regLabel.setBackground(Color.WHITE);
                this.add(regLabel);
            }
        }
        for (int i = 0; i < counters.length; i++) {
            JLabel lab = new JLabel(String.format("%5s: %8d", counters[i], ens.vals[i]));
            labels2[i] = lab;
            lab.setBounds((int) (4.5 * width), height + height * i, width, height);
            lab.setOpaque(true);
            lab.setBackground(Color.WHITE);
            this.add(lab);
        }

        // Add a button to change the display mode

        JButton cycleDisplay = new JButton();
        cycleDisplay.setText(RegistryLabel.intToDisplayMode(RegistryLabel.HEX));
        cycleDisplay.setBounds(height, 10 * height, 90, height);
        cycleDisplay.addActionListener(this);
        this.add(cycleDisplay);

        // Add a next instruction butto

        JButton nextInstruction = new JButton();
        nextInstruction.setText("Next Instruction");
        nextInstruction.setBounds(10 * height, 10 * height, 120, height);
        nextInstruction.addActionListener(new nextInstructionAction());
        this.add(nextInstruction);

        // window properties
        setBackground(new Color(0, 64, 128));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1920 / 2, 1080 / 2);
        setLayout(null);
        setVisible(true);
    }

    public void updateLabels() {
        for (int i = 0; i < labels.length; i++) {
            labels[i].setReg(regs[i]);

        }
        for (int i = 0; i < labels2.length; i++) {
            labels2[i].setText(String.format("%5s: %8d", counters[i], ens.vals[i]));
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton source = (JButton) e.getSource();
        int mode = labels[0].getDisplayMode();
        if (mode-- == RegistryLabel.DEC) {
            mode = -1;
        }
        source.setText(RegistryLabel.intToDisplayMode(mode));
        System.out.println("mode is now " + RegistryLabel.intToDisplayMode(mode));
        for (RegistryLabel label : labels) {
            label.setDisplayMode(mode);
        }
    }

    class nextInstructionAction implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            AssemblerProcess.nextInstruction();
            updateLabels();
        }

    }
}