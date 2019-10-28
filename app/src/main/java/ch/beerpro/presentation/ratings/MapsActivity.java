package ch.beerpro.presentation.ratings;


import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.Menu;
import android.view.View;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;

import butterknife.BindView;
import butterknife.ButterKnife;
import ch.beerpro.R;

public class MapsActivity extends AppCompatActivity {

    private GoogleMap mMap;

    View mainView;
    MapView rosterMapView;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_picker);

        // Toolbar
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);

        // Initialize Map
        rosterMapView = (MapView) findViewById(R.id.mapView);
        rosterMapView.onCreate(savedInstanceState);
    }

    @Override
    protected void onPause() {{
        super.onPause();
        rosterMapView.onPause();
    }}

    // Load pick my place icon
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_place_picker, menu);
        return super.onCreateOptionsMenu(menu);
    }

}