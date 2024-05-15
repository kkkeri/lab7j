package manager.Command;


import exceptions.WrongArgumentException;
import manager.CollectionManager;
import manager.Receiver;
import system.Request;

/**
 * Данная команда позваляет вывести информацию о коллекции
 *
 * @see BaseCommand
 * @author keri
 * @since 1.0
 */
public class InfoCommand implements BaseCommand {
    @Override
    public String execute(Request request) throws Exception {
        try {
            return Receiver.getInfo(request);
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    @Override
    public String getName() {
        return "info ";
    }

    @Override
    public String getDescription() {
        return "Informations about collection";
    }
}
