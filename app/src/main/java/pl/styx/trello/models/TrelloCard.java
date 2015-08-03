package pl.styx.trello.models;

public class TrelloCard {
    public String id;
    public String dateLastActivity;
    public String idList;
    public String name;
    public String desc;
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
