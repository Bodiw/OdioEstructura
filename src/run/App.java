package run;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JSpinner;

import gui.Gui;
import gui.RegDisplay;
import model.Ensamblador;
import network.AssemblerProcess;

public class App {

    public static String emuDir;
    public static String confDir;
    public static String binDir;

    public static void main(String[] args) throws Exception {

        if (args.length == 0) {
            emuDir = "/home/bogdan/Desktop/UPM/Estructura/emu/em88110";
            confDir = "/home/bogdan/Desktop/UPM/Estructura/src/conf";
            binDir = "/home/bogdan/Desktop/UPM/Estructura/bin/pro.bin";
        } else {
            emuDir = args[0];
            confDir = args[1];
            binDir = args[2];
        }

        Ensamblador ens = new Ensamblador(4, 32, (int) Math.pow(2, 18));

        AssemblerProcess ap = new AssemblerProcess(ens);
        Thread t = new Thread(ap);
        t.start();

        // pick a random label
        String s = Labels.NAMES[(int) (Math.random() * Labels.NAMES.length)];
        Gui gui = new Gui(s, ens);
        configs(args, gui.regDisp);
        
    }

    public static void configs(String[] args, RegDisplay rd) {

        args = new String[] { "/home/bogdan/Desktop/UPM/Estructura/emu/em88110",
                "/home/bogdan/Desktop/UPM/Estructura/src/conf", "/home/bogdan/Desktop/UPM/Estructura/bin/pro.bin",
                "HEX", "Yes", "Yes", "CHAR", "1", "4000" };
        for (String s : args) {
            System.out.println("Arg : " + s);
        }

        String regDisp = args[3];
        String rDisp = args[4];
        String spaceDisp = args[5];
        String MPDisp = args[6];
        int nextStep = Integer.parseInt(args[7]);
        int startMP = Integer.parseInt(args[8]);

        JButton s;
        if (regDisp == "DEC") {
            s = rd.dp.cycleDisplay;
            ActionListener[] acs = s.getActionListeners();
            acs[acs.length - 1].actionPerformed(new ActionEvent(s, ActionEvent.ACTION_FIRST, ""));
        }

        if (rDisp == "No") {
            s = rd.dp.displayR;
            ActionListener[] acs = s.getActionListeners();
            acs[acs.length - 1].actionPerformed(new ActionEvent(s, ActionEvent.ACTION_FIRST, ""));
        }

        if (spaceDisp == "No") {
            s = rd.dp.displaySpacing;
            ActionListener[] acs = s.getActionListeners();
            acs[acs.length - 1].actionPerformed(new ActionEvent(s, ActionEvent.ACTION_FIRST, ""));
        }

        if (MPDisp == "CHAR") {
            s = rd.dp.b1;
            ActionListener[] acs = s.getActionListeners();
            acs[acs.length - 1].actionPerformed(new ActionEvent(s, ActionEvent.ACTION_FIRST, ""));
        }

        ((JSpinner) rd.cp.nextSpinner).setValue(nextStep);
        ((JSpinner) rd.cp.RAMSpinner).setValue(startMP);
    }

}
