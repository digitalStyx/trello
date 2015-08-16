package pl.styx.trello;

import android.content.Context;

import com.squareup.otto.Bus;
import com.squareup.otto.ThreadEnforcer;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import pl.styx.trello.models.TrelloCardManager;
import pl.styx.trello.models.TrelloListManager;
import pl.styx.trello.services.SynchronizeTrello;
import pl.styx.trello.utils.DatabaseHelper;

@Module(injects = {
        MainActivity.class,
        SynchronizeTrello.class
}, library = true)
public class MainModuleAdapter {
    private final Context context;

    public MainModuleAdapter(Context context) {
        this.context = context;
    }

    @Provides
    public Context provideContext() {
        return context;
    }

    @Singleton
    @Provides
    public Bus provideBus() {
        return new Bus(ThreadEnforcer.ANY);
    }

    @Provides
    @Singleton
    public DatabaseHelper provideDatabaseHelper(Context context) {
        return new DatabaseHelper(context);
    }

    @Provides
    @Singleton
    public TrelloListManager provideTodoListManager(DatabaseHelper db) {
        return new TrelloListManager(db);
    }

    @Provides
    @Singleton
    public TrelloCardManager provideTodoCardManager(DatabaseHelper db) {
        return new TrelloCardManager(db);
    }





}