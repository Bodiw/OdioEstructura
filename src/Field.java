import java.nio.ByteBuffer;

public class Field {

    public int t;
    public ByteArr v;

    public Field(int size, String field) {
        if (field.startsWith("r")) {
            t = REGISTRY;
            v = new ByteArr(ByteBuffer.allocate(size).putInt(Integer.valueOf(field.substring(1))).array());
        } else if (field.startsWith(".")) {
            throw new IllegalArgumentException(". operator is not supported yet");
        } else if (field.startsWith("0x")) {
            t = RAW;
            v = new ByteArr(Integer.valueOf(field.substring(2), 16), size);
        } else {
            t = RAW;
            v = new ByteArr(Integer.parseInt(field), size);
        }
    }

    public static final int REGISTRY = -1;
    public static final int RAM = -2;
    public static final int RAW = -3;
}
