package model;

public class Registry {

    protected ByteArr arr;

    protected boolean modifyable;
    protected boolean accessible;

    public Registry(int size) {
        this.arr = new ByteArr(0, size);
    }

    protected Registry(int num, int size) {
        this.arr = new ByteArr(num, size);
    }

    protected Registry() {
    }

    public boolean isModifyable() {
        return modifyable;
    }

    public boolean isAccessible() {
        return accessible;
    }

    public ByteArr getByteArr() {
        return arr;
    }

    public int getInt() {
        return arr.getInt();
    }

    public int getIntUnSigned() {
        return arr.getIntUnSigned();
    }

    public String getHex() {
        return arr.getHex();
    }

    public boolean equals(Registry other) {
        return this.getHex().equals(other.getHex());
    }
}
