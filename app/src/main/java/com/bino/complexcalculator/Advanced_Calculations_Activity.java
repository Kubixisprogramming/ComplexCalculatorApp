package com.bino.complexcalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.material.textfield.TextInputLayout;

import Calculator.Calculator;
import Calculator.CalculatorCallback;
import Calculator.FormatConverter;
import Chart.Chartbuilder;
import CustomDialogs.AdvInputCallback;
import CustomDialogs.AdvNumberInputDialog;
import CustomDialogs.NumberInputCallback;
import CustomDialogs.NumberInputDialog;

public class Advanced_Calculations_Activity extends AppCompatActivity implements CalculatorCallback {

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advanced_calculations);

        chartbuilder = new Chartbuilder(findViewById(R.id.advancedchart));

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
                Perform_calculation("root");
            }
        });

        btnexpo.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Perform_calculation("expo");
            }
        });

    }


    public void Perform_calculation(String operation)
    {
        String s1 = txtinput1.getEditText().getText().toString();
        String s2 = txtinput2.getEditText().getText().toString();
        String s3 = txtinput3.getEditText().getText().toString();

        if(operation.equals("root"))
        {

        }
        else if(operation.equals("expo"))
        {
            //convert both inputs to polar

            if(!inputpolarformat)
            {
                String conv[] = Convert_to_Polar(s1,s2);
                s1 = conv[0];
                s2 = conv[1];
            }

            Calculator.Get(Advanced_Calculations_Activity.this).Exponentiate(s1,s2,s3);
        }

    }


    // This method will be called when the calculation is complete
    @Override
    public void OnCalculationResult(String s1, String s2, boolean polarformat)
    {
        if(s1.length() != 0 && s2.length() != 0)
        {

            if(outputpolarformat == true && polarformat == false)
            {
                String[] conv = Convert_to_Polar(s1,s2);
                s1 = conv[0];
                s2 = conv[1];
            }
            else if(outputpolarformat == false && polarformat == true)
            {
                String[] conv = Convert_to_Cartesian(s1,s2);
                s1 = conv[0];
                s2 = conv[1];
            }

            txtoutput1.getEditText().setText(s1);
            txtoutput2.getEditText().setText(s2);
            chartbuilder.Remove_Results();

            Add_visual_Output(s1,s2);
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
                HandleinputFormatchange(0);
            }
        });

        btnoutputformat.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                HandleinputFormatchange(1);
            }
        });
    }
    //inputtop = 0, inputbot = 1, output = 2

    private void HandleinputFormatchange(int lane)
    {
        String[] output;

        if(lane == 0)
        {

            if(inputpolarformat == false)
            {
                output = Convert_to_Polar(
                        txtinput1.getEditText().getText().toString(),
                        txtinput2.getEditText().getText().toString()
                );

                txtinput1.setHint("R");
                txtinput2.setHint("Phi");
                btninputformat.setText("C");
                inputpolarformat = true;
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
                inputpolarformat = false;
            }

            if(output[0].length() != 0 && output[1].length() != 0)
            {
                txtinput1.getEditText().setText(output[0]);
                txtinput2.getEditText().setText(output[1]);
            }
        }
        else if(lane == 1)
        {
            if(outputpolarformat == false)
            {
                output = FormatConverter.Get().Convert_to_Polar(
                        txtoutput1.getEditText().getText().toString(),
                        txtoutput2.getEditText().getText().toString()
                );

                btnoutputformat.setText("C");
                outputpolarformat = true;
            }
            else
            {
                output = FormatConverter.Get().Convert_to_Cartesian(
                        txtoutput1.getEditText().getText().toString(),
                        txtoutput2.getEditText().getText().toString()
                );

                btnoutputformat.setText("P");
                outputpolarformat = false;
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
            if(inputpolarformat)
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



    private AdvNumberInputDialog inputdialog = null;
    private NumberInputDialog basicinputdialog = null;

    private Chartbuilder chartbuilder = null;

    //GUI
    private Button btninputformat, btnoutputformat;
    private boolean inputpolarformat = false, outputpolarformat = false;

    private TextInputLayout txtinput1, txtinput2, txtinput3, txtoutput1, txtoutput2;

    private Button btnroot, btnexpo;
}