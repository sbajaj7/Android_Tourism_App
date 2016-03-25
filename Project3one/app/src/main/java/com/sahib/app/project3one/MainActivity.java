package com.sahib.app.project3one;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends Activity {

    private static final String INTENT_CHICAGO = "com.sahib.app.project3one.chicago";
    private static final String INTENT_INDIANA = "com.sahib.app.project3one.indianapolis";

    Button chicago;
    Button indianapolis;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        chicago = (Button)findViewById(R.id.chicago);
        indianapolis = (Button)findViewById(R.id.indianapolis);

        chicago.setOnClickListener(chicagoClicked);
        indianapolis.setOnClickListener((indianaClicked));

    }

    View.OnClickListener chicagoClicked = new View.OnClickListener(){

    public void onClick(View v){

        Toast.makeText(MainActivity.this, "Chicago Button pressed. Broadcast Sent", Toast.LENGTH_SHORT).show();

        Intent intent_chicago = new Intent(INTENT_CHICAGO);
        //intent_chicago.setAction("com.sahib.app.project3one.chicago");
        sendBroadcast(intent_chicago);

    }

    };

    View.OnClickListener indianaClicked = new View.OnClickListener(){

    public void onClick(View v){

        Toast.makeText(MainActivity.this, "Indianapolis button pressed. Broadcast sent.", Toast.LENGTH_SHORT).show();

        Intent intent_indiana = new Intent(INTENT_INDIANA);
        //intent_indiana.setAction("com.sahib.app.project3one.indianapolis");
        sendBroadcast(intent_indiana);

        }

    };

}
