package manager;
import collection.*;
import exceptions.WrongArgumentException;

import java.util.Iterator;
import java.util.LinkedHashSet;
/**
 * Класс для проверки на валидность полей объекта Vehicle и для проверки входных данных из файла
 *
 * @author keri
 * @since 1.0
 */
public class Validator {

    LinkedHashSet<Flat> flatList;
    public Validator(LinkedHashSet<Flat> flatList){
        this.flatList = flatList;
    }
    public static void validateName(String arg, String data) throws WrongArgumentException{
        if (arg.isEmpty() || arg.trim().isEmpty()){
            throw new WrongArgumentException(data);
        }
    }
    public static void coordinateX(String arg) throws WrongArgumentException{
        long x = Long.parseLong(arg);
        if (x > 668 || x >= Long.MAX_VALUE ) {
            throw new WrongArgumentException("x");
        }
    }
    public static void coordinatesY(String arg) throws WrongArgumentException{
        Double y = Double.parseDouble(arg);
        if (y > 431 || y >= Double.MAX_VALUE || y == null){
            throw new WrongArgumentException("y");
        }
    }

    public static void validateArea(String arg) throws WrongArgumentException{
        long area = Long.parseLong(arg);
        if (area >= 672 || area < 0 ) {
            throw new WrongArgumentException("area");
        }
    }

    public static void validatenumberOfRooms(String arg) throws WrongArgumentException{
        Integer numberOfRooms = Integer.parseInt(arg);
        if (numberOfRooms < 0 || numberOfRooms >= Integer.MAX_VALUE ) {
            throw new WrongArgumentException("number of rooms");
        }
    }
    public static void validateFurnish(String arg) throws WrongArgumentException{
        try {
            Furnish.valueOf(arg.toUpperCase());
        } catch (Exception e){
            throw new WrongArgumentException("furnish");
        }
    }
    public static void validateView(String arg) throws WrongArgumentException{
        try {
            View.valueOf(arg.toUpperCase());
        } catch (Exception e){
            throw new WrongArgumentException("view");
        }
    }
    public static void validateTransport(String arg) throws WrongArgumentException{
        try {
            Transport.valueOf(arg.toUpperCase());
        } catch (Exception e){
            throw new WrongArgumentException("transport");
        }
    }
    public static void validatename(String arg) throws WrongArgumentException{
        if (arg.isEmpty() || arg.trim().isEmpty()){
            throw new WrongArgumentException("name");
        }
    }
    public static void validateYear(String arg) throws WrongArgumentException{
        Integer year = Integer.parseInt(arg);
        if (year < 0 || year >= Integer.MAX_VALUE ) {
            throw new WrongArgumentException("year");
        }
    }
    public static void validatenumberOfFlatsOnFloor(String arg) throws WrongArgumentException{
        Long numberOfFlatsOnFloor = Long.parseLong(arg);
        if (numberOfFlatsOnFloor < 0 || numberOfFlatsOnFloor >= Long.MAX_VALUE ) {
            throw new WrongArgumentException("number of flats on floor");
        }
    }

}

