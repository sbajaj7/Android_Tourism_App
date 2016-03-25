package com.sahib.app.project3two;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;


public class IndianaActivity extends AppCompatActivity implements IndianaInterestsFragment.ListSelectionListener {

    String config;

    int backButtonCount = 0;

    public static String indiana_interests[];

    public static ArrayList<Uri> mUriList = new ArrayList<Uri>();

    public static final String TAG = "IndianaActivity";

    public FragmentManager fragmentManager;
    FrameLayout interestframelayout, webframelayout;

    private final IndianaWebFragment indianaWebFragment = new IndianaWebFragment();

    private static final int MATCH_PARENT = LinearLayout.LayoutParams.MATCH_PARENT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Log.i(TAG, getClass().getSimpleName() + ":entered onCreate()");

        super.onCreate(savedInstanceState);

        // Inflating the main layout (from the res folder)
        setContentView(R.layout.activity_indiana);

        if(getResources().getConfiguration().orientation==Configuration.ORIENTATION_LANDSCAPE){
            config = "landscape";
        }

        else{
            config = "portrait";
        }

        indiana_interests = getResources().getStringArray(R.array.interestsindiana);

        interestframelayout = (FrameLayout) findViewById(R.id.interest_fragment_container);
        webframelayout = (FrameLayout) findViewById(R.id.web_fragment_container);

        fragmentManager = getFragmentManager();

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction.add(R.id.interest_fragment_container, new IndianaInterestsFragment());

        // Commit the FragmentTransaction
        fragmentTransaction.commit();

        // Add a OnBackStackChangedListener to reset the layout when the back stack changes
        fragmentManager.addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
            public void onBackStackChanged() {
                Log.i(TAG, getClass().getSimpleName() + ":entered onBackStackChanged()");
                setLayout();
            }
        });

        //Initializing the url's of the attractions in Indianapolis
        initializeStateUris();

    }


    private void setLayout() {

        Log.i(TAG, getClass().getSimpleName() + ":entered setLayout()");

        if(config.equals("portrait")) {

            // Determine whether the Fragment containing Web View has been added
            if (!indianaWebFragment.isAdded()) {

                Log.i(TAG, getClass().getSimpleName() + ":Inside portrait setLayout() indianaWebFragment not added");

                // Make the TitleFragment occupy the entire layout
                interestframelayout.setLayoutParams(new LinearLayout.LayoutParams(
                        MATCH_PARENT, MATCH_PARENT));

                webframelayout.setLayoutParams(new LinearLayout.LayoutParams(0,
                        MATCH_PARENT));
            } else {

                Log.i(TAG, getClass().getSimpleName() + ":Inside portrait setLayout() indianaWebFragment added");

                interestframelayout.setLayoutParams(new LinearLayout.LayoutParams(0,
                        MATCH_PARENT));


                webframelayout.setLayoutParams(new LinearLayout.LayoutParams(MATCH_PARENT,
                        MATCH_PARENT));
            }

        }

        if(config.equals("landscape")) {

            // Determine whether the Fragment containing Web View has been added
            if (!indianaWebFragment.isAdded()) {

                Log.i(TAG, getClass().getSimpleName() + ":Inside landscape setLayout() indianaWebFragement not added");

                // Make the TitleFragment occupy the entire layout
                interestframelayout.setLayoutParams(new LinearLayout.LayoutParams(
                        MATCH_PARENT, MATCH_PARENT));
                webframelayout.setLayoutParams(new LinearLayout.LayoutParams(0,
                        MATCH_PARENT));
            } else {

                Log.i(TAG, getClass().getSimpleName() + ":Inside portrait setLayout() indianaWebFragement added");

                // Make the TitleFragment take 1/3 of the layout's width
                interestframelayout.setLayoutParams(new LinearLayout.LayoutParams(0,
                        MATCH_PARENT, 1f));

                // Make the Fragment containing Web View take 2/3's of the layout's width
                webframelayout.setLayoutParams(new LinearLayout.LayoutParams(0,
                        MATCH_PARENT, 2f));
            }

        }
    }

    // Called when the user selects an item in the IndianaInterestsFragment
    @Override
    public void onListSelection(int index) {

        Log.i(TAG, getClass().getSimpleName() + ":entered onListSelection()");

        // If the Fragment containing Web View has not been added, add it now
        if (!indianaWebFragment.isAdded()) {

            // Start a new FragmentTransaction
            FragmentTransaction fragmentTransaction = fragmentManager
                    .beginTransaction();

            // Add the Fragment containing Web View to the layout
            fragmentTransaction.add(R.id.web_fragment_container, indianaWebFragment);

            // Add this FragmentTransaction to the backstack
            fragmentTransaction.addToBackStack(null);

            // Commit the FragmentTransaction
            fragmentTransaction.commit();

            // Force Android to execute the committed FragmentTransaction
            fragmentManager.executePendingTransactions();
        }

        //if (indianaWebFragment.getShownIndex() != index)

        // Tell the indianaWebFragment to open the WebView according to the position index
        indianaWebFragment.showWebViewAtIndex(index);

    }

    public void onBackPressed() {
        if (getFragmentManager().getBackStackEntryCount() > 0 ){
            getFragmentManager().popBackStack();
        } else {
            super.onBackPressed();
        }
    }

    //Method to initialize the url's of the list of attractions
    private void initializeStateUris() {

        mUriList.add(Uri.parse(getString(R.string.motorspeedway))) ;
        mUriList.add(Uri.parse(getString(R.string.statemuseum))) ;
        mUriList.add(Uri.parse(getString(R.string.victoryfield))) ;
        mUriList.add(Uri.parse(getString(R.string.indianpoliszoo))) ;
        mUriList.add(Uri.parse(getString(R.string.lucasoilstadium))) ;
        mUriList.add(Uri.parse(getString(R.string.rcaDome))) ;

    }

    //Method to handle the changes in the configuration i.e from potrait to landscape and vice-versa
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        Log.i(TAG, getClass().getSimpleName() + ":Inside onConfigurationChanged()");

        if(newConfig.orientation==Configuration.ORIENTATION_LANDSCAPE){

            Log.i(TAG, getClass().getSimpleName() + ":set landscape in onConfigurationChanged()");
            //Log.i("On Config Change","LANDSCAPE");
            config = "landscape";
            retainingStateOnRotation1();
        }else{

            Log.i(TAG, getClass().getSimpleName() + ":set portrait in onConfigurationChanged()");
            //Log.i("On Config Change","PORTRAIT");
            config = "portrait";
            retainingStateOnRotation2();
        }

    }

    public void retainingStateOnRotation1(){

        if (indianaWebFragment.isAdded()) {

            // Make the TitleFragment take 1/3 of the layout's width
            interestframelayout.setLayoutParams(new LinearLayout.LayoutParams(0,
                    MATCH_PARENT, 1f));

            // Make the Fragment containing Web View take 2/3's of the layout's width
            webframelayout.setLayoutParams(new LinearLayout.LayoutParams(0,
                    MATCH_PARENT, 2f));

        }
    }

    public void retainingStateOnRotation2(){

        if (indianaWebFragment.isAdded()) {

            interestframelayout.setLayoutParams(new LinearLayout.LayoutParams(0,
                    MATCH_PARENT));

            webframelayout.setLayoutParams(new LinearLayout.LayoutParams(MATCH_PARENT,
                    MATCH_PARENT));

        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        Log.i(TAG, getClass().getSimpleName() + ":Inside onCreateOptionsMenu()");

        //Inflating the options menu.
        getMenuInflater().inflate(R.menu.menuindiana,menu);
        return true;

    }

    //Handling the click event of items in options menu
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.chicago_menu_item:
                //Toast.makeText(getApplicationContext(), "Chicago option Selected", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(this,ChicagoActivity.class);
                startActivity(intent);

        }
        return true;

    }
}


