package nl.saxion.sasa;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.UUID;

public class SasaApplicationOptions {
    @Getter
    private final int port;
    @Getter
    private final String name;
    @Getter
    private final boolean isClient;

    @Getter
    private final String host;

    private SasaApplicationOptions(int port, String host, String name, boolean isClient) {
        this.port = port;
        this.host = host;
        this.name = name;
        this.isClient = isClient;
    }

    public static Builder builder() {
        return new Builder();
    }

    @Setter
    @Accessors(chain = true)
    public static final class Builder {
        private int port = 50000;
        private String name = UUID.randomUUID().toString();
        private boolean isClient = false;

        private String host = "localhost";
        public Builder() {};

        public SasaApplicationOptions build() {
            return new SasaApplicationOptions(port, host, name, isClient);
        }
    }

    public static SasaApplicationOptions DEFAULT() {
        return SasaApplicationOptions.builder().build();
    }
}
