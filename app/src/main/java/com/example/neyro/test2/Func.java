package com.example.neyro.test2;

import android.graphics.Color;
import android.graphics.Point;

import com.jjoe64.graphview.series.DataPoint;

import java.util.Random;

/**
 * Created by Neyro on 24.03.2018.
 */

public class Func {

    public int ID=0;
    public String Function;
    public int DrawColor=Integer.MIN_VALUE;
    public double From=-20;
    public double To=20;
    public double Step=1;
    public boolean IsDraw = true;
    public DataPoint[] Points;

    public Func(int _id, String _func)
    {
        ID = _id;
        Function = _func;
    }

    public Func()
    {
    }

    public int getRandomColor(){
        Random rnd = new Random();
        DrawColor = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
        return DrawColor;
    }

    public DataPoint[] Calculate()
    {
        int count = (int)((Math.abs(From)+Math.abs(To))/Step);
        DataPoint[] _points = new DataPoint[count];
        try {
            int j=0;
            for(double i=From; i<To;i+=Step,j++)
            {
                _points[j]= new DataPoint(i, Math_parser.Evaluate(Function,i));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        Points = _points;
        return _points;
    }
}
