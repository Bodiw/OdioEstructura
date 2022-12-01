package gui.panels;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;

import gui.Colors;

public class OutControlPanel extends JPanel {

    private HashMap<JButton, Boolean> buttonsMap;

    public OutControlPanel() {
        buttonsMap = new HashMap<JButton, Boolean>();
        this.setBackground(Colors.BACKGROUND);
        this.setLayout(null);
        this.setVisible(true);
        this.setForeground(Colors.FOREGROUND);

        // Add 2 toggle buttons to either output the console or the main memory data

        JButton output = new JButton("Output");
        output.setBounds(1, 1, 70 - 2, 19);
        output.setForeground(Colors.FOREGROUND);
        output.setBackground(Colors.BACKGROUND);
        output.setBorder(BorderFactory.createLineBorder(Colors.BORDER, 1));
        output.setOpaque(true);
        output.addActionListener(new toggleAction());
        this.add(output);

        JButton data = new JButton("Data");
        data.setBounds(70 - 1, 1, 70 - 2, 19);
        data.setForeground(Colors.FOREGROUND);
        data.setBackground(Colors.BACKGROUND);
        data.setBorder(BorderFactory.createLineBorder(Colors.BORDER, 1));
        data.setOpaque(true);
        data.addActionListener(new toggleAction());
        this.add(data);

        buttonsMap.put(output, true);
        buttonsMap.put(data, false);
        this.update();
    }

    public void update() {
        for (JButton button : buttonsMap.keySet()) {
            if (buttonsMap.get(button)) {
                button.setBackground(Colors.MODIFIED);
            } else {
                button.setBackground(Colors.BACKGROUND);
            }
        }
    }

    class toggleAction implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            JButton source = (JButton) e.getSource();
            if (buttonsMap.get(source)) {
                return;
            }
            // get each entry and set all the values to false
            for (Map.Entry<JButton, Boolean> entry : buttonsMap.entrySet()) {
                entry.setValue(false);
            }

            // set the source to true
            buttonsMap.put(source, true);
            update();
        }
    }

}
