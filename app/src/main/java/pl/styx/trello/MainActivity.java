package pl.styx.trello;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

import java.sql.SQLException;
import java.util.List;

import javax.inject.Inject;

import pl.styx.trello.events.SyncSuccessEvent;
import pl.styx.trello.fragments.ListFragment;
import pl.styx.trello.models.TrelloListManager;
import pl.styx.trello.models.retrofit.TrelloList;
import pl.styx.trello.services.SynchronizeTrello;


public class MainActivity extends AppCompatActivity {
    @Inject Bus bus;
    @Inject TrelloListManager trelloListManager;

    private ViewPager mPager;
    private TrelloPagerAdapter mPagerAdapter;
    private TrelloPagerAdapter trelloPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        App.inject(this);

        mPager =  (ViewPager)findViewById(R.id.pager);
        //mPagerAdapter = new TrelloPagerAdapter(getSupportFragmentManager());

        Log.v("Radek", "MainActivity: onCreate");
        Intent i = new Intent(this, SynchronizeTrello.class);
        startService(i);


        /*
        TrelloApi.getInstance().getService().lists(BundleKeys.BOARD_ID, new Callback<List<TrelloList>>() {
            @Override
            public void success(List<TrelloList> trelloLists, Response response) {
                mPagerAdapter = new TrelloPagerAdapter(getSupportFragmentManager());
                mPagerAdapter.setTrelloList(trelloLists);
                mPager.setAdapter(mPagerAdapter);
            }

            @Override
            public void failure(RetrofitError retrofitError) {
            }
        });
        */
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void setupPager() {
        try {
            if (mPager!=null) {
                Log.v("Radek", trelloListManager.getAll().toString());
                trelloPagerAdapter = new TrelloPagerAdapter(getSupportFragmentManager(), trelloListManager.getAll());
                mPager.setAdapter(trelloPagerAdapter);
                mPager.notify();
                trelloPagerAdapter.notifyDataSetChanged();

            }
            /*
            mPagerAdapter.setTrelloList(trelloListManager.getAll());
            mPager.setAdapter(mPagerAdapter);
            mPagerAdapter.notifyDataSetChanged();
*/
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Subscribe
    public void onSyncSuccess(SyncSuccessEvent event) {
      //  stopLoadingAnim();
        Log.v("Radek","jest onSyncSuccess");
        setupPager();
    }

    @Override
    protected void onResume() {
        super.onResume();
        bus.register(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        bus.unregister(this);
    }

    public class TrelloPagerAdapter extends FragmentStatePagerAdapter {
        private List<TrelloList> mTrelloLists = null;
        private TrelloList mList;


        public TrelloPagerAdapter(FragmentManager fm,List<TrelloList> trelloLists) {
            super(fm);
            mTrelloLists = trelloLists;
        }


        @Override
        public CharSequence getPageTitle(int position) {
            Log.v("Radek","getPageTitle");
            if (mTrelloLists!=null) {
                mList = mTrelloLists.get(position);
                if (mList != null) {
                    Log.v("Radek","ustawiam name na: "+ mList.getName());
                    return mList.getName();
                }
            }
            return null;
        }

        @Override
        public int getItemPosition(Object object) {

            return POSITION_NONE;
        }

        @Override
        public Fragment getItem(int position) {
            Log.v("Radek","Fragment: getItem: "+position);
            Bundle args = new Bundle();
            if (mTrelloLists!=null) {
                mList = mTrelloLists.get(position);
                if (mList!=null) {
                    args.putString("id",mList.getId());
                 }
            }
            ListFragment fragment = new ListFragment();
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public int getCount() {
            if (mTrelloLists!=null) {
                return mTrelloLists.size();
            }
            return 0;
        }
    }

}
