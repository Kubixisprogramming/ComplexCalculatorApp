package Chart;

import static java.lang.Math.abs;

import android.graphics.Color;

import com.androidplot.ui.widget.TextLabelWidget;
import com.androidplot.xy.BoundaryMode;
import com.androidplot.xy.LineAndPointFormatter;
import com.androidplot.xy.PointLabelFormatter;
import com.androidplot.xy.SimpleXYSeries;
import com.androidplot.xy.XYPlot;
import com.androidplot.xy.XYSeries;


import java.util.ArrayList;
import java.util.List;

public class Chartbuilder
{

    public Chartbuilder(XYPlot ref)
    {
        chart = ref;
        axisDim = stdAxisDim;
        Init();
    }

    // Refreshing is responsibility of the user
    public void Refresh()
    {
        chart.redraw();
    }

    public void Add_Input(float x, float y)
    {
        Adjust_Axis(x, y);
        inputSeries.addLast(x, y);
    }

    public void Remove_Input(float x,float y)
    {


    }

    public void Add_Result(float x, float y)
    {
        Adjust_Axis(x, y);
        resultSeries.addLast(x, y);

    }

    public void Remove_Results()
    {
        resultSeries.clear();
    }

    public void clear()
    {
        inputSeries.clear();
        resultSeries.clear();
        Reset_Axis();
    }

    // Initialize the chart and datasets
    private void Init()
    {
        // Create origin point
        List<Number> originX = new ArrayList<>();
        List<Number> originY = new ArrayList<>();
        originX.add(0);
        originY.add(0);

        // Create input series
        inputSeries = new SimpleXYSeries("Input");
        resultSeries = new SimpleXYSeries("Result");

        // Create formatters to style input and result points
        LineAndPointFormatter inputFormatter = new LineAndPointFormatter(Color.BLUE, Color.BLUE, null, new PointLabelFormatter());
        LineAndPointFormatter resultFormatter = new LineAndPointFormatter(Color.RED, Color.RED, null, new PointLabelFormatter());

        // Add series and formatters to the plot
        chart.addSeries(inputSeries, inputFormatter);
        chart.addSeries(resultSeries, resultFormatter);

        // Set domain (X-axis) and range (Y-axis) boundaries
        chart.setDomainBoundaries(-axisDim, axisDim, BoundaryMode.FIXED);
        chart.setRangeBoundaries(-axisDim, axisDim, BoundaryMode.FIXED);

        // Set the chart title, labels, etc.
        chart.setTitle((TextLabelWidget) null);
        chart.getLegend().setVisible(true);

        // Customize axes
        chart.getDomainTitle().setText("X Axis");
        chart.getRangeTitle().setText("Y Axis");

        // Refresh to apply changes
        chart.redraw();
    }

    private void Reset_Axis()
    {
        axisDim = stdAxisDim;
        chart.setDomainBoundaries(-axisDim, axisDim, BoundaryMode.FIXED);
        chart.setRangeBoundaries(-axisDim, axisDim, BoundaryMode.FIXED);
        chart.redraw();
    }

    // Adjusts the axis if new data points go beyond current bounds
    private void Adjust_Axis(float x, float y)
    {
        float competitor = Math.max(Math.abs(x), Math.abs(y));

        if (competitor >= axisDim) {
            axisDim = competitor + 1.0f;
            chart.setDomainBoundaries(-axisDim, axisDim, BoundaryMode.FIXED);
            chart.setRangeBoundaries(-axisDim, axisDim, BoundaryMode.FIXED);
        }
    }



    private XYPlot chart;
    private SimpleXYSeries inputSeries;
    private SimpleXYSeries resultSeries;    // Axis dimension settings
    private float axisDim;
    private float stdAxisDim = 5.0f;
}
