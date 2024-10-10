package com.bino.complexcalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.widget.Toolbar;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.ScatterChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.ScatterData;
import com.github.mikephil.charting.data.ScatterDataSet;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.List;

import Calculator.Calculator;
import Calculator.CalculatorCallback;
import Calculator.FormatConverter;
import Calculator.Translator;
import Calculator.FormatType;
import Calculator.Translator_Callback;
import Calculator.Operation;
import Calculator.FormatLoc;
import Chart.Chartbuilder;
import CustomDialogs.AdvInputCallback;
import CustomDialogs.AdvNumberInputDialog;
import CustomDialogs.NumberInputCallback;
import CustomDialogs.NumberInputDialog;

public class Basic_Calculations_Activity extends AppCompatActivity implements Translator_Callback
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basic_calculations);
        Create_Topbar();

        chartbuilder = new Chartbuilder(findViewById(R.id.basicschart));
        translator = new Translator(inputformattop,inputformatbot,outputformat,Basic_Calculations_Activity.this);

        Init_Views();
        Init_Onclick();

    }

    private void Init_Views()
    {
        txtinput1 = findViewById(R.id.txtinput1);
        txtinput2 = findViewById(R.id.txtinput2);
        txtinput3 = findViewById(R.id.txtinput3);
        txtinput4 = findViewById(R.id.txtinput4);
        txtoutput1 = findViewById(R.id.txtoutput1);
        txtoutput2 = findViewById(R.id.txtoutput2);

        btninputformattop = findViewById(R.id.btninputformattop);
        btninputformatbot = findViewById(R.id.btninputformatbot);
        btnoutputformat = findViewById(R.id.btnoutputformat);

        btnaddition = findViewById(R.id.btnaddition);
        btnsubtraction = findViewById(R.id.btnsubtraction);
        btnmultiplication = findViewById(R.id.btnmultiplication);
        btndivision = findViewById(R.id.btndivision);
    }

    private void Init_Onclick()
    {
        Init_Formatbuttons();

        //Input boxes
        Init_Inputboxes();

        //basic operations:
        Init_Operationbtns();
    }


    //-------------------------------------------
    //Basic math operations
    private void Init_Operationbtns()
    {
        btnaddition.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                translator.Perform_Calculation(
                        txtinput1.getEditText().getText().toString(),
                        txtinput2.getEditText().getText().toString(),
                        txtinput3.getEditText().getText().toString(),
                        txtinput4.getEditText().getText().toString(),
                        Operation.ADD
                );
            }
        });

        btnsubtraction.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                translator.Perform_Calculation(
                        txtinput1.getEditText().getText().toString(),
                        txtinput2.getEditText().getText().toString(),
                        txtinput3.getEditText().getText().toString(),
                        txtinput4.getEditText().getText().toString(),
                        Operation.SUB
                );
            }
        });

        btnmultiplication.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                translator.Perform_Calculation(
                        txtinput1.getEditText().getText().toString(),
                        txtinput2.getEditText().getText().toString(),
                        txtinput3.getEditText().getText().toString(),
                        txtinput4.getEditText().getText().toString(),
                        Operation.MUL
                );
            }
        });


        btndivision.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                translator.Perform_Calculation(
                        txtinput1.getEditText().getText().toString(),
                        txtinput2.getEditText().getText().toString(),
                        txtinput3.getEditText().getText().toString(),
                        txtinput4.getEditText().getText().toString(),
                        Operation.DIV
                );
            }
        });

    }


    @Override
    public void OnTranslationResult(String s1, String s2)
    {
        txtoutput1.getEditText().setText(s1);
        txtoutput2.getEditText().setText(s2);

    }

    @Override
    public void OnAdvancedTranslationResult(ArrayList<String> outputs) {
        //not needed for basic calculations
    }




    //---------------------------------------
    //Chart & visual related

    private void Init_Inputboxes()
    {
        txtinput1.getEditText().setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                inputdialog = new AdvNumberInputDialog(Basic_Calculations_Activity.this, Basic_Calculations_Activity.this,
                        new AdvInputCallback()
                        {
                            @Override
                            public void OnNumberInput(String num)
                            {
                                if(!txtinput1.getEditText().getText().equals(num))
                                {
                                    txtinput1.getEditText().setText(num);
                                    Refresh_Visuals();
                                }
                            }
                        },txtinput1.getEditText().getText().toString());

                inputdialog.show();
            }
        });

        txtinput2.getEditText().setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                inputdialog = new AdvNumberInputDialog(Basic_Calculations_Activity.this, Basic_Calculations_Activity.this,
                        new AdvInputCallback()
                        {

                            @Override
                            public void OnNumberInput(String num)
                            {
                                if(!txtinput2.getEditText().getText().equals(num))
                                {
                                    txtinput2.getEditText().setText(num);
                                    Refresh_Visuals();
                                }
                            }
                        },txtinput2.getEditText().getText().toString());

                inputdialog.show();
            }
        });

        txtinput3.getEditText().setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                inputdialog = new AdvNumberInputDialog(Basic_Calculations_Activity.this, Basic_Calculations_Activity.this,
                        new AdvInputCallback()
                        {
                            @Override
                            public void OnNumberInput(String num)
                            {
                                if(!txtinput3.getEditText().equals(num))
                                {
                                    txtinput3.getEditText().setText(num);
                                    Refresh_Visuals();
                                }

                            }
                        },txtinput3.getEditText().getText().toString());

                inputdialog.show();
            }
        });

        txtinput4.getEditText().setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                inputdialog = new AdvNumberInputDialog(Basic_Calculations_Activity.this, Basic_Calculations_Activity.this,
                        new AdvInputCallback()
                        {
                            @Override
                            public void OnNumberInput(String num)
                            {
                                if(!txtinput4.getEditText().getText().equals(num))
                                {
                                    txtinput4.getEditText().setText(num);
                                    Refresh_Visuals();
                                }
                            }
                        },txtinput4.getEditText().getText().toString());

                inputdialog.show();
            }
        });
    }

    private void Refresh_Visuals()
    {
        chartbuilder.clear();

        String s1 = txtinput1.getEditText().getText().toString();
        String s2 = txtinput2.getEditText().getText().toString();
        String s3 = txtinput3.getEditText().getText().toString();
        String s4 = txtinput4.getEditText().getText().toString();
        String s5 = txtoutput1.getEditText().getText().toString();
        String s6 = txtoutput2.getEditText().getText().toString();

        if(s1.length() != 0 && s2.length() != 0)
        {
            //Need to convert polar-format because chart can only handle cartesian
            if(inputformattop == FormatType.POLAR)
            {
                String[] output = Convert_to_Cartesian(s1,s2);
                s1 = output[0];
                s2 = output[1];
            }

            Add_visual_Input(s1,s2);
        }

        if(s3.length() != 0 && s4.length() != 0)
        {
            if(inputformatbot == FormatType.POLAR)
            {
                String[] output = Convert_to_Cartesian(s3,s4);
                s3 = output[0];
                s4 = output[1];
            }

            Add_visual_Input(s3,s4);
        }

        //Reset result if there is one
        if(s5.length() != 0 && s6.length() != 0)
        {
            txtoutput1.getEditText().setText("");
            txtoutput2.getEditText().setText("");

            chartbuilder.Remove_Results();
        }

        chartbuilder.Refresh();
    }

    private void Add_visual_Input(String x, String y)
    {
        chartbuilder.Add_Input(Float.parseFloat(x),
                Float.parseFloat(y));
        chartbuilder.Refresh();
    }

    private void Add_visual_Output(String x, String y)
    {
        chartbuilder.Add_Result(Float.parseFloat(x),
                Float.parseFloat(y));
        chartbuilder.Refresh();
    }



    //--------------------------------------------
    //Formatting

    private void Init_Formatbuttons()
    {
        btninputformattop.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                HandleinputFormatchange(FormatLoc.INPUTTOP);
            }
        });

        btninputformatbot.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                HandleinputFormatchange(FormatLoc.INPUTBOT);
            }
        });

        btnoutputformat.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                HandleinputFormatchange(FormatLoc.OUTPUT);
            }
        });
    }
    //inputtop = 0, inputbot = 1, output = 2
    private void HandleinputFormatchange(FormatLoc location)
    {
        String[] output;
        translator.NotifyFormatChange(location);

        if(location == FormatLoc.INPUTTOP)
        {
            if(inputformattop == FormatType.CARTESIAN)
            {
                output = Convert_to_Polar(
                        txtinput1.getEditText().getText().toString(),
                        txtinput2.getEditText().getText().toString()
                );

                txtinput1.setHint("R");
                txtinput2.setHint("Phi");
                btninputformattop.setText("C");
                inputformattop = FormatType.POLAR;
            }
            else
            {
                output = Convert_to_Cartesian(
                        txtinput1.getEditText().getText().toString(),
                        txtinput2.getEditText().getText().toString()
                );

                txtinput1.setHint("Re");
                txtinput2.setHint("Im");
                btninputformattop.setText("P");
                inputformattop = FormatType.CARTESIAN;
            }

            if(output[0].length() != 0 && output[1].length() != 0)
            {
                txtinput1.getEditText().setText(output[0]);
                txtinput2.getEditText().setText(output[1]);
            }
        }
        else if(location == FormatLoc.INPUTBOT)
        {
            if(inputformatbot == FormatType.CARTESIAN)
            {
                output = FormatConverter.Get().Convert_to_Polar(
                        txtinput3.getEditText().getText().toString(),
                        txtinput4.getEditText().getText().toString()
                );

                txtinput3.setHint("R");
                txtinput4.setHint("Phi");
                btninputformatbot.setText("C");
                inputformatbot = FormatType.POLAR;
            }
            else
            {
                output = FormatConverter.Get().Convert_to_Cartesian(
                        txtinput3.getEditText().getText().toString(),
                        txtinput4.getEditText().getText().toString()
                );

                txtinput3.setHint("Re");
                txtinput4.setHint("Im");
                btninputformatbot.setText("P");
                inputformatbot = FormatType.CARTESIAN;
            }

            if(output[0].length() != 0 && output[1].length() != 0)
            {
                txtinput3.getEditText().setText(output[0]);
                txtinput4.getEditText().setText(output[1]);
            }
        }
        else if(location == FormatLoc.OUTPUT)
        {
            if(outputformat == FormatType.CARTESIAN)
            {
                output = FormatConverter.Get().Convert_to_Polar(
                        txtoutput1.getEditText().getText().toString(),
                        txtoutput2.getEditText().getText().toString()
                );

                btnoutputformat.setText("C");
                outputformat = FormatType.POLAR;
            }
            else
            {
                output = FormatConverter.Get().Convert_to_Cartesian(
                        txtoutput1.getEditText().getText().toString(),
                        txtoutput2.getEditText().getText().toString()
                );

                btnoutputformat.setText("P");
                outputformat = FormatType.CARTESIAN;
            }

            if(output[0].length() != 0 && output[1].length() != 0)
            {
                txtoutput1.getEditText().setText(output[0]);
                txtoutput2.getEditText().setText(output[1]);
            }
        }

    }

    private String[] Convert_to_Cartesian(String s1, String s2)
    {
        return FormatConverter.Get().Convert_to_Cartesian(s1,s2);
    }

    private String[] Convert_to_Polar(String s1, String s2)
    {
        return FormatConverter.Get().Convert_to_Polar(s1,s2);
    }




    //Menu


    //Creates and sets special top bar
    private void Create_Topbar()
    {
        toolbar = findViewById(R.id.topbarbasiccalculations);
        setSupportActionBar(toolbar);

        //Set navigation button
        //Switch back to main activity when back button is pressed
        toolbar.setNavigationOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(Basic_Calculations_Activity.this,MainActivity.class);
                startActivity(intent);
            }
        });
    }

    //Necessary to link menu to top bar
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.basic_calculations_menu, menu);
        return true;
    }


    //--------------------------------------------



    private Chartbuilder chartbuilder = null;
    private Translator translator = null;


    //Number input system
    AdvNumberInputDialog inputdialog = null;

    //GUI

    private TextInputLayout txtinput1, txtinput2, txtinput3, txtinput4,
            txtoutput1, txtoutput2;
    private Button btninputformattop, btninputformatbot, btnoutputformat;
    //when starting app cartesian format is default
    private FormatType inputformattop = FormatType.CARTESIAN, inputformatbot = FormatType.CARTESIAN,
            outputformat = FormatType.CARTESIAN;

    private Button btnaddition, btnsubtraction, btnmultiplication,btndivision;

    private Toolbar toolbar;


}