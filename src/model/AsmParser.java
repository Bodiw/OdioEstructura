package model;

public class AsmParser {
    private String s;

    public AsmParser(String s) {
        // prepare the string
        s = s.replaceAll(" *[:=] *", "=");
        int a = s.indexOf("Tot. Inst=");
        String sub1 = s.substring(0, a).replaceAll("\\s+", " ").replaceFirst("88110> ", "");
        sub1 = sub1.replaceFirst(" PC=", ",PC=");
        // After PC= there's an integer
        int b = sub1.indexOf(" ", sub1.indexOf("PC="));
        sub1 = "Comando=" + sub1.substring(0, b) + ",Instruccion=" + sub1.substring(b + 1).replace(",", "_");
        sub1 = sub1.trim().replace(" ", "_");
        String sub2 = s.substring(a);
        s = sub1 + "," + sub2;

        this.s = s
                .replaceFirst("Tot. Inst", "Instrucciones")
                .replaceFirst("�� Ciclo", "Ciclo")
                .replaceAll("\\s+", " ")
                .replaceAll(" *h +", ",")
                .replaceFirst(" h", "")
                .replace(" ", ",");

    }

    public String get(String key) {
        String v = s.substring(s.indexOf(key + "=") + key.length() + 1);
        v = v.substring(0, v.indexOf(","));
        return v;
    }

    public String[] getRegistries() {
        String[] regs = new String[32];
        String s2 = s.substring(s.indexOf("R01="));
        regs[0] = "0x00000000";
        for (int i = 1; i < regs.length; i++) {
            regs[i] = "0x" + s2.substring(4, s2.indexOf(",") != -1 ? s2.indexOf(",") : s2.length());
            s2 = s2.substring(s2.indexOf(",") + 1);
        }
        return regs;
    }

}
