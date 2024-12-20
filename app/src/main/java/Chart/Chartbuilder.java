package Chart;

import static java.lang.Math.abs;

import android.graphics.Color;
import android.provider.ContactsContract;


import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

public class Chartbuilder
{

    public Chartbuilder(GraphView ref)
    {
        chart = ref;
        axisDim = stdAxisDim;
        Init();
    }

    // Refreshing is the responsibility of the user
    public void Refresh()
    {
        chart.onDataChanged(false, false);
    }


    public void Add_Input(double x, double y)
    {
        Adjust_Axis(x, y);

        if (inputSeries.getHighestValueX() > x)
        {
            //resort is necessary

            ArrayList<DataPoint> points = Get_Input_Points();
            points.add(new DataPoint(x,y));
            points = Sort_Points(points);


            inputSeries.resetData(points.toArray(new DataPoint[0]));
        }
        else
        {
            inputSeries.appendData(new DataPoint(x, y), false, 1000); // '1000' is max data points
        }
    }

    public void Remove_Input(double x, double y)
    {
        // You can iterate and remove a specific point here, GraphView doesn't provide direct remove methods.

        ArrayList<DataPoint> points = Get_Input_Points();

        boolean resultrefresh = false;
        for(int i = 0; i < points.size(); ++i)
        {
            if (points.get(i).getX() == x && points.get(i).getY() == y)
            {
                points.remove(i);
                --i;
                resultrefresh = true;
            }
        }

        if(points.size() == 0)
        {
            Reset_Axis();
        }

        if(resultrefresh)
        {
            Remove_Results();
        }

        inputSeries.resetData(points.toArray(new DataPoint[0]));
    }



    public void Add_Result(double x, double y)
    {
        Adjust_Axis(x, y);

        if (resultSeries.getHighestValueX() > x)
        {
            //resort is necessary

            ArrayList<DataPoint> points = Get_Output_Points();
            points.add(new DataPoint(x,y));
            points = Sort_Points(points);


            resultSeries.resetData(points.toArray(new DataPoint[0]));
        }
        else
        {
            resultSeries.appendData(new DataPoint(x, y), false, 1000); // '1000' is max data points
        }
    }


    public void Remove_Results()
    {
        resultSeries.resetData(new DataPoint[]{});  // Clears the result series
    }


    private void Init()
    {

        // Initialize input and result series
        inputSeries = new LineGraphSeries<>(new DataPoint[] {});
        resultSeries = new LineGraphSeries<>(new DataPoint[] {});

        // Customize input series style (blue points)
        inputSeries.setColor(android.graphics.Color.BLUE);
        inputSeries.setDrawDataPoints(true);
        inputSeries.setDataPointsRadius(5);

        // Customize result series style (red points)
        resultSeries.setColor(android.graphics.Color.RED);
        resultSeries.setDrawDataPoints(true);
        resultSeries.setDataPointsRadius(5);

        // Add series to the chart
        chart.addSeries(inputSeries);
        chart.addSeries(resultSeries);

        // Set X and Y axis boundaries
        chart.getViewport().setXAxisBoundsManual(true);
        chart.getViewport().setYAxisBoundsManual(true);
        chart.getViewport().setMinX(-axisDim);
        chart.getViewport().setMaxX(axisDim);
        chart.getViewport().setMinY(-axisDim);
        chart.getViewport().setMaxY(axisDim);

        // Enable scaling/zooming
        chart.getViewport().setScalable(true);
        chart.getViewport().setScalableY(true);

        // Set axis labels (optional)
        chart.getGridLabelRenderer().setHorizontalAxisTitle("Re Axis");
        chart.getGridLabelRenderer().setVerticalAxisTitle("Im Axis");
    }

    // Reset axis boundaries to default
    private void Reset_Axis()
    {
        axisDim = stdAxisDim;
        chart.getViewport().setMinX(-axisDim);
        chart.getViewport().setMaxX(axisDim);
        chart.getViewport().setMinY(-axisDim);
        chart.getViewport().setMaxY(axisDim);
        chart.onDataChanged(false, false);
    }

    // Adjusts the axis if a point exceeds current boundaries
    private void Adjust_Axis(double x, double y)
    {
        double competitor = Math.max(Math.abs(x), Math.abs(y));

        if (competitor >= axisDim) {
            axisDim = competitor + 1.0;
            chart.getViewport().setMinX(-axisDim);
            chart.getViewport().setMaxX(axisDim);
            chart.getViewport().setMinY(-axisDim);
            chart.getViewport().setMaxY(axisDim);
        }
    }

    private ArrayList<DataPoint> Get_Input_Points()
    {
        ArrayList<DataPoint> points = new ArrayList<>();

        Iterator<DataPoint> itpoints = inputSeries.getValues(inputSeries.getLowestValueX(),inputSeries.getHighestValueX());

        while(itpoints.hasNext())
        {
            points.add(itpoints.next());
        }

        return points;
    }

    private ArrayList<DataPoint> Get_Output_Points()
    {
        ArrayList<DataPoint> points = new ArrayList<>();

        Iterator<DataPoint> itpoints = resultSeries.getValues(resultSeries.getLowestValueX(),resultSeries.getHighestValueX());

        while(itpoints.hasNext())
        {
            points.add(itpoints.next());
        }

        return points;
    }

    private ArrayList<DataPoint> Sort_Points(ArrayList<DataPoint> points)
    {
        // Sort the points by x-values
        Collections.sort(points, new Comparator<DataPoint>() {
            @Override
            public int compare(DataPoint p1, DataPoint p2) {
                return Double.compare(p1.getX(), p2.getX());
            }
        });

        return points;
    }




    private GraphView chart;
    private LineGraphSeries<DataPoint> inputSeries;
    private LineGraphSeries<DataPoint> resultSeries;
    private double axisDim;
    private double stdAxisDim = 5.0;
}
