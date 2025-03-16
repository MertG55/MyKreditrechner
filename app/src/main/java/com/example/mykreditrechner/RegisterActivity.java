package com.example.mykreditrechner;

import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class RegisterActivity extends AppCompatActivity {

    private EditText etFirstName, etLastName, etUsername, etPassword, etPasswordRepeat;
    private Button btnRegister, btnBackToLogin;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Views initialisieren
        etFirstName = findViewById(R.id.etFirstName);
        etLastName = findViewById(R.id.etLastName);
        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        etPasswordRepeat = findViewById(R.id.etPasswordRepeat);
        btnRegister = findViewById(R.id.btnRegister);
        btnBackToLogin = findViewById(R.id.btnBackToLogin);

        dbHelper = new DatabaseHelper(this);

        // Registrieren-Button Logik
        btnRegister.setOnClickListener(v -> {
            String firstName = etFirstName.getText().toString().trim();
            String lastName = etLastName.getText().toString().trim();
            String username = etUsername.getText().toString().trim();
            String password = etPassword.getText().toString().trim();
            String passwordRepeat = etPasswordRepeat.getText().toString().trim();

            // Validierung
            if (firstName.isEmpty() || lastName.isEmpty() || username.isEmpty() || password.isEmpty() || passwordRepeat.isEmpty()) {
                Toast.makeText(RegisterActivity.this, "Bitte alle Felder ausfüllen!", Toast.LENGTH_SHORT).show();
                return;
            }

            if (!password.equals(passwordRepeat)) {
                Toast.makeText(RegisterActivity.this, "Passwörter stimmen nicht überein!", Toast.LENGTH_SHORT).show();
                return;
            }

            // Benutzer in der Datenbank speichern
            ContentValues values = new ContentValues();
            values.put("username", username);
            values.put("password", password);

            long result = dbHelper.getWritableDatabase().insert("users", null, values);

            if (result != -1) {
                Toast.makeText(RegisterActivity.this, "Registrierung erfolgreich!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(RegisterActivity.this, "Fehler bei der Registrierung!", Toast.LENGTH_SHORT).show();
            }
        });

        // Zurück-Button Logik
        btnBackToLogin.setOnClickListener(v -> {
            Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
            startActivity(intent);
            finish(); // Beendet die aktuelle Aktivität
        });
    }
}
