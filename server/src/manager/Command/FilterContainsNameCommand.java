package manager.Command;

import collection.Flat;
import exceptions.NoElementException;
import exceptions.WrongArgumentException;
import manager.CollectionManager;
import manager.Receiver;
import system.Request;

import java.util.LinkedHashSet;
/**
 * Данная команда позваляет вывести данные коллекции, в которых названеи квартиры содержит заданную подстроку
 *
 * @see BaseCommand
 * @author keri
 * @since 1.0
 */
public class FilterContainsNameCommand implements BaseCommand {

    public String execute(Request request) throws Exception {
        try {
            return Receiver.filterContainsName(request);
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    @Override
    public String getName() {
        return "filter_contains_name {name} ";
    }

    @Override
    public String getDescription() {
        return "Displays flats whose names contain the specified substring.";
    }
}
