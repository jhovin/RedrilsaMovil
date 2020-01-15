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
import pe.bonifacio.redriwebservices.fragment.ProyectoInfoFragment;
import pe.bonifacio.redriwebservices.models.Proyecto;
import pe.bonifacio.redriwebservices.models.Usuario;

public class UsuarioAdapter extends RecyclerView.Adapter<UsuarioAdapter.ViewHolder> {

    private static final String TAG = UsuarioAdapter.class.getSimpleName();
    private FragmentActivity activity;
    private List<Usuario> usuarios;
    private List<Proyecto>proyectos;

    public UsuarioAdapter(FragmentActivity activity){
        this.activity=activity;
        this.usuarios = new ArrayList<>();
    }

    public void setUsuarios(List<Usuario> usuarios){
        this.usuarios = usuarios;
    }

    public void setProyectos(List<Proyecto>proyectos){
        this.proyectos=proyectos;
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        ImageView fotoImage;
        TextView nombreText;


        ViewHolder(View itemView) {
            super(itemView);
            nombreText=itemView.findViewById(R.id.nombre_text);
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
        final Usuario usuario=this.usuarios.get(position);
        final Proyecto proyecto=this.proyectos.get(position);

        viewHolder.nombreText.setText(proyecto.getNombre());

        //String url= ApiService.API_BASE_URL+"/productos/images"+negocio.getNeglogo();
        //Picasso.with(viewHolder.itemView.getContext()).load(url).into(viewHolder.logoImg);

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Fragment fragment = new ProyectoInfoFragment();
                Bundle bundle = new Bundle();
                bundle.putLong("ID", proyecto.getId());
                fragment.setArguments(bundle);
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.main_content, fragment).addToBackStack("tag").commit();
                //Toast.makeText(activity, "Mi negocio seleccionado", Toast.LENGTH_SHORT).show();
                /*
                Intent intent=new Intent(viewHolder.itemView.getContext(), NegocioInfoFragment.class);
                intent.putExtra("ID",negocio.getNegid());
                viewHolder.itemView.getContext().startActivity(intent);


                Intent intent = new Intent(viewHolder.itemView.getContext(), NegocioInfoActivity.class);
                intent.putExtra("ID", negocio.getNegid());
                viewHolder.itemView.getContext().startActivity(intent);
                 */
            }
        });


    }

    @Override
    public int getItemCount() {
        return 0;
    }
}

