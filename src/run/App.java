package run;

import gui.Gui;
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
    }

}
