package Calculator;

import java.util.ArrayList;

public interface CalculatorCallback
{
    void OnCalculationResult(Double n1, Double n2, Operation op);

    void OnAdvancedCalculationResult(ArrayList<Double> outputs, Operation op);
}
