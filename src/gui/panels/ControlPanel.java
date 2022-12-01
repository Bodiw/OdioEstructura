package gui.panels;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerModel;

import gui.Colors;
import gui.RegDisplay;
import model.Ensamblador;
import network.AssemblerProcess;

public class ControlPanel extends JPanel {

    public RegDisplay reg;
    public JButton back;
    public JButton next;
    public stepSpinner backSpinner;
    public stepSpinner nextSpinner;
    public stepSpinner RAMSpinner;
    public stepSpinner wordsSpinner;

    public ControlPanel(Ensamblador ens, RegDisplay display) {
        SpinnerModel model = new javax.swing.SpinnerNumberModel(1, 1, 100, 1);
        SpinnerModel model2 = new javax.swing.SpinnerNumberModel(1, 1, 10000, 1);
        SpinnerModel model3 = new javax.swing.SpinnerNumberModel(0, 0, 10000, 4);
        SpinnerModel model4 = new javax.swing.SpinnerNumberModel(32, 6, 32, 1);
        this.reg = display;
        setLayout(null);
        setVisible(true);
        setBackground(Colors.BACKGROUND);
        setForeground(Colors.FOREGROUND);

        // Lets add a back button , a field for how many steps and a next button

        back = new JButton();
        back.setText("Prev");
        back.setBounds(0, 0, 100, 60);
        back.setOpaque(true);
        back.setBackground(Colors.BACKGROUND);
        back.setForeground(Colors.FOREGROUND);
        back.setBorder(BorderFactory.createLineBorder(Colors.BORDER, 1));
        this.add(back);

        backSpinner = new stepSpinner(model);
        backSpinner.setBounds(0, 60, 100, 40);
        this.add(backSpinner);

        next = new JButton();
        next.setText("Next");
        next.setBounds(200 + 2, 0, 100, 60);
        next.setOpaque(true);
        next.setBackground(Colors.BACKGROUND);
        next.setForeground(Colors.FOREGROUND);
        next.setBorder(BorderFactory.createLineBorder(Colors.BORDER, 1));
        next.addActionListener(new nextInstructionAction());
        this.add(next);

        nextSpinner = new stepSpinner(model2);
        nextSpinner.setBounds(200 + 2, 60, 100, 40);
        this.add(nextSpinner);

        JButton RAMLabel = new JButton("MP");
        RAMLabel.setBounds(0, 100 + 1, 100, 60);
        RAMLabel.setOpaque(true);
        RAMLabel.setBackground(Colors.BACKGROUND);
        RAMLabel.setForeground(Colors.FOREGROUND);
        RAMLabel.setBorder(BorderFactory.createLineBorder(Colors.BORDER, 1));
        RAMLabel.addActionListener(new refreshRAMAction());
        this.add(RAMLabel);

        RAMSpinner = new stepSpinner(model3);
        RAMSpinner.setBounds(0, 100 + 60 + 1, 100, 40);
        this.add(RAMSpinner);

        JButton wordsLabel = new JButton("Words");
        wordsLabel.setBounds(200 + 2, 100 + 1, 100, 60);
        wordsLabel.setOpaque(true);
        wordsLabel.setBackground(Colors.BACKGROUND);
        wordsLabel.setForeground(Colors.FOREGROUND);
        wordsLabel.setBorder(BorderFactory.createLineBorder(Colors.BORDER, 1));
        wordsLabel.addActionListener(new refreshRAMAction());
        this.add(wordsLabel);

        wordsSpinner = new stepSpinner(model4);
        wordsSpinner.setBounds(200 + 2, 100 + 60 + 1, 100, 40);
        this.add(wordsSpinner);

    }

    class nextInstructionAction implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            AssemblerProcess.nextInstruction((Integer) nextSpinner.getValue());
            try {
                Thread.sleep(10);
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            }
            Integer value = (Integer) RAMSpinner.getValue();
            Integer words = (Integer) wordsSpinner.getValue();
            AssemblerProcess.showRAM(value, words);
            try {
                Thread.sleep(10);
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            }
            reg.updateLabels();
            reg.dp2.update(reg.ens.lastRAM);
        }

    }

    class refreshRAMAction implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            Integer value = (Integer) RAMSpinner.getValue();
            Integer words = (Integer) wordsSpinner.getValue();
            AssemblerProcess.showRAM(value, words);
            try {
                Thread.sleep(10);
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            }
            reg.dp2.update(reg.ens.lastRAM);
        }

    }

    class mouseWheelAction implements MouseWheelListener {

        @Override
        public void mouseWheelMoved(MouseWheelEvent e) {
            JSpinner source = (JSpinner) e.getSource();
            int value = (Integer) source.getValue();
            try {
                if (e.getWheelRotation() < 0) {
                    source.getModel().setValue(source.getModel().getNextValue());
                } else if (value > 1) {
                    source.getModel().setValue(source.getModel().getPreviousValue());
                }
            } catch (IllegalArgumentException ex) {
            }
        }

    }

    class stepSpinner extends JSpinner {

        public stepSpinner(SpinnerModel model) {
            super(model);
            this.setOpaque(true);
            this.setBackground(Colors.BACKGROUND);
            this.setForeground(Colors.FOREGROUND);
            this.setBorder(BorderFactory.createLineBorder(Colors.BORDER, 1));
            this.addMouseWheelListener(new mouseWheelAction());
            JFormattedTextField tf = ((JSpinner.DefaultEditor) this.getEditor()).getTextField();
            tf.setHorizontalAlignment(JTextField.CENTER);
            tf.setBackground(Colors.BACKGROUND);
            tf.setForeground(Colors.FOREGROUND);
            // change the background color of all the children of this
            for (int i = 0; i < this.getComponentCount(); i++) {
                Component c = this.getComponent(i);
                c.setBackground(Colors.BACKGROUND);
                c.setForeground(Colors.FOREGROUND);
            }
        }

    }
}
