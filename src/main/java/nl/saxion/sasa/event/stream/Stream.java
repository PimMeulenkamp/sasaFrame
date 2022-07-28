package nl.saxion.sasa.event.stream;

import java.util.List;

public interface Stream {
    StreamSubscription listen(StreamSubscription listener);

    void removeListener(StreamSubscription listener);

    List<StreamSubscription> getListeners();

    void dispose();

    void emit(Object action);
}
