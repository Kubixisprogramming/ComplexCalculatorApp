package com.bino.complexcalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

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
import Chart.Chartbuilder;
import CustomDialogs.NumberInputCallback;
import CustomDialogs.NumberInputDialog;

public class Basic_Calculations_Activity extends AppCompatActivity implements CalculatorCallback
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basic_calculations);

        chartbuilder = new Chartbuilder(findViewById(R.id.basicschart));

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
                Perform_calculation("add");
            }
        });

        btnsubtraction.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Perform_calculation("sub");
            }
        });

        btnmultiplication.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Perform_calculation("mul");
            }
        });


        btndivision.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Perform_calculation("div");

            }
        });

    }


    public void Perform_calculation(String operation)
    {
        String s1 = txtinput1.getEditText().getText().toString();
        String s2 = txtinput2.getEditText().getText().toString();
        String s3 = txtinput3.getEditText().getText().toString();
        String s4 = txtinput4.getEditText().getText().toString();

        if(operation.equals("add"))
        {
            //convert both inputs to cartesian

            if(inputpolarformattop)
            {
                String conv[] = Convert_to_Cartesian(s1,s2);
                s1 = conv[0];
                s2 = conv[1];
            }
            if(inputpolarformatbot)
            {
                String conv[] = Convert_to_Cartesian(s3,s4);
                s3 = conv[0];
                s4 = conv[1];
            }

            Calculator.Get(Basic_Calculations_Activity.this).Add(s1,s2,s3,s4);

        }
        else if(operation.equals("sub"))
        {
            //convert both inputs to cartesian

            if(inputpolarformattop)
            {
                String conv[] = Convert_to_Cartesian(s1,s2);
                s1 = conv[0];
                s2 = conv[1];
            }
            if(inputpolarformatbot)
            {
                String conv[] = Convert_to_Cartesian(s3,s4);
                s3 = conv[0];
                s4 = conv[1];
            }

            Calculator.Get(Basic_Calculations_Activity.this).Subtract(s1,s2,s3,s4);
        }
        else if(operation.equals("mul"))
        {
            //convert both inputs to polar

            if(!inputpolarformattop)
            {
                String conv[] = Convert_to_Polar(s1,s2);
                s1 = conv[0];
                s2 = conv[1];
            }
            if(!inputpolarformatbot)
            {
                String conv[] = Convert_to_Polar(s3,s4);
                s3 = conv[0];
                s4 = conv[1];
            }

            Calculator.Get(Basic_Calculations_Activity.this).Multiply(s1,s2,s3,s4);
        }
        else if(operation.equals("div"))
        {
            //convert both inputs to polar

            if(!inputpolarformattop)
            {
                String conv[] = Convert_to_Polar(s1,s2);
                s1 = conv[0];
                s2 = conv[1];
            }
            if(!inputpolarformatbot)
            {
                String conv[] = Convert_to_Polar(s3,s4);
                s3 = conv[0];
                s4 = conv[1];
            }

            Calculator.Get(Basic_Calculations_Activity.this).Divide(s1,s2,s3,s4);
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


    //---------------------------------------
    //Chart & visual related

    private void Init_Inputboxes()
    {
        txtinput1.getEditText().setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                inputdialog = new NumberInputDialog(Basic_Calculations_Activity.this, Basic_Calculations_Activity.this,
                        new NumberInputCallback()
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
                inputdialog = new NumberInputDialog(Basic_Calculations_Activity.this, Basic_Calculations_Activity.this,
                        new NumberInputCallback()
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
                inputdialog = new NumberInputDialog(Basic_Calculations_Activity.this, Basic_Calculations_Activity.this,
                        new NumberInputCallback()
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
                inputdialog = new NumberInputDialog(Basic_Calculations_Activity.this, Basic_Calculations_Activity.this,
                        new NumberInputCallback()
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
            if(inputpolarformattop)
            {
                String[] output = Convert_to_Cartesian(s1,s2);
                s1 = output[0];
                s2 = output[1];
            }

            Add_visual_Input(s1,s2);
        }

        if(s3.length() != 0 && s4.length() != 0)
        {
            if(inputpolarformatbot)
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
                HandleinputFormatchange(0);
            }
        });

        btninputformatbot.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                HandleinputFormatchange(1);
            }
        });

        btnoutputformat.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                HandleinputFormatchange(2);
            }
        });
    }
    //inputtop = 0, inputbot = 1, output = 2
    private void HandleinputFormatchange(int lane)
    {
        String[] output;

        if(lane == 0)
        {

            if(inputpolarformattop == false)
            {
                output = Convert_to_Polar(
                        txtinput1.getEditText().getText().toString(),
                        txtinput2.getEditText().getText().toString()
                );

                txtinput1.setHint("R");
                txtinput2.setHint("Phi");
                btninputformattop.setText("C");
                inputpolarformattop = true;
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
                inputpolarformattop = false;
            }

            if(output[0].length() != 0 && output[1].length() != 0)
            {
                txtinput1.getEditText().setText(output[0]);
                txtinput2.getEditText().setText(output[1]);
            }
        }
        else if(lane == 1)
        {
            if(inputpolarformatbot == false)
            {
                output = FormatConverter.Get().Convert_to_Polar(
                        txtinput3.getEditText().getText().toString(),
                        txtinput4.getEditText().getText().toString()
                );

                txtinput3.setHint("R");
                txtinput4.setHint("Phi");
                btninputformatbot.setText("C");
                inputpolarformatbot = true;
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
                inputpolarformatbot = false;
            }

            if(output[0].length() != 0 && output[1].length() != 0)
            {
                txtinput3.getEditText().setText(output[0]);
                txtinput4.getEditText().setText(output[1]);
            }
        }
        else if(lane == 2)
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



    private Chartbuilder chartbuilder = null;


    //Number input system
    NumberInputDialog inputdialog = null;

    //GUI

    private TextInputLayout txtinput1, txtinput2, txtinput3, txtinput4,
            txtoutput1, txtoutput2;
    private Button btninputformattop, btninputformatbot, btnoutputformat;
    //when starting app cartesian format is default
    private boolean inputpolarformattop = false, inputpolarformatbot = false,
            outputpolarformat = false;

    private Button btnaddition, btnsubtraction, btnmultiplication,btndivision;

}