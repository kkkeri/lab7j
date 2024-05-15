package manager;

import clientLog.ClientHandler;
import collection.Flat;
import exceptions.NoElementException;
import exceptions.WrongArgumentException;
import manager.Command.BaseCommand;
import manager.DB.PSQLmanager;
import system.Request;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;


public class Receiver {
    public static String addEl(Request request) throws WrongArgumentException {
        if (request.getMessage().split(" ").length == 1 ) {
            PSQLmanager manager = new PSQLmanager();
            Flat obj = request.getFlat();
            obj.setCreationDate(new Date());
            long generatedId = manager.writeObjToDB(obj);
            if (generatedId != -1) {
                CollectionManager.getInstance().loadCollectionFromDB();
            }
            return "Element was added";
        }else throw new WrongArgumentException("Add command must not contain arguments");
    }

    public static String addIfMax(Request request) throws WrongArgumentException {
        if(request.getMessage().split(" ").length == 1) {
            return CollectionManager.addIfMax(request);
        } else {
            throw new WrongArgumentException("AddIfMax command must not contain arguments");
        }
    }

    public static String addIfMin(Request request) throws WrongArgumentException {
        if(request.getMessage().split(" ").length == 1) {
            return CollectionManager.addIfMin(request);
        } else {
            throw new WrongArgumentException("AddIfMax command must not contain arguments");
        }
    }

    public static String updateId(Request request) throws WrongArgumentException {
        if(request.getMessage().split(" ").length == 2) {
            PSQLmanager manager = new PSQLmanager();
            long inputEl = Long.parseLong(request.getKey());
            Flat obj = request.getFlat();
            obj.setCreationDate(new Date());

            if (manager.isFlatOwnedByUser(inputEl)) {
                if (manager.updateFlat(obj)) {
                    CollectionManager.getInstance().loadCollectionFromDB();
                    return "element was updated";
                }
                return "Failed to update element";
            }
            else {
                return "Is not your flat";
            }
        } else {
            throw new WrongArgumentException("updateId command must contain only one required argument");
        }
    }

    public static String clearCollection(Request request) throws WrongArgumentException {
        if(request.getMessage().split(" ").length == 1) {
            PSQLmanager manager = new PSQLmanager();
            List<Long> deletedFlatId = manager.clearFlatForUser();
            if(!deletedFlatId.isEmpty()) {
                CollectionManager.getInstance().getCollection().removeIf(flat -> deletedFlatId.contains(flat.getId()));
                return "Collection cleared";
            } else {
                return "Collection already cleared your elements was deleted";
            }
        } else {
            throw new WrongArgumentException("Clear command must not contain arguments");
        }
    }

    public static String register(Request request) {
        if (request.getName() != null && !request.getName().isEmpty() &&
                request.getPasswd() != null && request.getPasswd().length > 0) {
            ClientHandler clientHandler = new ClientHandler(request.getName(), request.getPasswd());
            if (clientHandler.regUser()) {
                return "Registration successful";
            } else {
                return "Registration failed: username may already exists or other DB error";
            }
        }
        return "Invalid input: username or password missing";
    }

    public static String login(Request request) {
        if (request.getName() != null && !request.getName().isEmpty() &&
                request.getPasswd() != null && request.getPasswd().length > 0) {
            ClientHandler clientHandler = new ClientHandler(request.getName(), request.getPasswd());
            if (clientHandler.authUser()) {
                return "Authentication successful. User ID: " + ClientHandler.getUserId();
            } else {
                return "Authentication failed. Wrong username or password.";
            }
        }
        return "Invalid input: username or password missing";
    }

    public static String exit(Request request) throws WrongArgumentException {
        if(request.getMessage().split(" ").length == 1) {
            System.exit(1);
            return "";
        } else {
            throw new WrongArgumentException("Exit command must not contain arguments");
        }
    }

    public static String removeId(Request request) throws NoElementException{
        if(request.getMessage().split(" ").length == 2) {
            PSQLmanager manager = new PSQLmanager();
            long EL = Long.parseLong(request.getKey());
            if (manager.isFlatOwnedByUser(EL)) {
                if (manager.removeFlatById(EL)) {
                    CollectionManager.getInstance().loadCollectionFromDB();
                    return "Element was removed";
                }
            } else {
                return "Is not your flat";
            }

            return "No flat with id: " + EL;
        } else {
            throw new NoElementException("removeById command must contain only one required argument");
        }
    }

    public static String getInfo(Request request) throws WrongArgumentException {
        StringBuilder text = new StringBuilder("");
        if (request.getMessage().split(" ").length == 1) {
            text.append("Data type: " + CollectionManager.getInstance().getCollection().getClass());
            text.append("\nInit data: " + CollectionManager.getInstance().getCreationDate());
            text.append("\nSize: " + CollectionManager.getInstance().getCollection().size());
            return text.toString();
        } else {
            throw new WrongArgumentException("Info command must not contain arguments");
        }
    }

    public static String getHelp(Request request) throws WrongArgumentException {
        StringBuilder text = new StringBuilder("");
        if (request.getMessage().split(" ").length == 1) {
            LinkedHashMap<String, BaseCommand> commandList = CommandManager.getCommandList();
            int maxNameLenght = 0;
            for (String name : commandList.keySet()) {
                if (name.length() > maxNameLenght) {
                    maxNameLenght = name.length();
                }
            }
            String formatString = "%-" + (maxNameLenght + 2) + "s - %s\n";
            for (String name : commandList.keySet()) {
                if(!name.equals("save")) {
                    BaseCommand command = commandList.get(name);
                    text.append(String.format(formatString, command.getName(), command.getDescription()));
                }
            }
            String executeScriptDescription = "Execute script from file.";
            String historyDescription = "History of your last eight commands";
            text.append(String.format(formatString, "executeScript {file}", executeScriptDescription));
            text.append(String.format(formatString, "history", historyDescription));
            return text.toString();
        } else {
            throw new WrongArgumentException("Help command must not contain arguments");
        }
    }


    public static String showData(Request request) throws WrongArgumentException {
        if (request.getMessage().split(" ").length == 1) {
            return CollectionManager.getInstance().showCollection();
        } else {
            throw new WrongArgumentException("show command must not contain arguments");
        }
    }

    public static String countByHouse(Request request) throws WrongArgumentException {
        if (request.getMessage().split(" ").length == 2) {
            return CollectionManager.countByHouse(request);
        } else {
            throw new WrongArgumentException("countByHouse command must contain only one required argument");
        }
    }

    public static String filterStartsWithName(Request request) throws WrongArgumentException {
        if(request.getMessage().split(" ").length == 2) {
            return CollectionManager.filterStartsWithName(request);
        } else {
            throw new WrongArgumentException("filterStartsWithName command must contain only one required argument");
        }
    }

    public static String filterContainsName(Request request) throws WrongArgumentException {
        if(request.getMessage().split(" ").length == 2) {
            return CollectionManager.filterContainsName(request);
        } else {
            throw new WrongArgumentException("filterContainsName command must contain only one required argument");
        }
    }
}

