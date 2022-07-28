package nl.saxion.sasa.event;

import lombok.*;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Accessors(chain = true)
@Getter
@NoArgsConstructor
public class EventHandlerBuilder {
    @Setter
    private EventHandler handler;

    private String prefix;

    private final List<String> suffixes = new ArrayList<>();

    public EventHandlerBuilder(EventHandler handler) {
        this.handler = handler;
    }

    public EventHandlerBuilder addSuffix(String suffix) {
        this.suffixes.add(suffix.trim().toUpperCase());
        return this;
    }

    public EventHandlerBuilder setPrefix(String prefix) {
        this.prefix = prefix.trim().toUpperCase();
        return this;
    }

    public EventHandlerBuilder addSuffix(String[] suffixes) {
        for(String suffix : suffixes) {
            this.addSuffix(suffix);
        }
        return this;
    }
}
