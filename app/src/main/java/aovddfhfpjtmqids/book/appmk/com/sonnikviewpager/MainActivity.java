package aovddfhfpjtmqids.book.appmk.com.sonnikviewpager;

import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.astuetz.PagerSlidingTabStrip;
import com.backendless.Backendless;
import com.backendless.BackendlessCollection;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.backendless.persistence.BackendlessDataQuery;
import com.backendless.persistence.QueryOptions;

import java.util.ArrayList;
import java.util.List;

import static aovddfhfpjtmqids.book.appmk.com.sonnikviewpager.SonsAdapter.CONTENT;

public class MainActivity extends AppCompatActivity {

    private EditText mSonEditText;
    private Button mFindButton;
    private ViewPager mViewPager;
    private PagerSlidingTabStrip tabs;
    private ArrayList<String> sons;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Backendless.initApp(this, Konst.APP_ID,Konst.ANDROID_KEY,"v1");

        mSonEditText = (EditText) findViewById(R.id.sonEditText);
        mFindButton = (Button) findViewById(R.id.findButton);
        mViewPager = (ViewPager) findViewById(R.id.viewPager);

        // Bind the tabs to the ViewPager
        tabs = (PagerSlidingTabStrip) findViewById(R.id.tabs);
        tabs.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                Log.d("myLog",CONTENT[position]);
                downloadSons(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


        mFindButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onFindButtonClick();
            }
        });

        downloadSons(0);
    }

    private void downloadSons(int position) {

        String whereClause = "title LIKE '"+CONTENT[position]+"%'";

        QueryOptions queryOptions = new QueryOptions();
        List<String> sortBy = new ArrayList<String>();
        sortBy.add( "title" );
        queryOptions.setSortBy( sortBy );

        BackendlessDataQuery query = new BackendlessDataQuery();
        query.setPageSize(50);
        query.setWhereClause(whereClause);
        query.setQueryOptions( queryOptions );

        Backendless.Persistence.of(Sons.class).find(query,new AsyncCallback<BackendlessCollection<Sons>>() {

            @Override
            public void handleResponse(BackendlessCollection<Sons> response) {
                //Log.d("my_log",response+" my response is");
                sons = new ArrayList<>();
                for (int i=0; i<response.getData().size();i++){
                    String son = response.getData().get(i).getTitle();
                    sons.add(son);
                }
                displaySons();
            }

            @Override
            public void handleFault(BackendlessFault fault) {


                Log.e("my_log","some error "+ fault.getMessage());
            }
        });
    }

    private void onFindButtonClick() {

    }

    private void displaySons() {

        ArrayList<Fragment> fragments = new ArrayList<>();
        for (String son: sons) {
            fragments.add(SonFragment.newInstance(son));
        }

        SonsAdapter adapter = new SonsAdapter(getSupportFragmentManager(),fragments);
        mViewPager.setAdapter(adapter);
        tabs.setViewPager(mViewPager);
    }
}
