package Comparators;

import collection.Flat;

import java.util.Comparator;
/**
 * Сравнение по колл-во квартир на этаже
 *
 * @author keri
 * @since 1.0
 */
public class NumberOfFlatsOnFloorComparator implements Comparator<Flat> {
    @Override
    public int compare(Flat flatOne, Flat flatTwo) {
        return flatOne.getHouse().getNumberOfFlatsOnFloor().compareTo(flatTwo.getHouse().getNumberOfFlatsOnFloor());
    }
}
