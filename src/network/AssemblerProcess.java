package network;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import model.Ensamblador;

public class AssemblerProcess implements Runnable {

    private Process process;

    private static BufferedReader reader;
    private static BufferedWriter writer;
    private static String output;
    private static Ensamblador ens;

    public AssemblerProcess(Ensamblador e) {
        ens = e;
    }

    public static void printNext(BufferedReader reader) {
        output = "";
        String line;
        try {
            while ((line = reader.readLine()) != null) {
                // System.out.println(line);
                output += line;
                if (line.contains("R31")) {
                    System.out.println(output);
                    updateAssembler(output);
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void nextInstruction() {
        try {
            writer.write("t\n");
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        // in case of window
        ProcessBuilder builder = new ProcessBuilder("/home/bogdan/Desktop/UPM/Estructura/emu/em88110", "-c",
                "/home/bogdan/Desktop/UPM/Estructura/src/conf", "/home/bogdan/Desktop/UPM/Estructura/bin/pro.bin");
        builder.redirectErrorStream(true);
        Process process;
        try {
            process = builder.start();
            try (InputStreamReader isr = new InputStreamReader(process.getInputStream());
                    BufferedReader r = new BufferedReader(isr);
                    BufferedWriter w = new BufferedWriter(new OutputStreamWriter(process.getOutputStream()))) {
                reader = r;
                writer = w;
                while (r.ready()) {
                    printNext(r);
                }
            }
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }

    public static void updateAssembler(String s) {
        int[] vals = new int[8];
        String instruction;
        String conf = s.substring(s.indexOf("PC"), s.indexOf("R01"));
        String[] reg = s.substring(s.indexOf("R01")).split("\\s+");
        // Process the configs
        String[] confs = conf.split("\\s+");
        vals[0] = Integer.parseInt(confs[0].substring(3));
        instruction = conf.substring(conf.indexOf("PC") + 1 + vals[0] / 10, conf.indexOf("Tot.")).trim();
        conf = conf.substring(conf.indexOf("Tot."));
        confs = conf.split("\\s+");
        vals[1] = Integer.parseInt(confs[2]);
        vals[2] = Integer.parseInt(confs[6]);
        vals[3] = Integer.parseInt(confs[7].substring(3));
        vals[4] = Integer.parseInt(confs[8].substring(3));
        vals[5] = Integer.parseInt(confs[9].substring(3));
        vals[6] = Integer.parseInt(confs[10].substring(3));
        vals[7] = Integer.parseInt(confs[11].substring(3));
        // process the registries
        String[] regs = new String[32];
        regs[0] = "0x00000000";
        for (int i = 0; i < 31; i++) {
            regs[i + 1] = "0x" + reg[2 + 4 * i];
        }
        ens.updateFromProcess(vals, regs, instruction);
    }

}