package model;

public class StringConverter {

    public static String hexToIntString(String hex) {
        int i = Integer.parseInt(hex, 16);
        return Integer.toString(i);
    }

    public static String intToHexString(String i) {
        int hex = Integer.parseInt(i);
        return Integer.toHexString(hex);
    }

    public static String hexToBinString(String hex) {
        int i = Integer.parseInt(hex, 16);
        return Integer.toBinaryString(i);
    }

    public static String binToHexString(String bin) {
        int i = Integer.parseInt(bin, 2);
        return Integer.toHexString(i);
    }

    // Turns a hex string of chars into a String
    public static String hexToString(String hex) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < hex.length(); i += 2) {
            String s = hex.substring(i, i + 2);
            sb.append((char) Integer.parseInt(s, 16));
        }
        return sb.toString();
    }
}
