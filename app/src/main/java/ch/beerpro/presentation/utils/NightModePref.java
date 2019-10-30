package ch.beerpro.presentation.utils;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.appcompat.widget.Toolbar;
import androidx.core.content.res.ResourcesCompat;

import ch.beerpro.R;

import static androidx.core.graphics.drawable.IconCompat.getResources;

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

    public void loadNightOrDayMode(Context context){
        if(loadNightModeState()){
            context.setTheme(R.style.DarkAppTheme);

        } else{
            context.setTheme(R.style.AppTheme);
        }
    }

/*    public void loadNightOrDayMode(Context context, Toolbar toolbar){
        if(loadNightModeState()){
            context.setTheme(R.style.DarkAppTheme);
            toolbar.setTitleTextColor(Integer.parseInt("@color/colorNightAccent"));

        } else{
            context.setTheme(R.style.AppTheme);
        }
    }*/
}
