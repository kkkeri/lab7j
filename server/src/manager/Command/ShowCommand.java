package manager.Command;

import exceptions.WrongArgumentException;
import manager.CollectionManager;
import manager.Receiver;
import system.Request;

/**
 * Данная команда позваляет вывести коллекцию
 * @see BaseCommand
 * @author keri
 * @since 1.0
 */
public class ShowCommand implements BaseCommand {

    @Override
    public String execute(Request request) throws Exception{
        try {
            return Receiver.showData(request);
        } catch (Exception e) {
            return e.getMessage();
        }
    }


    @Override
    public String getName() {
        return "show ";
    }

    @Override
    public String getDescription() {
        return "Show collection";
    }
}
