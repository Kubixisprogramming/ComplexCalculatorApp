package com.bino.complexcalculator;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;

import Calculator.Calculator;
import Calculator.CalculatorCallback;
import Calculator.Translator;
import Calculator.FormatType;
import Calculator.Translator_Callback;
import Calculator.Operation;
import Calculator.FormatLoc;
import Calculator.FormatConverter;
import Chart.Chartbuilder;
import CustomDialogs.AdvInputCallback;
import CustomDialogs.AdvNumberInputDialog;
import CustomDialogs.NumberInputCallback;
import CustomDialogs.NumberInputDialog;

public class Advanced_Calculations_Activity extends AppCompatActivity implements Translator_Callback
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advanced_calculations);
        extraresultcontainer = findViewById(R.id.extraoutputbox);
        Create_Topbar();

        chartbuilder = new Chartbuilder(findViewById(R.id.advancedchart));
        translator = new Translator(inputformat,FormatType.EMPTY,outputformat,Advanced_Calculations_Activity.this);

        Init_Views();
        Init_Onclick();
    }



    private void Init_Views()
    {
        txtinput1 = findViewById(R.id.txtadvancedinput1);
        txtinput2 = findViewById(R.id.txtadvancedinput2);
        txtinput3 = findViewById(R.id.txtadvancedinput3);
        txtoutput1 = findViewById(R.id.txtadvancedoutput1);
        txtoutput2 = findViewById(R.id.txtadvancedoutput2);

        btninputformat = findViewById(R.id.btnadvancedinputformat);
        btnoutputformat = findViewById(R.id.btnadvancedoutputformat);
        btnroot = findViewById(R.id.btnroot);
        btnexpo = findViewById(R.id.btnexponent);
    }


    private void Init_Onclick()
    {
        Init_Formatbuttons();

        Init_Inputboxes();

        Init_Operationbtns();
    }


    //-------------------------------------------
    //Basic math operations

    private void Init_Operationbtns()
    {
        btnroot.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                translator.Perform_Calculation(
                        txtinput1.getEditText().getText().toString(),
                        txtinput2.getEditText().getText().toString(),
                        txtinput3.getEditText().getText().toString(),
                        "",
                        Operation.ROOT
                );
            }
        });

        btnexpo.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                translator.Perform_Calculation(
                        txtinput1.getEditText().getText().toString(),
                        txtinput2.getEditText().getText().toString(),
                        txtinput3.getEditText().getText().toString(),
                        "",
                        Operation.EXP
                );
            }
        });

    }

    @Override
    public void OnTranslationResult(String s1, String s2)
    {
        txtoutput1.getEditText().setText(s1);
        txtoutput2.getEditText().setText(s2);

        Clear_Extraoutputs();
    }

    @Override
    public void OnAdvancedTranslationResult(ArrayList<String> outputs)
    {
        Clear_Extraoutputs();

        txtoutput1.getEditText().setText(outputs.get(0));
        txtoutput2.getEditText().setText(outputs.get(1));

        for(int i = 2; i < outputs.size(); i+=2)
        {
            Add_Extraoutput(outputs.get(i),outputs.get(i+1),(i/2)+1);
        }
    }



    //--------------------------------------------
    //Formatting

    private void Init_Formatbuttons()
    {
        btninputformat.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                HandleinputFormatchange(FormatLoc.INPUTTOP);
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

            if(inputformat == FormatType.CARTESIAN)
            {
                output = Convert_to_Polar(
                        txtinput1.getEditText().getText().toString(),
                        txtinput2.getEditText().getText().toString()
                );

                txtinput1.setHint("R");
                txtinput2.setHint("Phi");
                btninputformat.setText("C");
                inputformat = FormatType.POLAR;
            }
            else
            {
                output = Convert_to_Cartesian(
                        txtinput1.getEditText().getText().toString(),
                        txtinput2.getEditText().getText().toString()
                );

                txtinput1.setHint("Re");
                txtinput2.setHint("Im");
                btninputformat.setText("P");
                inputformat = FormatType.CARTESIAN;
            }

            if(output[0].length() != 0 && output[1].length() != 0)
            {
                txtinput1.getEditText().setText(output[0]);
                txtinput2.getEditText().setText(output[1]);
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

                Convert_Extraoutputs(FormatType.POLAR);
            }
            else
            {
                output = FormatConverter.Get().Convert_to_Cartesian(
                        txtoutput1.getEditText().getText().toString(),
                        txtoutput2.getEditText().getText().toString()
                );

                btnoutputformat.setText("P");
                outputformat = FormatType.CARTESIAN;
                Convert_Extraoutputs(FormatType.CARTESIAN);
            }

            if(output[0].length() != 0 && output[1].length() != 0)
            {
                txtoutput1.getEditText().setText(output[0]);
                txtoutput2.getEditText().setText(output[1]);
            }
        }

    }


    private void Convert_Extraoutputs(FormatType format)
    {

        if(format == FormatType.POLAR)
        {
            for (int i = 0; i < extraresultcontainer.getChildCount(); i++)
            {
                View child = extraresultcontainer.getChildAt(i);

                // Check if the child is an instance of TextInputLayout
                if (child instanceof TextInputLayout)
                {
                    TextInputLayout textInputLayout = (TextInputLayout) child;

                    if (textInputLayout != null)
                    {
                        String s = textInputLayout.getEditText().getText().toString();


                        String[] numberstring = s.split(": ");

                        String numstring = numberstring[1];
                        String prefix = numberstring[0];

                        String[] numbers = numstring.split(",");

                        String s1 = numbers[0];
                        String s2 = numbers[1];

                        String[] conv = Convert_to_Polar(s1,s2);


                        String resultstring = prefix +": "+ conv[0] + " , " + conv[1];

                        textInputLayout.getEditText().setText(resultstring);
                    }
                }
            }
        }
        else
        {
            for (int i = 0; i < extraresultcontainer.getChildCount(); i++)
            {
                View child = extraresultcontainer.getChildAt(i);

                // Check if the child is an instance of TextInputLayout
                if (child instanceof TextInputLayout)
                {
                    TextInputLayout textInputLayout = (TextInputLayout) child;

                    if (textInputLayout != null)
                    {
                        String s = textInputLayout.getEditText().getText().toString();


                        String[] numberstring = s.split(": ");

                        String numstring = numberstring[1];
                        String prefix = numberstring[0];

                        String[] numbers = numstring.split(",");

                        String s1 = numbers[0];
                        String s2 = numbers[1];

                        String[] conv = Convert_to_Cartesian(s1,s2);


                        String resultstring = prefix +": "+ conv[0] + " , " + conv[1];

                        textInputLayout.getEditText().setText(resultstring);
                    }
                }
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


    //--------------------------------------------
    //Chart and visual related

    private void Init_Inputboxes()
    {
        txtinput1.getEditText().setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                inputdialog = new AdvNumberInputDialog(Advanced_Calculations_Activity.this, Advanced_Calculations_Activity.this,
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
                inputdialog = new AdvNumberInputDialog(Advanced_Calculations_Activity.this, Advanced_Calculations_Activity.this,
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
                basicinputdialog = new NumberInputDialog(Advanced_Calculations_Activity.this, Advanced_Calculations_Activity.this,
                        new NumberInputCallback() {
                            @Override
                            public void OnNumberInput(String num)
                            {
                                if(!txtinput3.getEditText().getText().equals(num))
                                {
                                    txtinput3.getEditText().setText(num);

                                    Refresh_Visuals();
                                }
                            }
                        },txtinput3.getEditText().getText().toString());

                basicinputdialog.show();
            }

        });
    }

    private void Refresh_Visuals()
    {
        chartbuilder.clear();

        String s1 = txtinput1.getEditText().getText().toString();
        String s2 = txtinput2.getEditText().getText().toString();
        String s3 = txtoutput1.getEditText().getText().toString();
        String s4 = txtoutput2.getEditText().getText().toString();


        if(s1.length() != 0 && s2.length() != 0)
        {
            //Need to convert polar-format because chart can only handle cartesian
            if(inputformat == FormatType.POLAR)
            {
                String[] output = Convert_to_Cartesian(s1,s2);
                s1 = output[0];
                s2 = output[1];
            }

            Add_visual_Input(s1,s2);
        }

        //Reset result if there is one
        if(s3.length() != 0 && s4.length() != 0)
        {
            txtoutput1.getEditText().setText("");
            txtoutput2.getEditText().setText("");

            chartbuilder.Remove_Results();
        }

        //Also clear extra outputs which can spawn because of rooting operation
        Clear_Extraoutputs();

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



    private void Clear_Extraoutputs()
    {
        extraresultcontainer.removeAllViews();
    }

    private void Add_Extraoutput(String s1, String s2, int pos)
    {
        // Inflate TextInputLayout from XML
        LayoutInflater inflater = LayoutInflater.from(this);

        // Inflate text_input_layout_template.xml for txts1
        TextInputLayout txts1 = (TextInputLayout) inflater.inflate(R.layout.text_input_template, null);


        // Get references to the inner TextInputEditText
        EditText textInputEditText1 = txts1.getEditText();


        // Set the text in the TextInputEditText fields
        textInputEditText1.setText(pos + ": " + s1 + " , " + s2);


        // Add the inflated layouts to the parent container (extraresultcontainer)
        extraresultcontainer.addView(txts1);
    }


    //Menu


    //Creates and sets special top bar
    private void Create_Topbar()
    {
        toolbar = findViewById(R.id.topbaradvcalculations);
        setSupportActionBar(toolbar);

        //Set navigation button
        //Switch back to main activity when back button is pressed
        toolbar.setNavigationOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(Advanced_Calculations_Activity.this,MainActivity.class);
                startActivity(intent);
            }
        });
    }

    //Necessary to link menu to top bar
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.advanced_calculations_menu, menu);
        return true;
    }


    //------------------------------------------------




    private AdvNumberInputDialog inputdialog = null;
    private NumberInputDialog basicinputdialog = null;

    private Chartbuilder chartbuilder = null;
    private Translator translator = null;


    //GUI

    private LinearLayout extraresultcontainer=null;
    private Button btninputformat, btnoutputformat;
    private FormatType inputformat = FormatType.POLAR, outputformat = FormatType.POLAR;

    private TextInputLayout txtinput1, txtinput2, txtinput3, txtoutput1, txtoutput2;

    private Button btnroot, btnexpo;

    private Toolbar toolbar;
}