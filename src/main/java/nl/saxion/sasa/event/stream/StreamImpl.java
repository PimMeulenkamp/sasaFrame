package nl.saxion.sasa.event.stream;

import lombok.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class StreamImpl implements Stream {
    private static final Logger log  = LoggerFactory.getLogger(Stream.class);
    private final List<StreamSubscription> subscriptions = new ArrayList<>();

    @Override
    public StreamSubscription listen(@NonNull StreamSubscription listener) {
        listener.setStream(this);
        listener.setStatus(StreamStatus.LISTENING);
        subscriptions.add(listener);

        log.info("[LISTENING]: {}", listener.getClass());

        return listener;
    }

    @Override
    public void removeListener(@NonNull StreamSubscription listener) {
        subscriptions.get(this.subscriptions.indexOf(listener)).setStatus(StreamStatus.STOPPED);
    }

    @Override
    public List<StreamSubscription> getListeners() {
        return this.subscriptions;
    }

    @Override
    public void dispose() {
        subscriptions.forEach(StreamSubscription::dispose);
    }

    @Override
    public void emit(Object action) {
        for (StreamSubscription listener : subscriptions) {
            switch (listener.getStatus()) {
                case STOPPED:
                case PAUSED:
                    break;
                case LISTENING:
                    listener.actionPerformed(action);
                    break;
                case DISPOSING:
                    listener.actionPerformed(action);
                    listener.setStatus(StreamStatus.STOPPED);
            }
        }
    }
}
