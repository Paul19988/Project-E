package uk.co.paulcodes.projecte;

/**
 * Created by paulb on 01/11/2017.
 */
public enum Screens {

    OFFLINE("ProjectE-Offline.png", 1),
    RESETTING("ProjectE-Resetting.png", 2),
    MAINTENANCE("ProjectE-Maintenance.png", 3),
    WAITING("ProjectE-Waiting.png", 4),
    WAITINGFORPLAYERS("ProjectE-WaitingPlayers.png", 5);

    String filename;
    int index;

    Screens(String filename, int index) {
        this.filename = filename;
        this.index = index;
    }

    public String getFilename() {
        return filename;
    }

    public int getIndex() {
        return index;
    }
}
