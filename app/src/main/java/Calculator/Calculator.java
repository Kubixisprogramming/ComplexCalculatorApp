package Calculator;

import java.util.ArrayList;

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
    public void Add(double n1, double n2, double n3, double n4)
    {
        //adding re parts
        double re = n1 + n3;
        //Adding im
        double im = n2 + n4;

        instance.callback.OnCalculationResult(re,im,Operation.ADD);
    }

    //Subtraction is easier with cartesian coordinates
    public void Subtract(double n1, double n2, double n3, double n4)
    {

        //sub re parts
        double re = n1 - n3;
        //sub im
        double im = n2 - n4;

        instance.callback.OnCalculationResult(re,im,Operation.SUB);
    }


    //multiplication is easier with polar coordinates
    public void Multiply(double n1, double n2, double n3, double n4)
    {

        //mult r's
        double r = n1 * n3;
        //add phi's
        double phi = n2 + n4;

        instance.callback.OnCalculationResult(r,phi,Operation.MUL);
    }


    //division is easier with polar coordinates
    public void Divide(double n1, double n2, double n3, double n4)
    {

        //div r's
        double r = n1 / n3;

        //sub phi's
        double phi = n2 - n4;

        instance.callback.OnCalculationResult(r,phi,Operation.DIV);
    }


    //requires polar coordinates
    public void Exponentiate(double n1, double n2, double n3)
    {

        //expo r
        double r = Math.pow(n1,n3);

        //mul phi
        double phi = n2 * n3;

        instance.callback.OnCalculationResult(r,phi,Operation.EXP);
    }

    public void Root(double n1, double n2, double n3)
    {
        ArrayList<Double> results = new ArrayList<>();

        double r = n1;
        double phi = n2;
        double n = n3;

        //root r
        r = Math.pow(r,1/n);

        //first value is the r for all following roots
        results.add(r);

        for(int i = 1; i <= n; ++i)
        {
            double phase = (phi / n) + ((2.0*Math.PI*(i-1)) / n);

            results.add(phase);
        }

        instance.callback.OnAdvancedCalculationResult(results,Operation.ROOT);
    }

    private static Calculator instance = null;
    private CalculatorCallback callback = null;
}
