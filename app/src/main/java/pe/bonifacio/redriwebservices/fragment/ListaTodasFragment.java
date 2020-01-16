package pe.bonifacio.redriwebservices.fragment;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.List;

import pe.bonifacio.redriwebservices.R;
import pe.bonifacio.redriwebservices.adapters.ProyectosAdapter;
import pe.bonifacio.redriwebservices.models.Proyecto;
import pe.bonifacio.redriwebservices.models.Usuario;
import pe.bonifacio.redriwebservices.services.ApiService;
import pe.bonifacio.redriwebservices.services.ApiServiceGenerator;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ListaTodasFragment extends Fragment {


    private ProgressDialog progress;
    private static final String TAG = ListaTodasFragment.class.getSimpleName();
    private RecyclerView todosproyectosList;
    public SwipeRefreshLayout swipeRefreshLayout;
    public FloatingActionButton floatingActionButton;

    public ListaTodasFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_lista_todas, container, false);
        floatingActionButton=v.findViewById(R.id.btn_showRegister);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new RegistroProyectoFragment();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.main_content, fragment).addToBackStack("tag").commit();
            }
        });


        todosproyectosList = v.findViewById(R.id.recyclerview_lista_proyectos_todas);
        todosproyectosList.setLayoutManager(new LinearLayoutManager(getContext()));

        todosproyectosList.setAdapter(new ProyectosAdapter());

        swipeRefreshLayout=v.findViewById(R.id.swiperefresh_lista_proyectos_todas);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                initialize();
            }
        });

        initialize();

        return v;
    }

    private void initialize(){
        swipeRefreshLayout.setRefreshing(true);

        ApiService service= ApiServiceGenerator.createService(ApiService.class);
        service.getProyectos().enqueue(new Callback<List<Proyecto>>() {
            @Override
            public void onResponse(Call<List<Proyecto>> call, Response<List<Proyecto>> response) {
                try{
                    if(response.isSuccessful()){
                        List<Proyecto> proyectos=response.body();
                        Log.d(TAG,"proyectos: "+proyectos);

                        ProyectosAdapter adapter=(ProyectosAdapter)todosproyectosList.getAdapter();
                        adapter.setProyectos(proyectos);
                        adapter.notifyDataSetChanged();
                        Toast.makeText(getContext(), "Lista de Proyectos", Toast.LENGTH_LONG).show();
                    }else{
                        throw new Exception(ApiServiceGenerator.parseError(response).getMessage());
                    }
                }catch (Throwable t){
                    Log.e(TAG, "onThrowable: " + t.getMessage(), t);
                    Toast.makeText(getContext(), "Error en el Servidor al listar las proyectos", Toast.LENGTH_LONG).show();
                } finally {
                    swipeRefreshLayout.setRefreshing(false);
                }
            }

            @Override
            public void onFailure(Call<List<Proyecto>> call, Throwable t) {
                Log.e(TAG, "onFailure: " + t.getMessage(), t);
                Toast.makeText(getContext(), "No se puede conectar, verifique el acceso a internet e intente nuevamente", Toast.LENGTH_LONG).show();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }
}




