package agh.cs.project;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

class Main {
    private static final String PARAMETERS_JSON_PATH = "\\Resources\\parameters.json";

    public static void main(String[] args) {
        int[] parameters = loadParameters();
        Map map = new Map(parameters);
        MapSimulation simulation = new MapSimulation(map);
        simulation.startGame();
    }

    private static int[] loadParameters() {
        JSONParser jsonParser = new JSONParser();
        int[] parameters = new int[5];
        String parametersPath = new File("").getAbsolutePath();
        parametersPath = parametersPath.concat(PARAMETERS_JSON_PATH);


        try {
            FileReader fileReader = new FileReader(parametersPath);
            JSONObject jsonObject = (JSONObject) jsonParser.parse(fileReader);
            parameters[0] = (int) (long) jsonObject.get("mapSize");
            parameters[1] = (int) (long) jsonObject.get("startLength");
            parameters[2] = (int) (long) jsonObject.get("numOfStartingObstacles");
            parameters[3] = (int) (long) jsonObject.get("snakeGrowth");
            parameters[4] = (int) (long) jsonObject.get("objectSize");

        } catch (FileNotFoundException e) {
            System.out.println("File not found.");
            System.exit(-1);
        } catch (IOException e) {
            System.out.println("Input error.");
            System.exit(-1);
        } catch (ParseException e) {
            System.out.println("Error in file structure.");
            System.exit(-1);
        }

        return parameters;
    }
}
