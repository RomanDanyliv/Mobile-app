package com.example.neyro.test2;

import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class MainActivity extends AppCompatActivity {

    public int Counter = 0;
    public ArrayList<Func> AllFunctions = new ArrayList<Func>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        Button btn = (Button) findViewById(R.id.Button);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ///get chart
                GraphView graph = (GraphView) findViewById(R.id.graph);
                //setttings
                Math_parser.Degrees_or_radian = 1;
                //get func
                EditText input = (EditText) findViewById(R.id.Input);
                int CurrentCounter = Counter++;
                Func function = new Func(CurrentCounter,input.getText().toString());
                AllFunctions.add(function);
                LineGraphSeries<DataPoint> series = new LineGraphSeries<>(function.Calculate());
                //create graph series
                series.setColor(function.getRandomColor());
                series.setAnimated(true);
                graph.addSeries(series);
                graph.getViewport().setScalable(true);
                graph.getViewport().setScrollable(true);
                //nav
                NavigationView side = (NavigationView) findViewById(R.id.sideMenu);
                //menu from nav
                Menu sideMenu = side.getMenu();
                MenuItem mi = side.getMenu().add(Menu.NONE, Menu.NONE, Menu.NONE, "");
                //crete linear
                LinearLayout ll = new LinearLayout(MainActivity.this);
                ll.setId(CurrentCounter);
                CheckBox ch = new CheckBox(MainActivity.this);
                ch.setId(CurrentCounter);
                ch.setChecked(true);
                TextView func = new TextView(MainActivity.this);
                func.setText(input.getText().toString());
                Button b1 = new Button(MainActivity.this);
                b1.setId(CurrentCounter);
                //set item to linear
                ll.addView(ch);
                ll.addView(func);
                ll.addView(b1);
                //add linear to nav
                mi.setActionView(ll);

                View.OnClickListener clicker = new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        TextView ed = (TextView) findViewById(R.id.Result);
                        ed.setText(String.valueOf(view.getId()));
                    }
                };
                b1.setOnClickListener(clicker);

                CompoundButton.OnCheckedChangeListener checked = new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                        AllFunctions.get(compoundButton.getId()).IsDraw = b;
                        ReDrawGraph();
                    }
                };
                ch.setOnCheckedChangeListener(checked);
            }
        });

    }

    private void ReDrawGraph() {
        GraphView graph = (GraphView) findViewById(R.id.graph);
        graph.removeAllSeries();
        for (Func item:AllFunctions)
        {
            if (item.IsDraw) {
                LineGraphSeries<DataPoint> series = new LineGraphSeries<>(item.Points);
                series.setColor(item.DrawColor);
                graph.addSeries(series);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
