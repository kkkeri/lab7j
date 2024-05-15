package clientLog;

import manager.DB.PSQLmanager;

public class ClientHandler {
    private final String name;
    private final char[] passwd;
    private static long userId;

    public ClientHandler(String name, char[] passwd) {
        this.name = name;
        this.passwd = passwd;
    }

    public boolean regUser() {
        PSQLmanager manager = new PSQLmanager();
        long id = manager.regUser(name, passwd);
        if (id > 0) {
            userId = id;
            return true;
        }
        return false;
    }

    public boolean authUser() {
        PSQLmanager manager = new PSQLmanager();
        long id = manager.authUser(name, passwd);
        if (id > 0) {
            userId = id;
            return true;
        }
        return false;
    }

    public static void isAuthUserCommand(String name, char[] passwd) {
        PSQLmanager manager = new PSQLmanager();
        long id = manager.authUser(name, passwd);
        if (id > 0) {
            userId = id;
        } else {
            System.out.println("No such user");
        }
    }

    public static long getUserId() {
        return userId;
    }
}
