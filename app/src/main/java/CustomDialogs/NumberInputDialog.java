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
import com.google.android.material.textfield.TextInputLayout;

public class NumberInputDialog extends Dialog
{
    public NumberInputDialog(@NonNull Context context, Activity activity,NumberInputCallback callback , String curinput)
    {
        super(context);
        setContentView(R.layout.number_input_dialog);

        this.activity = activity;
        this.callback = callback;

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

        inputbox = findViewById(R.id.dialoginput);
        inputbox.getEditText().setText(curinput);

        Init_Buttons();
    }



    private void Init_Buttons()
    {
        btnenter = findViewById(R.id.btndialogenter);
        btnremove = findViewById(R.id.btndialogremove);
        btnclose = findViewById(R.id.btndialogclose);
        btnclear = findViewById(R.id.btndialogclear);


        // Initialize the number buttons using a loop
        for (int i = 1; i < 10; i++)
        {
            String buttonID = "btn" + i;
            int resID = activity.getResources().getIdentifier(buttonID, "id", activity.getPackageName());
            buttons[i-1] = findViewById(resID);

            // Set OnClickListener for number buttons
            buttons[i-1].setOnClickListener(v ->
            {
                Button clickedButton = (Button) v;
                inputbox.getEditText().setText(inputbox.getEditText().getText().toString() +
                        clickedButton.getText().toString());
            });
        }

        btnremove.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                String curinput = inputbox.getEditText().getText().toString();

                if(curinput.length() > 0)
                {
                    curinput = curinput.substring(0, curinput.length() - 1);
                    inputbox.getEditText().setText(curinput);

                }
            }
        });

        btnclose.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                NumberInputDialog.this.dismiss();
            }
        });

        btnclear.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                inputbox.getEditText().setText("");
            }
        });



        btnenter.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                String curinput = inputbox.getEditText().getText().toString();
                if(checkString(curinput))
                {
                    Finish_Dialog(curinput);
                }
                else
                {
                    inputbox.getEditText().setError("Not valid number");
                }
            }
        });

    }


    private boolean checkString(String tocheck)
    {
        if (tocheck == null )
        {
            return false;
        }
        else if(tocheck.length() == 0)
        {
            return true;
        }



        try {
            // Try to parse the string to a float
            Integer.parseInt(tocheck);
            return true; // If parsing was successful, return true
        } catch (NumberFormatException e) {
            // If parsing fails, it throws NumberFormatException
            return false;
        }
    }



    private void Finish_Dialog(String number)
    {
        //Pass entry to interface to give activity access to new entry
        callback.OnNumberInput(number);
        this.dismiss();
    }


    private Activity activity;
    private NumberInputCallback callback;


    //GUI

    private TextInputLayout inputbox;
    private Button[] buttons = new Button[9];
    private Button btnenter, btnclose, btnremove, btnclear;
}
