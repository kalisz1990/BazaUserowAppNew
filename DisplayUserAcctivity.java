package pl.pafc.bazauserowapp;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

public class DisplayUserAcctivity extends Activity {

    private TextView mDispayUser;
    private Button   mRetunToMenuFromDisplayActivity;
    private String   viewUser;
    MyDatabaseSQLite myDatabaseSQLite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_user_acctivity);

        myDatabaseSQLite = new MyDatabaseSQLite(this,1);

        viewUser         = getIntent().getStringExtra("View");
        mDispayUser      = (TextView) findViewById(R.id.displayNewUserID);
        mDispayUser.setText(viewUser);

        mRetunToMenuFromDisplayActivity = (Button) findViewById(R.id.returnToMenuFromDisplayButtonID);
        mRetunToMenuFromDisplayActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DisplayUserAcctivity.this, MenuActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}

