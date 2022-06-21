package Commands;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class CommandsMap implements Serializable {
    private Map<String, Command> commands = new HashMap<>();
    //private VehiclesCollectionKeeper collectionKeeper;

    public CommandsMap(){
        //this.collectionKeeper = collectionKeeper;
        commands.put("help", new HelpCommand());
        commands.put("info", new InfoCommand());
        commands.put("show", new ShowCommand());
        commands.put("add", null); //** {element}
        commands.put("update",  null); //** {element}
        commands.put("remove_by_id", null); //*
        commands.put("clear", null);
        commands.put("save", null); //*
        commands.put("execute_script", null); //*
        commands.put("exit", new ExitCommand());
        commands.put("remove_first", null);
        commands.put("remove_greater", null); //** {element}
        commands.put("history", null);
        commands.put("filter_greater_than_fuel_type", null); //*
        commands.put("print_ascending", new PrintAscendingCommand());
        commands.put("print_field_descending_engine_power", new PrintEnginePowerCommand());
    }

    public void put(String commandName, Command command){
        this.commands.put(commandName, command);
    }

    public Command get(String key) throws NullPointerException{
        if (key.equals("clear")) {
            commands.put("clear", new ClearCommand());
        }
        if (key.equals("remove_first")) {
            commands.put("remove_first", new RemoveFirstCommand());
        }
        if (key.equals("update")) {
            commands.put("update", new UpdateIdCommand(GetArgument.getArgument()));
        }
        if (key.equals("remove_by_id")) {
            commands.put("remove_by_id", new RemoveByIdCommand(GetArgument.getArgument()));
        }
        if (key.equals("execute_script")) {
            commands.put("execute_script", new ExecuteScript(GetArgument.getArgument()));
        }
        if (key.equals("filter_greater_than_fuel_type")) {
            commands.put("filter_greater_than_fuel_type", new GreaterFuelType(GetArgument.getArgument()));
        }
        if (key.equals("add")) {
            commands.put("add", new AddCommand());
        }
        if (key.equals("remove_greater")) {
            commands.put("remove_greater", new RemoveGreaterCommand());
        }
        if (key.equals("history")) {
            commands.put("history", new HistoryCommand(GetCommandsHistory.getCommandsHistory()));
        }
        if (key.equals("exit")) {
            System.exit(1);
        }
        return this.commands.get(key);
    }

    public Set<String> getKeys(){
        return this.commands.keySet();
    }
}
