package generators;



import collection.*;

import exceptions.WrongArgumentException;
import manager.Validator;


import java.util.Date;
import java.util.Scanner;

/**
 * Класс генерирует объект Flat
 *
 * @author keri
 * @see Flat
 * @since 1.0
 */
public class FlatGenerator {
    /**
     * Метод поэтапно запрашивает данные и проверяет их для создания объекта
     *
     * @see Flat
     * @since 1.0
     */
    public static Flat createFlat() throws Exception {
        long id = 0;
        String input, x, y, name, year, nbf;
        Coordinates coordinates;
        House house;
        Scanner scanner = new Scanner(System.in);
        Flat flat;
        flat = new Flat();
        flat.setId(id);
        flat.setCreationDate(new Date(0));

        while (true) {
            try {
                System.out.print("Input name: ");
                input = scanner.nextLine();
                Validator.validateName(input, "Name");
                flat.setName(input);
                break;
            } catch (WrongArgumentException e) {
                System.out.println(e.getMessage() + " name cannot be empty or null");
            }
        }

        while (true) {
            try {
                System.out.print("Input x: ");
                input = scanner.nextLine();
                Validator.validateName(input, "x");
                Validator.coordinateX(input);
                x = input;
                break;
            } catch (WrongArgumentException e) {
                System.out.println(e.getMessage() + " X cannot be more then 668");
            } catch (NumberFormatException e) {
                System.out.println("Please enter digit less then 668 and long");
            }
        }

        while (true) {
            try {
                System.out.print("Input y: ");
                input = scanner.nextLine();
                Validator.validateName(input, "y");
                Validator.coordinatesY(input);
                y = input;
                break;
            } catch (WrongArgumentException e) {
                System.out.println(e.getMessage() + " Y cannot be more then 431 and not null");
            } catch (NumberFormatException e) {
                System.out.println("Please enter digit less then 431 and Double");
            }
        }
        coordinates = new Coordinates(Long.parseLong(x), Double.parseDouble(y));
        flat.setCoordinates(coordinates);


        while (true) {
            try {
                System.out.print("Input area: ");
                input = scanner.nextLine();
                Validator.validateName(input, "area");
                Validator.validateArea(input);
                flat.setArea(Long.valueOf(input));
                break;
            } catch (WrongArgumentException e) {
                System.out.println(e.getMessage() + " area cannot be more then 672 and less 0");
            } catch (NumberFormatException e) {
                System.out.println("Enter the value again");
            }
        }
        while (true) {
            try {
                System.out.print("Input number of rooms: ");
                input = scanner.nextLine();
                Validator.validateName(input, "number of rooms");
                Validator.validatenumberOfRooms(input);
                flat.setNumberOfRooms(Integer.valueOf(input));
                break;
            } catch (WrongArgumentException e) {
                System.out.println(e.getMessage() + " number of rooms cannot be less then 0");
            } catch (NumberFormatException e) {
                System.out.println("Enter the value again");
            }
        }

        while (true) {
            try {
                System.out.print("Input furnish type DESIGNER, NONE, BAD, LITTLE: ");
                input = scanner.nextLine().trim().toUpperCase();
                Validator.validateName(input, "furnish type");
                Validator.validateFurnish(input);
                flat.setFurnish(Furnish.valueOf(input));
                break;
            } catch (WrongArgumentException e) {
                System.out.println(e.getMessage() + " furnish type cannot have a value other than DESIGNER, NONE, BAD, LITTLE");
            }
        }
        while (true) {
            try {
                System.out.print("Input view type YARD, PARK, NORMAL, TERRIBLE ");
                input = scanner.nextLine().trim().toUpperCase();
                Validator.validateName(input, "view type");
                Validator.validateView(input);
                flat.setView(View.valueOf(input));
                break;
            } catch (WrongArgumentException e) {
                System.out.println(e.getMessage() + " view type cannot have a value other than YARD, PARK, NORMAL, TERRIBLE");
            }
        }
        while (true) {
            try {
                System.out.print("Input transport type FEW, LITTLE, NORMAL, ENOUGH ");
                input = scanner.nextLine().trim().toUpperCase();
                Validator.validateName(input, "trasport type");
                Validator.validateTransport(input);
                flat.setTransport(Transport.valueOf(input));
                break;
            } catch (WrongArgumentException e) {
                System.out.println(e.getMessage() + " transport type cannot have a value other than FEW, LITTLE, NORMAL, ENOUGH");
            }
        }
        while (true) {
                System.out.print("Input name house: ");
                input = scanner.nextLine();
                name = input;
                break;
        }
        while (true) {
            try {
                System.out.print("Input year: ");
                input = scanner.nextLine();
                Validator.validateName(input, "year");
                Validator.validateYear(input);
                year = input;
                break;
            } catch (WrongArgumentException e) {
                System.out.println(e.getMessage() + " year cannot be less then 0");
            } catch (NumberFormatException e) {
                System.out.println("Enter the value again");
            }

        }
        while (true) {
            try {
                System.out.print("Input number of flats on floor: ");
                input = scanner.nextLine();
                Validator.validateName(input, "number of flats on floor");
                Validator.validatenumberOfFlatsOnFloor(input);
                nbf = input;
                break;
            } catch (WrongArgumentException e) {
                System.out.println(e.getMessage() + " number of flats on floor cannot be less then 0");
            } catch (NumberFormatException e) {
                System.out.println("Enter the value again");
            }

        }
        house = new House(name, Integer.parseInt(year), Long.parseLong(nbf));
        flat.setHouse(house);
        return flat;
    }
}
