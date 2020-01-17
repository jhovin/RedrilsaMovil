package pe.bonifacio.redriwebservices.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

import pe.bonifacio.redriwebservices.R;
import pe.bonifacio.redriwebservices.adapters.MinaAdapter;
import pe.bonifacio.redriwebservices.fragment.RegistroProyectoFragment;
import pe.bonifacio.redriwebservices.models.MaquinaIm;
import pe.bonifacio.redriwebservices.services.ApiService;
import pe.bonifacio.redriwebservices.services.ApiServiceGenerator;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MinaActivity extends AppCompatActivity {

    private static final String TAG = MinaActivity.class.getSimpleName();

    private RecyclerView minaList;
    public FloatingActionButton floatingActionButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mina);

        floatingActionButton=findViewById(R.id.btn_showRegister);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),MaquinaImActivity.class));
            }
        });


        minaList=findViewById(R.id.recyclerview_lista_maquina_mina);
        minaList.setLayoutManager(new LinearLayoutManager(this));

        minaList.setAdapter(new MinaAdapter());

        initialize();



    }
    public void initialize(){

        ApiService service= ApiServiceGenerator.createService(ApiService.class);

        service.getMaquinasIm().enqueue(new Callback<List<MaquinaIm>>() {
            @Override
            public void onResponse(Call<List<MaquinaIm>> call, Response<List<MaquinaIm>> response) {

                try{
                    if(response.isSuccessful()){

                        List<MaquinaIm> mina =response.body();
                        Log.d(TAG,"mina: "+mina);
                        MinaAdapter adapter=(MinaAdapter)minaList.getAdapter();
                        adapter.setMaquinasIM(mina);
                        adapter.notifyDataSetChanged();
                    }

                }catch (Throwable t){
                    Log.e(TAG,"onThrowable: "+t.getMessage(),t);
                    Toast.makeText(MinaActivity.this,t.getMessage(), Toast.LENGTH_SHORT).show();
                }

            }
            @Override
            public void onFailure(Call<List<MaquinaIm>> call, Throwable t) {
                Log.e(TAG, "onFailure: " + t.getMessage(), t);
                Toast.makeText(MinaActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

}


