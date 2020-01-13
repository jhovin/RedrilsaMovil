package pe.bonifacio.redriwebservices.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;


import java.util.ArrayList;
import java.util.List;


import pe.bonifacio.redriwebservices.R;
import pe.bonifacio.redriwebservices.activities.SelectorActivity;
import pe.bonifacio.redriwebservices.models.Proyecto;
import pe.bonifacio.redriwebservices.services.ApiService;
import pe.bonifacio.redriwebservices.services.ApiServiceGenerator;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProyectosAdapter extends RecyclerView.Adapter<ProyectosAdapter.ViewHolder>{

    private static final String TAG = ProyectosAdapter.class.getSimpleName();

    private List<Proyecto> proyectos;

    public ProyectosAdapter(){
        this.proyectos = new ArrayList<>();
    }

    public void setProyectos(List<Proyecto> proyectos){
        this.proyectos = proyectos;
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        ImageView fotoImage;
        TextView nombreText;
        ImageButton menuButton;

        ViewHolder(View itemView) {
            super(itemView);
            fotoImage = itemView.findViewById(R.id.foto_image);
            nombreText = itemView.findViewById(R.id.nombre_text);
            menuButton = itemView.findViewById(R.id.menu_button);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_proyecto, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder,final int position) {

        final Context context = viewHolder.itemView.getContext();

        final Proyecto proyecto = this.proyectos.get(position);

        viewHolder.nombreText.setText(proyecto.getNombre());

        String url = ApiService.API_BASE_URL + "/proyectos/images/" + proyecto.getImagen();
        Picasso.with(context).load(url).into(viewHolder.fotoImage);

        viewHolder.menuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popup = new PopupMenu(v.getContext(), v);
                popup.getMenuInflater().inflate(R.menu.popup_menu, popup.getMenu());
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.remove_button:

                                ApiService service = ApiServiceGenerator.createService(ApiService.class);

                                service.destroyProyecto(proyecto.getId()).enqueue(new Callback<String>() {
                                    @Override
                                    public void onResponse(@NonNull Call<String> call, @NonNull Response<String> response) {
                                        try {

                                            if (response.isSuccessful()) {

                                                String message = response.body();
                                                Log.d(TAG, "message: " + message);

                                                // Eliminar item del recyclerView y notificar cambios
                                                proyectos.remove(position);
                                                notifyItemRemoved(position);
                                                notifyItemRangeChanged(position, proyectos.size());

                                                Toast.makeText(context, message, Toast.LENGTH_LONG).show();

                                            } else {
                                                throw new Exception(ApiServiceGenerator.parseError(response).getMessage());
                                            }

                                        } catch (Throwable t) {
                                            Log.e(TAG, "onThrowable: " + t.getMessage(), t);
                                            Toast.makeText(context, t.getMessage(), Toast.LENGTH_LONG).show();
                                        }
                                    }

                                    @Override
                                    public void onFailure(@NonNull Call<String> call, @NonNull Throwable t) {
                                        Log.e(TAG, "onFailure: " + t.getMessage(), t);
                                        Toast.makeText(context, t.getMessage(), Toast.LENGTH_LONG).show();
                                    }

                                });

                                break;
                        }
                        return false;
                    }
                });
                popup.show();
            }
        });


        final Proyecto pro = this.proyectos.get(position);

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, SelectorActivity.class);
                intent.putExtra("id", pro.getId());
                context.startActivity(intent);
            }
        });


    }




    @Override
    public int getItemCount() {
        return this.proyectos.size();
    }

}
