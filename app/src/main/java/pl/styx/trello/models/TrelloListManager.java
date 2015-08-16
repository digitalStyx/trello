package pl.styx.trello.models;


import android.support.annotation.NonNull;

import java.sql.SQLException;
import java.util.List;

import pl.styx.trello.models.retrofit.TrelloList;
import pl.styx.trello.utils.DatabaseHelper;

public class TrelloListManager {
    private final DatabaseHelper db;

    public TrelloListManager(DatabaseHelper db) {
        this.db = db;
    }


    public void add(@NonNull List<TrelloList> telloList) throws SQLException {
        int size = telloList.size();
        for (int i = 0; i < size; i++) {
            db.getTrelloList().createOrUpdate(telloList.get(i));
        }
    }

    public List<TrelloList> getAll() throws SQLException {
        return db.getTrelloList().queryForAll();
    }
}
