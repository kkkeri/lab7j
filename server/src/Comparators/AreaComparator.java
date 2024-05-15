package Comparators;

import collection.Flat;

import java.util.Comparator;
/**
 * Сравнение по области
 *
 * @author keri
 * @since 1.0
 */
public class AreaComparator implements Comparator<Flat> {
    @Override
    public int compare(Flat flatOne, Flat flatTwo) {
        return flatOne.getArea().compareTo(flatTwo.getArea());
    }
}
