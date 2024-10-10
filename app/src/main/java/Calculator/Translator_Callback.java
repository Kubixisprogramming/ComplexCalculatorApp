package Calculator;

import java.util.ArrayList;

public interface Translator_Callback
{
    void OnTranslationResult(String s1, String s2);

    void OnAdvancedTranslationResult(ArrayList<String> outputs);
}
