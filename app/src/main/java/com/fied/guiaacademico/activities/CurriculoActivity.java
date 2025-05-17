package com.fied.guiaacademico.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.fied.guiaacademico.R;
import com.fied.guiaacademico.database.DatabaseHelper;
import com.fied.guiaacademico.fragments.PeriodoFragment;
import com.fied.guiaacademico.models.Disciplina;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CurriculoActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private ChipGroup chipGroupPeriodos;
    private LinearLayout containerPeriodos;

    private DatabaseHelper databaseHelper;
    private List<Disciplina> todasDisciplinas;
    private Map<Integer, List<Disciplina>> disciplinasPorPeriodo;

    private int filtroPeriodo = 0;  

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_curriculo);

        inicializarViews();
        configurarToolbar();
        configurarChips();

        carregarDisciplinas();
        exibirDisciplinas();
    }

    private void inicializarViews() {
        toolbar = findViewById(R.id.toolbar);
        chipGroupPeriodos = findViewById(R.id.chipGroupPeriodos);
        containerPeriodos = findViewById(R.id.containerPeriodos);

        databaseHelper = new DatabaseHelper(this);
        todasDisciplinas = new ArrayList<>();
        disciplinasPorPeriodo = new HashMap<>();
    }

    private void configurarToolbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        toolbar.setNavigationOnClickListener(v -> onBackPressed());
    }

    private void configurarChips() {
        chipGroupPeriodos.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == R.id.chipTodos) {
                filtroPeriodo = 0;
            } else if (checkedId == R.id.chip1Periodo) {
                filtroPeriodo = 1;
            } else if (checkedId == R.id.chip2Periodo) {
                filtroPeriodo = 2;
            } else if (checkedId == R.id.chip3Periodo) {
                filtroPeriodo = 3;
            } else if (checkedId == R.id.chip4Periodo) {
                filtroPeriodo = 4;
            } else if (checkedId == R.id.chip5Periodo) {
                filtroPeriodo = 5;
            } else if (checkedId == R.id.chip6Periodo) {
                filtroPeriodo = 6;
            } else if (checkedId == R.id.chip7Periodo) {
                filtroPeriodo = 7;
            } else if (checkedId == R.id.chip8Periodo) {
                filtroPeriodo = 8;
            } else {
                filtroPeriodo = 0;
            }

            aplicarFiltros();
        });
    }

    private void carregarDisciplinas() {
        todasDisciplinas = databaseHelper.getAllDisciplinas();

        for (Disciplina disciplina : todasDisciplinas) {
            int periodo = disciplina.getPeriodo();

            if (!disciplinasPorPeriodo.containsKey(periodo)) {
                disciplinasPorPeriodo.put(periodo, new ArrayList<>());
            }

            disciplinasPorPeriodo.get(periodo).add(disciplina);
        }
    }

    private void exibirDisciplinas() {
        containerPeriodos.removeAllViews();

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();

        if (filtroPeriodo > 0) {
            List<Disciplina> disciplinasFiltradas = disciplinasPorPeriodo.getOrDefault(filtroPeriodo, new ArrayList<>());

            if (!disciplinasFiltradas.isEmpty()) {
                PeriodoFragment fragment = PeriodoFragment.newInstance(filtroPeriodo, disciplinasFiltradas);
                transaction.add(R.id.containerPeriodos, fragment, "periodo_" + filtroPeriodo);
            }
        }
        else {
            for (int i = 1; i <= 8; i++) {
                if (disciplinasPorPeriodo.containsKey(i)) {
                    List<Disciplina> disciplinasFiltradas = disciplinasPorPeriodo.get(i);

                    if (!disciplinasFiltradas.isEmpty()) {
                        PeriodoFragment fragment = PeriodoFragment.newInstance(i, disciplinasFiltradas);
                        transaction.add(R.id.containerPeriodos, fragment, "periodo_" + i);
                    }
                }
            }
        }

        transaction.commit();
    }

    private void aplicarFiltros() {
        exibirDisciplinas();
    }
}