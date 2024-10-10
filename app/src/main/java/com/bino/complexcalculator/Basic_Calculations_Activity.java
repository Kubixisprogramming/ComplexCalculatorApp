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


        translator = new Translator(FormatType.CARTESIAN,FormatType.CARTESIAN,FormatType.CARTESIAN,Basic_Calculations_Activity.this,
                findViewById(R.id.basicchart));

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
                                    translator.Notify_Input_Changed_Left(num,txtinput1.getEditText().getText().toString(),
                                            txtinput2.getEditText().getText().toString());
                                    txtinput1.getEditText().setText(num);
                                    txtoutput1.getEditText().setText("");
                                    txtoutput2.getEditText().setText("");
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
                                    translator.Notify_Input_Changed_Right(num,txtinput2.getEditText().getText().toString(),
                                            txtinput1.getEditText().getText().toString());
                                    txtinput2.getEditText().setText(num);
                                    txtoutput1.getEditText().setText("");
                                    txtoutput2.getEditText().setText("");
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
                                    translator.Notify_Input_Changed_Left(num,txtinput3.getEditText().getText().toString(),
                                            txtinput4.getEditText().getText().toString());
                                    txtinput3.getEditText().setText(num);
                                    txtoutput1.getEditText().setText("");
                                    txtoutput2.getEditText().setText("");
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
                                    translator.Notify_Input_Changed_Right(num,txtinput4.getEditText().getText().toString(),
                                            txtinput3.getEditText().getText().toString());
                                    txtinput4.getEditText().setText(num);
                                    txtoutput1.getEditText().setText("");
                                    txtoutput2.getEditText().setText("");
                                }
                            }
                        },txtinput4.getEditText().getText().toString());

                inputdialog.show();
            }
        });
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
                Change_Formatbuttons(FormatLoc.INPUTTOP);

                ArrayList<String> curinput = new ArrayList<>();
                curinput.add(txtinput1.getEditText().getText().toString());
                curinput.add(txtinput2.getEditText().getText().toString());

                ArrayList<String> converted = translator.Perform_Formatchange(curinput,FormatLoc.INPUTTOP);

                if(converted.size() > 0)
                {
                    txtinput1.getEditText().setText(converted.get(0));
                    txtinput2.getEditText().setText(converted.get(1));
                }

            }
        });

        btninputformatbot.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Change_Formatbuttons(FormatLoc.INPUTBOT);

                ArrayList<String> curinput = new ArrayList<>();
                curinput.add(txtinput3.getEditText().getText().toString());
                curinput.add(txtinput4.getEditText().getText().toString());

                ArrayList<String> converted = translator.Perform_Formatchange(curinput,FormatLoc.INPUTBOT);

                if(converted.size() > 0)
                {
                    txtinput3.getEditText().setText(converted.get(0));
                    txtinput4.getEditText().setText(converted.get(1));
                }
            }
        });

        btnoutputformat.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Change_Formatbuttons(FormatLoc.OUTPUT);

                ArrayList<String> curoutput = new ArrayList<>();
                curoutput.add(txtoutput1.getEditText().getText().toString());
                curoutput.add(txtoutput2.getEditText().getText().toString());

                ArrayList<String> converted = translator.Perform_Formatchange(curoutput,FormatLoc.OUTPUT);

                if(converted.size() > 0)
                {
                    txtoutput1.getEditText().setText(converted.get(0));
                    txtoutput2.getEditText().setText(converted.get(1));
                }
            }
        });
    }


    private void Change_Formatbuttons(FormatLoc location)
    {
        if(location == FormatLoc.INPUTTOP)
        {
            if(btninputformattop.getText().equals("P"))
            {
                btninputformattop.setText("C");
                txtinput1.setHint("Re");
                txtinput2.setHint("Im");
            }
            else
            {
                btninputformattop.setText("P");
                txtinput1.setHint("R");
                txtinput2.setHint("Phi");
            }
        }
        else if(location == FormatLoc.INPUTBOT)
        {
            if(btninputformatbot.getText().equals("P"))
            {
                btninputformatbot.setText("C");
                txtinput3.setHint("Re");
                txtinput4.setHint("Im");
            }
            else
            {
                btninputformatbot.setText("P");
                txtinput3.setHint("R");
                txtinput4.setHint("Phi");
            }
        }
        else if(location == FormatLoc.OUTPUT)
        {
            if(btnoutputformat.getText().equals("P"))
            {
                btnoutputformat.setText("C");

            }
            else
            {
                btnoutputformat.setText("P");
            }
        }
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



    private Translator translator = null;
    //Number input system
    AdvNumberInputDialog inputdialog = null;

    //GUI
    private TextInputLayout txtinput1, txtinput2, txtinput3, txtinput4, txtoutput1, txtoutput2;
    private Button btninputformattop, btninputformatbot, btnoutputformat;
    private Button btnaddition, btnsubtraction, btnmultiplication,btndivision;
    private Toolbar toolbar;

}