import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class ExecuteScript implements Command, Serializable {
    private VehiclesCollectionKeeper collectionKeeper;
    String file_name;
    private Command command;
    private List<String> lines;
    private boolean loopChecker = true;
    private CommandsMap commandsMap = GetCommandsMap.getCommandsMap();

    public ExecuteScript(VehiclesCollectionKeeper collectionKeeper, String argument) {
        this.collectionKeeper = collectionKeeper;
        this.file_name = argument;
    }

    @Override
    public void execute() {
        Path path = Paths.get(this.file_name);
        try {
//            fis = new FileInputStream(commandsFile);
//            BufferedInputStream bis = new BufferedInputStream(fis);

            this.lines = Files.readAllLines(path);
            for (String line : lines) {
                String[] userInputMas = line.split(" ", 3);
                String command = userInputMas[0];
                if (this.commandsMap.getKeys().contains(command)) {
                    if (userInputMas.length == 2) {
                        String argument = userInputMas[1];
                        if (command.equals("execute_script")) {
                            System.out.println("Цикл оаоаоа");
                            this.loopChecker = false;
                            System.exit(1);
                        }
                        if (command.equals("update")) {
                            new UpdateIdCommand(this.collectionKeeper, argument).execute();
                        }
                        if (command.equals("remove_by_id")) {
                            new RemoveByIdCommand(this.collectionKeeper, argument).execute();
                        }
                        if (command.equals("save")) {
                            new SaveCommand(collectionKeeper, argument).execute();
                        }
                        if (command.equals("filter_greater_than_fuel_type")) {
                            new GreaterFuelType(collectionKeeper, argument).execute();
                        }
                    } else {
                        this.commandsMap.get(command).execute();
                    }
                } else {
                    System.out.println("В файле есть запросы не из списка команд");
                }
            }
        } catch (IOException e) {
            System.out.println("Файл не найден");
        }
    }
}
