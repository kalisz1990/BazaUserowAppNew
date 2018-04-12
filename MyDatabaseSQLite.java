package pl.pafc.bazauserowapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MyDatabaseSQLite extends SQLiteOpenHelper{

    public MyDatabaseSQLite(Context context, int version) {
        super (context, "userDatabase.db",null,version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table userDatabase(id integer primary key autoincrement,"
        + "pesel text,"
        + "name text,"
        + "address text,"
        + "email text);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    public void addUser (String pesel, String name, String address, String email){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("pesel", pesel);
        values.put("name", name);
        values.put("address", address);
        values.put("email", email);
        db.insertOrThrow("userDatabase", null, values);

    }
    public Cursor getUser(String pesel) {
        SQLiteDatabase db = getReadableDatabase();
        String[] columns =  {"id", "pesel", "name", "address", "email"};

        return db.query("userDatabase", columns, "pesel=?", new String[]{String.valueOf(pesel)},null,null,null,null);
    }
    public Cursor getAllUsers() {
        SQLiteDatabase db = getReadableDatabase();
        String[] columns =  {"id", "pesel", "name", "address", "email"};

        return db.query("userDatabase", columns, null, null,null,null,null,null);
    }
    public void deleteAllUsers () {
        SQLiteDatabase db = getWritableDatabase();
        db.delete("userDatabase", null,null);

    }
    public void deleteOneUser (String id) {
        SQLiteDatabase db = getWritableDatabase();
        db.delete("userDatabase", "pesel" + "=" + id,null);
    }
}
