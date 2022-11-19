package gui;

import model.Ensamblador;

public class Gui {

    String s;
    Ensamblador ens;
    RegDisplay frame;

    public Gui(String s, Ensamblador ens) {
        this.s = s;
        this.ens = ens;
        frame = new RegDisplay(s, ens);
    }

}
