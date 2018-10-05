package com.maurdan.flaco.udacitynd_project2_popularmovies.util;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class AppExecutors {

    private static AppExecutors sInstance;
    private final Executor diskIO;
    private final Executor networkIO;

    private AppExecutors(Executor diskIO, Executor networkIO) {
        this.diskIO = diskIO;
        this.networkIO = networkIO;
    }

    private static AppExecutors getInstance() {
        if (sInstance == null) {
            synchronized (new Object()) {
                sInstance = new AppExecutors(Executors.newSingleThreadExecutor(),
                        Executors.newFixedThreadPool(2));
            }
        }
        return sInstance;
    }

    public Executor getDiskIO() {
        return diskIO;
    }

    public Executor getNetworkIO() {
        return networkIO;
    }
}
