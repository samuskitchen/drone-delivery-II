package com.s4n.dronedeliveryII.controller;

import com.s4n.dronedeliveryII.exception.AppException;
import com.s4n.dronedeliveryII.model.Position;
import com.s4n.dronedeliveryII.service.DomicileService;
import com.s4n.dronedeliveryII.service.DroneService;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.s4n.dronedeliveryII.util.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class DomicileController {

    private static final Logger LOGGER = LoggerFactory.getLogger(DomicileController.class);

    @Autowired
    private DroneService droneService;

    @Autowired
    private DomicileService domicileService;

    @Value("${route.entry.folder}")
    private String routeEntryFolder;

    @Value("${route.output.folder}")
    private String routeOutputFolder;

    @Value("${drone.limit}")
    private Integer droneLimit;

    @Value("${drone.limit.lunch}")
    private Integer droneLimitLunch;

    private ExecutorService executorService;

    public void readFiles() {

        executorService = Executors.newFixedThreadPool(droneLimit);

        String pathFolder = String.format("%s%s" + routeEntryFolder, System.getProperty("user.dir"), File.separator);
        try (Stream<Path> walk = Files.walk(Paths.get(pathFolder))) {

            List<String> result = walk.filter(Files::isRegularFile)
                    .map(path -> path.toString()).collect(Collectors.toList());

            if(result.size() > droneLimit){
                throw new AppException("Only one drone is allowed for delivery");
            }

            result.forEach(files -> {

                try {
                    List<String> routes = droneService.pathsPerFile(files);

                    String numberDrone = Util.getNumberDrone(files);
                    CompletableFuture<List<Position>> positions = domicileService.moveDrone(routes, files);

                    domicileService.generateReport(positions.get(), numberDrone);
                } catch (InterruptedException | ExecutionException e) {
                    LOGGER.error(e.getMessage());
                }

            });
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
        }
    }
}