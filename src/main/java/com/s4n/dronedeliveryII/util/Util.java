package com.s4n.dronedeliveryII.util;

import com.s4n.dronedeliveryII.exception.AppException;
import com.s4n.dronedeliveryII.model.Position;
import com.s4n.dronedeliveryII.model.enums.Movement;
import org.apache.commons.text.WordUtils;

public class Util {

    public static String senseFormatted(String sense) {
        String senseCapitalLetters = sense.toLowerCase();
        String senseFormatted = WordUtils.capitalize(senseCapitalLetters);
        return senseFormatted;
    }

    public static Boolean validateNameFile(String filePath){
        String file = filePath.substring(filePath.lastIndexOf('/') + 1);
        if ("in.txt".equals(file)){
            return false;
        }else{
            String numberDrone = file.substring(2,4);
            String fileName = "in" + numberDrone + ".txt";

            if(fileName.equals(file)){
                return true;
            }
        }

        return false;
    }

    public static Movement validateMovement(char ch) {
        Movement movement;
        try {
            movement = Movement.valueOf(String.valueOf(ch));
        } catch (Exception e) {
            throw new AppException("Invalid Move");
        }
        return movement;
    }

    public static boolean validateBlocks(Position position) {
        boolean result= true;

        if (position.getCoordinateX() > 10 || position.getCoordinateX() < -10) {
            result = false;
        }
        else if (position.getCoordinateY() > 10 || position.getCoordinateY() < -10) {
            result = false;
        }
        return result;
    }

    public static String getNumberDrone(String filePath){
        String file = filePath.substring(filePath.lastIndexOf('/') + 1);
        return file.substring(2,4);
    }
}