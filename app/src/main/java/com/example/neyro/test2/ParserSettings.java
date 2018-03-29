package com.example.neyro.test2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class ParserSettings extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parser_settings);

        Spinner spinner = (Spinner) findViewById(R.id.Accuracy);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.Accuracy, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        AdapterView.OnItemSelectedListener spinerListener = new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Math_parser.Accuracy = Integer.valueOf(getResources().getStringArray(R.array.Accuracy)[i]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        };
        spinner.setOnItemSelectedListener(spinerListener);

        Spinner type = (Spinner) findViewById(R.id.Type);
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this,
                R.array.Type, android.R.layout.simple_spinner_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        type.setAdapter(adapter2);

        AdapterView.OnItemSelectedListener spinerListener2 = new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Math_parser.Accuracy = Integer.valueOf(getResources().getStringArray(R.array.Accuracy)[i]);
                if (i==0)
                    Math_parser.Degrees_or_radian=1;
                else Math_parser.Degrees_or_radian=0;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        };
        type.setOnItemSelectedListener(spinerListener2);
    }
}
