package pl.styx.trello.models.retrofit;

import com.google.gson.annotations.Expose;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "trello_list")
public class TrelloList {

    @Expose @DatabaseField(id = true)
    public String id;

    @DatabaseField
    public String name;

    @DatabaseField
    public Boolean closed;

    @DatabaseField
    public String idBoard;

    @DatabaseField
    public int pos;

    @DatabaseField
    public String subscribed;

    @Override
    public String toString() {
        return id+":"+name+":"+closed+":"+idBoard+":"+pos+":"+subscribed;
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }
}
