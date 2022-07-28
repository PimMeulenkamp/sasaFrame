package nl.saxion.sasa.database;

import nl.saxion.sasa.ApplicationRunner;
import nl.saxion.sasa.SasaApplication;
import nl.saxion.sasa.anotations.Runner;

@Runner
public class DatabaseRunner implements ApplicationRunner {
    @Override
    public void run(SasaApplication application) {
        DatabaseManager manager = application.getEnvironment().getDatabaseManger();
    }
}
