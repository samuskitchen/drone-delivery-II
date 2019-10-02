package com.s4n.dronedeliveryII.model.enums;

public enum Sense {

    NORTH("Norte"),
    SOUTH("Sur"),
    EAST("Oriente"),
    WEST("Occidente");

    private String sense;

    Sense(String sense){
        this.sense = sense;
    }

    public String getSense() {
        return sense;
    }

    public void setSense(String sense) {
        this.sense = sense;
    }
}
