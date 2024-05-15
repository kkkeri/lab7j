package manager;

import exceptions.UnknowCommandException;
import manager.Command.*;
import java.util.ArrayDeque;
import java.util.LinkedHashMap;

import system.Request;

/**
 * Класс обеспечивает связь между командами и CollectionManager
 *
 * @see CollectionManager
 * @author keri
 * @since 1.0
 */
public class CommandManager {
    private static LinkedHashMap<String, BaseCommand> commandList;

    public static ArrayDeque<BaseCommand> lastEightCommand = new ArrayDeque<>();

    public CommandManager() {
        commandList = new LinkedHashMap<>();
        commandList.put("help", new HelpCommand());
        commandList.put("show",new ShowCommand());
        commandList.put("clear", new ClearCommand());
        commandList.put("exit", new ExitCommand());
        commandList.put("info", new InfoCommand());
        commandList.put("rbid", new RemoveIdCommand());
        commandList.put("count_by_house", new CountByHouse());
        commandList.put("add", new AddCommand());
        commandList.put("updateId", new UpdateIdCommand());
        commandList.put("add_if_max", new AddIfMaxCommand());
//        commandList.put("save", new SaveCommand());
        commandList.put("add_if_min", new AddIfMinCommand());
        commandList.put("filter_contains_name", new FilterContainsNameCommand());
        commandList.put("filter_starts_with_name", new FilterStartsWithNameCommand());
        commandList.put("register", new RegisterCommand());
        commandList.put("login", new LoginCommand());
    }
    private static String startExecuting(Request request) throws Exception{
        String commandName = request.getMessage().split(" ")[0];
        if (!commandList.containsKey(commandName)){
            throw new UnknowCommandException(commandName);
        }
        BaseCommand command = commandList.get(commandName);
        String message = command.execute(request);
        if (lastEightCommand.size() == 8){
            lastEightCommand.pop();
            lastEightCommand.addLast(command);
        } else {
            lastEightCommand.addFirst(command);
        }
        return message;
    }

    public static String startExecutingClientMode(Request request) {
        try {
            if (!request.getMessage().equals("exit") && !request.getMessage().equals("save")){
                return startExecuting(request);
            }
            return "Unknown command";
        } catch (Exception e){
            return e.getMessage();
        }
    }

    public static void startExecutingServerMode(Request request) {
        try {
            if (request.getMessage().equals("exit") || request.getMessage().equals("save")){
                startExecuting(request);
            }
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    public static LinkedHashMap<String, BaseCommand> getCommandList() {
        return commandList;
    }
}
