package Calculator;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class FormatConverter
{


    public static FormatConverter Get()
    {
        if(instance == null)
        {
            instance = new FormatConverter();
        }

        return instance;
    }


    private FormatConverter()
    {}


    public String[] Convert_to_Polar(String re, String im)
    {
        String[] output = {"",""};

        if(re.length() == 0 || im.length() == 0)
        {
            return output;
        }


        float fre = Float.parseFloat(re), fim = Float.parseFloat(im);

        float r = (float)Math.sqrt((fre * fre)+ (fim * fim));

        float phi = 0.0f;

        if(r != 0.0f) {
            if (fim >= 0.0f) {
                phi = (float) Math.acos(fre / r);
            } else {
                phi = (float) Math.acos((-fre) / r) + (float) Math.PI;
            }
        }

        output[0] = Round(String.valueOf(r));
        output[1] = Round(String.valueOf(phi));

        return output;
    }

    public String[] Convert_to_Cartesian(String r, String phi)
    {
        String[] output = {"",""};

        if(r.length() == 0 || phi.length() == 0)
        {
            return output;
        }


        float fr = Float.parseFloat(r), fphi = Float.parseFloat(phi);

        float re = fr * (float)Math.cos(fphi);
        float im = fr * (float)Math.sin(fphi);

        output[0] = Round(String.valueOf(re));
        output[1] = Round(String.valueOf(im));

        return output;
    }


    public String Round(String in)
    {
        if(!in.isEmpty())
        {
            BigDecimal bd = new BigDecimal(in);
            bd = bd.setScale(precision, RoundingMode.HALF_UP).stripTrailingZeros();  // Rounds to precision decimal places
            return bd.toString();
        }
        else
        {
            return "";
        }
    }


    private static FormatConverter instance = null;
    private static int precision = 5;
}
