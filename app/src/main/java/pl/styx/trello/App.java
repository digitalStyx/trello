package pl.styx.trello;

import android.app.Application;

import dagger.ObjectGraph;

public class App extends Application {

    private static ObjectGraph objectGraph;

    @Override
    public void onCreate() {
        super.onCreate();
        objectGraph = ObjectGraph.create(new MainModuleAdapter(getApplicationContext()));
    }

    public static void inject(Object object) {
        objectGraph.inject(object);
    }
}
