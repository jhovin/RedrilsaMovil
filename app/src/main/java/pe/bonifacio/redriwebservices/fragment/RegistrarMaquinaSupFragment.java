package pe.bonifacio.redriwebservices.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import pe.bonifacio.redriwebservices.R;
import pe.bonifacio.redriwebservices.models.MaquinaSup;
import pe.bonifacio.redriwebservices.services.ApiService;
import pe.bonifacio.redriwebservices.services.ApiServiceGenerator;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class RegistrarMaquinaSupFragment extends Fragment {

    private static final String TAG=RegistrarMaquinaSupFragment.class.getSimpleName();

    private EditText maquinasupInput;
    private EditText observacionsupInput;
    private Button registrarMaquinaSup;

    public RegistrarMaquinaSupFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fragment_registrar_maquina_sup, container, false);

        maquinasupInput=(EditText)v.findViewById(R.id.nombresup_input);
        observacionsupInput=(EditText)v.findViewById(R.id.observacionsup_input);
        registrarMaquinaSup=(Button)v.findViewById(R.id.btn_registrar_maquinasup);

        registrarMaquinaSup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callRegister();
            }
        });
        return v;
    }

    private void callRegister() {

        String maquinasup = maquinasupInput.getText().toString();
        String observacionsup = observacionsupInput.getText().toString();

        if (maquinasup.isEmpty()) {
            Toast.makeText(getContext(), "Máquina es un campo obligatorio!", Toast.LENGTH_SHORT).show();
            return;
        }
        ApiService service = ApiServiceGenerator.createService(ApiService.class);
        Call<MaquinaSup> call;
        call=service.createMaquinasSup(maquinasup,observacionsup);
        call.enqueue(new Callback<MaquinaSup>() {
            @Override
            public void onResponse(Call<MaquinaSup> call, Response<MaquinaSup> response) {
                try {
                    if(response.isSuccessful()) {

                        MaquinaSup maquinasup = response.body();
                        Log.d(TAG, "maquinasup: " + maquinasup);

                        Toast.makeText(getContext(), "Máquina registrada satisfactoriamente", Toast.LENGTH_SHORT).show();



                    }else{
                        throw new Exception(ApiServiceGenerator.parseError(response).getMessage());
                    }
                } catch (Throwable t) {
                    Log.e(TAG, "onThrowable: " + t.getMessage(), t);
                    Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<MaquinaSup> call, Throwable t) {
                Log.e(TAG, "onFailure: " + t.getMessage(), t);
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

    }

}


