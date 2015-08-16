package pl.styx.trello.models;

import android.support.annotation.NonNull;

import java.sql.SQLException;
import java.util.List;

import pl.styx.trello.models.retrofit.TrelloCard;
import pl.styx.trello.models.retrofit.TrelloList;
import pl.styx.trello.utils.DatabaseHelper;

public class TrelloCardManager {
    private final DatabaseHelper db;

    public TrelloCardManager(DatabaseHelper db) {
        this.db = db;
    }


    public void add(@NonNull List<TrelloCard> telloCard) throws SQLException {
        int size = telloCard.size();
        for (int i = 0; i < size; i++) {
            db.getTrelloCard().createOrUpdate(telloCard.get(i));
        }
    }

    public List<TrelloCard> getAll() throws SQLException {
        return db.getTrelloCard().queryForAll();
    }
}


