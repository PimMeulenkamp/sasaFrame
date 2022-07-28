package nl.saxion.sasa;

import nl.saxion.ptbc.sockets.SasaSocketInformation;

public class SasaRunListener implements SasaSocketInformation {
    private final SasaApplication application;

    public SasaRunListener(SasaApplication application) {
        this.application = application;
    }

    @Override
    public void receivedData(String s) {
        this.application.getEnvironment().getEventManager().handle(s);
    }

    @Override
    public void statusUpdate(String s, String s1) {
    }
}
