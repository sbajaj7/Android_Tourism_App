package com.sahib.app.project3two;

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
import java.util.ArrayList;


//Main Activity for chicago contaning 2 fragments, one for showing list of attractions in chicago and other one for showing a wikipedia page of selected attraction
public class ChicagoActivity extends AppCompatActivity implements ChicagoInterestsFragment.ListSelectionListener {

    String config;

    public static String chicago_interests[];

    public static ArrayList<Uri> mUriList = new ArrayList<Uri>();

    public static final String TAG = "ChicagoActivity";

    //Reference variable of FragmentManager for making changes programmatically
    public FragmentManager fragmentManager;
    FrameLayout interestframelayout, webframelayout;

    private final ChicagoWebFragment chicagoWebFragment = new ChicagoWebFragment();

    private static final int MATCH_PARENT = LinearLayout.LayoutParams.MATCH_PARENT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Log.i(TAG, getClass().getSimpleName() + ":entered onCreate()");

        super.onCreate(savedInstanceState);

        // Inflating the main layout (from the res folder)
        setContentView(R.layout.activity_chicago);

        if(getResources().getConfiguration().orientation==Configuration.ORIENTATION_LANDSCAPE){
            config = "landscape";
        }

        else{
            config = "portrait";
        }

        chicago_interests = getResources().getStringArray(R.array.interests);

        interestframelayout = (FrameLayout) findViewById(R.id.interest_fragment_container);
        webframelayout = (FrameLayout) findViewById(R.id.web_fragment_container);

        fragmentManager = getFragmentManager();

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction.add(R.id.interest_fragment_container, new ChicagoInterestsFragment());

        // Commit the FragmentTransaction
        fragmentTransaction.commit();

        // Add a OnBackStackChangedListener to reset the layout when the back stack changes
        fragmentManager.addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
            public void onBackStackChanged() {
                Log.i(TAG, getClass().getSimpleName() + ":entered onBackStackChanged()");
                setLayout();
            }
        });

        //Initializing the url's of the attractions in Chicago
        initializeStateUris();

    }


    private void setLayout() {

        Log.i(TAG, getClass().getSimpleName() + ":entered setLayout()");

        //Settings the layout for the potrait mode
        if(config.equals("portrait")) {

            // Determine whether the Fragment containing Web View has been added
            if (!chicagoWebFragment.isAdded()) {

                Log.i(TAG, getClass().getSimpleName() + ":Inside portrait setLayout() chicagoWebFragment not added");

                // Make the TitleFragment occupy the entire layout
                interestframelayout.setLayoutParams(new LinearLayout.LayoutParams(
                        MATCH_PARENT, MATCH_PARENT));

                webframelayout.setLayoutParams(new LinearLayout.LayoutParams(0,
                        MATCH_PARENT));
            } else {

                Log.i(TAG, getClass().getSimpleName() + ":Inside portrait setLayout() chicagoWebFragment added");

                interestframelayout.setLayoutParams(new LinearLayout.LayoutParams(0,
                        MATCH_PARENT));

                webframelayout.setLayoutParams(new LinearLayout.LayoutParams(MATCH_PARENT,
                        MATCH_PARENT));
            }

        }

        //Settings the layout for the landscape mode
        if(config.equals("landscape")) {

            // Determine whether the Fragment containing Web View has been added
            if (!chicagoWebFragment.isAdded()) {

                Log.i(TAG, getClass().getSimpleName() + ":Inside landscape setLayout() chicagoWebFragement not added");

                // Make the TitleFragment occupy the entire layout
                interestframelayout.setLayoutParams(new LinearLayout.LayoutParams(
                        MATCH_PARENT, MATCH_PARENT));
                webframelayout.setLayoutParams(new LinearLayout.LayoutParams(0,
                        MATCH_PARENT));
            } else {

                Log.i(TAG, getClass().getSimpleName() + ":Inside portrait setLayout() chicagoWebFragement added");

                // Make the TitleFragment take 1/3 of the layout's width
                interestframelayout.setLayoutParams(new LinearLayout.LayoutParams(0,
                        MATCH_PARENT, 1f));

                // Make the Fragment containing Web View take 2/3's of the layout's width
                webframelayout.setLayoutParams(new LinearLayout.LayoutParams(0,
                        MATCH_PARENT, 2f));
            }

        }
    }

    // Called when the user selects an item in the ChicagoInterestsFragment
    @Override
    public void onListSelection(int index) {

        Log.i(TAG, getClass().getSimpleName() + ":entered onListSelection()");

        // If the Fragment containing Web View has not been added, add it now
        if (!chicagoWebFragment.isAdded()) {

            // Start a new FragmentTransaction
            FragmentTransaction fragmentTransaction = fragmentManager
                    .beginTransaction();

            // Add the Fragment containing Web View to the layout
            fragmentTransaction.add(R.id.web_fragment_container, chicagoWebFragment);

            // Add this FragmentTransaction to the backstack
            fragmentTransaction.addToBackStack(null);

            // Commit the FragmentTransaction
            fragmentTransaction.commit();

            // Force Android to execute the committed FragmentTransaction
            fragmentManager.executePendingTransactions();
        }

            // Tell the chicagoWebFragment to open the WebView according to the position index
            chicagoWebFragment.showWebViewAtIndex(index);

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

        mUriList.add(Uri.parse(getString(R.string.milleniumpark))) ;
        mUriList.add(Uri.parse(getString(R.string.museum))) ;
        mUriList.add(Uri.parse(getString(R.string.artinstitute))) ;
        mUriList.add(Uri.parse(getString(R.string.willis))) ;
        mUriList.add(Uri.parse(getString(R.string.zoo))) ;
        mUriList.add(Uri.parse(getString(R.string.bean))) ;
        mUriList.add(Uri.parse(getString(R.string.navypier)));
        mUriList.add(Uri.parse(getString(R.string.shedd)));
        mUriList.add(Uri.parse(getString(R.string.adler)));
        mUriList.add(Uri.parse(getString(R.string.buckingham))) ;
        mUriList.add(Uri.parse(getString(R.string.soldier))) ;
        mUriList.add(Uri.parse(getString(R.string.theatre)));

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

        if (chicagoWebFragment.isAdded()) {

            // Make the TitleFragment take 1/3 of the layout's width
            interestframelayout.setLayoutParams(new LinearLayout.LayoutParams(0,
                    MATCH_PARENT, 1f));

            // Make the Fragment containing Web View take 2/3's of the layout's width
            webframelayout.setLayoutParams(new LinearLayout.LayoutParams(0,
                    MATCH_PARENT, 2f));

        }
    }


    public void retainingStateOnRotation2(){

        if (chicagoWebFragment.isAdded()) {

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
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;

    }

    //Handling the click event of items in options menu
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.indiana_menu_item:
                //Toast.makeText(getApplicationContext(), "Indiana option Selected", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(this,IndianaActivity.class);
                startActivity(intent);
        }
                return true;

        }
    }

