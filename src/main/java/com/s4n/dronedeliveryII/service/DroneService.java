package com.s4n.dronedeliveryII.service;

import com.s4n.dronedeliveryII.exception.AppException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.springframework.stereotype.Service;

@Service
public class DroneService  {

    /**
     * Metodo que lee las lineas del archivo que son las rutas del drone
     *
     * @param pathFile
     * @return
     */
    public List<String> pathsPerFile(String pathFile) {
        List<String> listRoutes;

        try (Stream<String> stream = Files.lines(Paths.get(pathFile))) {
            listRoutes = stream.map(String::toUpperCase)
                    .collect(Collectors.toList());
        } catch (IOException e) {
            throw new AppException("Exception file not found or read", e);
        }

        return listRoutes;
    }
}