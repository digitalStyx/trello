package pl.styx.trello;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import java.util.List;

import pl.styx.trello.fragments.ListFragment;
import pl.styx.trello.models.TrelloList;
import pl.styx.trello.utils.TrelloApi;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class MainActivity extends ActionBarActivity {

    private ViewPager mPager;
    private TrelloPagerAdapter mPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mPager =  (ViewPager)findViewById(R.id.pager);

        TrelloApi.getInstance().getService().lists(BundleKeys.BOARD_ID, BundleKeys.TRELLO_KEY, new Callback<List<TrelloList>>() {
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


    public class TrelloPagerAdapter extends FragmentStatePagerAdapter {
        private List<TrelloList> mTrelloLists = null;
        private TrelloList mList;

        public void setTrelloList(List<TrelloList> trelloLists) {
            mTrelloLists = trelloLists;
        }

        public TrelloPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            if (mTrelloLists!=null) {
                mList = mTrelloLists.get(position);
                if (mList != null) {
                    return mList.getName();
                }
            }
            return null;
        }

        @Override
        public Fragment getItem(int position) {
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
