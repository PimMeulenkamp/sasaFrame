package nl.saxion.sasa;

import lombok.Getter;
import lombok.Setter;
import nl.saxion.sasa.database.DatabaseManager;
import nl.saxion.sasa.event.EventManager;

@Getter
@Setter
public class SasaApplicationEnvironment {
    private EventManager eventManager;

    private DatabaseManager databaseManger;
}
