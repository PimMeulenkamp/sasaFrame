package nl.saxion.sasa.event.stream;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

public abstract class StreamSubscription {
    @Setter(AccessLevel.PACKAGE)
    private Stream stream;

    @Getter
    @Setter
    private StreamStatus status;

    public void dispose() {
        this.stream.removeListener(this);
    }

    public Stream getStream() {
        if (stream == null) {
            throw new RuntimeException("StreamSubscription has not been listing, add this subscription to the handler of choice");
        }
        return stream;
    }

    public abstract void actionPerformed(Object action);
}
