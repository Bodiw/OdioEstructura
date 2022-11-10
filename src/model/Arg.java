package model;

import java.nio.ByteBuffer;

public class Arg {

    public int t;
    public ByteArr v;

    public Arg(int size, String field) {
        if (field.startsWith("r")) {
            t = REGISTRY;
            v = new ByteArr(ByteBuffer.allocate(size).putLong(Long.valueOf(field.substring(1))).array());
        } else if (field.startsWith(".")) {
            throw new IllegalArgumentException(". operator is not supported yet");
        } else if (field.startsWith("0x") || field.startsWith("0X")) {
            t = RAW;
            long l = Long.valueOf(field.substring(2), 16).longValue();
            v = new ByteArr(l, size);
        } else {
            t = RAW;
            v = new ByteArr(Long.parseLong(field), size);
        }
    }

    public static final int REGISTRY = -1;
    public static final int RAM = -2;
    public static final int RAW = -3;
}
