package com.s4n.dronedeliveryII.model.enums;

public enum Movement {

    A("A"),
    I("I"),
    D("D");

    private String movement;

    Movement(String movement) {
        this.movement = movement;
    }

    public String getMovement() {
        return movement;
    }

    public void setMovement(String movement) {
        this.movement = movement;
    }
}
