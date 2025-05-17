package com.fied.guiaacademico.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.fied.guiaacademico.R;
import com.fied.guiaacademico.adapters.AvaliacaoAdapter;
import com.fied.guiaacademico.adapters.DisciplinaAdapter;
import com.fied.guiaacademico.adapters.PreRequisitosAdapter;
import com.fied.guiaacademico.database.DatabaseHelper;
import com.fied.guiaacademico.models.Avaliacao;
import com.fied.guiaacademico.models.Disciplina;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class DisciplinaDetalheActivity extends AppCompatActivity {

    private DatabaseHelper db;
    private Disciplina disciplina;
    private TextView tvDisciplinaCodigo, tvDisciplinaNome, tvPeriodo, tvCreditos, tvCargaHoraria, tvEmenta, tvBibliografiaBasica;
    private RecyclerView rvPreRequisitos;
    private TextView tvNotaMedia, tvTotalAvaliacoes;
    private RatingBar ratingBar;
    private MaterialButton btnAvaliar;
    private FloatingActionButton fabShare;
    private TextView tvDisciplinaInitials;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_disciplina_detalhes);

        db = DatabaseHelper.getInstance(this);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }

        inicializarViews();
        carregarDadosDisciplina();

        btnAvaliar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DisciplinaDetalheActivity.this, AvaliacoesActivity.class);
                intent.putExtra("disciplina_id", disciplina.getId());
                startActivity(intent);
            }
        });

        fabShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (disciplina != null) {
                    String shareText = "Disciplina: " + disciplina.getNome() +
                            "\nCódigo: " + disciplina.getCodigo() +
                            "\nCarga Horária: " + disciplina.getCargaHoraria() + "h" +
                            "\nPeríodo: " + disciplina.getPeriodo() + "º";

                    Intent shareIntent = new Intent(Intent.ACTION_SEND);
                    shareIntent.setType("text/plain");
                    shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Informações da Disciplina");
                    shareIntent.putExtra(Intent.EXTRA_TEXT, shareText);
                    startActivity(Intent.createChooser(shareIntent, "Compartilhar via"));
                }
            }
        });
    }

    private void inicializarViews() {
        tvDisciplinaCodigo = findViewById(R.id.tvDisciplinaCodigo);
        tvDisciplinaNome = findViewById(R.id.tvDisciplinaNome);
        tvPeriodo = findViewById(R.id.tvPeriodo);
        tvCreditos = findViewById(R.id.tvCreditos);
        tvCargaHoraria = findViewById(R.id.tvCargaHoraria);
        tvEmenta = findViewById(R.id.tvEmenta);
        tvBibliografiaBasica = findViewById(R.id.tvBibliografiaBasica);
        rvPreRequisitos = findViewById(R.id.rvPreRequisitos);
        rvPreRequisitos.setLayoutManager(new LinearLayoutManager(this));
        tvNotaMedia = findViewById(R.id.tvNotaMedia);
        tvTotalAvaliacoes = findViewById(R.id.tvTotalAvaliacoes);
        ratingBar = findViewById(R.id.ratingBar);
        btnAvaliar = findViewById(R.id.btnAvaliar);
        fabShare = findViewById(R.id.fabShare);
        tvDisciplinaInitials = findViewById(R.id.tvDisciplinaInitials);
    }

    private String gerarIniciais(String nome) {
        if (nome == null || nome.isEmpty()) {
            return "";
        }

        String[] palavras = nome.split("\\s+");
        StringBuilder iniciais = new StringBuilder();

        if (palavras.length > 0 && !palavras[0].isEmpty()) {
            iniciais.append(palavras[0].charAt(0));
        }

        if (palavras.length > 1) {
            iniciais.append(palavras[palavras.length - 1].charAt(0));
        }

        return iniciais.toString().toUpperCase();
    }

    private void carregarDadosDisciplina() {
        int disciplinaId = getIntent().getIntExtra("disciplina_id", -1);
        if (disciplinaId == -1) {
            finish();
            return;
        }

        disciplina = db.getDisciplinaById(disciplinaId);
        if (disciplina == null) {
            finish();
            return;
        }

        tvDisciplinaCodigo.setText(disciplina.getCodigo());
        tvDisciplinaNome.setText(disciplina.getNome());
        tvDisciplinaInitials.setText(gerarIniciais(disciplina.getNome()));
        tvPeriodo.setText(String.valueOf(disciplina.getPeriodo()));
        tvCreditos.setText(String.valueOf(disciplina.getCreditos()));
        tvCargaHoraria.setText(disciplina.getCargaHoraria() + "h");
        tvEmenta.setText(disciplina.getEmenta());
        tvBibliografiaBasica.setText(disciplina.getBibliografia());

        String preRequisitosStr = disciplina.getPreRequisitos();
        List<Disciplina> disciplinasPreRequisitos = new ArrayList<>();
        TextView tvSemPreRequisitos = findViewById(R.id.tvSemPreRequisitos);
        
        if (preRequisitosStr != null && !preRequisitosStr.isEmpty()) {
            String[] preRequisitos = preRequisitosStr.split(",");
            for (String codigo : preRequisitos) {
                codigo = codigo.trim();
                if (!codigo.isEmpty()) {
                    Disciplina preRequisito = db.getDisciplinaByCodigo(codigo);
                    if (preRequisito != null) {
                        disciplinasPreRequisitos.add(preRequisito);
                    }
                }
            }
        }

        if (disciplinasPreRequisitos.isEmpty()) {
            tvSemPreRequisitos.setVisibility(View.VISIBLE);
            rvPreRequisitos.setVisibility(View.GONE);
        } else {
            tvSemPreRequisitos.setVisibility(View.GONE);
            rvPreRequisitos.setVisibility(View.VISIBLE);
            PreRequisitosAdapter preRequisitosAdapter = new PreRequisitosAdapter(this, disciplinasPreRequisitos);
            rvPreRequisitos.setAdapter(preRequisitosAdapter);
        }

        loadAvaliacoes();
    }

    private void loadAvaliacoes() {
        List<Avaliacao> avaliacaoList = db.getAvaliacoesByDisciplina(disciplina.getId());

        if (avaliacaoList != null && !avaliacaoList.isEmpty()) {
            float somaNotas = 0;
            for (Avaliacao avaliacao : avaliacaoList) {
                somaNotas += avaliacao.getNota();
            }
            float media = somaNotas / avaliacaoList.size();

            tvNotaMedia.setText(String.format("%.1f", media));
            ratingBar.setRating(media);
            tvTotalAvaliacoes.setText("(" + avaliacaoList.size() + " avaliações)");
        } else {
            tvNotaMedia.setText("--");
            ratingBar.setRating(0);
            tvTotalAvaliacoes.setText("(0 avaliações)");
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}