package com.example.mykreditrechner;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    private EditText editTextUsername, editTextPassword;
    private Button btnLogin, btnRegister;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Initialisiere Views
        editTextUsername = findViewById(R.id.editTextUsername);
        editTextPassword = findViewById(R.id.editTextPassword);
        btnLogin = findViewById(R.id.btnLogin);
        btnRegister = findViewById(R.id.btnRegister);

        // Initialisiere die Datenbank-Hilfe-Klasse
        dbHelper = new DatabaseHelper(this);

        // Login-Button
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = editTextUsername.getText().toString().trim();
                String password = editTextPassword.getText().toString().trim();

                if (username.isEmpty() || password.isEmpty()) {
                    Toast.makeText(LoginActivity.this, "Bitte füllen Sie alle Felder aus!", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Überprüfe Benutzer in der Datenbank
                boolean isUserValid = dbHelper.checkUser(username, password);

                if (isUserValid) {
                    // Wechsel zur MainActivity
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    intent.putExtra("username", username); // Benutzername an MainActivity übergeben
                    startActivity(intent);
                    finish(); // LoginActivity beenden
                } else {
                    Toast.makeText(LoginActivity.this, "Ungültige Anmeldedaten!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Register-Button
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
    }
}

