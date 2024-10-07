package Calculator;

import java.util.ArrayList;

public interface CalculatorCallback
{
    void OnCalculationResult(String s1, String s2, boolean polarformat);

    void OnAdvancedCalculationResult(ArrayList<String> outputs, boolean polarformat);
}
