package Chart;

import static java.lang.Math.abs;

import android.graphics.Color;

import com.github.mikephil.charting.charts.ScatterChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.ScatterData;
import com.github.mikephil.charting.data.ScatterDataSet;
import com.github.mikephil.charting.interfaces.datasets.IScatterDataSet;

import java.util.ArrayList;
import java.util.List;

public class Chartbuilder
{

    public Chartbuilder(ScatterChart ref)
    {
        chart = ref;

        axisdim = stdaxisdim;


        Init();
    }

    //refreshing is responsibility of user
    public void Refresh()
    {
        chart.invalidate();
    }

    public void Add_Input(float x, float y)
    {
        Adjust_Axis(x,y);

        inputset.addEntry(new Entry(x,y));

        inputset.notifyDataSetChanged();

    }

    public void Add_Result(float x, float y)
    {
        Adjust_Axis(x,y);

        resultset.addEntry(new Entry(x,y));

        resultset.notifyDataSetChanged();
    }

    public void Remove_Results()
    {

        resultset.clear();
        chart.notifyDataSetChanged();
    }


    public void clear()
    {

        inputset.clear();

        resultset.clear();

        Reset_Axis();

        chart.notifyDataSetChanged();
    }



    private void Init()
    {
        //Add origin point
        List<Entry> entries = new ArrayList<>();
        entries.add(new Entry(0, 0));

        // Create origin
        ScatterDataSet originset = new ScatterDataSet(entries, "Origin");
        originset.setColor(Color.GREEN);
        originset.setScatterShape(ScatterChart.ScatterShape.CIRCLE);

        //Create dataset for input points to display

        inputset = new ScatterDataSet(new ArrayList<>(),"Input");
        inputset.setColor(Color.BLUE);
        inputset.setScatterShape(ScatterChart.ScatterShape.CIRCLE);

        //Create dataset for input results to display

        resultset = new ScatterDataSet(new ArrayList<>(),"Result");
        resultset.setColor(Color.RED);
        resultset.setScatterShape(ScatterChart.ScatterShape.CIRCLE);

        // Create a list of datasets
        List<IScatterDataSet> dataSets = new ArrayList<>();
        dataSets.add(originset);
        dataSets.add(inputset);
        dataSets.add(resultset);

        // Set the data to the chart
        ScatterData scatterData = new ScatterData(dataSets);
        chart.setData(scatterData);


        // Configure the X axis
        XAxis xAxis = chart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setAxisMinimum(-axisdim);
        xAxis.setAxisMaximum(axisdim);
        xAxis.setGranularity(0.1f);
        xAxis.setDrawGridLines(true);



        // Configure the left Y axis
        YAxis leftAxis = chart.getAxisLeft();
        leftAxis.setAxisMinimum(-axisdim);
        leftAxis.setAxisMaximum(axisdim);
        leftAxis.setGranularity(0.1f);
        leftAxis.setDrawGridLines(true);

        // Disable the right Y axis
        chart.getAxisRight().setEnabled(false);


        // Customize chart
        chart.getDescription().setEnabled(false);  // Remove the description label
        chart.getLegend().setEnabled(true);        // Enable the legend
    }

    private void Reset_Axis()
    {
        axisdim =stdaxisdim;

        XAxis xAxis = chart.getXAxis();
        YAxis leftAxis = chart.getAxisLeft();

        xAxis.setAxisMinimum(-axisdim);
        xAxis.setAxisMaximum(axisdim);
        leftAxis.setAxisMinimum(-axisdim);
        leftAxis.setAxisMaximum(axisdim);
    }

    //ensures that everypoint is inbounds of axis
    private void Adjust_Axis(float x, float y)
    {
        XAxis xAxis = chart.getXAxis();
        YAxis leftAxis = chart.getAxisLeft();

        float competitor = Math.max(Math.abs(x),Math.abs(y));

        if(competitor >= axisdim)
        {
            axisdim = competitor+1.0f;

            xAxis.setAxisMinimum(-axisdim);
            xAxis.setAxisMaximum(axisdim);
            leftAxis.setAxisMinimum(-axisdim);
            leftAxis.setAxisMaximum(axisdim);
        }

    }



    private ScatterChart chart = null;
    private ScatterDataSet inputset = null;
    private ScatterDataSet resultset = null;


    //Axis dimention
    private float axisdim;
    private float stdaxisdim=5.0f;

}
