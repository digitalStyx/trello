package pl.styx.trello.utils;

import pl.styx.trello.BundleKeys;
import retrofit.RequestInterceptor;
import retrofit.RestAdapter;

public class TrelloApi {
    private RestAdapter restAdapter;
    private TrelloService service;
    private static TrelloApi instance = null;

    public TrelloApi() {
        restAdapter = new RestAdapter.Builder()
                .setEndpoint("https://api.trello.com/1/")
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setRequestInterceptor(new RequestInterceptor() {
                    @Override
                    public void intercept(RequestFacade request) {
                        request.addQueryParam("key", BundleKeys.TRELLO_KEY);
                    }
                })
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
