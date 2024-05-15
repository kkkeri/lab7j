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
 * Данная команда добавляет элемент в коллекцию, если его меньше всех в коллекции
 *
 * @see BaseCommand
 * @author keri
 * @since 1.0
 */

public class AddIfMinCommand implements BaseCommand{
    @Override
    public String execute(Request request) throws Exception {
        try {
            return Receiver.addIfMin(request);
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    @Override
    public String getName() {
        return "add_if_min {element} ";
    }

    @Override
    public String getDescription() {
        return "Adds if less";
    }
}