package nl.saxion.database;

import lombok.NonNull;
import org.jdbi.v3.core.Jdbi;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class SchematicLoaderManager {
    private final List<Loader> loaders;
    private static Logger log = LoggerFactory.getLogger(SchematicLoaderManager.class);

    public SchematicLoaderManager() {
        loaders = new ArrayList<>();
    }

    public SchematicLoaderManager add(@NonNull Loader loader) {
        this.loaders.add(loader);
        return this;
    }

    public void up(Jdbi db) {
        for (Loader loader : loaders) {
            log.info("CREATE TABLE FOR: {}", loader.getClass().getName());
            loader.up(db);
        }
    }

    public void down(Jdbi db) {
        for (Loader loader : loaders) {
            log.info("DROPPING TABLE FOR: {}", loader.getClass().getName());
            loader.down(db);
        }
    }
}
