package net.ddns.tpnerd.nerd;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity implements View.OnClickListener{
    private Button login,register;
    private EditText NameText;
    private EditText PassText;
    private DbHelper db;
    private Session session;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        login=(Button) findViewById(R.id.loginButton);
        register=(Button) findViewById(R.id.registerButton);
        NameText=(EditText) findViewById(R.id.name);
        PassText=(EditText) findViewById(R.id.password);
        PassText.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {

                if ((actionId== EditorInfo.IME_ACTION_DONE )   )
                {
                    login();
                    return true;
                }
                    return false;
            }

        });

        register.setOnClickListener(this);
        login.setOnClickListener(this);
        db=new DbHelper(this);
        session=new Session(this);
        if (session.loggedIn()){
            startActivity(new Intent(MainActivity.this,homepage.class));
            finish();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.loginButton:
                login();
                break;
            case R.id.registerButton:

                startActivity(new Intent(MainActivity.this,register.class));
                break;

        }

    }

    private boolean login() {
        String ema=NameText.getText().toString();
        String pas=PassText.getText().toString();

        if (ema.isEmpty())
        {
            displayToast("user cannot be empty");
            return false;
        }

        if (pas.isEmpty())
        {
            displayToast("password cannot be empty");
            return false;
        }

        if (db.getUser(ema,pas)){

            session.setLoggedIn(true);
            startActivity(new Intent(MainActivity.this,homepage.class));
            finish();
            return true;
        }
        else
        if (db.getUserByEmail(ema,pas)){
            startActivity(new Intent(MainActivity.this,homepage.class));
            finish();
            return true;
        }
         else
        {
            InputMethodManager imm = (InputMethodManager) MainActivity.this.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(PassText.getWindowToken(), 0);
            displayToast("Wrong user/password");

            return false;
        }

        //System.err.println("END USER CHECK");
    }

    private void displayToast(String s){
        Toast.makeText(getApplicationContext(),s,Toast.LENGTH_LONG).show();
    }
}
