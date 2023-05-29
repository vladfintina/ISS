package service;

public interface IBugsObserver {
    void updateReceived() throws BugException;
}
