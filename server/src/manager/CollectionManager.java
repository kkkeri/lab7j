package manager;
import Comparators.AreaComparator;
import Comparators.NumberOfFlatsOnFloorComparator;
import Comparators.NumberOfRoomsComparator;
import collection.*;
import manager.DB.PSQLmanager;
import system.Request;

import java.util.*;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 * Класс отвечает за взаимодействие с коллекцией на базовом уровне
 *
 * @see Flat
 * @author keri
 * @since 1.0
 */
public class CollectionManager {
    private static CollectionManager instance;

    private static Set<Flat> flatCollection;
    private final java.util.Date creationDate;

    private CollectionManager() {
        flatCollection = new CopyOnWriteArraySet<>();
        creationDate = new java.sql.Date(new Date().getTime());
    }

    public static synchronized CollectionManager getInstance() {
        if (instance == null) {
            instance = new CollectionManager();
        }
        return instance;
    }


    public void loadCollectionFromDB() {
        synchronized (flatCollection) {
            PSQLmanager manager = new PSQLmanager();
            Set<Flat> flats = manager.getCollectionFromDB();
            flatCollection.clear();
            flatCollection.addAll(flats);
        }
        Logger.getLogger(CollectionManager.class.getName()).info("Collection reloaded from DB");
    }

    public void writeCollectionToDB() {
        PSQLmanager dbmanager = new PSQLmanager();
        dbmanager.writeCollectionToDB();
    }


    public Date getCreationDate() {
        return creationDate;
    }

    public Set<Flat> getCollection() {
        return flatCollection;
    }

    public void setFlatCollection(Set<Flat> newFlatCollection) {
        flatCollection.clear();
        flatCollection.addAll(newFlatCollection);
    }

    public static void addToCollection(Flat flat) {
        synchronized (flatCollection) {
            if(!flatCollection.contains(flat)) {
                flatCollection.add(flat);
                Logger.getLogger(CollectionManager.class.getName()).info("Flat was added" + flat);
            } else {
                Logger.getLogger(CollectionManager.class.getName()).warning("Attempt to add duplicate flat: " + flat);
            }
        }
    }

    public String showCollection() {
        if (flatCollection.isEmpty()) {
            return "Collection is empty";
        }

        String result = flatCollection.stream()
                .map(Flat::toString)
                .collect(Collectors.joining("\n"));
        return result;
    }

    public static String addIfMax(Request request) {
        AreaComparator comparator = new AreaComparator();
        NumberOfRoomsComparator comparator1 = new NumberOfRoomsComparator();
        NumberOfFlatsOnFloorComparator comparator2 = new NumberOfFlatsOnFloorComparator();
        if(flatCollection.isEmpty() || (flatCollection.stream().allMatch(flat -> comparator.compare(flat,request.getFlat()) < 0) && flatCollection.stream().allMatch(flat -> comparator1.compare(flat,request.getFlat()) <0) && flatCollection.stream().allMatch(flat -> comparator2.compare(flat,request.getFlat()) < 0))) {
            PSQLmanager manager = new PSQLmanager();
            Flat obj = request.getFlat();
            obj.setCreationDate(new Date());
            long generatedId = manager.writeObjToDB(obj);
            if (generatedId != 1) {
                CollectionManager.getInstance().loadCollectionFromDB();
            }
        } else {
            return "new element has lower then max!";
        }
        return "successfully added";
    }

    public static String addIfMin(Request request) {
        AreaComparator comparator = new AreaComparator();
        NumberOfRoomsComparator comparator1 = new NumberOfRoomsComparator();
        NumberOfFlatsOnFloorComparator comparator2 = new NumberOfFlatsOnFloorComparator();
        if(flatCollection.isEmpty() || (flatCollection.stream().allMatch(flat -> comparator.compare(flat,request.getFlat()) > 0) && flatCollection.stream().allMatch(flat -> comparator1.compare(flat,request.getFlat()) >0) && flatCollection.stream().allMatch(flat -> comparator2.compare(flat,request.getFlat()) > 0))) {
            PSQLmanager manager = new PSQLmanager();
            Flat obj = request.getFlat();
            obj.setCreationDate(new Date());
            long generatedId = manager.writeObjToDB(obj);
            if (generatedId != 1) {
                CollectionManager.getInstance().loadCollectionFromDB();
            }
        } else {
            return "Element greater then min";
        }
        return "successfully added";
    }

//    public static String RemoveId(Request request) throws NoElementException{
//        if (flatCollection.isEmpty()) {
//            return "Collection is empty";
//        }
//        long id = Long.parseLong(request.getKey());
//        Optional<Flat> vehicleOptional = flatCollection.stream()
//                .filter(vehicle -> vehicle.getId() == id)
//                .findFirst();
//        if(vehicleOptional.isPresent()) {
//            flatCollection.remove(vehicleOptional.get());
//            GeneratorID.remove(id);
//            return "Element with id " + id + " was successfully removed";
//        } else {
//            throw new NoElementException(id);
//        }
//    }

    public static String filterContainsName(Request request) {
        Set<Flat> flatCollection = CollectionManager.getInstance().getCollection();
        String enterName = request.getKey();

        String result = flatCollection.stream()
                .filter(flat -> flat.getName().contains(enterName))
                .map(Flat::toString)
                .collect(Collectors.joining("\n"));
        if(result.isEmpty()) {
            return ("No flats names containing: " + enterName);
        } else {
            return result;
        }
    }

    public static String filterStartsWithName(Request request) {
        Set<Flat> flatCollection = CollectionManager.getInstance().getCollection();
        String enterName = request.getKey();

        String result = flatCollection.stream()
                .filter(flat -> flat.getName().startsWith(enterName))
                .map(Flat::toString)
                .collect(Collectors.joining("\n"));
        if(result.isEmpty()) {
            return ("No flats names containing: " + enterName);
        } else {
            return result;
        }
    }

    public static String countByHouse(Request request) {
        Set<Flat> flatCollection = CollectionManager.getInstance().getCollection();
        String enterName = request.getKey();

        long count = flatCollection.stream()
                .filter(vehicle -> vehicle.getHouse().getName().equalsIgnoreCase(enterName))
                .count();
        return ("Coincidences: " + count);
    }

//    public static String updateId(Request request) {
//        try {
//            LinkedHashSet<Flat> FlatCollection = CollectionManager.getInstance().getCollection();
//            long inputEl = Long.parseLong(request.getKey());
//            if (flatCollection.isEmpty()) {
//                return "Collection is empty";
//            }
//
//            Optional<Flat> vehicleToUpdate = flatCollection.stream()
//                    .filter(flat -> flat.getId() == inputEl)
//                    .findFirst();
//
//            if (!vehicleToUpdate.isPresent()) {
//                return "No element with id " + inputEl + " found in the collection";
//            }
//
//            Flat updatedFlat = request.getFlat();
//            updatedFlat.setIdForUpdate(inputEl);
//            updatedFlat.setCreationDate(vehicleToUpdate.get().getCreationDate());
//            RemoveId(request);
//            add(updatedFlat);
//            return "Flat with id " + inputEl + " was successfully updated";
//        }catch (NumberFormatException e) {
//            return "Please enter digit";
//        }catch (NoElementException e) {
//            return e.getMessage();
//        }
//    }

    public void clearCollection() { flatCollection.clear();}

}
