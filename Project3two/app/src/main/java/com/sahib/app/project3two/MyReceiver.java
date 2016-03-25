package com.sahib.app.project3two;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class MyReceiver extends BroadcastReceiver {
    public MyReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        // This method is called when the BroadcastReceiver is receiving an Intent broadcast.

        //System.out.println(intent.getAction());

        String action = intent.getAction();

        if(action.equals("com.sahib.app.project3one.chicago")){

            intent.setClassName(context, "com.sahib.app.project3two.ChicagoActivity");
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);

        }

        if(action.equals("com.sahib.app.project3one.indianapolis")){

            intent.setClassName(context, "com.sahib.app.project3two.IndianaActivity");
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);

        }

        //throw new UnsupportedOperationException("Not yet implemented");
    }
}
