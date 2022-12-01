package network;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import model.AsmParser;
import model.Ensamblador;
import run.App;

public class AssemblerProcess implements Runnable {

    private Process process;

    private static BufferedReader reader;
    private static BufferedWriter writer;
    private static String output;
    private static String output2;
    private static Ensamblador ens;
    private static int linea = 0;
    private static boolean nextRAM = false;
    private static String startRAM;
    private static String wordsRAM;
    private static String endRAM;

    public AssemblerProcess(Ensamblador e) {
        ens = e;
    }

    public static void printNext(BufferedReader reader) {
        output = "";
        output2 = "";
        String line;
        try {
            while ((line = reader.readLine()) != null) {
                // System.out.println(line);
                output += line;
                output2 += line + "\n";
                if (line.contains("R31")) { // Registries
                    if (++linea == 1) {
                        output = "88110> run" + output.substring(output.indexOf("pro.bin") + 7);
                    }
                    // System.out.println(output);
                    updateAssembler(output);
                    break;
                } else if (nextRAM && line.contains(" " + endRAM + " ")) {// Data/RAM
                    output = output.substring(11 + startRAM.length() + wordsRAM.length());
                    output2 = output2.substring(11 + startRAM.length() + wordsRAM.length());
                    System.out.println(output2);
                    ens.updateRAM(output2);
                    nextRAM = false;
                    startRAM = null;
                    endRAM = null;
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void nextInstruction(int steps) {

        try {
            writer.write("t " + steps + "\n");
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void showRAM(int start, int words) {
        startRAM = "" + start;
        wordsRAM = "" + words;
        int firstNum = start - (start % 16);
        int end = start + (words - 1) * 4;
        int lastNum = end - (end % 16);
        try {
            nextRAM = true;
            startRAM = "" + firstNum;
            endRAM = "" + lastNum;
            writer.write("v " + start + " " + words + "\n");
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        // in case of window
        ProcessBuilder builder = new ProcessBuilder(App.emuDir, "-c", App.confDir, App.binDir);
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
            if (ap.get(valKeys[i]).startsWith("0x")) {
                vals[i] = Integer.parseInt(ap.get(valKeys[i]).substring(2), 16);
            } else {
                vals[i] = Integer.parseInt(ap.get(valKeys[i]));
            }
        }
        String instruction = ap.get("Instruccion");
        String[] regs = ap.getRegistries();
        ens.updateFromProcess(vals, regs, instruction.replace("_", " "), output2);
    }

}