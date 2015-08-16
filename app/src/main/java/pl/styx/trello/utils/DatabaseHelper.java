package pl.styx.trello.utils;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

import pl.styx.trello.models.retrofit.TrelloCard;
import pl.styx.trello.models.retrofit.TrelloList;


public class DatabaseHelper extends OrmLiteSqliteOpenHelper {

    private static final String DATABASE_NAME = "trello.db";
    private static final int DATABASE_VERSION = 1;

    private Dao<TrelloCard, String> trelloCard = null;
    private Dao<TrelloList, String> trelloList = null;


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db, ConnectionSource connectionSource) {
        try {
            TableUtils.createTable(connectionSource, TrelloCard.class);
            TableUtils.createTable(connectionSource, TrelloList.class);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        try {
            TableUtils.dropTable(connectionSource, TrelloCard.class, true);
            TableUtils.dropTable(connectionSource, TrelloList.class, true);
            onCreate(db, connectionSource);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public Dao<TrelloCard, String> getTrelloCard() throws SQLException {
        if (trelloCard == null) {
            trelloCard = getDao(TrelloCard.class);
        }
        return trelloCard;
    }


    public Dao<TrelloList, String> getTrelloList() throws SQLException {
        if (trelloList == null) {
            trelloList = getDao(TrelloList.class);
        }
        return trelloList;
    }
    @Override
    public void close() {
        super.close();
        trelloList = null;
        trelloCard = null;
    }
}
