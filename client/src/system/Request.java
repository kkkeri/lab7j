package system;

import collection.Flat;

import java.io.Serializable;

public class Request implements Serializable {
    private static final long serialVersionUID = 5760575944040770143L;
    private String message = null;
    private Flat flat = null;
    private String key = null;

    private String name = null;
    private char[] passwd = null;

    public Request(String message, Flat flat, String key, String name, char[] passwd) {
        this.message = message;
        this.flat = flat;
        this.key = key;
        this.name = name;
        this.passwd = passwd;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Flat getFlat() {
        return flat;
    }

    public void setFlat(Flat flat) {
        this.flat = flat;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public char[] getPasswd() {
        return passwd;
    }

    public void setPasswd(char[] passwd) {
        this.passwd = passwd;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
