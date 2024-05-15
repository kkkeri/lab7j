package manager.Command;


import collection.Flat;
import exceptions.NoArgumentException;
import exceptions.NoElementException;
import exceptions.WrongArgumentException;
import manager.CollectionManager;
import manager.Receiver;
import system.Request;

import java.util.Date;
import java.util.LinkedHashSet;

/**
 * Данная команда обновляет значения полей элемента по его ID
 *
 * @see BaseCommand
 * @author keri
 * @since 1.0
 */
public class UpdateIdCommand implements BaseCommand{
    @Override
    public String execute(Request request) throws Exception{
        try {
            return Receiver.updateId(request);
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    @Override
    public String getName() {
        return "updateId {element} ";
    }

    @Override
    public String getDescription() {
        return "Update element";
    }
}
