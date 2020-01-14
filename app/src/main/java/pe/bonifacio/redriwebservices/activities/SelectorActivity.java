package pe.bonifacio.redriwebservices.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;

import pe.bonifacio.redriwebservices.R;

public class SelectorActivity extends AppCompatActivity {
    private static final String TAG =SelectorActivity.class.getSimpleName();
    private CardView maquinasuperficie;
    private CardView interiormina;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selector);

        interiormina=findViewById(R.id.cvMInterior);
        interiormina.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mina();
            }
        });


    }

    public void mina(){
        Intent intent=new Intent(SelectorActivity.this,MinaActivity.class);
        startActivity(intent);
    }
    public void superficie(){
        
    }



}
