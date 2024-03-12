package cue.edu.co.threads;

import cue.edu.co.services.impl.ToysServiceImpl;

import java.util.concurrent.Callable;

public class LoadSystem implements Callable<Void> {

    public LoadSystem (ToysServiceImpl toyStore) {
    }

    @Override
    public Void call() throws Exception {
        try {
            System.out.println("Starting system ...");
            Thread.sleep(1000);
            System.out.println("system loaded successfully.");
        } catch (InterruptedException e) {
            System.err.println("System interrupted.");
        } catch (Exception e) {
            System.err.println("Error while loading the system: " + e.getMessage());
        }
        return null;
    }
}
