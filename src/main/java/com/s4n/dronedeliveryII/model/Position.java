package com.s4n.dronedeliveryII.model;

import com.s4n.dronedeliveryII.model.enums.Sense;

public class Position {

    private Integer coordinateY;
    private Integer coordinateX;
    private Sense sense;

    public Position(Integer coordinateY, Integer coordinateX, Sense sense) {
        this.coordinateY = coordinateY;
        this.coordinateX = coordinateX;
        this.sense = sense;
    }

    public Integer getCoordinateY() {
        return coordinateY;
    }

    public void setCoordinateY(Integer coordinateY) {
        this.coordinateY = coordinateY;
    }

    public Integer getCoordinateX() {
        return coordinateX;
    }

    public void setCoordinateX(Integer coordinateX) {
        this.coordinateX = coordinateX;
    }

    public Sense getSense() {
        return sense;
    }

    public void setSense(Sense sense) {
        this.sense = sense;
    }
}
