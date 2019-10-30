package ch.beerpro.presentation;

import android.content.Intent;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.Switch;

import androidx.appcompat.app.AppCompatActivity;

import ch.beerpro.R;
import ch.beerpro.presentation.utils.NightModePref;

public class SettingsActivity extends AppCompatActivity {

    private Switch themeSwitch;
    NightModePref nightmodePref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        nightmodePref = new NightModePref(this);

        if(nightmodePref.loadNightModeState()){
            setTheme(R.style.DarkAppTheme);
        } else setTheme(R.style.AppTheme); // Not sure if else is needed.

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        themeSwitch = (Switch) findViewById(R.id.theme_switch);

        if (nightmodePref.loadNightModeState()){
            themeSwitch.setChecked(true);
        }

        themeSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    nightmodePref.setAndStoreNightModeState(true);
                    restartApp();
                }
                else {
                    nightmodePref.setAndStoreNightModeState(false);
                    restartApp();
                }
            }
        });
    }

    public void restartApp(){
        Intent i = new Intent(getApplicationContext(), SettingsActivity.class);
        startActivity(i);
        finish();
    }
}
