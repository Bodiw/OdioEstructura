package gui;

import model.Ensamblador;

public class Gui {

    public Gui(String s, Ensamblador ens) {
        RegDisplay frame = new RegDisplay(s, ens);
    }

}
