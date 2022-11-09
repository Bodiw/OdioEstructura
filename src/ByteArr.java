import java.nio.ByteBuffer;

public class ByteArr {

    public byte[] arr;

    public ByteArr(byte[] arr) {
        this.arr = arr;
    }

    public ByteArr(int num, int size) {
        this.arr = ByteBuffer.allocate(size).putInt(num).array();
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
        StringBuilder sb = new StringBuilder();
        for (byte b : arr) {
            sb.append(String.format("%02X", b));
        }
        return "0x" + sb.toString();
    }

    public int getInt() {
        return ByteBuffer.wrap(arr).getInt();
    }
}
