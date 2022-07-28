package nl.saxion.database;

import org.jdbi.v3.core.Jdbi;

public interface Loader {
    void up(Jdbi db);

    void down(Jdbi db);
}
