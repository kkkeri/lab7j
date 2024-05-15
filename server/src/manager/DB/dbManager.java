package manager.DB;

import collection.Flat;

import java.util.Set;

public interface dbManager {
    Set<Flat> getCollectionFromDB();

    void writeCollectionToDB();
}

