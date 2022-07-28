package nl.saxion.sasa.event;

import java.util.List;
import java.util.UUID;

public interface SasaEventContext {
    String getPrefix();

    List<String> getArgs();

    String getSuffix();
}
