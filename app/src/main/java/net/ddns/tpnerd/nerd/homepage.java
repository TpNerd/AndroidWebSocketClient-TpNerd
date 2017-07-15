package net.ddns.tpnerd.nerd;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class homepage extends Activity implements View.OnClickListener{
    private Button logout;
    private Session session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);
        logout= (Button) findViewById(R.id.logoutButton);
        logout.setOnClickListener(this);
        session=new Session(this);


    }

    private void logout()
    {
        session.setLoggedIn(false);
        finish();
        startActivity(new Intent(homepage.this,MainActivity.class));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.logoutButton:
                logout();

            break;
            default:
        }

    }
}
