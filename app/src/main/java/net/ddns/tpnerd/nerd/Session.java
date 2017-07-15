package net.ddns.tpnerd.nerd;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Toshi on 14/07/2017.
 */

public class Session {
    SharedPreferences prefs;
    SharedPreferences.Editor editor;
    Context ctx;

    public Session(Context c)
    {
        this.ctx=c;
        prefs=ctx.getSharedPreferences("nerd", Context.MODE_PRIVATE);
        editor=prefs.edit();

    }

    public void setLoggedIn(boolean loggg)
    {
        editor.putBoolean("loggedinMode",loggg);
        editor.commit();
    }

    public boolean loggedIn() {
        return prefs.getBoolean("loggedinMode",false);
    }
}
