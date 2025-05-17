package com.fied.guiaacademico.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.fied.guiaacademico.R;
import com.fied.guiaacademico.activities.DisciplinaDetalheActivity;
import com.fied.guiaacademico.models.Disciplina;

import java.util.List;

public class PreRequisitosAdapter extends RecyclerView.Adapter<PreRequisitosAdapter.ViewHolder> {

    private final Context context;
    private final List<Disciplina> disciplinaList;

    public PreRequisitosAdapter(Context context, List<Disciplina> disciplinaList) {
        this.context = context;
        this.disciplinaList = disciplinaList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_pre_requisito, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Disciplina disciplina = disciplinaList.get(position);

        holder.tvPreReqNome.setText(disciplina.getNome());
        holder.tvPreReqCodigo.setText(disciplina.getCodigo());

        String[] palavras = disciplina.getNome().split(" ");
        StringBuilder iniciais = new StringBuilder();
        for (String palavra : palavras) {
            if (!palavra.isEmpty() && !palavra.equalsIgnoreCase("de") &&
                    !palavra.equalsIgnoreCase("da") && !palavra.equalsIgnoreCase("do") &&
                    !palavra.equalsIgnoreCase("e") && !palavra.equalsIgnoreCase("à")) {
                iniciais.append(palavra.charAt(0));
                if (iniciais.length() >= 2) break;
            }
        }
        holder.tvPreReqInitials.setText(iniciais.toString().toUpperCase());

        holder.ivVerDisciplina.setOnClickListener(v -> {
            Intent intent = new Intent(context, DisciplinaDetalheActivity.class);
            intent.putExtra("disciplina_id", disciplina.getId());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return disciplinaList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvPreReqNome, tvPreReqCodigo, tvPreReqInitials;
        ImageView ivVerDisciplina;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvPreReqNome = itemView.findViewById(R.id.tvPreReqNome);
            tvPreReqCodigo = itemView.findViewById(R.id.tvPreReqCodigo);
            tvPreReqInitials = itemView.findViewById(R.id.tvPreReqInitials);
            ivVerDisciplina = itemView.findViewById(R.id.ivVerDisciplina);
        }
    }
}