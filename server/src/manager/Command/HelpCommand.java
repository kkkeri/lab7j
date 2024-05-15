package manager.Command;
import java.util.LinkedHashMap;
import manager.*;
import system.Request;

/**
 * Данная команда выводит все команды и краткое их содержание
 *
 * @see BaseCommand
 * @author keri
 * @since 1.0
 */
public class HelpCommand implements BaseCommand{

    @Override
    public String execute(Request request) throws Exception {
        try {
            return Receiver.getHelp(request);
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    @Override
    public String getName() {
        return "help ";
    }

    @Override
    public String getDescription() {
        return "Command to get information";
    }
}
