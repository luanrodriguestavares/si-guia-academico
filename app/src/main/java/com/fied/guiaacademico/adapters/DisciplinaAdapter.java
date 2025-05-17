package com.fied.guiaacademico.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.fied.guiaacademico.R;
import com.fied.guiaacademico.models.Disciplina;

import java.util.List;

public class DisciplinaAdapter extends RecyclerView.Adapter<DisciplinaAdapter.DisciplinaViewHolder> {

    private Context context;
    private List<Disciplina> disciplinas;
    private OnDisciplinaClickListener listener;

    public interface OnDisciplinaClickListener {
        void onDisciplinaClick(Disciplina disciplina);
    }

    public DisciplinaAdapter(Context context, List<Disciplina> disciplinas, OnDisciplinaClickListener listener) {
        this.context = context;
        this.disciplinas = disciplinas;
        this.listener = listener;
    }

    @NonNull
    @Override
    public DisciplinaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_disciplina, parent, false);
        return new DisciplinaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DisciplinaViewHolder holder, int position) {
        Disciplina disciplina = disciplinas.get(position);
        
        holder.tvDisciplinaNome.setText(disciplina.getNome());
        holder.tvDisciplinaCodigo.setText(disciplina.getCodigo());
        holder.tvDisciplinaCreditos.setText(String.valueOf(disciplina.getCreditos()));
        
        try {
            holder.tvDisciplinaTipo.setText(disciplina.getTipo());
        } catch (Exception e) {
            holder.tvDisciplinaTipo.setText("Obrigatória");
        }
        
        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onDisciplinaClick(disciplina);
            }
        });
    }

    @Override
    public int getItemCount() {
        return disciplinas.size();
    }

    public static class DisciplinaViewHolder extends RecyclerView.ViewHolder {
        TextView tvDisciplinaNome;
        TextView tvDisciplinaCodigo;
        TextView tvDisciplinaTipo;
        TextView tvDisciplinaCreditos;

        public DisciplinaViewHolder(@NonNull View itemView) {
            super(itemView);
            tvDisciplinaNome = itemView.findViewById(R.id.tvDisciplinaNome);
            tvDisciplinaCodigo = itemView.findViewById(R.id.tvDisciplinaCodigo);
            tvDisciplinaTipo = itemView.findViewById(R.id.tvDisciplinaTipo);
            tvDisciplinaCreditos = itemView.findViewById(R.id.tvDisciplinaCreditos);
        }
    }
}