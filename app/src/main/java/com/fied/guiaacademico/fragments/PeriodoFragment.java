package com.fied.guiaacademico.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.fied.guiaacademico.R;
import com.fied.guiaacademico.activities.DisciplinaDetalheActivity;
import com.fied.guiaacademico.adapters.DisciplinaAdapter;
import com.fied.guiaacademico.models.Disciplina;

import java.util.ArrayList;
import java.util.List;

public class PeriodoFragment extends Fragment implements DisciplinaAdapter.OnDisciplinaClickListener {

    private static final String ARG_PERIODO = "periodo";
    private static final String ARG_DISCIPLINAS = "disciplinas";

    private int periodo;
    private ArrayList<Disciplina> disciplinas;

    private TextView tvPeriodoTitulo;
    private TextView tvTotalCreditos;
    private RecyclerView rvDisciplinas;
    private TextView tvSemDisciplinas;

    public PeriodoFragment() {
    }

    public static PeriodoFragment newInstance(int periodo, List<Disciplina> disciplinas) {
        PeriodoFragment fragment = new PeriodoFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PERIODO, periodo);
        args.putParcelableArrayList(ARG_DISCIPLINAS, new ArrayList<>(disciplinas));
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            periodo = getArguments().getInt(ARG_PERIODO);
            disciplinas = getArguments().getParcelableArrayList(ARG_DISCIPLINAS);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_periodo, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tvPeriodoTitulo = view.findViewById(R.id.tvPeriodoTitulo);
        tvTotalCreditos = view.findViewById(R.id.tvTotalCreditos);
        rvDisciplinas = view.findViewById(R.id.rvDisciplinas);
        tvSemDisciplinas = view.findViewById(R.id.tvSemDisciplinas);

        configurarViews();
        configurarRecyclerView();
    }

    private void configurarViews() {
        tvPeriodoTitulo.setText(periodo + "º Período");

        int totalCreditos = calcularTotalCreditos();
        tvTotalCreditos.setText(totalCreditos + " créditos");

        if (disciplinas.isEmpty()) {
            rvDisciplinas.setVisibility(View.GONE);
            tvSemDisciplinas.setVisibility(View.VISIBLE);
        } else {
            rvDisciplinas.setVisibility(View.VISIBLE);
            tvSemDisciplinas.setVisibility(View.GONE);
        }
    }

    private int calcularTotalCreditos() {
        int total = 0;
        for (Disciplina disciplina : disciplinas) {
            total += disciplina.getCreditos();
        }
        return total;
    }

    private void configurarRecyclerView() {
        rvDisciplinas.setLayoutManager(new LinearLayoutManager(getContext()));
        DisciplinaAdapter adapter = new DisciplinaAdapter(getContext(), disciplinas, this);
        rvDisciplinas.setAdapter(adapter);
    }

    @Override
    public void onDisciplinaClick(Disciplina disciplina) {
        Intent intent = new Intent(getActivity(), DisciplinaDetalheActivity.class);
        intent.putExtra("disciplina_id", disciplina.getId());
        startActivity(intent);
    }
}