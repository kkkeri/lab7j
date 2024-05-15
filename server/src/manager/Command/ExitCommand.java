package manager.Command;

import manager.Receiver;
import system.Request;

/**
 * Данная команда позваляет прервать выполнение программы
 *
 * @see BaseCommand
 * @author keri
 * @since 1.0
 */
public class ExitCommand implements BaseCommand {
    @Override
    public String execute(Request request) throws Exception {
        try {
            return Receiver.exit(request);
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    @Override
    public String getName() {
        return "exit ";
    }

    @Override
    public String getDescription() {
        return "Close command without save";
    }
}