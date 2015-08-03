package pl.styx.trello.services;


import java.util.List;

import pl.styx.trello.models.TrelloCard;
import pl.styx.trello.models.TrelloList;
import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Query;

public interface TrelloService {

    @GET("/board/{board}/lists")
    void lists(@Path("board") String board, @Query("key") String key, Callback<List<TrelloList>> cb);

    @GET("/lists/{list}/cards")
    void cards(@Path("list") String list, @Query("key") String key, Callback<List<TrelloCard>> cb);
}
