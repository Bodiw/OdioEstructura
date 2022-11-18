package model;

public class AsmParser {
    private String s;
    private int pos;
    static String example = "88110> t 2 PC=6032       br       -4       Tot. Inst: 23  �� Ciclo : 354        FL=1 FE=1 FC=0 FV=0 FR=0 R01 = 00000FB8 h R02 = 00000000 h R03 = 000000F4 h R04 = 00000000 h R05 = 00000000 h R06 = 00000000 h R07 = 00005998 h R08 = 00000000 h R09 = 00000000 h R10 = 00000003 h R11 = 000003FC h R12 = 00000000 h R13 = 00000000 h R14 = 00000000 h R15 = 00000000 h R16 = 00000000 h R17 = 00000000 h R18 = 00000000 h R19 = 00000000 h R20 = 04009E16 h R21 = 00000000 h R22 = 00000000 h R23 = 00000000 h R24 = 00000000 h R25 = 00000000 h R26 = 00000000 h R27 = 00000000 h R28 = 00000000 h R29 = 00000000 h R30 = FFFFFFF8 h R31 = 00000000 h";

    public AsmParser(String s) {
        // prepare the string
        s = s.replaceAll(" *[:=] *", "=");
        int a = s.indexOf("Tot. Inst=");
        String sub1 = s.substring(0, a).replaceAll("\\s+", " ").replaceFirst("88110> ", "");
        sub1 = sub1.replaceFirst(" PC=", ",PC=");
        // After PC= there's an integer
        int b = sub1.indexOf(" ", sub1.indexOf("PC="));
        sub1 = "Comando=" + sub1.substring(0, b) + ",Instruccion=" + sub1.substring(b + 1);
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

    public static void main(String[] args) {
        AsmParser ap = new AsmParser(example);
        System.out.println(ap.get("PC"));
        System.out.println(ap.s);
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
