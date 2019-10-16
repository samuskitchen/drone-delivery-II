package com.s4n.dronedeliveryII.service;

import com.s4n.dronedeliveryII.exception.AppException;
import com.s4n.dronedeliveryII.model.Position;
import com.s4n.dronedeliveryII.model.enums.Movement;
import com.s4n.dronedeliveryII.model.enums.Sense;
import com.s4n.dronedeliveryII.util.Util;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class DomicileService {

    @Value("${route.output.folder}")
    private String routeOutputFolder;

    @Value("${drone.limit.lunch}")
    private Integer droneLimitLunch;

    /**
     * Translate the movement code by Cartesian vector
     *
     * @param routes
     * @return
     */
    @Async
    public CompletableFuture<List<Position>> moveDrone(List<String> routes, String filePath){

        if(Util.validateNameFile(filePath) && (routes.size() > 0 && routes.size() <= droneLimitLunch)){

            return CompletableFuture.completedFuture(routes.stream().map(route -> {
                Position initialPosition = new Position(0,0, Sense.NORTH);

                for (char ch : route.toCharArray()) {

                    Movement movement = Util.validateMovement(ch);

                    switch (movement) {
                        case A:
                            moveForward(initialPosition);
                            break;
                        case I:
                            turnLeft(initialPosition);
                            break;
                        case D:
                            turnRight(initialPosition);
                            break;
                        default:
                            break;
                    }
                }

                if (!Util.validateBlocks(initialPosition)) {
                    throw new AppException("The number of blocks established for delivery has been exceeded: " + 10);
                }

                return initialPosition;
            }).collect(Collectors.toList()));

        }else {
            throw new  AppException("Invalid name file or exceeds the number of lunches");
        }
    }

    @Async
    public void generateReport(List<Position> positions, java.lang.String numberDrone) {

        StringBuilder report = new StringBuilder();

        report.append("== Reporte de entregas ==");
        report.append(StringUtils.LF);

        positions.forEach(position -> {
            report.append("(");
            report.append(position.getCoordinateX());
            report.append(",");
            report.append(StringUtils.SPACE);
            report.append(position.getCoordinateY());
            report.append(")");
            report.append(StringUtils.SPACE);
            report.append("dirección");
            report.append(StringUtils.SPACE);
            report.append(Util.senseFormatted(position.getSense().getSense()));
            report.append(StringUtils.LF);

        });

        StringBuilder fileName = new StringBuilder();
        fileName.append("out");
        fileName.append(numberDrone);
        fileName.append(".txt");

        String pathFolder;
        try {
            pathFolder = String.format("%s%s" + routeOutputFolder, System.getProperty("user.dir"), File.separator);
            Files.write(Paths.get(pathFolder + fileName.toString()), Collections.singleton(report.toString()));
        } catch (IOException e) {
            throw new  AppException("File not Found", e);
        }
    }


    /**
     * Method that translates the code for forward movement
     *
     * @param position
     */
    private void moveForward(Position position) {
        switch (position.getSense()) {
            case NORTH:
                position.setCoordinateY(position.getCoordinateY() + 1);
                break;
            case SOUTH:
                position.setCoordinateY(position.getCoordinateY() - 1);
                break;
            case EAST:
                position.setCoordinateX(position.getCoordinateX() + 1);
                break;
            case WEST:
                position.setCoordinateX(position.getCoordinateX() - 1);
                break;
        }
    }

    /**
     * Method that translates the code to turn left
     *
     * @param position
     */
    private void turnLeft(Position position) {
        switch (position.getSense()) {
            case NORTH:
                position.setSense(Sense.WEST);
                break;
            case EAST:
                position.setSense(Sense.NORTH);
                break;
            case WEST:
                position.setSense(Sense.SOUTH);
                break;
            case SOUTH:
                position.setSense(Sense.EAST);
                break;
        }
    }

    /**
     * Method that translates the code to turn right
     *
     * @param position
     */
    private void turnRight(Position position) {
        switch (position.getSense()) {
            case NORTH:
                position.setSense(Sense.EAST);
                break;
            case EAST:
                position.setSense(Sense.SOUTH);
                break;
            case WEST:
                position.setSense(Sense.NORTH);
                break;
            case SOUTH:
                position.setSense(Sense.WEST);
                break;
        }
    }


}