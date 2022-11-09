public class App {
    public static void main(String[] args) throws Exception {
        Ensamblador ens = new Ensamblador(4, 32, (int) Math.pow(2, 18));

        System.out.println(ens.getRegister(0).getInt());

        ens.ld("r0", "1");

        System.out.println(ens.getRegister(0).getInt());

    }

}
