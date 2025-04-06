package com.bino.complexcalculator;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class MainActivity extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Init_Buttons();
    }


    private void Init_Buttons()
    {
        btnbasic = findViewById(R.id.btngotobasic);
        btnadvanced = findViewById(R.id.btngotoadvanced);
        btnabout = findViewById(R.id.btngotoabout);

        btnbasic.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(MainActivity.this,Basic_Calculations_Activity.class);
                startActivity(intent);
            }
        });

        btnadvanced.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(MainActivity.this,Advanced_Calculations_Activity.class);
                startActivity(intent);
            }
        });

        btnabout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,About_Activity.class);
                startActivity(intent);
            }
        });
    }


    private Button btnbasic, btnadvanced, btnabout;

}