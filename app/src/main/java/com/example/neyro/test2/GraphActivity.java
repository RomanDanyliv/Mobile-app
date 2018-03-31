package com.example.neyro.test2;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import eltos.simpledialogfragment.color.SimpleColorDialog;

public class GraphActivity extends AppCompatActivity {

    int id;
    int Color;
    SimpleColorDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.graph_setting_activity);
        Intent intent = getIntent();
        id = intent.getIntExtra("id", Integer.MIN_VALUE);
        Func func = FuncManager.AllFunctions.get(id);
        Color = func.DrawColor;
        ((EditText) findViewById(R.id.FuncName)).setText(func.Function);
        ((Button) findViewById(R.id.Color)).setBackgroundColor(Color);
        ((Button) findViewById(R.id.Color)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent("Color.Picker");
                intent.putExtra("Color",Color);
                startActivityForResult(intent, 0);
            }
        });
        ((EditText) findViewById(R.id.From)).setText(String.valueOf(func.From));
        ((EditText) findViewById(R.id.To)).setText(String.valueOf(func.To));
        ((EditText) findViewById(R.id.Step)).setText(String.valueOf(func.Step));
        ((Button) findViewById(R.id.Save)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Func func = FuncManager.FindById(id);
                func.Function = ((EditText) findViewById(R.id.FuncName)).getText().toString();
                func.DrawColor = Integer.valueOf(Color);
                func.From = Double.valueOf(((EditText) findViewById(R.id.From)).getText().toString());
                func.To = Double.valueOf(((EditText) findViewById(R.id.To)).getText().toString());
                func.Step = Double.valueOf(((EditText) findViewById(R.id.Step)).getText().toString());
                func.Calculate();
                setResult(1);
                finish();
            }
        });
        ((Button) findViewById(R.id.Clone)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Func func = new Func();
                func.ID = FuncManager.GetLasst().ID + 1;
                func.Function = ((EditText) findViewById(R.id.FuncName)).getText().toString();
                func.DrawColor = Integer.valueOf(Color);
                func.From = Double.valueOf(((EditText) findViewById(R.id.From)).getText().toString());
                func.To = Double.valueOf(((EditText) findViewById(R.id.To)).getText().toString());
                func.Step = Double.valueOf(((EditText) findViewById(R.id.Step)).getText().toString());
                func.Calculate();
                FuncManager.AllFunctions.add(func);
                setResult(1);
                finish();
            }
        });
        ((Button) findViewById(R.id.Remove)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FuncManager.AllFunctions.remove(FuncManager.FindById(id));
                setResult(1);
                finish();
            }
        });
        ((Button) findViewById(R.id.Close)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setResult(0);
                finish();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode > Integer.MIN_VALUE)
            Color = resultCode;
        ((Button) findViewById(R.id.Color)).setBackgroundColor(Color);
    }
}
