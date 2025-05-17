package com.fied.guiaacademico.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.fied.guiaacademico.R;
import com.fied.guiaacademico.adapters.AvaliacaoAdapter;
import com.fied.guiaacademico.database.DatabaseHelper;
import com.fied.guiaacademico.models.Avaliacao;
import com.fied.guiaacademico.models.Disciplina;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.List;

public class AvaliacoesActivity extends AppCompatActivity {

    private RatingBar ratingBar;
    private TextView tvRatingDescription;
    private TextInputEditText etComentario;
    private MaterialButton btnEnviar;
    private RecyclerView recyclerViewAvaliacoes;
    private TextView tvSemAvaliacoes;
    
    private int disciplinaId;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_avaliacoes);

        databaseHelper = DatabaseHelper.getInstance(this);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(v -> onBackPressed());

        disciplinaId = getIntent().getIntExtra("disciplina_id", -1);
        if (disciplinaId == -1) {
            Toast.makeText(this, "Erro ao carregar disciplina", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        inicializarViews();
        configurarListeners();
        carregarAvaliacoes();
    }

    private void inicializarViews() {
        ratingBar = findViewById(R.id.ratingBar);
        tvRatingDescription = findViewById(R.id.tvRatingDescription);
        etComentario = findViewById(R.id.etComentario);
        btnEnviar = findViewById(R.id.btnEnviar);
        recyclerViewAvaliacoes = findViewById(R.id.recyclerViewAvaliacoes);
        tvSemAvaliacoes = findViewById(R.id.tvSemAvaliacoes);

        recyclerViewAvaliacoes.setLayoutManager(new LinearLayoutManager(this));
    }

    private void configurarListeners() {
        ratingBar.setOnRatingBarChangeListener((ratingBar, rating, fromUser) -> {
            atualizarDescricaoRating(rating);
        });

        btnEnviar.setOnClickListener(v -> {
            enviarAvaliacao();
        });
    }

    private void atualizarDescricaoRating(float rating) {
        String descricao;
        if (rating == 0) {
            descricao = "Toque para avaliar";
        } else if (rating <= 1) {
            descricao = "Péssimo";
        } else if (rating <= 2) {
            descricao = "Ruim";
        } else if (rating <= 3) {
            descricao = "Regular";
        } else if (rating <= 4) {
            descricao = "Bom";
        } else {
            descricao = "Excelente";
        }
        tvRatingDescription.setText(descricao);
    }

    private void enviarAvaliacao() {
        float rating = ratingBar.getRating();
        String comentario = etComentario.getText().toString().trim();

        if (rating == 0) {
            Toast.makeText(this, "Por favor, selecione uma nota", Toast.LENGTH_SHORT).show();
            return;
        }

        if (comentario.isEmpty()) {
            Toast.makeText(this, "Por favor, escreva um comentário", Toast.LENGTH_SHORT).show();
            return;
        }

        Avaliacao avaliacao = new Avaliacao();
        avaliacao.setDisciplinaId(disciplinaId);
        avaliacao.setNota(rating);
        avaliacao.setComentario(comentario);
        avaliacao.setData(System.currentTimeMillis());

        databaseHelper.addAvaliacao(avaliacao);

        ratingBar.setRating(0);
        etComentario.setText("");
        tvRatingDescription.setText("Toque para avaliar");

        carregarAvaliacoes();

        Toast.makeText(this, "Avaliação enviada com sucesso!", Toast.LENGTH_SHORT).show();
    }

    private void carregarAvaliacoes() {
        List<Avaliacao> avaliacaoList = databaseHelper.getAvaliacoesByDisciplina(disciplinaId);
        
        if (avaliacaoList.isEmpty()) {
            tvSemAvaliacoes.setVisibility(View.VISIBLE);
            recyclerViewAvaliacoes.setVisibility(View.GONE);
        } else {
            tvSemAvaliacoes.setVisibility(View.GONE);
            recyclerViewAvaliacoes.setVisibility(View.VISIBLE);
            AvaliacaoAdapter adapter = new AvaliacaoAdapter(this, avaliacaoList, null);
            recyclerViewAvaliacoes.setAdapter(adapter);
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}