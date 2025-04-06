package Calculator;



import com.jjoe64.graphview.GraphView;

import java.util.ArrayList;

import Chart.Chartbuilder;

//Translator class is the "translator" between the calculator class and activity
//activity gives strings and operations to the translator, it translates the input from activity
//into correct calculator format and passes the calculator output to the class in it's fitting format
public class Translator implements CalculatorCallback
{

    public Translator(FormatType t1, FormatType t2, FormatType t3,Translator_Callback callback, GraphView ref)
    {
        formatinput1 = t1;
        formatinput2 = t2;
        formatoutput1 = t3;

        this.callback = callback;
        chart = new Chartbuilder(ref);
    }



    //Activity to Calculator translation
    public void Perform_Calculation(String s1, String s2, String s3, String s4, Operation op)
    {
        if(op == Operation.ADD)
        {
            Perform_Addition(s1,s2,s3,s4);
        }
        else if(op == Operation.SUB)
        {
            Perform_Subtraction(s1,s2,s3,s4);
        }
        else if(op == Operation.MUL)
        {
            Perform_Mulitplication(s1,s2,s3,s4);
        }
        else if(op == Operation.DIV)
        {
            Perform_Division(s1,s2,s3,s4);
        }
        else if(op == Operation.EXP)
        {
            Perform_Exponentiation(s1,s2,s3);
        }
        else if(op == Operation.ROOT)
        {
            Perform_Root(s1,s2,s3);
        }
    }

    private void Perform_Addition(String s1, String s2, String s3, String s4)
    {
        //check for valid input
        if(s1.length() == 0 || s2.length() == 0 || s3.length() == 0 || s4.length() == 0)
        {return;}

        //Calculator accepts only input in cartesian format for addition

        if(formatinput1 == FormatType.POLAR)
        {
            String[] conv = FormatConverter.Get().Convert_to_Cartesian(s1,s2);
            s1 = conv[0];
            s2 = conv[1];
        }

        if(formatinput2 == FormatType.POLAR)
        {
            String[] conv = FormatConverter.Get().Convert_to_Cartesian(s3,s4);
            s3 = conv[0];
            s4 = conv[1];
        }

        //convert to double values

        double n1 = Double.parseDouble(s1),
               n2 = Double.parseDouble(s2),
               n3 = Double.parseDouble(s3),
               n4 = Double.parseDouble(s4);

        //call calculator instance
        Calculator.Get(Translator.this).Add(n1,n2,n3,n4);

    }

    private void Perform_Subtraction(String s1, String s2, String s3, String s4)
    {
        //check for valid input
        if(s1.length() == 0 || s2.length() == 0 || s3.length() == 0 || s4.length() == 0)
        {return;}

        //Calculator accepts only input in cartesian format for subtraction

        if(formatinput1 == FormatType.POLAR)
        {
            String[] conv = FormatConverter.Get().Convert_to_Cartesian(s1,s2);
            s1 = conv[0];
            s2 = conv[1];
        }

        if(formatinput2 == FormatType.POLAR)
        {
            String[] conv = FormatConverter.Get().Convert_to_Cartesian(s3,s4);
            s3 = conv[0];
            s4 = conv[1];
        }

        //convert to double values

        double n1 = Double.parseDouble(s1),
                n2 = Double.parseDouble(s2),
                n3 = Double.parseDouble(s3),
                n4 = Double.parseDouble(s4);

        //call calculator instance
        Calculator.Get(Translator.this).Subtract(n1,n2,n3,n4);
    }

    private void Perform_Division(String s1, String s2, String s3, String s4)
    {
        //check for valid input
        if(s1.length() == 0 || s2.length() == 0 || s3.length() == 0 || s4.length() == 0)
        {return;}

        //Calculator accepts only input in polar format for division

        if(formatinput1 == FormatType.CARTESIAN)
        {
            String[] conv = FormatConverter.Get().Convert_to_Polar(s1,s2);
            s1 = conv[0];
            s2 = conv[1];
        }

        if(formatinput2 == FormatType.CARTESIAN)
        {
            String[] conv = FormatConverter.Get().Convert_to_Polar(s3,s4);
            s3 = conv[0];
            s4 = conv[1];
        }

        //convert to double values

        double n1 = Double.parseDouble(s1),
                n2 = Double.parseDouble(s2),
                n3 = Double.parseDouble(s3),
                n4 = Double.parseDouble(s4);

        //call calculator instance
        Calculator.Get(Translator.this).Divide(n1,n2,n3,n4);
    }

    private void Perform_Mulitplication(String s1, String s2, String s3, String s4)
    {
        //check for valid input
        if(s1.length() == 0 || s2.length() == 0 || s3.length() == 0 || s4.length() == 0)
        {return;}

        //Calculator accepts only input in cartesian format for multiplication

        if(formatinput1 == FormatType.CARTESIAN)
        {
            String[] conv = FormatConverter.Get().Convert_to_Polar(s1,s2);
            s1 = conv[0];
            s2 = conv[1];
        }

        if(formatinput2 == FormatType.CARTESIAN)
        {
            String[] conv = FormatConverter.Get().Convert_to_Polar(s3,s4);
            s3 = conv[0];
            s4 = conv[1];
        }

        //convert to double values

        double n1 = Double.parseDouble(s1),
                n2 = Double.parseDouble(s2),
                n3 = Double.parseDouble(s3),
                n4 = Double.parseDouble(s4);

        //call calculator instance
        Calculator.Get(Translator.this).Multiply(n1,n2,n3,n4);
    }

    private void Perform_Exponentiation(String s1, String s2, String s3)
    {
        //check for valid input
        if(s1.length() == 0 || s2.length() == 0 || s3.length() == 0)
        {return;}

        //Calculator accepts only input in polar format for exponentiation
        //s3 is a natural number so it cant be converted

        if(formatinput1 == FormatType.CARTESIAN)
        {
            String[] conv = FormatConverter.Get().Convert_to_Polar(s1,s2);
            s1 = conv[0];
            s2 = conv[1];
        }

        //convert to double values

        double n1 = Double.parseDouble(s1),
                n2 = Double.parseDouble(s2),
                n3 = Double.parseDouble(s3);

        //call calculator instance
        Calculator.Get(Translator.this).Exponentiate(n1,n2,n3);
    }

    private void Perform_Root(String s1, String s2, String s3)
    {
        //check for valid input
        if(s1.length() == 0 || s2.length() == 0 || s3.length() == 0)
        {return;}

        //Calculator accepts only input in polar format for rooting
        //s3 is a natural number so it cant be converted

        if(formatinput1 == FormatType.CARTESIAN)
        {
            String[] conv = FormatConverter.Get().Convert_to_Polar(s1,s2);
            s1 = conv[0];
            s2 = conv[1];
        }

        //convert to double values

        double n1 = Double.parseDouble(s1),
                n2 = Double.parseDouble(s2),
                n3 = Double.parseDouble(s3);

        //call calculator instance
        Calculator.Get(Translator.this).Root(n1,n2,n3);
    }



    //CALCULATOR TO ACTIVITY translation

    @Override
    public void OnCalculationResult(Double n1, Double n2, Operation op)
    {
        String s1 = FormatConverter.Get().Round(String.valueOf(n1));
        String s2 = FormatConverter.Get().Round(String.valueOf(n2));


        if(op == Operation.ADD || op == Operation.SUB)
        {
            //calculator returns cartesian output fitting for chart
            Chartify(s1,s2,FormatType.CARTESIAN);
            //check if conversion to activity output format is needed

            if(formatoutput1 == FormatType.POLAR)
            {
                String[] conv = FormatConverter.Get().Convert_to_Polar(s1,s2);
                s1 = conv[0];
                s2 = conv[1];
            }

            //return result
            callback.OnTranslationResult(s1,s2);
        }
        else if(op == Operation.MUL || op == Operation.DIV || op == Operation.EXP)
        {
            //calculator returns polar output
            //check if conversion to activity output format is needed

            if(formatoutput1 == FormatType.CARTESIAN)
            {
                String[] conv = FormatConverter.Get().Convert_to_Cartesian(s1,s2);
                s1 = conv[0];
                s2 = conv[1];

                //send result also to chart
                Chartify(s1,s2,FormatType.CARTESIAN);
            }
            else
            {
                //chart result needs cartesian format
                Chartify(s1,s2,FormatType.POLAR);
            }

            //return result
            callback.OnTranslationResult(s1,s2);
        }
    }

    @Override
    public void OnAdvancedCalculationResult(ArrayList<Double> outputs, Operation op)
    {
        //Convert doubles to string
        ArrayList<String> out = new ArrayList<>();
        for(int i = 0; i < outputs.size(); ++i)
        {
            out.add(FormatConverter.Get().Round(String.valueOf(outputs.get(i))));
        }

        if(op == Operation.ROOT)
        {
            //Outputs return in polar format with outputs[0] being the r for all further pairs
            //so to create pairs suitable for activity to input we need to rearrange out array

            if(formatoutput1 == FormatType.CARTESIAN)
            {
                ArrayList<String> cartesianpairs = new ArrayList<>();

                for(int i = 1; i < out.size(); ++i)
                {
                    String[] conv = FormatConverter.Get().Convert_to_Cartesian(out.get(0),out.get(i));

                    cartesianpairs.add(conv[0]);
                    cartesianpairs.add(conv[1]);
                }

                out = cartesianpairs;

                //send coordinates also to chart
                Chartify(out,FormatType.CARTESIAN);

            }
            else if(formatoutput1 == FormatType.POLAR)
            {
                //rearrange in pairs (r,phi)
                ArrayList<String> polarpairs = new ArrayList<>();

                for(int i = 1; i < out.size(); ++i)
                {
                    polarpairs.add(out.get(0));
                    polarpairs.add(out.get(i));
                }

                out = polarpairs;

                //additional conversion is needed for chart
                Chartify(out,FormatType.POLAR);
            }

            //return result
            callback.OnAdvancedTranslationResult(out);
        }

    }



    //FORMATTING

    public ArrayList<String> Perform_Formatchange(ArrayList<String> tochange, FormatLoc location)
    {
        if(location == FormatLoc.INPUTTOP)
        {

            if(formatinput1 == FormatType.POLAR)
            {
                formatinput1 = FormatType.CARTESIAN;
                return Convert_to_Cartesian(tochange);
            }
            else
            {
                formatinput1 = FormatType.POLAR;
                return Convert_to_Polar(tochange);
            }
        }
        else if(location == FormatLoc.INPUTBOT)
        {

            if(formatinput2 == FormatType.POLAR)
            {
                formatinput2 = FormatType.CARTESIAN;
                return Convert_to_Cartesian(tochange);
            }
            else
            {
                formatinput2 = FormatType.POLAR;
                return Convert_to_Polar(tochange);
            }
        }
        else if(location == FormatLoc.OUTPUT)
        {

            if(formatoutput1 == FormatType.POLAR)
            {
                formatoutput1 = FormatType.CARTESIAN;
                return Convert_to_Cartesian(tochange);
            }
            else
            {
                formatoutput1 = FormatType.POLAR;
                return Convert_to_Polar(tochange);
            }
        }
        else
        {
            return new ArrayList<>();
        }
    }


    private ArrayList<String> Convert_to_Cartesian(ArrayList<String> tochange)
    {
        ArrayList<String> out = new ArrayList<>();

        for(int i = 0; i*2 < tochange.size();++i)
        {
            String[] conv = FormatConverter.Get().Convert_to_Cartesian(tochange.get(i*2),
                    tochange.get(i*2+1));

            out.add(conv[0]);
            out.add(conv[1]);
        }

        return out;
    }

    private ArrayList<String> Convert_to_Polar(ArrayList<String> tochange)
    {
        ArrayList<String> out = new ArrayList<>();

        for(int i = 0; i*2 < tochange.size();++i)
        {
            String[] conv = FormatConverter.Get().Convert_to_Polar(tochange.get(i*2),
                    tochange.get(i*2+1));

            out.add(conv[0]);
            out.add(conv[1]);
        }

        return out;
    }


    //Charting

    public void Notify_Input_Changed_Left(String newinput1,String oldinput1, String oldinput2)
    {
        boolean refresh_needed = false;

        //remove if there was a old point
        if(!oldinput1.isEmpty() && !oldinput2.isEmpty())
        {
            chart.Remove_Input(Float.parseFloat(oldinput1), Float.parseFloat(oldinput2));
            refresh_needed = true;
        }

        //check if there will be a new input
        if(!newinput1.isEmpty() && !oldinput2.isEmpty())
        {
            chart.Add_Input(Float.parseFloat(newinput1), Float.parseFloat(oldinput2));
            refresh_needed = true;
        }

        if(refresh_needed)
        {
            chart.Refresh();
        }
    }

    public void Notify_Input_Changed_Right(String newinput2,String oldinput2, String oldinput1)
    {
        boolean refresh_needed = false;

        //remove if there was a old point
        if(!oldinput1.isEmpty() && !oldinput2.isEmpty())
        {
            chart.Remove_Input(Float.parseFloat(oldinput1), Float.parseFloat(oldinput2));
            refresh_needed = true;
        }

        //check if there will be a new input
        if(!newinput2.isEmpty() && !oldinput1.isEmpty())
        {
            chart.Add_Input(Float.parseFloat(oldinput1), Float.parseFloat(newinput2));
            refresh_needed = true;
        }

        if(refresh_needed)
        {
            chart.Refresh();
        }
    }

    private void Add_Results(ArrayList<String> resultpairs)
    {
        //clear possible previous results
        chart.Remove_Results();

        //Add new results
        for(int i = 0; i*2 < resultpairs.size(); ++i)
        {
            chart.Add_Result(Double.parseDouble(resultpairs.get(i*2)),
                    Double.parseDouble(resultpairs.get(i*2+1)));
        }

        chart.Refresh();
    }

    //Helper functions to turn results into fitting string vectors
    private void Chartify(String s1, String s2, FormatType format)
    {
        ArrayList<String> output = new ArrayList<>();
        output.add(s1);
        output.add(s2);

        //covnersion is needed
        if(format == FormatType.POLAR)
        {
            output = Convert_to_Cartesian(output);
        }

        Add_Results(output);
    }

    private void Chartify(ArrayList<String> s, FormatType format)
    {
        ArrayList<String> output;

        //Cartesian is needed for chart
        if(format == FormatType.POLAR)
        {
            output = Convert_to_Cartesian(s);
        }
        else
        {
            output = s;
        }

        Add_Results(output);
    }



    private FormatType formatinput1, formatinput2, formatoutput1;
    private Translator_Callback callback;
    private Chartbuilder chart;

}
