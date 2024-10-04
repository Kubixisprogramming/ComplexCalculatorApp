package Calculator;

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

        if(fim >= 0.0f)
        {
            phi = (float)Math.acos(fre / r);
        }
        else
        {
            phi = (float)Math.acos((-fre) / r) + (float)Math.PI;
        }

        output[0] = String.valueOf(r);
        output[1] = String.valueOf(phi);

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

        output[0] = String.valueOf(re);
        output[1] = String.valueOf(im);

        return output;
    }


    private static FormatConverter instance = null;
}
