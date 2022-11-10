import gui.Gui;
import model.Ensamblador;
import network.AssemblerProcess;

public class App {
    public static void main(String[] args) throws Exception {
        Ensamblador ens = new Ensamblador(4, 32, (int) Math.pow(2, 18));

        AssemblerProcess ap = new AssemblerProcess(ens);
        Thread t = new Thread(ap);
        t.start();

        Gui gui = new Gui("Odio Estructura", ens);
    }

}
