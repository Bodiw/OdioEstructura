public class Ensamblador {

    private int wordSize;
    public ByteArr[] registros;
    public ByteArr[] ram;
    private byte alu;

    public Ensamblador(int wordSize, int registrySize, int ramSize) {
        this.registros = new ByteArr[registrySize];
        this.ram = new ByteArr[ramSize];
        this.wordSize = wordSize;
        for (int i = 0; i < registros.length; i++) {
            registros[i] = new ByteArr(0, wordSize);
        }
    }

    public void ld(String arg1, String arg2) {
        Field[] fields = processFields(arg1, arg2);
        if (fields[0].t == Field.REGISTRY) {
            registros[fields[0].v.getInt()] = fields[1].v;
        }
    }

    /**
     * 
     * @param registro1
     * @param registro2
     */

    private Field[] processFields(String... args) {
        Field[] fields = new Field[args.length];
        for (int i = 0; i < args.length; i++) {
            fields[i] = new Field(wordSize, args[i]);
        }
        return fields;
    }

    public ByteArr getRegister(int i) {
        return registros[i];
    }

}
