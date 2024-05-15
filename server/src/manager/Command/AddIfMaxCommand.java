package manager.Command;

import Comparators.AreaComparator;
import Comparators.NumberOfFlatsOnFloorComparator;
import Comparators.NumberOfRoomsComparator;
import collection.Flat;
import exceptions.WrongArgumentException;
import manager.CollectionManager;
import manager.Receiver;
import system.Request;

import java.util.LinkedHashSet;
/**
 * Данная команда добавляет элемент в коллекцию, если больше всех в коллекции
 *
 * @see BaseCommand
 * @author keri
 * @since 1.0
 */

public class AddIfMaxCommand implements BaseCommand{
    @Override
    public String execute(Request request) throws Exception {
        try {
            return Receiver.addIfMax(request);
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    @Override
    public String getName() {
        return "add_if_max {element} ";
    }

    @Override
    public String getDescription() {
        return "Adds if more";
    }
}
