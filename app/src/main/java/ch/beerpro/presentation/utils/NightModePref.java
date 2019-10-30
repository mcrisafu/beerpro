package ch.beerpro.presentation.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class NightModePref {

    SharedPreferences pref;

    public NightModePref(Context context){
        pref = context.getSharedPreferences("filename", Context.MODE_PRIVATE);
    }

    public void setAndStoreNightModeState(boolean nightmode){
        SharedPreferences.Editor editor = pref.edit();
        editor.putBoolean("NightMode", nightmode);
        editor.commit();
    }

    public Boolean loadNightModeState(){
        return pref.getBoolean("NightMode", false);
    }
}
