package net.ddns.tpnerd.nerd;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class register extends Activity implements View.OnClickListener{

    private DbHelper db;
    private Button go;
    private TextView goBack;
    private TextView avai;

    private EditText name;
    private EditText pass;
    private EditText pass2;
    private EditText email;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        db=new DbHelper(this);

        name= (EditText) findViewById(R.id.name);
        name.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {

                    if (db.isTaken(name.getText().toString())) {

                        avai.setVisibility(View.VISIBLE);

                        InputMethodManager imm = (InputMethodManager) register.this.getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(name.getWindowToken(), 0);

                        return true;
                    }
                    else
                        avai.setVisibility(View.INVISIBLE);


                return false;
                    }

        });



        pass= (EditText) findViewById(R.id.password);

        pass.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {

                if (pass.getText().toString().length()<8)
                {
                    InputMethodManager imm = (InputMethodManager) register.this.getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(pass.getWindowToken(), 0);
                    displayToast("Minimum password size is 8");
                    return true;
                }
                return false;
            }
        });

                pass2= (EditText) findViewById(R.id.password2);

        pass2.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {

                if (!pass.getText().toString().equals(pass2.getText().toString()))
                {
                    InputMethodManager imm = (InputMethodManager) register.this.getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(pass2.getWindowToken(), 0);
                    displayToast("Passwords are different!");
                    return true;
                }
                return false;
            }
        });


        email= (EditText) findViewById(R.id.email);
        go= (Button) findViewById(R.id.confirm);
        goBack= (TextView) findViewById((R.id.goback));

        go.setOnClickListener(this);
        goBack.setOnClickListener(this);

        email.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {

                if ((actionId== EditorInfo.IME_ACTION_DONE )   )
                {
                    reg();
                    return true;
                }
                return false;
            }

        });

        avai= (TextView) findViewById(R.id.unavailableText);



    }

    @Override
    public void onClick(View v) {
        switch(v.getId())
        {
            case R.id.confirm:
                reg();
                break;
            case R.id.goback:
                finish();
                break;
            default:
        }

    }

    public void reg(){
        String nam=name.getText().toString();
        if (nam.isEmpty())
        {
            displayToast("User name cannot be empty");
            return;
        }

        if (db.isTaken(nam))
        {
            displayToast("User name is unavailable");
            return;
        }

        String ema=email.getText().toString();
        String pas=pass.getText().toString();
        String pa2=pass2.getText().toString();

        if (pas.length()<8)
        {
            displayToast("Minimum lenght of password is 8");
        return;
        }

        if (!pas.equals(pa2))
        {
            displayToast("passwords are different");
            return;
        }

        if (ema.isEmpty() || pas.isEmpty() || pa2.isEmpty())
        {
            displayToast("Fill all inputs please");
            return;
        }

        boolean valid = android.util.Patterns.EMAIL_ADDRESS.matcher(ema).matches();
        if (!valid)
        {
           InputMethodManager imm = (InputMethodManager) register.this.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(email.getWindowToken(), 0);
            displayToast("Email is not valid");


            return;
        }
            db.addUser(nam,ema,pas);
            displayToast("user registered");
            finish();



    }

    private void displayToast(String s){
        Toast.makeText(getApplicationContext(),s,Toast.LENGTH_LONG).show();
    }

}
