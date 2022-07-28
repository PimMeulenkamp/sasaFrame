package nl.saxion.sasa;

import lombok.Getter;
import nl.saxion.ptbc.sockets.SasaClient;
import nl.saxion.ptbc.sockets.SasaConnectionException;
import nl.saxion.ptbc.sockets.SasaServer;
import nl.saxion.sasa.event.EventManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SasaApplication {
    private static Logger LOG = LoggerFactory.getLogger(SasaApplication.class);
    @Getter
    private final Class<?> primarySource;
    private final EventManager eventManager = null;

    private final SasaApplicationOptions options;

    private SasaRunListener sasaRunListener;

    private SasaServer sasaServer;
    private SasaClient sasaClient;

    @Getter
    private SasaApplicationEnvironment environment;

    @Getter
    private ApplicationRunners runners;

    public SasaApplication(Class<?> primarySource, SasaApplicationOptions options) {
        this.primarySource = primarySource;
        this.options = options;
        initializeSasaLibrary();

        this.environment = new SasaApplicationEnvironment();
        this.runners = getApplicationRunnersFromApplicationRunnerFactory();
        this.runAllRunners();
    }

    public static SasaApplication run(Class<?> primarySource) {
        return run(primarySource, SasaApplicationOptions.DEFAULT());
    }
    public static SasaApplication run(Class<?> primarySource, SasaApplicationOptions options) {
        return new SasaApplication(primarySource, options);
    }

    private ApplicationRunners getApplicationRunnersFromApplicationRunnerFactory() {
         return new ApplicationRunnerFactory(this).loadApplicationRunners();
    }

    public void runAllRunners() {
        this.runners.runners.forEach(applicationRunner -> applicationRunner.run(this));
    }

    private void initializeSasaLibrary() {
        try {
            if (this.options.isClient()) {
                this.sasaClient = new SasaClient();

                this.sasaRunListener = new SasaRunListener(this);

                this.sasaClient.subscribe(this.sasaRunListener);
                this.sasaClient.connect(this.options.getHost(), this.options.getPort());
                LOG.info("{} is connected to {}:{}", this.options.getName(), this.options.getHost(), this.options.getPort());
            } else {
                this.sasaServer = new SasaServer();
                this.sasaRunListener = new SasaRunListener(this);
                this.sasaServer.subscribe(this.sasaRunListener);
                this.sasaServer.listen(this.options.getPort());
                LOG.info("{} is listening on port {}", this.options.getName(), this.options.getPort());
            }
        } catch (SasaConnectionException e) {
            throw new RuntimeException(e);
        }
    }

    public void send(String message) {
        if (this.options.isClient()) {
            this.sasaClient.send(message);
        } else {
            this.sasaServer.send(message);
        }
    }
}
