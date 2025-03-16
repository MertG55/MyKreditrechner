package com.example.mykreditrechner;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "kreditrechner.db";
    private static final int DATABASE_VERSION = 2;

    // Tabellenname und Spaltennamen für Benutzer
    private static final String TABLE_USERS = "users";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_USERNAME = "username";
    private static final String COLUMN_PASSWORD = "password";

    // Tabellenname und Spaltennamen für Kredite
    private static final String TABLE_CREDITS = "credits";
    private static final String COLUMN_CREDIT_ID = "credit_id";
    private static final String COLUMN_USER = "user";
    private static final String COLUMN_CREDIT_DETAILS = "credit_details";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Tabelle für Benutzer erstellen
        String createUsersTable = "CREATE TABLE " + TABLE_USERS + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_USERNAME + " TEXT UNIQUE, " +
                COLUMN_PASSWORD + " TEXT)";
        db.execSQL(createUsersTable);

        // Tabelle für Kredite erstellen
        String createCreditsTable = "CREATE TABLE " + TABLE_CREDITS + " (" +
                COLUMN_CREDIT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_USER + " TEXT, " +
                COLUMN_CREDIT_DETAILS + " TEXT)";
        db.execSQL(createCreditsTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CREDITS);
        onCreate(db);
    }

    // Methode zum Überprüfen von Benutzername und Passwort
    public boolean checkUser(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;

        try {
            String query = "SELECT * FROM " + TABLE_USERS + " WHERE " +
                    COLUMN_USERNAME + " = ? AND " + COLUMN_PASSWORD + " = ?";
            cursor = db.rawQuery(query, new String[]{username, password});

            if (cursor != null && cursor.moveToFirst()) {
                return true; // Benutzer gefunden
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            db.close();
        }

        return false; // Benutzer nicht gefunden
    }

    // Methode zum Speichern eines Kredits
    public void saveCredit(String username, String creditDetails) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_USER, username);
        values.put(COLUMN_CREDIT_DETAILS, creditDetails);
        db.insert(TABLE_CREDITS, null, values);
        db.close();
    }

    // Methode zum Abrufen der gespeicherten Kredite für einen Benutzer
    public ArrayList<String> getCredits(String username) {
        ArrayList<String> credits = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT " + COLUMN_CREDIT_DETAILS +
                " FROM " + TABLE_CREDITS + " WHERE " + COLUMN_USER + " = ?", new String[]{username});

        if (cursor != null) {
            while (cursor.moveToNext()) {
                credits.add(cursor.getString(0));
            }
            cursor.close();
        }
        db.close();
        return credits;
    }

    // Methode zum Löschen eines Kredits
    public void deleteCredit(String creditDetails) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_CREDITS, COLUMN_CREDIT_DETAILS + " = ?", new String[]{creditDetails});
        db.close();
    }
}
