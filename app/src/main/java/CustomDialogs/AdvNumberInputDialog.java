package CustomDialogs;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import androidx.annotation.NonNull;

import com.bino.complexcalculator.R;
import com.ezylang.evalex.Expression;
import com.google.android.material.textfield.TextInputLayout;

import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AdvNumberInputDialog extends Dialog
{

    public AdvNumberInputDialog(@NonNull Context context, Activity activity, AdvInputCallback callback , String curinput)
    {
        super(context);
        setContentView(R.layout.advnumber_input_dialog);

        //Make dialog more wide
        Window window = this.getWindow();
        if (window != null) {
            WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
            layoutParams.copyFrom(window.getAttributes());

            // Set custom width and height if necessary
            layoutParams.width = (int)(activity.getResources().getDisplayMetrics().widthPixels * 0.95);  // 90% of screen width
            layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT; // Height adapts to content

            window.setAttributes(layoutParams);
        }


        this.activity = activity;
        this.callback = callback;

        Init_Button_Dict();
        Init_Views(curinput);
    }




    //-----------------------------------------------
    //View and input


    private void Init_Views(String curinput)
    {
        inputbox = findViewById(R.id.dialogainput);
        inputbox.getEditText().setText(curinput);

        Init_Control_Buttons();
        Init_Input_Buttons();
    }


    private void Init_Control_Buttons()
    {
        btnenter = findViewById(R.id.btnaenter);
        btnexit = findViewById(R.id.btnadialogclose);
        btnclear = findViewById(R.id.btnadialogclear);
        btnremove = findViewById(R.id.btnaremove);


        btnenter.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if(Check_Input(inputbox.getEditText().getText().toString()))
                {
                    Finish_Dialog(inputbox.getEditText().getText().toString());
                }
                else
                {
                    inputbox.getEditText().setError("no good");
                }
            }
        });

        btnexit.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                AdvNumberInputDialog.this.dismiss();
            }
        });

        btnclear.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                inputbox.getEditText().setText("");
                inputhistory.clear();
            }
        });

        btnremove.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                //Advanced function required which determines if character is a single sign (e.g
                // +) or a function (e.g sin)
                String curinput = inputbox.getEditText().getText().toString();

                if(inputhistory.size() > 0)
                {
                    int newsize = curinput.length() - inputhistory.get(inputhistory.size()-1);

                    inputbox.getEditText().setText(curinput.substring(0,newsize));
                    inputhistory.remove(inputhistory.size()-1);
                }
                else if(!curinput.equals(""))
                {
                    inputbox.getEditText().setText(curinput.substring(0, curinput.length() - 1));
                }
            }
        });

    }


    private void Init_Input_Buttons()
    {

        buttons = new Button[27];

        int i = 0;
        for (Map.Entry<String, String> entry : buttondict.entrySet())
        {
            String key = entry.getKey();

            String buttonID = key;
            int resID = activity.getResources().getIdentifier(buttonID, "id", activity.getPackageName());
            buttons[i] = findViewById(resID);

            // Set OnClickListener for number buttons
            buttons[i].setOnClickListener(v ->
            {
                Button clickedButton = (Button) v;
                String buttonname = clickedButton.getResources().getResourceEntryName(clickedButton.getId());

                inputbox.getEditText().setText(inputbox.getEditText().getText().toString() +
                        buttondict.get(buttonname));

                inputhistory.add(buttondict.get(buttonname).length());
            });
            ++i;
        }

    }



    //EVALEX AND CONVERSIONS

    private boolean Check_Input(String curinput)
    {

        if(curinput.equals(""))
        {
            return true;
        }
        else
        {
            try
            {
                Expression expression = new Expression(curinput);
                BigDecimal result = expression.evaluate().getNumberValue();  // This will throw an ArithmeticException

                result = result.setScale(6, RoundingMode.HALF_UP);

                inputbox.getEditText().setText(result.toString());

            }
            catch (ArithmeticException e)
            {
                return false;
            }
            catch (Exception e)
            {
                return false;
            }

            return true;

        }

    }

    private void Init_Button_Dict()
    {
        inputhistory = new ArrayList<>();

        buttondict = new HashMap<>();

        buttondict.put("btna7","7");
        buttondict.put("btna8","8");
        buttondict.put("btna9","9");
        buttondict.put("btna6","6");
        buttondict.put("btna5","5");
        buttondict.put("btna4","4");
        buttondict.put("btna3","3");
        buttondict.put("btna2","2");
        buttondict.put("btna1","1");
        buttondict.put("btna0","0");


        buttondict.put("btnapi","PI");
        buttondict.put("btnae","e");

        buttondict.put("btnasin","sinr(");
        buttondict.put("btnacos","cosr(");
        buttondict.put("btnatan","tanr(");
        buttondict.put("btnaarcsin","asinr(");
        buttondict.put("btnaarccos","acosr(");
        buttondict.put("btnaarctan","atanr(");

        buttondict.put("btnaroot","sqrt(");
        buttondict.put("btnapow","^");

        buttondict.put("btnadiv","/");
        buttondict.put("btnaadd","+");
        buttondict.put("btnamin","-");
        buttondict.put("btnamul","*");

        buttondict.put("btnabracopen","(");
        buttondict.put("btnabracclose",")");
        buttondict.put("btnacomma",".");

    }





    private void Finish_Dialog(String input)
    {
        callback.OnNumberInput(input);
        this.dismiss();
    }


    private Activity activity;
    private AdvInputCallback callback;


    //GUI

    private Button[] buttons;
    private Button btnclear,btnremove,btnenter,btnexit;
    private TextInputLayout inputbox;



    //links button names to actual input (e.g btnname: btnasin, input: sin( ,
    // btncomma : ','...)
    private Map<String,String> buttondict;

    //stores the size of last inputs to make removing them easier
    private List<Integer> inputhistory;


}
