package manager.Command;

import system.Request;

/**
 * Интерфейс для реализации команд.
 *
 * @author keri
 * @since 1.0
 */
public interface BaseCommand {
    String execute(Request request) throws Exception;

    String getName();

    String getDescription();
}
