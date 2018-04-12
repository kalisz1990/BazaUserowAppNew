package pl.pafc.bazauserowapp;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class AddUserAcctivity extends Activity {

    private Button           mAddButton;
    private Button           mReturnToMenuButton;
    private EditText         mPesel;
    private EditText         mName;
    private EditText         mAddress;
    private EditText         mEmail;
    private MyDatabaseSQLite myDatabaseSQLite;

    //TODO poprawic wyglad activity, aby nie bylo tyle linear layoutów, dodac scroll View

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_user_acctivity);

        myDatabaseSQLite = new MyDatabaseSQLite(this,1);

        mPesel              = (EditText) findViewById(R.id.peselId);
        mName               = (EditText) findViewById(R.id.nameId);
        mAddress            = (EditText) findViewById(R.id.addressId);
        mEmail              = (EditText) findViewById(R.id.emailID);
        mAddButton          = (Button) findViewById(R.id.addButtonID);
        mReturnToMenuButton = (Button) findViewById(R.id.returnToMenuFromAddUserButtonID);

        mAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myDatabaseSQLite.addUser(mPesel.getText().toString(), mName.getText().toString(), mAddress.getText().toString(), mEmail.getText().toString());
                displayNewUser();
            }
        });

        mReturnToMenuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddUserAcctivity.this, MenuActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    public void displayNewUser (){

        StringBuilder builder = new StringBuilder();
        Cursor cursor = myDatabaseSQLite.getUser(mPesel.getText().toString());

        while (cursor.moveToNext()) {
            builder.append("Pesel: " + cursor.getString(1));
            builder.append("\nName: " + cursor.getString(2));
            builder.append("\nAddress: " + cursor.getString(3));
            builder.append("\nEmail: " + cursor.getString(4));
            builder.append("\n-------------------\n");
        }

        createDialogNewUser("New User", builder.toString());

    }
    public void createDialogNewUser (String title, String message) {
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle(title)
                .setMessage(message)
                .setCancelable(false)
                .setIcon(R.mipmap.ic_launcher)
                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setNegativeButton("exit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(AddUserAcctivity.this, MenuActivity.class);
                        startActivity(intent);
                        finish();
                        Toast.makeText(AddUserAcctivity.this, "wracam do menu", Toast.LENGTH_SHORT).show();
                    }
                })
                .show();
    }

}



