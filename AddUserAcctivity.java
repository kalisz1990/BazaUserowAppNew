package pl.pafc.bazauserowapp;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
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

public class AddUserAcctivity extends AppCompatActivity {

    private Button           mAddButton;
    private Button           mReturnToMenuButton;
    private EditText         mPesel;
    private EditText         mName;
    private EditText         mAddress;
    private EditText         mEmail;
    private MyDatabaseSQLite myDatabaseSQLite;

    // TODO jesli pesel jest w bazie to nie dodawaj, wyrzuć komunikat

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_user_acctivity);

        myDatabaseSQLite    = new MyDatabaseSQLite(this,1);

        mPesel              = (EditText) findViewById(R.id.peselId);
        mName               = (EditText) findViewById(R.id.nameId);
        mAddress            = (EditText) findViewById(R.id.addressId);
        mEmail              = (EditText) findViewById(R.id.emailID);
        mAddButton          = (Button) findViewById(R.id.addButtonID);
        mReturnToMenuButton = (Button) findViewById(R.id.returnToMenuFromAddUserButtonID);

        mAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isPeselCorrent();
            }

        });

        mReturnToMenuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddUserAcctivity.this, MenuActivity.class);
                finish();
                startActivity(intent);
            }
        });
    }

    public void isPeselCorrent () {
        int sum;
        byte PESEL[] = new byte[11];


        if (mPesel.getText().toString().length() != 11) {
            if (mPesel.getText().toString().length() > 11) {
                Toast.makeText(AddUserAcctivity.this, "Pesel too long", Toast.LENGTH_SHORT).show();
            } else if (mPesel.getText().toString().length() < 11) {
                Toast.makeText(AddUserAcctivity.this, "Pesel too short", Toast.LENGTH_SHORT).show();
            }
        } else {
            for (int i = 0; i < 11; i++) {
                PESEL[i] = Byte.parseByte(mPesel.getText().toString().substring(i, i + 1));
            }
            sum = 1 * PESEL[0] +
                    3 * PESEL[1] +
                    7 * PESEL[2] +
                    9 * PESEL[3] +
                    1 * PESEL[4] +
                    3 * PESEL[5] +
                    7 * PESEL[6] +
                    9 * PESEL[7] +
                    1 * PESEL[8] +
                    3 * PESEL[9];
            sum %= 10;
            sum = 10 - sum;
            sum %= 10;

            if (sum != PESEL[10]) {
                Toast.makeText(AddUserAcctivity.this, "wrong Pesel, check again", Toast.LENGTH_SHORT).show();
            } else {
                myDatabaseSQLite.addUser(mPesel.getText().toString(), mName.getText().toString(), mAddress.getText().toString(), mEmail.getText().toString());
                displayNewUser();
            }
        }
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
                        finish();
                        startActivity(intent);
                        Toast.makeText(AddUserAcctivity.this, "return to menu", Toast.LENGTH_SHORT).show();
                    }
                })
                .show();
    }

}



