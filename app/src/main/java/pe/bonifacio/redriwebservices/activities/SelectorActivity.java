package pe.bonifacio.redriwebservices.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.widget.ImageView;

import pe.bonifacio.redriwebservices.R;

public class SelectorActivity extends AppCompatActivity {
    private static final String TAG =SelectorActivity.class.getSimpleName();
    private CardView maquinasuperficie;
    private CardView interiormina;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selector);

        maquinasuperficie = findViewById(R.id.cvMSuperficie);
        interiormina=findViewById(R.id.cvMInterior);


    }
}
