package network;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import model.AsmParser;
import model.Ensamblador;

public class AssemblerProcess implements Runnable {

    private Process process;

    private static BufferedReader reader;
    private static BufferedWriter writer;
    private static String output;
    private static Ensamblador ens;
    private static int linea = 0;

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
                    if (++linea == 1) {
                        output = "88110> run" + output.substring(output.indexOf("pro.bin") + 7);
                    }
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
        AsmParser ap = new AsmParser(s);
        String[] valKeys = { "PC", "Instrucciones", "Ciclo", "FL", "FE", "FC", "FV", "FR" };
        int[] vals = new int[8];
        for (int i = 0; i < vals.length; i++) {
            vals[i] = Integer.parseInt(ap.get(valKeys[i]));
        }
        String instruction = ap.get("Instruccion");
        String[] regs = ap.getRegistries();
        ens.updateFromProcess(vals, regs, instruction.replace("_", " "));
    }

}