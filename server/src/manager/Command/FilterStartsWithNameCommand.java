package manager.Command;

import collection.Flat;
import exceptions.NoElementException;
import exceptions.WrongArgumentException;
import manager.CollectionManager;
import manager.Receiver;
import system.Request;

import java.util.LinkedHashSet;
/**
 * Данная команда позваляет вывести данные коллекции, в которых названеи квартиры начинается с заданной подстроки
 *
 * @see BaseCommand
 * @author keri
 * @since 1.0
 */
public class FilterStartsWithNameCommand implements BaseCommand {

    public String execute(Request request) throws Exception {
        try {
            return Receiver.filterStartsWithName(request);
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    @Override
    public String getName() {
        return "filter_starts_with_name {name} ";
    }

    @Override
    public String getDescription() {
        return "Displays flats whose names start with the specified substring.";
    }
}
