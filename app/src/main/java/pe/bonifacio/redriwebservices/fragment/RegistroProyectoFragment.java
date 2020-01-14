package pe.bonifacio.redriwebservices.fragment;


import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import static android.Manifest.permission.CAMERA;
import static android.app.Activity.RESULT_OK;

import java.io.ByteArrayOutputStream;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import pe.bonifacio.redriwebservices.R;
import pe.bonifacio.redriwebservices.activities.RegisterActivity;
import pe.bonifacio.redriwebservices.models.Proyecto;
import pe.bonifacio.redriwebservices.services.ApiService;
import pe.bonifacio.redriwebservices.services.ApiServiceGenerator;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegistroProyectoFragment extends Fragment {


    private static final String TAG = RegisterActivity.class.getSimpleName();
    private static final int REQUEST_CAMERA = 100;
    String CAMERA_PERMISSION = android.Manifest.permission.CAMERA;

    private ImageView imagenPreview;
    private EditText clienteInput;
    private EditText nombreInput;
    private EditText distritoInput;
    private EditText provinciaInput;
    private EditText departamentoInput;
    private EditText gerenteInput,telefonoInput;

    private Button tomarFoto,registrarProyecto;

    private Long usuid;

    public RegistroProyectoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fragment_registro_proyecto, container, false);
        // Inflate the layout for this fragment

        imagenPreview = (ImageView) v.findViewById(R.id.imagen_preview);
        nombreInput = (EditText)v.findViewById(R.id.nombre_input);
        clienteInput=(EditText)v.findViewById(R.id.cliente_input);
        distritoInput=(EditText)v.findViewById(R.id.distrito_input);
        provinciaInput=(EditText)v.findViewById(R.id.provincia_input);
        departamentoInput=(EditText)v.findViewById(R.id.departamento_input);
        gerenteInput=(EditText)v.findViewById(R.id.gerente_input);
        telefonoInput=(EditText)v.findViewById(R.id.telefono_input);
        registrarProyecto=(Button)v.findViewById(R.id.btn_registrar_proyectos);
        tomarFoto=(Button) v.findViewById(R.id.btn_takePicture);

        tomarFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                camara();
            }
        });
        registrarProyecto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callRegister();
            }
        });
        return v;
    }
    ///////////// Escaner codigo QR /////////////
    private void camara(){
        //////camara
        int currentapiVersion = android.os.Build.VERSION.SDK_INT;
        if (currentapiVersion >= android.os.Build.VERSION_CODES.M) {
            if (checkPermission()) {
                Toast.makeText(getContext(), "Permiso ya otorgado", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, REQUEST_CAMERA);
            } else {
                requestPermission();
            }
        }
    }

    private boolean checkPermission() {
        return ( ContextCompat.checkSelfPermission(getContext(), CAMERA ) == PackageManager.PERMISSION_GRANTED);
    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(getActivity(), new String[]{CAMERA}, REQUEST_CAMERA);
    }

    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CAMERA:
                if (grantResults.length > 0) {

                    boolean cameraAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    if (cameraAccepted){
                        Toast.makeText(getContext(), "Permiso concedido, ahora puedes acceder a la cámara", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        startActivityForResult(intent, REQUEST_CAMERA);
                    }else {
                        Toast.makeText(getContext(), "Permiso denegado, no puede acceder y cámara", Toast.LENGTH_LONG).show();
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            if (shouldShowRequestPermissionRationale(CAMERA)) {
                                showMessageOKCancel("Debe acceder a los permisos",
                                        new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                                    requestPermissions(new String[]{CAMERA},
                                                            REQUEST_CAMERA);
                                                }
                                            }
                                        });
                                return;
                            }
                        }
                    }
                }
                break;
        }
    }

    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new android.support.v7.app.AlertDialog.Builder(getContext())
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancelar", null)
                .create()
                .show();
    }

    private Bitmap bitmap;

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CAMERA) {
            if (resultCode == RESULT_OK) {
                bitmap = (Bitmap) data.getExtras().get("data");
                bitmap = scaleBitmapDown(bitmap, 800);  // Redimensionar
                imagenPreview.setImageBitmap(bitmap);
            }
        }
    }
    public void callRegister(){
        String nombre = nombreInput.getText().toString().toUpperCase();
        String cliente=clienteInput.getText().toString().toUpperCase();
        String distrito=distritoInput.getText().toString().toUpperCase();
        String provincia=provinciaInput.getText().toString().toUpperCase();
        String departamento=departamentoInput.getText().toString().toUpperCase();
        String gerente=gerenteInput.getText().toString().toUpperCase();
        String telefono=telefonoInput.getText().toString().toUpperCase();

        if (nombre.isEmpty()||cliente.isEmpty()||distrito.isEmpty()||provincia.isEmpty()) {
            Toast.makeText(getContext(), "Todos los campos son requeridos", Toast.LENGTH_SHORT).show();
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

            SharedPreferences spa = PreferenceManager.getDefaultSharedPreferences(getContext());
            usuid = spa.getLong("usuid", 0L);
            //usuid = getActivity().getIntent().getExtras().getLong("ID");
            Log.e(TAG, "usuid:" + usuid);

            // Paramestros a Part
            RequestBody nombrePart = RequestBody.create(MultipartBody.FORM, nombre);
            RequestBody clientePart = RequestBody.create(MultipartBody.FORM,cliente);
            RequestBody distritoPart = RequestBody.create(MultipartBody.FORM,distrito);
            RequestBody provinciaPart = RequestBody.create(MultipartBody.FORM,provincia);
            RequestBody departamentoPart = RequestBody.create(MultipartBody.FORM,departamento);
            RequestBody gerentePart= RequestBody.create(MultipartBody.FORM,gerente);
            RequestBody telefonoPart = RequestBody.create(MultipartBody.FORM,telefono);

            call = service.createProyecto(nombrePart,clientePart,distritoPart,provinciaPart,departamentoPart,gerentePart,telefonoPart,imagenPart);
        }

        call.enqueue(new Callback<Proyecto>() {
            @Override
            public void onResponse(@NonNull Call<Proyecto> call, @NonNull Response<Proyecto> response) {
                try {
                    if(response.isSuccessful()) {

                        Proyecto proyecto = response.body();
                        Log.d(TAG, "proyecto: " + proyecto);

                        Toast.makeText(getContext(), "Registro guardado satisfactoriamente", Toast.LENGTH_SHORT).show();
                        Fragment fragment = new ListaFragment();
                        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.main_content, fragment).addToBackStack("tag").commit();


                    }else{
                        throw new Exception(ApiServiceGenerator.parseError(response).getMessage());
                    }
                } catch (Throwable t) {
                    Log.e(TAG, "onThrowable: " + t.getMessage(), t);
                    Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<Proyecto> call, @NonNull Throwable t) {
                Log.e(TAG, "onFailure: " + t.getMessage(), t);
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_LONG).show();
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
