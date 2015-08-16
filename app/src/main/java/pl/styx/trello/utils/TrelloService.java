package pl.styx.trello.utils;


import java.util.List;

import pl.styx.trello.models.retrofit.TrelloCard;
import pl.styx.trello.models.retrofit.TrelloList;
import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Path;

public interface TrelloService {

    @GET("/board/{board}/lists")
    List<TrelloList> lists(@Path("board") String board);

    //void lists(@Path("board") String board, Callback<List<TrelloList>> cb);

    @GET("/boards/{board}/cards")
    List<TrelloCard> cardsList(@Path("board") String list);


    @GET("/lists/{list}/cards")
    void cards(@Path("list") String list, Callback<List<TrelloCard>> cb);
}
