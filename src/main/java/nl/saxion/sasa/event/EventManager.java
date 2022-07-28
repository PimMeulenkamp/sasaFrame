package nl.saxion.sasa.event;

import lombok.NonNull;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EventManager {
    private final Map<String, Map<String, EventHandler>> handlers;
    private final List<EventHandler> globalHandlers;

    private static final org.slf4j.Logger log = LoggerFactory.getLogger(EventManager.class);

    public EventManager() {
        this.handlers = new HashMap<>();
        this.globalHandlers = new ArrayList<>();
    }

    public void addHandler(@NonNull EventHandlerBuilder builder) {
        Map<String, EventHandler> map = new HashMap<>();

        for (String suffix : builder.getSuffixes()) {
            map.put(suffix, builder.getHandler());
            log.info("[ADDED]: {} {} ref: {}", builder.getPrefix(), suffix, builder.getHandler().getClass());
        }
        if (this.handlers.containsKey(builder.getPrefix())) {
            this.handlers.get(builder.getPrefix()).putAll(map);
        } else {
            this.handlers.put(builder.getPrefix(), map);
        }

        this.globalHandlers.add(builder.getHandler());
    }

    public void removeHandler(@NonNull Class<? extends EventHandler> handler) {
        this.getHandler(handler).getStream().dispose();
    }

    public void handle(String args) {
        log.debug("Handling event: {}", args);

        String[] split = args.split(" ");
        String prefix = split[0];
        String suffix = split[1];

        String[] argsArray = new String[split.length - 2];
        System.arraycopy(split, 2, argsArray, 0, argsArray.length);

        Map<String, EventHandler> map = this.handlers.get(prefix);
        if (map != null) {
            EventHandler handler = map.get(suffix);
            if (handler != null) {
                handler.handle(new SasaEventContextImpl(prefix, List.of(argsArray) ,suffix));
            }
        }
    }

    public EventHandler getHandler(@NonNull Class<? extends EventHandler> handler) {
        for (EventHandler h : this.globalHandlers) {
            if (h.getClass() == handler) {
                return h;
            }
        }
        throw new RuntimeException("No handler found");
    }
}
