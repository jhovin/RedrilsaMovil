package pe.bonifacio.redriwebservices.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import pe.bonifacio.redriwebservices.R;
import pe.bonifacio.redriwebservices.models.Usuario;
import pe.bonifacio.redriwebservices.services.ApiService;
import pe.bonifacio.redriwebservices.services.ApiServiceGenerator;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegistroActivity extends AppCompatActivity {

    private static final String TAG = RegistroActivity.class.getSimpleName();

    private EditText dniUsuInput;
    private EditText nombreUsuInput;
    private EditText cargoUsuInput;
    private EditText correoUsuInput;
    private EditText passwordUsuInput;
    private Button registrarUsu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        dniUsuInput=(EditText)findViewById(R.id.edit_usu_dni);
        nombreUsuInput =(EditText) findViewById(R.id.edit_usu_nombre);
        correoUsuInput =(EditText) findViewById(R.id.edit_usu_email);
        passwordUsuInput =(EditText) findViewById(R.id.edit_usu_password);
        cargoUsuInput=(EditText)findViewById(R.id.edit_usu_cargo);
        registrarUsu=(Button) findViewById(R.id.btn_usu_registro);

        registrarUsu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callRegister();
            }
        });
    }

    public void callRegister(){
        String dni=dniUsuInput.getText().toString();
        String nombre = nombreUsuInput.getText().toString();
        String correo = correoUsuInput.getText().toString();
        String cargo=cargoUsuInput.getText().toString();
        String password = passwordUsuInput.getText().toString();



        if (nombre.isEmpty() || correo.isEmpty() || password.isEmpty()||cargo.isEmpty()||dni.isEmpty()) {
            Toast.makeText(this, "Todos los campos son requeridos", Toast.LENGTH_SHORT).show();
            return;
        }

        ApiService service = ApiServiceGenerator.createService(ApiService.class);
        Call<Usuario> call;
        call = service.createUsuario(dni,nombre,cargo,correo,password);
        call.enqueue(new Callback<Usuario>() {
            @Override
            public void onResponse(Call<Usuario> call, Response<Usuario> response) {
                try {
                    if(response.isSuccessful()) {

                        Usuario usuario = response.body();
                        Log.d(TAG, "usuario: " + usuario);

                        Toast.makeText(RegistroActivity.this, "Usuario registrado satisfactoriamente", Toast.LENGTH_SHORT).show();

                        setResult(RESULT_OK);

                        finish();

                    }else{
                        throw new Exception(ApiServiceGenerator.parseError(response).getMessage());
                    }
                } catch (Throwable t) {
                    Log.e(TAG, "onThrowable: " + t.getMessage(), t);
                    Toast.makeText(RegistroActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Usuario> call, Throwable t) {
                Log.e(TAG, "onFailure: " + t.getMessage(), t);
                Toast.makeText(RegistroActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

    }

}
