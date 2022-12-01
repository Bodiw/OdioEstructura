package model;

import exception.IllegalModificationException;

public class Ensamblador {

    private int wordSize;
    public Registry[] registros;
    public ByteArr[] ram;

    public String lastInstruction;
    public String instruction;

    public String lastOutput;

    public String lastRAM = "";
    /*
     * public int pc;
     * public int contadorInstrucciones;
     * public int ciclo;
     * public int fl;
     * public int fe;
     * public int fc;
     * public int fv;
     * public int fr;
     */
    public int[] vals;

    public Ensamblador(int wordSize, int registrySize, int ramSize) {
        this.registros = new Registry[registrySize];
        this.ram = new ByteArr[ramSize];
        this.wordSize = wordSize;

        RegistryBuilder b = RegistryBuilder.getInstance();

        for (int i = 0; i < registros.length; i++) {
            registros[i] = b.size(wordSize).value(0).accessible(true).modifyable(true).build();
        }
    }

    /**
     * Carga en arg1 el valor de arg2
     * 
     * @param arg1
     * @param arg2
     */
    public void ld(String arg1, String arg2) {

        Arg[] Args = processArgs(arg1, arg2);

        if (Args[0].t == Arg.REGISTRY) { // Cargar en un registro

            int reg = Args[0].v.getInt();

            if (reg >= registros.length || reg < 0) { // Registro inexsistente
                throw new IndexOutOfBoundsException(
                        "El indice del registro " + arg1 + " se sale del rango 0-" + (registros.length - 1));
            }

            if (!registros[reg].isAccessible()) { // Registro inaccesible
                throw new IllegalModificationException("El registro " + arg1 + " no es accesible");
            }

            if (!registros[reg].isModifyable()) { // Registro no modificable
                throw new IllegalModificationException("El registro " + arg1 + " no es modificable");
            }

            registros[Args[0].v.getInt()] = new Registry(Args[1].v.getInt(), wordSize);
        }

    }

    /**
     * 
     * @param registro1
     * @param registro2
     */

    private Arg[] processArgs(String... args) {
        Arg[] Args = new Arg[args.length];
        for (int i = 0; i < args.length; i++) {
            Args[i] = new Arg(wordSize, args[i]);
        }
        return Args;
    }

    public Registry getRegistry(int i) {
        return registros[i];
    }

    public Registry[] getRegistries() {
        return registros;
    }

    public String getOutput() {
        return lastOutput;
    }

    public int getPC() {
        return vals[0];
    }

    public void updateFromProcess(int[] vals, String[] regs, String instruc, String output) {
        this.lastInstruction = instruction;
        this.instruction = instruc;
        this.vals = vals;
        this.lastOutput = output;
        Arg[] a = processArgs(regs);
        RegistryBuilder b = RegistryBuilder.getInstance();
        for (int i = 0; i < a.length; i++) {
            registros[i] = b.size(4).value(a[i].v.getInt()).accessible(true).modifyable(true).build();
        }
    }

    public void updateRAM(String ram) {
        this.lastRAM = ram;
    }

}
