package com.example.unitconverter;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.unitconverter.R;

public class MainActivity extends AppCompatActivity {
    private Spinner sourceUnitSpinner;
    private Spinner destUnitSpinner;
    private EditText valueEditText;
    private TextView resultTextView;

    private final String[] lengthUnits = {"In", "Ft", "Yd", "Mi", "Cm", "Km"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sourceUnitSpinner = findViewById(R.id.source_unit_spinner);
        destUnitSpinner = findViewById(R.id.destination_unit_spinner);
        valueEditText = findViewById(R.id.input_value_edittext);
        Button convertButton = findViewById(R.id.convert_button);
        resultTextView = findViewById(R.id.result_textview);

        ArrayAdapter<String> sourceUnitAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, lengthUnits);
        sourceUnitSpinner.setAdapter(sourceUnitAdapter);

        ArrayAdapter<String> destUnitAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, lengthUnits);
        destUnitSpinner.setAdapter(destUnitAdapter);

        convertButton.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("DefaultLocale")
            @Override
            public void onClick(View v) {
                // Get selected units and input value
                String sourceUnit = sourceUnitSpinner.getSelectedItem().toString();
                String destUnit = destUnitSpinner.getSelectedItem().toString();
                String inputValue = valueEditText.getText().toString();

                // Validate input value
                if (TextUtils.isEmpty(inputValue)) {
                    Toast.makeText(getApplicationContext(), "Please enter a value", Toast.LENGTH_SHORT).show();
                    return;
                }

                double value = Double.parseDouble(inputValue);

                // Handle same unit conversion
                if (sourceUnit.equals(destUnit)) {
                    resultTextView.setText(String.format("%.2f %s = %.2f %s", value, sourceUnit, value, destUnit));
                    return;
                }

                // Convert value
                double result = convert(sourceUnit, destUnit, value);

                // Update result text view
                resultTextView.setText(String.format("%.2f %s = %.2f %s", value, sourceUnit, result, destUnit));
            }
        });
    }

    private double convert(String sourceUnit, String destUnit, double value) {
        double sourceValueInCm = 0;

        switch (sourceUnit) {
            case "In":
                sourceValueInCm = value * 2.54;
                break;
            case "Ft":
                sourceValueInCm = value * 30.48;
                break;
            case "Yd":
                sourceValueInCm = value * 91.44;
                break;
            case "Mi":
                sourceValueInCm = value * 160934.4;
                break;
            case "Cm":
                sourceValueInCm = value;
                break;
            case "Km":
                sourceValueInCm = value * 100000;
        }

        double result = 0;

        switch (destUnit) {
            case "In":
                result = sourceValueInCm / 2.54;
                break;
            case "Ft":
                result = sourceValueInCm / 30.48;
                break;
            case "Yd":
                result = sourceValueInCm / 91.44;
                break;
            case "Mi":
                result = sourceValueInCm / 160934.4;
                break;
            case "Cm":
                result = sourceValueInCm;
                break;
            case "Km":
                result = sourceValueInCm/100000;
                break;
        }

        return result;
    }
}
