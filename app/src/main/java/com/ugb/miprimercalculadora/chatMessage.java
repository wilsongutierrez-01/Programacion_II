package com.ugb.miprimercalculadora;

public class chatMessage {
    public boolean posicion;  //izquierdo, derecho
    public String message;

    public chatMessage(boolean posicion, String message) {
        this.posicion = posicion;
        this.message = message;
    }
}
