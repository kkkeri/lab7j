package manager;


import exceptions.WrongArgumentException;
import system.Request;

import java.util.Deque;

/**
 * Данная команда выводит историю введенных команд
 * Максимум 8 последних команд
 *
 * @author keri
 * @since 1.0
 */
public class HistoryCommand {
    public static void execute(Deque<String> historyCom) throws Exception {
        synchronized (historyCom) {
            if (historyCom.isEmpty()) {
                System.out.println("No commands have been executed");
            } else {
                historyCom.forEach(System.out::println);
            }
        }
    }

    public String getName() {
        return "history ";
    }

    public String getDescription() {
        return "show last eight commands";
    }
}

