package pl.styx.trello.utils;

import pl.styx.trello.services.TrelloService;
import retrofit.RestAdapter;

public class TrelloApi {
    private RestAdapter restAdapter;
    private TrelloService service;
    private static TrelloApi instance = null;

    public TrelloApi() {
        restAdapter = new RestAdapter.Builder()
                .setEndpoint("https://api.trello.com/1/")
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .build();

        service = restAdapter.create(TrelloService.class);
    }

    public static TrelloApi getInstance() {
        if (instance == null) {
            instance = new  TrelloApi();
        }
        return instance;
    }

    public TrelloService getService() {
        return service;
    }
}
