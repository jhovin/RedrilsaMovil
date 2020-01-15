package pe.bonifacio.redriwebservices.activities;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;
import com.squareup.picasso.Picasso;

import pe.bonifacio.redriwebservices.R;
import pe.bonifacio.redriwebservices.models.Proyecto;
import pe.bonifacio.redriwebservices.models.Usuario;
import pe.bonifacio.redriwebservices.services.ApiService;
import pe.bonifacio.redriwebservices.services.ApiServiceGenerator;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetalleProyectoActivity extends AppCompatActivity {

    private static final String TAG = DetalleProyectoActivity.class.getSimpleName();
    private Long id;
    private ImageView fotoImage;
    private TextView nombreText,clienteText,distritoText,provinciaText,departamentoText,gerenteText,telefonoText;
    String dueño,correo,idUsu;
    private ImageView CodeImageViewQR;
    Usuario usuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_proyecto);

        fotoImage = findViewById(R.id.foto_image);
        nombreText = findViewById(R.id.txt_detalle_nombre);
        clienteText=findViewById(R.id.txt_detalle_cliente);
        distritoText = findViewById(R.id.txt_detalle_distrito);
        provinciaText = findViewById(R.id.txt_detalle_provincia);
        departamentoText = findViewById(R.id.txt_detalle_departamento);
        gerenteText = findViewById(R.id.txt_detalle_gerente);
        telefonoText=findViewById(R.id.txt_detalle_telefono);
        CodeImageViewQR = findViewById(R.id.qrCodeImageView);

        id = getIntent().getExtras().getLong("ID");
        Log.e(TAG, "id:" + id);

        initialize();

    }

    private void initialize() {

        ApiService service = ApiServiceGenerator.createService(ApiService.class);
        Call<Proyecto> call = service.showProyecto(id);
        call.enqueue(new Callback<Proyecto>() {
            String nombreUsu,correoUsu;
            @Override
            public void onResponse(@NonNull Call<Proyecto> call, @NonNull Response<Proyecto> response) {
                try {
                    if (response.isSuccessful()) {
                        Proyecto proyecto = response.body();
                        Log.d(TAG, "LOG__proyecto : " + proyecto);
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
                                        nombreUsu=usuario.getNombre();
                                        correoUsu=usuario.getCorreo();

                                    }
                                } catch (Throwable t) {
                                    Log.e(TAG, "onThrowable: " + t.getMessage(), t);
                                    Toast.makeText(DetalleProyectoActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
                                }
                            }
                            @Override
                            public void onFailure(Call<Usuario> call, Throwable t) {
                                Log.e(TAG, "onFailure: " + t.getMessage(), t);
                                Toast.makeText(DetalleProyectoActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
                            }
                        });

                        nombreText.setText(proyecto.getNombre());
                        clienteText.setText(proyecto.getCliente());
                        distritoText.setText(proyecto.getDistrito());
                        provinciaText.setText(proyecto.getProvincia());
                        departamentoText.setText(proyecto.getDepartamento());
                        gerenteText.setText(proyecto.getGerente());
                        telefonoText.setText(proyecto.getTelefono());
                        String url = ApiService.API_BASE_URL + "/proyectos/images/" + proyecto.getImagen();
                        Picasso.with(DetalleProyectoActivity.this).load(url).into(fotoImage);

                        ///////////////
                        generarQR();
                        ///////////////

                    } else {
                        throw new Exception(ApiServiceGenerator.parseError(response).getMessage());
                    }

                } catch (Throwable t) {
                    Log.e(TAG, "onThrowable: " + t.getMessage(), t);
                    Toast.makeText(DetalleProyectoActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<Proyecto> call, @NonNull Throwable t) {
                Log.e(TAG, "onFailure: " + t.getMessage(), t);
                Toast.makeText(DetalleProyectoActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }

        });
    }

    ///////////////
    private void generarQR(){
        String nombreTextqr=nombreText.getText().toString();
        String clienteTextqr=clienteText.getText().toString();
        String distritoTextqr=distritoText.getText().toString();
        String provinciaTextqr=provinciaText.getText().toString();
        String departamentoTextqr=departamentoText.getText().toString();
        String gerenteTextqr=gerenteText.getText().toString();
        String telefonoTextqr=telefonoText.getText().toString();

        MultiFormatWriter multiFormatWriter=new MultiFormatWriter();
        try {
            BitMatrix bitMatrix=multiFormatWriter.encode(String.valueOf(id), BarcodeFormat.QR_CODE,1000,1000);
            BarcodeEncoder barcodeEncoder=new BarcodeEncoder();
            Bitmap bitmap=barcodeEncoder.createBitmap(bitMatrix);
            CodeImageViewQR.setImageBitmap(bitmap);
        }catch (WriterException e){
            e.printStackTrace();
        }
    }
    ///////////////

}
