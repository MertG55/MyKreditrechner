package com.example.mykreditrechner;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private DatabaseHelper dbHelper;
    private String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new DatabaseHelper(this);
        username = getIntent().getStringExtra("username"); // Benutzername aus dem Intent abrufen

        Button toLoanCalculatorButton = findViewById(R.id.toLoanCalculatorButton);
        Button showPreviousButton = findViewById(R.id.showPreviousButton);
        Button backToLoginButton = findViewById(R.id.backToLoginButton); // Neuer Zurück-Button

        // Weiterleitung zum Kreditrechner
        toLoanCalculatorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, LoanCalculatorActivity.class);
                intent.putExtra("username", username); // Benutzername weitergeben
                startActivity(intent);
            }
        });

        // Anzeige der gespeicherten Kreditberechnungen
        showPreviousButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<String> creditCalculations = dbHelper.getCredits(username);

                Intent intent = new Intent(MainActivity.this, PreviousCreditsActivity.class);
                intent.putStringArrayListExtra("creditCalculations", creditCalculations);
                startActivity(intent);
            }
        });

        // Zurück zum Login-Screen
        backToLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
                finish(); // Schließt die MainActivity
            }
        });
    }
}
