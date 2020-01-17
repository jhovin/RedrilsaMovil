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

import pe.bonifacio.redriwebservices.HorometroFrimanActivity;
import pe.bonifacio.redriwebservices.R;
import pe.bonifacio.redriwebservices.models.MaquinaIm;


public class MinaAdapter extends RecyclerView.Adapter<MinaAdapter.ViewHolder> {

    private static final String TAG = MinaAdapter.class.getSimpleName();

    private List<MaquinaIm> minas;

    public MinaAdapter(){
        this.minas = new ArrayList<>();
    }

    public void setMaquinasIM(List<MaquinaIm> minas){
        this.minas = minas;
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        TextView nombreText;


        ViewHolder(View itemView) {
            super(itemView);
            nombreText = itemView.findViewById(R.id.nombre_mina_text);

        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_maquina_mina, parent, false);
        return new ViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        final Context context = viewHolder.itemView.getContext();

        final MaquinaIm mina = this.minas.get(position);

        viewHolder.nombreText.setText(mina.getNombre_im());



        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, HorometroFrimanActivity.class);
                intent.putExtra("ID", mina.getId_im());
                context.startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return this.minas.size();
    }
}

