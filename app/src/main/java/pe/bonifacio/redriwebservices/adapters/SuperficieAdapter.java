package pe.bonifacio.redriwebservices.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import pe.bonifacio.redriwebservices.R;
import pe.bonifacio.redriwebservices.activities.HorometroActivity;
import pe.bonifacio.redriwebservices.models.MaquinaSup;

public class SuperficieAdapter extends RecyclerView.Adapter<SuperficieAdapter.ViewHolder> {

    private static final String TAG=SuperficieAdapter.class.getSimpleName();

    private List<MaquinaSup>superficies;

    public SuperficieAdapter(){
        this.superficies=new ArrayList<>();
    }
    public void setMaquinasSUP(List<MaquinaSup>superficies){
        this.superficies=superficies;
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        TextView nombreText;


        ViewHolder(View itemView) {
            super(itemView);
            nombreText = itemView.findViewById(R.id.nombre_superficie_text);

        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_maquina_sup, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull SuperficieAdapter.ViewHolder viewHolder, int position) {
        final Context context = viewHolder.itemView.getContext();

        final MaquinaSup superficie = this.superficies.get(position);

        viewHolder.nombreText.setText(superficie.getNombre_sup());
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, HorometroActivity.class);
                intent.putExtra("ID", superficie.getId_sup());
                context.startActivity(intent);

            }
        });

    }


    @Override
    public int getItemCount() {
        return this.superficies.size();
    }
}
