package pl.styx.trello.models;


public class TrelloList {
    public String id;
    public String name;
    public Boolean closed;
    public String idBoard;
    public int pos;
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
