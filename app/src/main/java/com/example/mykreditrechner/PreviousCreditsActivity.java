package com.example.mykreditrechner;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class PreviousCreditsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_previous_credits);

        // RecyclerView initialisieren
        RecyclerView recyclerViewCredits = findViewById(R.id.recyclerViewCredits);
        recyclerViewCredits.setLayoutManager(new LinearLayoutManager(this));

        // Daten aus dem Intent abrufen
        ArrayList<String> creditCalculations = getIntent().getStringArrayListExtra("creditCalculations");

        // Adapter setzen
        CreditAdapter adapter = new CreditAdapter(this, creditCalculations);
        recyclerViewCredits.setAdapter(adapter);

        // Zurück-Button initialisieren
        Button backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Zurück zur MainActivity navigieren
                Intent intent = new Intent(PreviousCreditsActivity.this, MainActivity.class);
                startActivity(intent);
                finish(); // Schließt die aktuelle Aktivität
            }
        });
    }
}
