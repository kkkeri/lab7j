package exceptions;
/**
 * Обеспечивает исключение, если такой команды не существует
 *
 * @author keri
 * @since 1.0
 */

public class UnknowCommandException extends Exception{
    public UnknowCommandException(String command) {
        super("Unknow command " + command);
    }
}
