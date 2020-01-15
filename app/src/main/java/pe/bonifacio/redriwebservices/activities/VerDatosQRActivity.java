package pe.bonifacio.redriwebservices.activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import pe.bonifacio.redriwebservices.R;
import pe.bonifacio.redriwebservices.models.Proyecto;
import pe.bonifacio.redriwebservices.models.Usuario;
import pe.bonifacio.redriwebservices.services.ApiService;
import pe.bonifacio.redriwebservices.services.ApiServiceGenerator;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VerDatosQRActivity extends AppCompatActivity {

    private static final String TAG = DetalleProyectoActivity.class.getSimpleName();
    private Long rutaid;
    private ImageView fotoImage;
    private TextView nombreText,clienteText,distritoText,provinciaText,departamentoText,gerenteText,telefonoText;
    private TextView edadText,dueñoText,correoText;
    String dueño,correo,idUsu;
    Usuario usuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_datos_qr);

        fotoImage = findViewById(R.id.foto_verqr_image);
        nombreText = findViewById(R.id.txt_verqr_nombre);
        clienteText = findViewById(R.id.txt_verqr_cliente);
        distritoText = findViewById(R.id.txt_verqr_distrito);
        provinciaText= findViewById(R.id.txt_verqr_provincia);
        departamentoText = findViewById(R.id.txt_verqr_departamento);
        gerenteText=findViewById(R.id.txt_verqr_gerente);
        telefonoText=findViewById(R.id.txt_verqr_telefono);

        Bundle bundle=getIntent().getExtras();
        String rutaidString=bundle.getString("dato","");

        try {
            rutaid = new Long(Long.parseLong(rutaidString));
        } catch (Exception e) {
            Toast.makeText(VerDatosQRActivity.this, "Error\n:No existe esa mascota.\nNo coincide con el código QR.", Toast.LENGTH_LONG).show();
        }


        Log.d(TAG, "LOG__rutaid : " + rutaid);

        initialize();
    }
    private void initialize() {

        ApiService service = ApiServiceGenerator.createService(ApiService.class);
        Call<Proyecto> call = service.showMascotaQR(rutaid);
        call.enqueue(new Callback<Proyecto>() {
            String dueñoUsu,correoUsu;
            @Override
            public void onResponse(@NonNull Call<Proyecto> call, @NonNull Response<Proyecto> response) {
                try {
                    if (response.isSuccessful()) {
                        Proyecto proyecto = response.body();
                        Log.d(TAG, "LOG__proyectos : " + proyecto);
                        Log.d(TAG, "LOG__usuario__proyecto : " + proyecto.getUsuario_id());

                        ApiService serviceusu = ApiServiceGenerator.createService(ApiService.class);
                        Call<Usuario> callusu=serviceusu.showUsuario(proyecto.getUsuario_id());

                        callusu.enqueue(new Callback<Usuario>() {
                            @Override
                            public void onResponse(Call<Usuario> call, Response<Usuario> response) {
                                try{
                                    if(response.isSuccessful()){
                                        usuario=response.body();
                                        Log.d(TAG, "LOG__usuario : " + usuario);
                                        dueñoUsu=usuario.getNombre();
                                        correoUsu=usuario.getCorreo();

                                        dueñoText.setText(dueñoUsu);
                                        correoText.setText(correoUsu);
                                    }
                                } catch (Throwable t) {
                                    Log.e(TAG, "onThrowable: " + t.getMessage(), t);
                                    Toast.makeText(VerDatosQRActivity.this, "onThrowable "+t.getMessage(), Toast.LENGTH_LONG).show();
                                }
                            }
                            @Override
                            public void onFailure(Call<Usuario> call, Throwable t) {
                                Log.e(TAG, "onFailure: " + t.getMessage(), t);
                                Toast.makeText(VerDatosQRActivity.this, "onFailure "+t.getMessage(), Toast.LENGTH_LONG).show();
                            }
                        });

                        nombreText.setText(proyecto.getNombre());
                        nombreText.setText(proyecto.getNombre());
                        clienteText.setText(proyecto.getCliente());
                        distritoText.setText(proyecto.getDistrito());
                        provinciaText.setText(proyecto.getProvincia());
                        departamentoText.setText(proyecto.getDepartamento());
                        gerenteText.setText(proyecto.getGerente());
                        telefonoText.setText(proyecto.getTelefono());
                        String url = ApiService.API_BASE_URL + "/proyectos/images/" + proyecto.getImagen();
                        Picasso.with(VerDatosQRActivity.this).load(url).into(fotoImage);

                    } else {
                        throw new Exception(ApiServiceGenerator.parseError(response).getMessage());
                    }

                } catch (Throwable t) {
                    Log.e(TAG, "onThrowable: " + t.getMessage(), t);
                    Toast.makeText(VerDatosQRActivity.this, "No existe esa proyecto.\nNo coincide con el código QR.\nError: "+t.getMessage(), Toast.LENGTH_LONG).show();
                    Intent intent=new Intent(VerDatosQRActivity.this, HomeActivity.class);
                    startActivity(intent);
                }
            }

            @Override
            public void onFailure(@NonNull Call<Proyecto> call, @NonNull Throwable t) {
                Log.e(TAG, "onFailure: " + t.getMessage(), t);
                Toast.makeText(VerDatosQRActivity.this, "No existe esa proyecto.\nNo coincide con el código QR.\nError: "+t.getMessage(), Toast.LENGTH_LONG).show();
                Intent intent=new Intent(VerDatosQRActivity.this, HomeActivity.class);
                startActivity(intent);
            }

        });
    }
}
