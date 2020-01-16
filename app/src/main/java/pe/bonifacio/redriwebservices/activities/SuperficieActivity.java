package pe.bonifacio.redriwebservices.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TableRow;
import android.widget.Toast;


import java.util.List;

import pe.bonifacio.redriwebservices.R;
import pe.bonifacio.redriwebservices.adapters.SuperficieAdapter;
import pe.bonifacio.redriwebservices.models.MaquinaIm;
import pe.bonifacio.redriwebservices.models.MaquinaSup;
import pe.bonifacio.redriwebservices.services.ApiService;
import pe.bonifacio.redriwebservices.services.ApiServiceGenerator;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SuperficieActivity extends AppCompatActivity {
    private static final String TAG = SuperficieActivity.class.getSimpleName();

    private RecyclerView superficieList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_superficie);

        superficieList=findViewById(R.id.recyclerview_lista_maquina_superficie);
        superficieList.setLayoutManager(new LinearLayoutManager(this));
        superficieList.setAdapter(new SuperficieAdapter());
        initialize();

    }
    public void initialize() {
        ApiService service= ApiServiceGenerator.createService(ApiService.class);

        service.getMaquinasSup().enqueue(new Callback<List<MaquinaSup>>() {
            @Override
            public void onResponse(Call<List<MaquinaSup>> call, Response<List<MaquinaSup>> response) {
                try{
                    if(response.isSuccessful()){
                        List<MaquinaSup>superficie=response.body();
                        Log.d(TAG,"superficie: "+superficie);
                        SuperficieAdapter adapter=(SuperficieAdapter)superficieList.getAdapter();
                        adapter.setMaquinasSUP(superficie);
                        adapter.notifyDataSetChanged();

                    }
                }catch (Throwable t){
                    Log.e(TAG,"onThrowable: "+t.getMessage(),t);
                    Toast.makeText(SuperficieActivity.this,t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<MaquinaSup>> call, Throwable t) {

                Log.e(TAG, "onFailure: " + t.getMessage(), t);
                Toast.makeText(SuperficieActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

    }
}
