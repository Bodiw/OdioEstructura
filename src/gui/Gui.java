package gui;

import model.Ensamblador;

public class Gui {

    String s;
    Ensamblador ens;
    public RegDisplay regDisp;

    public Gui(String s, Ensamblador ens) {
        this.s = s;
        this.ens = ens;
        regDisp = new RegDisplay(s, ens);
    }

}
