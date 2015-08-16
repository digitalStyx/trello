package pl.styx.trello.services;


import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.squareup.otto.Bus;

import java.sql.SQLException;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import javax.inject.Inject;

import pl.styx.trello.App;
import pl.styx.trello.BundleKeys;
import pl.styx.trello.events.SyncErrorEvent;
import pl.styx.trello.events.SyncSuccessEvent;
import pl.styx.trello.models.TrelloCardManager;
import pl.styx.trello.models.TrelloListManager;
import pl.styx.trello.models.retrofit.TrelloCard;
import pl.styx.trello.models.retrofit.TrelloList;
import pl.styx.trello.utils.TrelloApi;

public class SynchronizeTrello extends Service {
    @Inject TrelloListManager trelloListManager;
    @Inject TrelloCardManager trelloCardManager;

    @Inject Bus bus;

    private static Timer timerTicker = new Timer();

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    @Override
    public void onCreate() {
        super.onCreate();
        App.inject(this);
        timerTicker.scheduleAtFixedRate(new checkList(), 0, 10000);
    }


    private class checkList extends TimerTask {
        @Override
        public void run() {
            try {
                List<TrelloList> trelloLists = TrelloApi.getInstance().getService().lists(BundleKeys.BOARD_ID);
                List<TrelloCard> trelloCards = TrelloApi.getInstance().getService().cardsList(BundleKeys.BOARD_ID);

                trelloCardManager.add(trelloCards);
                trelloListManager.add(trelloLists);

                bus.post(new SyncSuccessEvent());
            } catch (SQLException e) {
                bus.post(new SyncErrorEvent());
            } catch (Exception e) {
                bus.post(new SyncErrorEvent());
            }

        }
    }
}
