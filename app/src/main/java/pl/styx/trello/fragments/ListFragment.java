package pl.styx.trello.fragments;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import it.gmariotti.cardslib.library.internal.Card;
import it.gmariotti.cardslib.library.internal.CardArrayAdapter;
import it.gmariotti.cardslib.library.internal.CardHeader;
import it.gmariotti.cardslib.library.view.CardListView;
import pl.styx.trello.R;
import pl.styx.trello.models.retrofit.TrelloCard;
import pl.styx.trello.utils.TrelloApi;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class ListFragment extends Fragment {

    private CardListView listView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.v("Radek", "onCreateView");
        View view = inflater.inflate(R.layout.list_trello, null);

        listView = (CardListView) view.findViewById(R.id.myList);


        Bundle args = getArguments();
        if (args!=null && args.containsKey("id")) {
            setCards(args.getString("id"));
        }

        return view;
    }

    public void setCards(String id) {
        TrelloApi.getInstance().getService().cards(id, new Callback<List<TrelloCard>>() {
            @Override
            public void success(List<TrelloCard> trelloLists, Response response) {

                ArrayList<Card> cards = new ArrayList<Card>();

                for (Iterator<TrelloCard> iterator = trelloLists.iterator(); iterator.hasNext(); ) {
                    TrelloCard trelloCard = iterator.next();
                    CustomTrelloCard card = new CustomTrelloCard(getActivity());

                    CardHeader header = new CardHeader(getActivity());
                    header.setTitle(trelloCard.getName());
                    card.setDescription(trelloCard.getDesc());
                    card.addCardHeader(header);

                    cards.add(card);
                }
                if (cards.size() > 0) {
                    CardArrayAdapter mCardArrayAdapter = new CardArrayAdapter(getActivity(), cards);


                    if (listView != null) {
                        listView.setAdapter(mCardArrayAdapter);
                    }
                    mCardArrayAdapter.setInnerViewTypeCount(3);
                }

            }

            @Override
            public void failure(RetrofitError retrofitError) {
                Log.v("Radek", "cos nie tak z kartami");
            }
        });
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.v("Radek","onCreate");
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    public class CustomTrelloCard extends Card {
        private String mText;

        public CustomTrelloCard(Context context) {
            this(context, R.layout.card_trello_layout);
        }

        public CustomTrelloCard(Context context, int innerLayout) {
            super(context, innerLayout);
            init();
        }

        public void setDescription(String text) {
            mText = text;
        }

        private void init(){
            setOnClickListener(new OnCardClickListener() {
                @Override
                public void onClick(Card card, View view) {
                    AlertDialog.Builder builderSingle = new AlertDialog.Builder(
                            getActivity());
                    builderSingle.setIcon(R.drawable.abc_ic_menu_selectall_mtrl_alpha);
                    builderSingle.setTitle("Wybierz akcję");
                    final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                            getActivity(),
                            android.R.layout.select_dialog_item);
                    arrayAdapter.add("Usuń");
                    arrayAdapter.add("Przenieś do: ");
                    arrayAdapter.add("Przenieś do: ");
                    builderSingle.setNegativeButton("Anuluj",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });

                    builderSingle.setAdapter(arrayAdapter,
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    String strName = arrayAdapter.getItem(which);
                                }
                            });
                    builderSingle.show();
                }
            });
        }

        @Override
        public void setupInnerViewElements(ViewGroup parent, View view) {
            TextView mTextView = (TextView) parent.findViewById(R.id.card_main_content);
            if (mTextView!=null) {
                mTextView.setText(mText);
            }
        }
    }

}
