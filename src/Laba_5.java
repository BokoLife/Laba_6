import java.io.*;
import java.util.*;

public class Laba_5 {
    public static String[] userInputMas = new String[2];
    public static ArrayList<String> commandsHistory = new ArrayList<>(7);
    public static CommandsMap commands;

    public static void main(String[] args) throws IOException {
        VehiclesCollectionKeeper vehicles = new VehiclesCollectionKeeper(ReadFile.getCollectionFromFile());
        commands = new CommandsMap(vehicles);
        UserInput.start();
    }

    public static String getCommand() {
        return userInputMas[0];
    }

    public static String getArgument() throws NullPointerException {
        if (userInputMas.length > 1) {
            return userInputMas[1];
        } else {
            throw new NullPointerException("Аргумент команды не был введен");
        }
    }

    public static CommandsMap getCommandsMap() {
        return commands;
    }
}