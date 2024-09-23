package com.example.currencyconverter;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    private EditText amountEditText;
    private Spinner currencyFromSpinner, currencyToSpinner;
    private Button convertButton;
    private TextView resultTextView;

    private HashMap<String, Double> currencyRates;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        amountEditText = findViewById(R.id.amountEditText);
        currencyFromSpinner = findViewById(R.id.currencyFromSpinner);
        currencyToSpinner = findViewById(R.id.currencyToSpinner);
        convertButton = findViewById(R.id.convertButton);
        resultTextView = findViewById(R.id.resultTextView);

        setupCurrencyRates();
        setupSpinners();

        convertButton.setOnClickListener(v -> convertCurrency());
    }

    private void setupCurrencyRates() {
        currencyRates = new HashMap<>();
        currencyRates.put("USD", 1.0);
        currencyRates.put("PKR", 280.0);
    }

    private void setupSpinners() {
        String[] currencies = currencyRates.keySet().toArray(new String[0]);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, currencies);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        currencyFromSpinner.setAdapter(adapter);
        currencyToSpinner.setAdapter(adapter);
    }

    private void convertCurrency() {
        String fromCurrency = currencyFromSpinner.getSelectedItem().toString();
        String toCurrency = currencyToSpinner.getSelectedItem().toString();

        double amount = Double.parseDouble(amountEditText.getText().toString());
        double fromRate = currencyRates.get(fromCurrency);
        double toRate = currencyRates.get(toCurrency);

        double convertedAmount = (amount / fromRate) * toRate;
        resultTextView.setText(String.format("%.2f %s", convertedAmount, toCurrency));
    }
}
