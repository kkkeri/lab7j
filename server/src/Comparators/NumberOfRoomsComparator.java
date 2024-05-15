package Comparators;

import collection.Flat;

import java.util.Comparator;
/**
 * Сравнение по номеру квартиры
 *
 * @author keri
 * @since 1.0
 */

public class NumberOfRoomsComparator implements Comparator<Flat> {
    @Override
    public int compare(Flat flatOne, Flat flatTwo) {
        return flatOne.getNumberOfRooms().compareTo(flatTwo.getNumberOfRooms());
    }
}
