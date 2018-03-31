package com.example.neyro.test2;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

public class ColorPicker extends AppCompatActivity {

    int funcColor;
    int RPar = 0, GPar = 0, BPar = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_color_picker);

        Intent intent = getIntent();
        funcColor = intent.getIntExtra("Color", 0);
        RPar = Color.red(funcColor);
        GPar = Color.green(funcColor);
        BPar = Color.blue(funcColor);
        ((SeekBar) findViewById(R.id.RParam)).setProgress(RPar);
        ((SeekBar) findViewById(R.id.GParam)).setProgress(GPar);
        ((SeekBar) findViewById(R.id.BParam)).setProgress(BPar);
        ((SeekBar) findViewById(R.id.RParam)).setBackgroundColor(Color.RED);
        ((SeekBar) findViewById(R.id.GParam)).setBackgroundColor(Color.GREEN);
        ((SeekBar) findViewById(R.id.BParam)).setBackgroundColor(Color.BLUE);
        ((SeekBar) findViewById(R.id.RParam)).setAlpha(0.5f);
        ((SeekBar) findViewById(R.id.GParam)).setAlpha(0.5f);
        ((SeekBar) findViewById(R.id.BParam)).setAlpha(0.5f);
        ((TextView)findViewById(R.id.RValue)).setText(String.valueOf(RPar));
        ((TextView)findViewById(R.id.GValue)).setText(String.valueOf(GPar));
        ((TextView)findViewById(R.id.BValue)).setText(String.valueOf(BPar));
        ((TextView)findViewById(R.id.ColorResult)).setBackgroundColor(Color.argb(255, RPar, GPar, BPar));
        ((Button) findViewById(R.id.ColorChoosed)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setResult(Color.argb(255, RPar, GPar, BPar));
                finish();
            }
        });
        ((Button) findViewById(R.id.ColorChoosed)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setResult(Integer.MIN_VALUE);
                finish();
            }
        });

        SeekBar.OnSeekBarChangeListener listener = new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                int id = seekBar.getId();
                try {
                    switch (id) {
                        case R.id.RParam: {
                            RPar = seekBar.getProgress();
                            ((TextView)findViewById(R.id.RValue)).setText(String.valueOf(RPar));
                            break;
                        }
                        case R.id.GParam: {
                            GPar = seekBar.getProgress();
                            ((TextView)findViewById(R.id.GValue)).setText(String.valueOf(GPar));
                            break;
                        }
                        case R.id.BParam: {
                            BPar = seekBar.getProgress();
                            ((TextView)findViewById(R.id.BValue)).setText(String.valueOf(BPar));
                            break;
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                ((TextView)findViewById(R.id.ColorResult)).setBackgroundColor(Color.argb(255, RPar, GPar, BPar));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        };
        ((SeekBar) findViewById(R.id.RParam)).setOnSeekBarChangeListener(listener);
        ((SeekBar) findViewById(R.id.GParam)).setOnSeekBarChangeListener(listener);
        ((SeekBar) findViewById(R.id.BParam)).setOnSeekBarChangeListener(listener);
    }
}
