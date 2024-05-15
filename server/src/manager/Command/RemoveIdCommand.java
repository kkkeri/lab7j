package manager.Command;

import exceptions.NoElementException;
import exceptions.WrongArgumentException;
import manager.CollectionManager;
import manager.Receiver;
import system.Request;

/**
 * Данная команда позваляет удалить элемент коллекции по ID
 * @see BaseCommand
 * @author keri
 * @since 1.0
 */
public class RemoveIdCommand implements BaseCommand{
    @Override
    public String execute(Request request) throws Exception {
        try {
            return Receiver.removeId(request);
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    @Override
    public String getName() {
        return "rbid ";
    }

    @Override
    public String getDescription() {
        return "Remove by id";
    }
}

