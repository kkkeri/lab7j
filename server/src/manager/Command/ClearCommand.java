package manager.Command;

import exceptions.WrongArgumentException;
import manager.CollectionManager;
import manager.Receiver;
import system.Request;

/**
 * Данная команда очищает коллекцию
 *
 * @see BaseCommand
 * @author keri
 * @since 1.0
 */
public class ClearCommand implements BaseCommand{
    public String execute(Request request) throws Exception{
        try {
            return Receiver.clearCollection(request);
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    public String getName(){
        return "clear ";
    }
    public String getDescription() {
        return "Command to clear colletion";
    }
}
