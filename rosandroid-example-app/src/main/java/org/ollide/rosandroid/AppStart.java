package org.ollide.rosandroid;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.View;
import android.widget.Toast;

public class AppStart extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_start);
        this.setTitle(Html.fromHtml("<font color='#000000'>Please choose your drop-off point</font>"));
        MapsFragment MapsFragment = new MapsFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_2, MapsFragment, "").commit();
    }


    public void onBackPressed(){
//        Toast.makeText(getApplicationContext(), " Press Back again to Exit ", Toast.LENGTH_SHORT).show();
        if(MapsFragment.appState == "initialState"){
            Toast.makeText(getApplicationContext(), " Initial state ", Toast.LENGTH_SHORT).show();
            System.exit(0);
        }
        if(MapsFragment.appState == "routeReady"){
            //Toast.makeText(getApplicationContext(), " Route ready", Toast.LENGTH_SHORT).show();
            MapsFragment.appState = "initialState";
            MapsFragment.removePolylines();
            MapsFragment.bottomSheet.setVisibility(View.VISIBLE);
            MapsFragment.bottomSheet2.setVisibility(View.GONE);
        }

    }
}
