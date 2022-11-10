package model;

public class RegistryBuilder {

    private static RegistryBuilder builder;

    private Registry reg;

    private RegistryBuilder() {
        reg = new Registry();
    }

    public static RegistryBuilder getInstance() {
        if (builder == null) {
            builder = new RegistryBuilder();
        }
        return builder;
    }

    public RegistryBuilder size(int size) {
        this.reg.arr = new ByteArr(0, size);
        return this;
    }

    public RegistryBuilder value(int value) {
        this.reg.arr = new ByteArr(value, this.reg.arr.size());
        return this;
    }

    public RegistryBuilder value(long value) {
        this.reg.arr = new ByteArr(value, this.reg.arr.size());
        return this;
    }

    public RegistryBuilder modifyable(boolean modifyable) {
        this.reg.modifyable = modifyable;
        return this;
    }

    public RegistryBuilder accessible(boolean accessible) {
        this.reg.accessible = accessible;
        return this;
    }

    public RegistryBuilder reset() {
        this.reg = new Registry();
        this.accessible(false);
        this.modifyable(false);
        return this;
    }

    public Registry build() {
        Registry res = reg;
        this.reset();
        return res;
    }
}
