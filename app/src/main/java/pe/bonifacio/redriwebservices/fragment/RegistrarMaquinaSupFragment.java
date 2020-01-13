package pe.bonifacio.redriwebservices.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import pe.bonifacio.redriwebservices.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class RegistrarMaquinaSupFragment extends Fragment {


    public RegistrarMaquinaSupFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_registrar_maquina_sup, container, false);
    }

}
