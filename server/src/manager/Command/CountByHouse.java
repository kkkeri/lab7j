package manager.Command;

import collection.Flat;
import collection.House;
import exceptions.NoElementException;
import exceptions.WrongArgumentException;
import manager.CollectionManager;
import manager.Receiver;
import system.Request;

import java.util.LinkedHashSet;
/**
 * Данная команда cчитает коллекции с одинаковым именем
 *
 * @see BaseCommand
 * @author kei
 * @since 1.0
 */
public class CountByHouse implements BaseCommand{
    @Override
    public String execute(Request request) throws Exception {
       try {
           return Receiver.countByHouse(request);
       }catch (Exception e) {
           return e.getMessage();
       }
    }

    @Override
    public String getName() {
        return "count_by_house ";
    }

    @Override
    public String getDescription() {
        return "Displays the number of houses with the same name";
    }
}
