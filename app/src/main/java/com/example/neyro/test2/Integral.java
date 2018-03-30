package com.example.neyro.test2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

public class Integral extends AppCompatActivity {

    public int SelectedIntType = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_integral);

        Spinner spinner = (Spinner) findViewById(R.id.IntType);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.IntType, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        AdapterView.OnItemSelectedListener spinerListener = new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                SelectedIntType = i;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        };
        spinner.setOnItemSelectedListener(spinerListener);

        ((ImageButton) findViewById(R.id.IntCalculate)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String func = ((EditText) findViewById(R.id.IntInput)).getText().toString();
                double From = Double.valueOf(((EditText) findViewById(R.id.IntFrom)).getText().toString());
                double To = Double.valueOf(((EditText) findViewById(R.id.IntTo)).getText().toString());
                int Points = Integer.valueOf(((EditText) findViewById(R.id.IntPoints)).getText().toString());
                TextView res = (TextView) findViewById(R.id.IntResult);
                switch (SelectedIntType) {
                    case 0: {
                        res.setText("За методом Сімпсона результат \nстановить " +
                                SimpsonsMethod(func, Points, From, To));
                        break;
                    }
                    case 1: {
                        res.setText("За методом квадратів результат \nстановить " +
                                RectanglesMethod(func, Points, From, To));
                        break;
                    }
                    case 2: {
                        res.setText("За методом трапецій результат \nстановить " +
                                TrapeziumMethod(func, Points, From, To));
                        break;
                    }
                }
                GraphView graph = (GraphView) findViewById(R.id.IntGraph);
                graph.removeAllSeries();
                int count = (int)((Math.abs(From)+Math.abs(To))/1);
                DataPoint[] points = new DataPoint[count];
                    int j=0;
                    for(double i=From; i<To;i+=1,j++)
                    {
                        try {
                        points[j]= new DataPoint(i, Math_parser.Evaluate(func,i));
                        } catch (Exception e) {
                            points[j]=new DataPoint(i, 0);
                        }
                    }
                LineGraphSeries<DataPoint> series = new LineGraphSeries<>(points);
                series.setAnimated(true);
                graph.addSeries(series);
            }
        });
    }

    private String RectanglesMethod(String func, int points, double from, double to) {
        try {
            double n = points;
            double h = (to - from) / n;
            double f, s = 0;
            for (double x1 = 0, x = from; x <= to; x += h) {
                if (x < to) {
                    x1 = x + h / 2;
                    if (x1 >= 2)
                        continue;
                    f = Math_parser.Evaluate(func, x1);
                    s += f;
                }
            }
            return String.valueOf(s * h);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "0.0";
    }

    private String TrapeziumMethod(String func, int points, double from, double to) {
        try {
            double sum = 0.0;
            double h = (to - from) / points;
            for (int i = 0; i < points; i++)
                sum += 0.5 * h * (Math_parser.Evaluate(func, from + i * h)
                        + Math_parser.Evaluate(func, from + (i + 1) * h));
            return String.valueOf(sum);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "0.0";
    }

    private String SimpsonsMethod(String func, int points, double from, double to) {
        double h = 0;
        double s = 0;
        try {
            h = (to - from) / points;
            s = (Math_parser.Evaluate(func, from) + Math_parser.Evaluate(func, to)) * 0.5;
            for (int i = 1; i <= points - 1; i++) {
                double xk = from + h * i; //xk
                double xk1 = from + h * (i - 1); //Xk-1
                s += Math_parser.Evaluate(func, xk) + 2 * Math_parser.Evaluate(func, (xk1 + xk) / 2);
            }
            double x = from + h * to; //xk
            double x1 = from + h * (to - 1); //Xk-1
            s += 2 * Math_parser.Evaluate(func, (x1 + x) / 2);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return String.valueOf(s * h / 3.0);
    }
}
