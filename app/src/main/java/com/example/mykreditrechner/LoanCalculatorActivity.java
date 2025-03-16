package com.example.mykreditrechner;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class LoanCalculatorActivity extends AppCompatActivity {

    private DatabaseHelper dbHelper;
    private String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loan_calculator);

        dbHelper = new DatabaseHelper(this);
        username = getIntent().getStringExtra("username");

        EditText loanAmountInput = findViewById(R.id.loanAmountInput);
        EditText loanTermInput = findViewById(R.id.loanTermInput);
        EditText interestRateInput = findViewById(R.id.interestRateInput);
        EditText loanPurposeInput = findViewById(R.id.loanPurposeInput);
        Button calculateButton = findViewById(R.id.calculateButton);
        TextView resultTextView = findViewById(R.id.resultTextView);
        Button backButton = findViewById(R.id.backButton); // Neuer Zurück-Button

        calculateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String loanAmountStr = loanAmountInput.getText().toString();
                String loanTermStr = loanTermInput.getText().toString();
                String interestRateStr = interestRateInput.getText().toString();
                String loanPurposeStr = loanPurposeInput.getText().toString();

                if (loanAmountStr.isEmpty() || loanTermStr.isEmpty() || interestRateStr.isEmpty() || loanPurposeStr.isEmpty()) {
                    Toast.makeText(LoanCalculatorActivity.this, "Bitte alle Felder ausfüllen.", Toast.LENGTH_SHORT).show();
                    return;
                }

                double loanAmount = Double.parseDouble(loanAmountStr);
                int loanTerm = Integer.parseInt(loanTermStr);
                double interestRate = Double.parseDouble(interestRateStr) / 100;

                double totalInterest = loanAmount * interestRate * loanTerm;
                double totalAmount = loanAmount + totalInterest;
                double monthlyRate = totalAmount / (loanTerm * 12);

                String calculationResult = "Betreff: " + loanPurposeStr +
                        "\nKreditsumme: " + loanAmount +
                        " EUR\nLaufzeit: " + loanTerm + " Jahre" +
                        "\nZins: " + interestRate * 100 + "%" +
                        "\nMonatliche Rate: " + String.format("%.2f", monthlyRate) +
                        " EUR\nGesamtkosten: " + String.format("%.2f", totalAmount) + " EUR";

                dbHelper.saveCredit(username, calculationResult);

                resultTextView.setText("Monatliche Rate: " + String.format("%.2f", monthlyRate) + " EUR\n" +
                        "Gesamtkosten: " + String.format("%.2f", totalAmount) + " EUR");

                Toast.makeText(LoanCalculatorActivity.this, "Berechnung gespeichert.", Toast.LENGTH_SHORT).show();
            }
        });

        // Zurück-Button-Listener
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoanCalculatorActivity.this, MainActivity.class);
                intent.putExtra("username", username); // Benutzername weitergeben
                startActivity(intent);
                finish(); // Schließt die aktuelle Aktivität
            }
        });
    }
}
