package pe.bonifacio.redriwebservices.fragment;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
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

import pe.bonifacio.redriwebservices.R;
import pe.bonifacio.redriwebservices.adapters.ProyectosAdapter;
import pe.bonifacio.redriwebservices.models.Usuario;
import pe.bonifacio.redriwebservices.services.ApiService;
import pe.bonifacio.redriwebservices.services.ApiServiceGenerator;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ListaFragment extends Fragment {

    private static final int REQUEST_REGISTER_FORM = 100;
    private FloatingActionButton floatingActionButton;
    private static final String TAG = ListaFragment.class.getSimpleName();
    private RecyclerView proyectosList;
    public SwipeRefreshLayout swipeRefreshLayout;
    private Long usuid;

    public ListaFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_lista, container, false);

        floatingActionButton=v.findViewById(R.id.btn_showRegister);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new RegistroProyectoFragment();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.main_content, fragment).addToBackStack("tag").commit();
            }
        });

        proyectosList = v.findViewById(R.id.recyclerview_lista_proyectos);
        proyectosList.setLayoutManager(new LinearLayoutManager(getContext()));

        proyectosList.setAdapter(new ProyectosAdapter());

        swipeRefreshLayout=v.findViewById(R.id.swiperefresh_lista_proyectos);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                initialize();
            }
        });

        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getContext());
        usuid = sp.getLong("usuid", 0L);
        //usuid = getActivity().getIntent().getExtras().getLong("ID");
        Log.e(TAG, "usuid:" + usuid);

        initialize();


        return v;
    }

    private void initialize() {
        swipeRefreshLayout.setRefreshing(true);

        ApiService service = ApiServiceGenerator.createService(ApiService.class);

        Call<Usuario> call=service.showUsuario(this.usuid);

        call.enqueue(new Callback<Usuario>() {

            @Override
            public void onResponse(Call<Usuario> call, Response<Usuario> response) {
                try {
                    if (response.isSuccessful()) {
                        Usuario usuario=response.body();
                        Log.d(TAG, "usuarioooooo: " + usuario);
                        Log.d(TAG, "usuario getNegocio: " + usuario.getProyecto());

                        ProyectosAdapter adapter = (ProyectosAdapter) proyectosList.getAdapter();
                        adapter.setProyectos(usuario.getProyecto());
                        adapter.notifyDataSetChanged();


                    } else {
                        throw new Exception(ApiServiceGenerator.parseError(response).getMessage());
                    }

                } catch (Throwable t) {
                    Log.e(TAG, "onThrowable: " + t.getMessage(), t);
                    Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_LONG).show();
                }finally {
                    swipeRefreshLayout.setRefreshing(false);
                }
            }

            @Override
            public void onFailure(Call<Usuario> call, Throwable t) {
                Log.e(TAG, "onFailure: " + t.getMessage(), t);
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_LONG).show();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

}

