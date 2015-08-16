package pl.styx.trello.models.retrofit;

import com.google.gson.annotations.Expose;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "trello_card")
public class TrelloCard {
    @Expose @DatabaseField(id = true)
    public String id;

    @DatabaseField
    public String dateLastActivity;

    @DatabaseField
    public String idList;

    @DatabaseField
    public String name;

    @DatabaseField
    public String desc;

    @DatabaseField
    public Boolean description;

    public String getName() {
        return name;
    }

    public String getDesc() {
        return desc;
    }


    @Override
    public String toString() {
        return id+":"+dateLastActivity+":"+idList+":"+desc+":"+description;
    }
}
