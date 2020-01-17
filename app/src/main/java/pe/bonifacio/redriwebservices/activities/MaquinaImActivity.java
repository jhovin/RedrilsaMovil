package pe.bonifacio.redriwebservices.activities;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;

import pe.bonifacio.redriwebservices.R;
import pe.bonifacio.redriwebservices.models.Proyecto;

public class MaquinaImActivity extends AppCompatActivity {

    private EditText nombreMinaInput;
    private EditText observacionMinaInput;

    private Proyecto proyecto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maquina_im);

        register();

    }
    private void register(){


    }

}
