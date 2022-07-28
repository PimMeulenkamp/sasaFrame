package nl.saxion.sasa.event;

import lombok.Getter;
import lombok.NoArgsConstructor;
import nl.saxion.sasa.event.stream.Stream;
import nl.saxion.sasa.event.stream.StreamImpl;

import java.util.ArrayList;
import java.util.List;

public abstract class EventHandler {
    @Getter
    private final Stream stream;


    public EventHandler() {
        this.stream = new StreamImpl();
    }

    public abstract void handle(SasaEventContext ctx);

    public void emit(Object o) {
        this.stream.emit(o);
    }
}
