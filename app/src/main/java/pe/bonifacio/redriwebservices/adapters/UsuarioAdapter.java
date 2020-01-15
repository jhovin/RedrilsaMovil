package pe.bonifacio.redriwebservices.adapters;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import pe.bonifacio.redriwebservices.R;
import pe.bonifacio.redriwebservices.models.Proyecto;
import pe.bonifacio.redriwebservices.models.Usuario;

public class UsuarioAdapter extends RecyclerView.Adapter<UsuarioAdapter.ViewHolder> {

    private static final String TAG = UsuarioAdapter.class.getSimpleName();
    private List<Usuario> usuarios;

    public UsuarioAdapter(){
        this.usuarios = new ArrayList<>();
    }

    public void setUsuarios(List<Usuario> usuarios){
        this.usuarios = usuarios;
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        ImageView fotoImage;
        TextView nombreText;
        TextView distritoText;



        ViewHolder(View itemView) {
            super(itemView);
            nombreText=itemView.findViewById(R.id.nombre_text);
            distritoText=itemView.findViewById(R.id.distrito_text);
            fotoImage=itemView.findViewById(R.id.foto_image);
        }
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_proyecto,parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, final int position) {


    }

    @Override
    public int getItemCount() {
        return 0;
    }
}

