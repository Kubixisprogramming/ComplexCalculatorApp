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

        translator = new Translator(FormatType.POLAR,FormatType.EMPTY,FormatType.POLAR,Advanced_Calculations_Activity.this,
                findViewById(R.id.advancedchart));

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

        btnoutputformat.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Change_Formatbuttons(FormatLoc.OUTPUT);

                ArrayList<String> curoutput = new ArrayList<>();
                curoutput.add(txtoutput1.getEditText().getText().toString());
                curoutput.add(txtoutput2.getEditText().getText().toString());

                ArrayList<String> extraoutputs = Extract_Extraoutputs();

                if(extraoutputs.size() > 0)
                {
                    curoutput.addAll(extraoutputs);
                }


                ArrayList<String> converted = translator.Perform_Formatchange(curoutput,FormatLoc.OUTPUT);

                if(converted.size() > 0)
                {
                    txtoutput1.getEditText().setText(converted.get(0));
                    txtoutput2.getEditText().setText(converted.get(1));
                    converted.remove(0);
                    converted.remove(0);
                }

                if(converted.size() > 0)
                {
                    Set_Extraoutputs(converted);
                }
            }
        });
    }

    private void Change_Formatbuttons(FormatLoc location)
    {
        if(location == FormatLoc.INPUTTOP)
        {
            if(btninputformat.getText().equals("P"))
            {
                btninputformat.setText("C");
                txtinput1.setHint("Re");
                txtinput2.setHint("Im");
            }
            else
            {
                btninputformat.setText("P");
                txtinput1.setHint("R");
                txtinput2.setHint("Phi");
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

    private ArrayList<String> Extract_Extraoutputs()
    {
        ArrayList<String> extraoutputs = new ArrayList<>();

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

                    extraoutputs.add(numbers[0]);
                    extraoutputs.add(numbers[1]);
                }
            }
        }

        return extraoutputs;
    }

    private void Set_Extraoutputs(ArrayList<String> extras)
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

                    String s1 = extras.get(i*2);
                    String s2 = extras.get(i*2+1);

                    String resultstring = prefix +": "+ s1 + " , " + s2;

                    textInputLayout.getEditText().setText(resultstring);
                }
            }
        }
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
                                    translator.Notify_Input_Changed_Left(num,txtinput1.getEditText().getText().toString(),
                                            txtinput2.getEditText().getText().toString());
                                    txtinput1.getEditText().setText(num);
                                    txtoutput1.getEditText().setText("");
                                    txtoutput2.getEditText().setText("");
                                    Clear_Extraoutputs();
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
                                    translator.Notify_Input_Changed_Right(num,txtinput2.getEditText().getText().toString(),
                                            txtinput1.getEditText().getText().toString());
                                    txtinput2.getEditText().setText(num);
                                    txtoutput1.getEditText().setText("");
                                    txtoutput2.getEditText().setText("");
                                    Clear_Extraoutputs();
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
                                    txtoutput1.getEditText().setText("");
                                    txtoutput2.getEditText().setText("");
                                    Clear_Extraoutputs();
                                }
                            }
                        },txtinput3.getEditText().getText().toString());

                basicinputdialog.show();
            }

        });
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
    private Translator translator = null;


    //GUI
    private LinearLayout extraresultcontainer=null;
    private Button btninputformat, btnoutputformat;
    private TextInputLayout txtinput1, txtinput2, txtinput3, txtoutput1, txtoutput2;
    private Button btnroot, btnexpo;
    private Toolbar toolbar;
}