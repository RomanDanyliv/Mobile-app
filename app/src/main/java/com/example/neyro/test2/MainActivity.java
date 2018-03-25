package com.example.neyro.test2;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TextView;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class MainActivity extends AppCompatActivity {

    public int Counter = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        FuncManager ff = new FuncManager();
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
                int CurrentCounter = Counter++;
                //get func
                EditText input = (EditText) findViewById(R.id.Input);
                Func function = new Func(CurrentCounter, input.getText().toString());
                FuncManager.AllFunctions.add(function);
                function.Calculate();
                ReDrawGraph();
                graph.getViewport().setScalable(true);
                graph.getViewport().setScrollable(true);
            }
        });

    }

    public void Menu_Refresh()
    {
        for(Func item:FuncManager.AllFunctions)
        {
            //nav
            NavigationView side = (NavigationView) findViewById(R.id.sideMenu);
            //menu from nav
            Menu sideMenu = side.getMenu();
            sideMenu.clear();
            MenuItem mi = side.getMenu().add(Menu.NONE, Menu.NONE, Menu.NONE, item.Function.toUpperCase());
            //crete linear
            mi.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem menuItem) {
                    int id = menuItem.getActionView().getId();
                    ((TextView) findViewById(R.id.Result)).setText(String.valueOf(id));
                    Intent intent = new Intent("Graph.Setting");
                    intent.putExtra("id", id);
                    startActivityForResult(intent,1);
                    return true;
                }
            });
            LinearLayout ll = new LinearLayout(MainActivity.this);
            ll.setGravity(Gravity.CENTER);
            ll.setId(item.ID);
            ll.setOrientation(LinearLayout.HORIZONTAL);
            CheckBox ch = new CheckBox(MainActivity.this);
            ch.setId(item.ID);
            ch.setChecked(true);
            //set item to linear
            ll.addView(ch);
            //add linear to nav
            mi.setActionView(ll);
            CompoundButton.OnCheckedChangeListener checked = new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    FuncManager.AllFunctions.get(compoundButton.getId()).IsDraw = b;
                    ReDrawGraph();
                }
            };
            ch.setOnCheckedChangeListener(checked);
        }
    }

    private void ReDrawGraph() {
        GraphView graph = (GraphView) findViewById(R.id.graph);
        graph.removeAllSeries();
        for (Func item : FuncManager.AllFunctions) {
            if (item.IsDraw) {
                LineGraphSeries<DataPoint> series = new LineGraphSeries<>(item.Points);
                if (item.DrawColor!=Integer.MIN_VALUE)
                series.setColor(item.DrawColor);
                else
                series.setColor(item.getRandomColor());
                series.setAnimated(true);
                graph.addSeries(series);
            }
        }
        Menu_Refresh();
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode>0)
        ReDrawGraph();
    }
}
