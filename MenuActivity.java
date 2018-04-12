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
import android.widget.LinearLayout;
import android.widget.Toast;

import java.io.Serializable;

public class MenuActivity extends Activity {

    private Button mAddUserFromMenu;
    private Button mFindUser;
    private Button mDeleteUser;
    private Button mExit;
    private MyDatabaseSQLite myDatabaseSQLite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        myDatabaseSQLite = new MyDatabaseSQLite(this,1);

        mAddUserFromMenu = (Button) findViewById(R.id.addUserID);
        mAddUserFromMenu.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent addUserButton = new Intent(MenuActivity.this, AddUserAcctivity.class);
                startActivity(addUserButton);
                finish();

            }
        });

        mFindUser = (Button) findViewById(R.id.findUserID);
        mFindUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createDialogFindUser("Find User", "What user do you want to find?");
            }
        });

        mDeleteUser = (Button) findViewById(R.id.deleteUserID);
        mDeleteUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createDialogDeleteUser("Delete user", "what user do you want to delete");

            }
        });

        mExit = (Button) findViewById(R.id.exitButtonID);
        mExit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                finish();
                System.exit(0);
            }
        });

    }

    public void createDialogDeleteUser (String title, String message) {
        final EditText input = new EditText(MenuActivity.this);

        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        input.setLayoutParams(lp);

        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle(title)
                .setMessage(message)
                .setCancelable(false)
                .setIcon(R.mipmap.ic_launcher)
                .setPositiveButton("delete all database", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        myDatabaseSQLite.deleteAllUsers();
                        Toast.makeText(MenuActivity.this, "Database Deleted", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        myDatabaseSQLite.deleteOneUser(input.getText().toString());
                        Toast.makeText(MenuActivity.this, "User Deleted", Toast.LENGTH_SHORT).show();
                    }
                })
                .setView(input)
                .show();
    }
    public void createDialogFindUser (String title, String message) {

        final Intent intent = new Intent(MenuActivity.this, DisplayUserAcctivity.class);
        final EditText input = new EditText(MenuActivity.this);

        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        input.setLayoutParams(lp);

        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle(title)
                .setMessage(message)
                .setCancelable(false)
                .setIcon(R.mipmap.ic_launcher)
                .setNeutralButton("Search", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        intent.putExtra("View", viewOne(input.getText().toString()));
                        startActivity(intent);
                    }
                })
                .setPositiveButton("return", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(MenuActivity.this, "wracam do menu", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("View all Users", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        intent.putExtra("View", viewAll());
                        startActivity(intent);
                    }
                })
                .setView(input)
                .show();
    }
    public String viewOne(String input) {
        StringBuilder builder = new StringBuilder();
        Cursor cursor = myDatabaseSQLite.getUser(input);

        while (cursor.moveToNext()) {
            builder.append("ID: " + cursor.getInt(0));
            builder.append("\nPesel: " + cursor.getString(1));
            builder.append("\nName: " + cursor.getString(2));
            builder.append("\nAddress: " + cursor.getString(3));
            builder.append("\nEmail: " + cursor.getString(4));
            builder.append("\n-------------------\n");
        }
        return builder.toString();
    }
    public String viewAll() {
        StringBuilder builder = new StringBuilder();
        Cursor cursor = myDatabaseSQLite.getAllUsers();

        while (cursor.moveToNext()) {
            builder.append("ID: " + cursor.getInt(0));
            builder.append("\nPesel: " + cursor.getString(1));
            builder.append("\nName: " + cursor.getString(2));
            builder.append("\nAddress: " + cursor.getString(3));
            builder.append("\nEmail: " + cursor.getString(4));
            builder.append("\n-------------------\n");
        }
        return builder.toString();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }
}
