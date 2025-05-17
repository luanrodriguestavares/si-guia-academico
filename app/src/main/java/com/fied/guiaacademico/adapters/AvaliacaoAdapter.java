package com.fied.guiaacademico.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.fied.guiaacademico.R;
import com.fied.guiaacademico.database.DatabaseHelper;
import com.fied.guiaacademico.models.Avaliacao;
import com.fied.guiaacademico.models.Disciplina;

import java.util.List;

public class AvaliacaoAdapter extends RecyclerView.Adapter<AvaliacaoAdapter.ViewHolder> {

    private final Context context;
    private final List<Avaliacao> avaliacaoList;
    private final Disciplina disciplina;
    private final DatabaseHelper databaseHelper;

    public AvaliacaoAdapter(Context context, List<Avaliacao> avaliacaoList, Disciplina disciplina) {
        this.context = context;
        this.avaliacaoList = avaliacaoList;
        this.disciplina = disciplina;
        this.databaseHelper = DatabaseHelper.getInstance(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_avaliacao, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Avaliacao avaliacao = avaliacaoList.get(position);
        
        if (disciplina == null) {
            Disciplina disc = databaseHelper.getDisciplinaById(avaliacao.getDisciplinaId());
            if (disc != null) {
                holder.tvDisciplina.setText(disc.getNome());
            } else {
                holder.tvDisciplina.setText("Disciplina não encontrada");
            }
        } else {
            holder.tvDisciplina.setText(disciplina.getNome());
        }
        
        holder.ratingBar.setRating(avaliacao.getNota());
        holder.tvComentario.setText(avaliacao.getComentario());
        holder.tvData.setText(avaliacao.getDataFormatada());
    }

    @Override
    public int getItemCount() {
        return avaliacaoList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvDisciplina, tvComentario, tvData;
        RatingBar ratingBar;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvDisciplina = itemView.findViewById(R.id.tvDisciplina);
            ratingBar = itemView.findViewById(R.id.ratingBar);
            tvComentario = itemView.findViewById(R.id.tvComentario);
            tvData = itemView.findViewById(R.id.tvData);
        }
    }
}