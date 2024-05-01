package utils;

public abstract class Task {
    public Task() {
        super();
    }

    public abstract boolean activate();

    public abstract boolean execute();
}