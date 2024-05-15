package manager.Command;

import exceptions.WrongArgumentException;
import manager.CollectionManager;
import manager.Receiver;
import system.Request;

/**
 * Данная команда добавляет новый элемент в коллекцию
 *
 * @see BaseCommand
 * @author keri
 * @since 1.0
 */

public class AddCommand implements BaseCommand{
    @Override
    public String execute(Request request) throws Exception {
        try {
            return Receiver.addEl(request);
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    @Override
    public String getName() {
        return "add {element} ";
    }

    @Override
    public String getDescription() {
        return "Add new flat in collection";
    }
}
