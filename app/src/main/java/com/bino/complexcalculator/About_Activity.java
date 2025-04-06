package com.bino.complexcalculator;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class About_Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        Create_Topbar();
    }


    //Creates and sets special top bar
    private void Create_Topbar()
    {
        toolbar = findViewById(R.id.topbarabout);
        setSupportActionBar(toolbar);

        //Set navigation button
        //Switch back to main activity when back button is pressed
        toolbar.setNavigationOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(About_Activity.this,MainActivity.class);
                startActivity(intent);
            }
        });
    }

    //Necessary to link menu to top bar
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.about_menu, menu);
        return true;
    }


    private Toolbar toolbar;

}