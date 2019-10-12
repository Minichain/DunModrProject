package main;

public class ApplicationStatus {
    private static ApplicationStatus instance = null;

    public enum Status {
        TRAINING, TESTING
    }
    private Status status = Status.TRAINING;
    private boolean applicationRunning;

    private ApplicationStatus() {
    }

    public static ApplicationStatus getInstance() {
        if (instance == null) {
            instance = new ApplicationStatus();
        }
        return instance;
    }

    public void setApplicationRunning(boolean applicationRunning) {
        this.applicationRunning = applicationRunning;
    }

    public boolean isApplicationRunning() {
        return applicationRunning;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Status getStatus() {
        return status;
    }
}
