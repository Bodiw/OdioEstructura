package gui.panels;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import gui.Colors;
import gui.RegDisplay;
import gui.labels.RegistryLabel;

public class DisplayPanel extends JPanel {

    RegDisplay reg;
    String[] options = { "Regs", "Dec Sign", "R--", "Spacing", "Hotel?", "Trivago" };

    public DisplayPanel(RegDisplay display) {
        this.reg = display;
        setLayout(null);
        setVisible(true);

        // Add a "display" label
        JLabel displayLabel = new JLabel("Display");
        displayLabel.setBounds(0, 0, 140, 40);
        displayLabel.setOpaque(true);
        displayLabel.setBackground(Colors.MODIFIED);
        displayLabel.setForeground(Colors.FOREGROUND);
        displayLabel.setHorizontalAlignment(JLabel.CENTER);
        displayLabel.setBorder(BorderFactory.createLineBorder(Colors.BORDER, 1));
        this.add(displayLabel);

        for (int i = 0; i < options.length; i++) {
            JLabel button = new JLabel(options[i]);
            button.setBounds(0, 40 + 20 * i, 70, 20);
            button.setOpaque(true);
            button.setBackground(Colors.BACKGROUND);
            button.setForeground(Colors.FOREGROUND);
            button.setBorder(BorderFactory.createLineBorder(Colors.BORDER, 1));
            this.add(button);
        }

        JButton cycleDisplay = new JButton();
        cycleDisplay.setText(RegistryLabel.intToDisplayMode(RegistryLabel.HEX));
        cycleDisplay.setBounds(70, 40, 70, 20);
        cycleDisplay.addActionListener(new nextDisplayModeAction());
        cycleDisplay.setOpaque(true);
        cycleDisplay.setBackground(Colors.BACKGROUND);
        cycleDisplay.setForeground(Colors.FOREGROUND);
        cycleDisplay.setBorder(BorderFactory.createLineBorder(Colors.BORDER, 1));
        this.add(cycleDisplay);

        JButton decSign = new JButton();
        decSign.setText("Unsigned");
        decSign.setBounds(70, 60, 70, 20);
        decSign.addActionListener(new signDisplayModeAction());
        decSign.setOpaque(true);
        decSign.setBackground(Colors.BACKGROUND);
        decSign.setForeground(Colors.FOREGROUND);
        decSign.setBorder(BorderFactory.createLineBorder(Colors.BORDER, 1));
        this.add(decSign);

        JButton displayR = new JButton();
        displayR.setText("Yes");
        displayR.setBounds(70, 80, 70, 20);
        displayR.addActionListener(new displayRAction());
        displayR.setOpaque(true);
        displayR.setBackground(Colors.BACKGROUND);
        displayR.setForeground(Colors.FOREGROUND);
        displayR.setBorder(BorderFactory.createLineBorder(Colors.BORDER, 1));
        this.add(displayR);

        JButton displaySpacing = new JButton();
        displaySpacing.setText("Yes");
        displaySpacing.setBounds(70, 100, 70, 20);
        displaySpacing.addActionListener(new displaySpacingAction());
        displaySpacing.setOpaque(true);
        displaySpacing.setBackground(Colors.BACKGROUND);
        displaySpacing.setForeground(Colors.FOREGROUND);
        displaySpacing.setBorder(BorderFactory.createLineBorder(Colors.BORDER, 1));
        this.add(displaySpacing);

        JButton b1 = new JButton();
        b1.setText("Useless");
        b1.setBounds(70, 120, 70, 20);
        // b1.addActionListener(new b1Action());
        b1.setOpaque(true);
        b1.setBackground(Colors.BACKGROUND);
        b1.setForeground(Colors.FOREGROUND);
        b1.setBorder(BorderFactory.createLineBorder(Colors.BORDER, 1));
        this.add(b1);

        JButton b2 = new JButton();
        b2.setText("Button");
        b2.setBounds(70, 140, 70, 20);
        // b2.addActionListener(new b2Action());
        b2.setOpaque(true);
        b2.setBackground(Colors.BACKGROUND);
        b2.setForeground(Colors.FOREGROUND);
        b2.setBorder(BorderFactory.createLineBorder(Colors.BORDER, 1));
        this.add(b2);
    }

    class nextDisplayModeAction implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            JButton source = (JButton) e.getSource();
            int mode = reg.getRegPanel().getDisplayMode();
            if (mode-- == RegistryLabel.DEC) {
                mode = -1;
            }
            source.setText(RegistryLabel.intToDisplayMode(mode));
            reg.getRegPanel().setDisplayMode(mode);
        }

    }

    class signDisplayModeAction implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            JButton source = (JButton) e.getSource();
            String mode = source.getText();
            if (mode.equals("Signed")) {
                source.setText("Unsigned");
            } else {
                source.setText("Signed");
            }
            reg.getRegPanel().changeSign();
        }

    }

    class displayRAction implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            JButton source = (JButton) e.getSource();
            String mode = source.getText();
            if (mode.equals("Yes")) {
                source.setText("No");
                reg.getRegPanel().setIncludeR(false);
            } else {
                source.setText("Yes");
                reg.getRegPanel().setIncludeR(true);
            }
        }

    }

    class displaySpacingAction implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            JButton source = (JButton) e.getSource();
            String mode = source.getText();
            if (mode.equals("Yes")) {
                source.setText("No");
                reg.getRegPanel().setIncludeSpaces(false);
            } else {
                source.setText("Yes");
                reg.getRegPanel().setIncludeSpaces(true);
            }
        }

    }

}
