package aovddfhfpjtmqids.book.appmk.com.sonnikviewpager;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ListView;
import android.widget.TextView;

import com.backendless.Backendless;
import com.backendless.BackendlessCollection;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.backendless.persistence.BackendlessDataQuery;
import com.backendless.persistence.QueryOptions;

import java.util.ArrayList;
import java.util.List;

import static aovddfhfpjtmqids.book.appmk.com.sonnikviewpager.SonsAdapter.CONTENT;

/**
 * Created by BEK on 19.02.2017.
 */

public class FirstFragment extends Fragment {
    // Store instance variables
    private String title;
    private int page;
    private ListView listView;
    private List<Sons> sons;
    private Context context;

    // newInstance constructor for creating fragment with arguments
    public static FirstFragment newInstance(int page, String title) {
        FirstFragment fragmentFirst = new FirstFragment();
        Bundle args = new Bundle();
        args.putInt("someInt", page);
        args.putString("someTitle", title);
        fragmentFirst.setArguments(args);
        return fragmentFirst;
    }

    // Store instance variables based on arguments passed
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        page = getArguments().getInt("someInt", 0);
        title = getArguments().getString("someTitle");
    }

    // Inflate the view for the fragment based on layout XML
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        context = inflater.getContext();
        View view = inflater.inflate(R.layout.fragment_first, container, false);
        listView = (ListView) view.findViewById(R.id.listView);
        listView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                hideKeyboard(v);
                return false;
            }
        });
        downloadSons();

        return view;
    }

    private void downloadSons() {
        String whereClause;

        whereClause = "title LIKE '" + title + "%'";

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
                displaySons( response.getData());
            }

            @Override
            public void handleFault(BackendlessFault fault) {
                Log.e("my_log","some error "+ fault.getMessage());
            }
        });
    }
    private void displaySons(List<Sons> sons) {
        this.sons=sons;

        SonsListViewAdapter adapter = new SonsListViewAdapter(context, sons);

        listView.setAdapter(adapter);
    }

    protected void hideKeyboard(View view)
    {
        InputMethodManager in = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        in.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }
}