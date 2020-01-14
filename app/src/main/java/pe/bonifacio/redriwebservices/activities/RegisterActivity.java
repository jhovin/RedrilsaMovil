package pe.bonifacio.redriwebservices.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import pe.bonifacio.redriwebservices.R;
import pe.bonifacio.redriwebservices.models.Proyecto;
import pe.bonifacio.redriwebservices.services.ApiService;
import pe.bonifacio.redriwebservices.services.ApiServiceGenerator;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {

    private static final String TAG = RegisterActivity.class.getSimpleName();

    private ImageView imagenPreview;

    private EditText nombreInput,clienteInput,distritoInput,provinciaInput,departamentoInput,gerenteInput,telefonoInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        imagenPreview = findViewById(R.id.imagen_preview);
        nombreInput = findViewById(R.id.nombre_input);
        clienteInput=findViewById(R.id.cliente_input);
        distritoInput=findViewById(R.id.distrito_input);
        provinciaInput=findViewById(R.id.provincia_input);
        departamentoInput=findViewById(R.id.departamento_input);
        gerenteInput=findViewById(R.id.gerente_input);
        telefonoInput=findViewById(R.id.telefono_input);

    }
    private static final int REQUEST_CAMERA = 100;

    public void takePicture(View view) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, REQUEST_CAMERA);
    }

    private Bitmap bitmap;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CAMERA) {
            if (resultCode == RESULT_OK) {
                bitmap = (Bitmap) data.getExtras().get("data");
                bitmap = scaleBitmapDown(bitmap, 800);  // Redimensionar
                imagenPreview.setImageBitmap(bitmap);
            }
        }
    }

    public void callRegister(View view){
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        final Long usuario_id = sp.getLong("id",0);

        String nombre = nombreInput.getText().toString().toUpperCase();
        String cliente=clienteInput.getText().toString().toUpperCase();
        String distrito=distritoInput.getText().toString().toUpperCase();
        String provincia=provinciaInput.getText().toString().toUpperCase();
        String departamento=departamentoInput.getText().toString().toUpperCase();
        String gerente=gerenteInput.getText().toString().toUpperCase();
        String telefono=telefonoInput.getText().toString().toUpperCase();

        if (nombre.isEmpty()||cliente.isEmpty()||distrito.isEmpty()||provincia.isEmpty()) {
            Toast.makeText(this, "Todos los campos son requeridos", Toast.LENGTH_SHORT).show();
            return;
        }

        ApiService service = ApiServiceGenerator.createService(ApiService.class);

        Call<Proyecto> call;

        if(bitmap == null){
            call = service.createProyecto(nombre,cliente,distrito,provincia,departamento,gerente,telefono);
        } else {

            // De bitmap a ByteArray
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
            byte[] byteArray = stream.toByteArray();

            // ByteArray a MultiPart
            RequestBody requestFile = RequestBody.create(MediaType.parse("image/jpeg"), byteArray);
            MultipartBody.Part imagenPart = MultipartBody.Part.createFormData("imagen", "photo.jpg", requestFile);

            // Paramestros a Part
            RequestBody nombrePart = RequestBody.create(MultipartBody.FORM, nombre);
            RequestBody clientePart = RequestBody.create(MultipartBody.FORM, cliente);
            RequestBody distritoPart = RequestBody.create(MultipartBody.FORM, distrito);
            RequestBody provinciaPart = RequestBody.create(MultipartBody.FORM, provincia);
            RequestBody departamentoPart = RequestBody.create(MultipartBody.FORM, departamento);
            RequestBody gerentePart = RequestBody.create(MultipartBody.FORM, gerente);
            RequestBody telefonoPart = RequestBody.create(MultipartBody.FORM, telefono);

            call = service.createProyecto(nombrePart,clientePart,distritoPart,provinciaPart,departamentoPart,gerentePart,telefonoPart,imagenPart);
        }

        call.enqueue(new Callback<Proyecto>() {
            @Override
            public void onResponse(@NonNull Call<Proyecto> call, @NonNull Response<Proyecto> response) {
                try {
                    if(response.isSuccessful()) {

                        Proyecto proyecto = response.body();
                        Log.d(TAG, "proyecto: " + proyecto);

                        Toast.makeText(RegisterActivity.this, "Registro guardado satisfactoriamente", Toast.LENGTH_SHORT).show();

                        setResult(RESULT_OK);

                        finish();

                    }else{
                        throw new Exception(ApiServiceGenerator.parseError(response).getMessage());
                    }
                } catch (Throwable t) {
                    Log.e(TAG, "onThrowable: " + t.getMessage(), t);
                    Toast.makeText(RegisterActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<Proyecto> call, @NonNull Throwable t) {
                Log.e(TAG, "onFailure: " + t.getMessage(), t);
                Toast.makeText(RegisterActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

    }

    // Redimensionar una imagen bitmap
    private Bitmap scaleBitmapDown(Bitmap bitmap, int maxDimension) {

        int originalWidth = bitmap.getWidth();
        int originalHeight = bitmap.getHeight();
        int resizedWidth = maxDimension;
        int resizedHeight = maxDimension;

        if (originalHeight > originalWidth) {
            resizedHeight = maxDimension;
            resizedWidth = (int) (resizedHeight * (float) originalWidth / (float) originalHeight);
        } else if (originalWidth > originalHeight) {
            resizedWidth = maxDimension;
            resizedHeight = (int) (resizedWidth * (float) originalHeight / (float) originalWidth);
        } else if (originalHeight == originalWidth) {
            resizedHeight = maxDimension;
            resizedWidth = maxDimension;
        }
        return Bitmap.createScaledBitmap(bitmap, resizedWidth, resizedHeight, false);
    }

}
