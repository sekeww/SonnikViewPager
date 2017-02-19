package aovddfhfpjtmqids.book.appmk.com.sonnikviewpager;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.print.PrintAttributes;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.SearchView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.astuetz.PagerSlidingTabStrip;
import com.backendless.Backendless;
import com.backendless.BackendlessCollection;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.backendless.persistence.BackendlessDataQuery;
import com.backendless.persistence.QueryOptions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static aovddfhfpjtmqids.book.appmk.com.sonnikviewpager.SonsAdapter.CONTENT;

public class MainActivity extends AppCompatActivity {

    private EditText mSonEditText;
    private Button mFindButton;
    private ViewPager mViewPager;
    private PagerSlidingTabStrip tabs;
    private ArrayList<String> sons;
    private String mToFindText;
    private LinearLayout activity_main;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        activity_main = (LinearLayout) findViewById(R.id.activity_main);
        activity_main.setOnTouchListener(new View.OnTouchListener()
        {
            @Override
            public boolean onTouch(View view, MotionEvent ev)
            {
                hideKeyboard(view);
                return false;
            }
        });
        Backendless.initApp(this, Konst.APP_ID,Konst.ANDROID_KEY,"v1");

        mSonEditText = (EditText) findViewById(R.id.sonEditText);

        mViewPager = (ViewPager) findViewById(R.id.viewPager);
        mViewPager.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                hideKeyboard(v);
                return false;
            }
        });

        // Bind the tabs to the ViewPager
        tabs = (PagerSlidingTabStrip) findViewById(R.id.tabs);
        mSonEditText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_ENTER) {
                    InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(mSonEditText.getWindowToken(), 0);
                    return true;
                }
                return MainActivity.super.onKeyDown(keyCode, event);
            }
        });
        mSonEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (!mSonEditText.getText().toString().equals("")) {
                    Log.d("myLogh",mSonEditText.getText().toString().charAt(0)+"");
                    char first = mSonEditText.getText().toString().charAt(0);
                    String firstS = first+"";
                    Log.d("myLogh",firstS.toUpperCase());
                    Log.d("myLogh",Arrays.asList(CONTENT).indexOf(firstS.toUpperCase())+"");
                    int pageNum = Arrays.asList(CONTENT).indexOf(firstS.toUpperCase());
                    mViewPager.setCurrentItem(pageNum);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        SonsAdapter adapter = new SonsAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(adapter);
        tabs.setViewPager(mViewPager);
    }


    protected void setupParent(View view) {
        //Set up touch listener for non-text box views to hide keyboard.
        if(!(view instanceof EditText)) {
            view.setOnTouchListener(new View.OnTouchListener() {
                public boolean onTouch(View v, MotionEvent event) {
                                        return false;
                }
            });
        }
        //If a layout container, iterate over children
        if (view instanceof ViewGroup) {
            for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {
                View innerView = ((ViewGroup) view).getChildAt(i);
                setupParent(innerView);
            }
        }
    }

    protected void hideKeyboard(View view)
    {
        InputMethodManager in = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        in.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }
}
