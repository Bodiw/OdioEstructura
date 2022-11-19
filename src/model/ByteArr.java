package model;

import java.nio.ByteBuffer;

public class ByteArr {

    public byte[] arr;

    public ByteArr(byte[] arr) {
        this.arr = arr;
    }

    public ByteArr(int num, int size) {
        this.arr = ByteBuffer.allocate(size).putInt(num).array();
    }

    public ByteArr(long num, int size) {
        this.arr = ByteBuffer.allocate(8).putLong(num).array();
        // trim the array and copy over the trailing bytes
        byte[] tmp = new byte[size];
        System.arraycopy(this.arr, this.arr.length - size, tmp, 0, size);
        this.arr = tmp;
    }

    /**
     * Returns a String of 0s and 1s representing the byte array
     */
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (byte b : arr) {
            sb.append(String.format("%8s", Integer.toBinaryString(b & 0xFF)).replace(' ', '0'));
        }
        return sb.toString();
    }

    /**
     * Returns a String representing the byte array in hexadecimal
     */
    public String toHexString() {
        return "0x" + getHex();
    }

    public int getInt() {
        return ByteBuffer.wrap(arr).getInt();
    }

    public int getIntUnSigned() {
        return ByteBuffer.wrap(arr).getInt() & 0xFFFFFFFF;
    }

    public String getHex() {
        StringBuilder sb = new StringBuilder();
        for (byte b : arr) {
            sb.append(String.format("%02X", b));
        }
        return sb.toString();
    }

    public int size() {
        return arr.length;
    }
}
