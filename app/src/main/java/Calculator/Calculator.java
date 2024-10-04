package Calculator;

public class Calculator
{

    public static Calculator Get(CalculatorCallback callback)
    {
        if(instance == null)
        {
            instance = new Calculator();
            instance.callback = callback;
        }
        return instance;
    }

    private Calculator()
    {}


    //Addition is easier with cartesian coordinates
    public void Add(String s1, String s2, String s3, String s4)
    {

        if(s1.length() == 0 || s2.length() == 0 ||
           s3.length() == 0 || s4.length() == 0)
        {
            instance.callback.OnCalculationResult("","",false);
        }
        else
        {
            //adding re parts
            float re = Float.parseFloat(s1) + Float.parseFloat(s3);
            //Adding im
            float im = Float.parseFloat(s2) + Float.parseFloat(s4);

            instance.callback.OnCalculationResult(String.valueOf(re),String.valueOf(im),false);
        }
    }

    //Subtraction is easier with cartesian coordinates
    public void Subtract(String s1, String s2, String s3, String s4)
    {
        if(s1.length() == 0 || s2.length() == 0 ||
                s3.length() == 0 || s4.length() == 0)
        {
            instance.callback.OnCalculationResult("","",false);
        }
        else
        {
            //sub re parts
            float re = Float.parseFloat(s1) - Float.parseFloat(s3);
            //sub im
            float im = Float.parseFloat(s2) - Float.parseFloat(s4);

            instance.callback.OnCalculationResult(String.valueOf(re),String.valueOf(im),false);
        }
    }


    //multiplication is easier with polar coordinates
    public void Multiply(String s1, String s2, String s3, String s4)
    {

        if(s1.length() == 0 || s2.length() == 0 ||
                s3.length() == 0 || s4.length() == 0)
        {
            instance.callback.OnCalculationResult("","",true);
        }
        else
        {
            //mult r's
            float r = Float.parseFloat(s1) * Float.parseFloat(s3);

            //add phi's
            float phi = Float.parseFloat(s2) + Float.parseFloat(s4);

            instance.callback.OnCalculationResult(String.valueOf(r),String.valueOf(phi),true);
        }
    }


    //division is easier with polar coordinates
    public void Divide(String s1, String s2, String s3, String s4)
    {

        if(s1.length() == 0 || s2.length() == 0 ||
                s3.length() == 0 || s4.length() == 0)
        {
            instance.callback.OnCalculationResult("","",true);
        }
        else
        {
            //div r's
            float r = Float.parseFloat(s1) / Float.parseFloat(s3);

            //sub phi's
            float phi = Float.parseFloat(s2) - Float.parseFloat(s4);

            instance.callback.OnCalculationResult(String.valueOf(r),String.valueOf(phi),true);
        }
    }


    //requires polar coordinates
    public void Exponentiate(String s1, String s2,String exponent)
    {

        if(s1.length() == 0 || s2.length() == 0 || exponent.length() == 0)
        {
            instance.callback.OnCalculationResult("","",true);
        }
        else
        {

            float r = (float)Math.pow(Float.parseFloat(s1),Float.parseFloat(exponent));

            float phi = Float.parseFloat(s2) * Float.parseFloat(exponent);

            instance.callback.OnCalculationResult(String.valueOf(r),String.valueOf(phi),true);
        }

    }


    private static Calculator instance = null;
    private CalculatorCallback callback = null;
}
