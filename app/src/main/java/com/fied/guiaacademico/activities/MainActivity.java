package com.fied.guiaacademico.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.fied.guiaacademico.R;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.button.MaterialButton;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private MaterialCardView cardGradeCurricular;
    private MaterialButton btnSobreCurso;
    private MaterialCardView cardInstagram;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }

        inicializarViews();
    }

    private void inicializarViews() {
        cardGradeCurricular = findViewById(R.id.cardGradeCurricular);
        cardGradeCurricular.setOnClickListener(this);
        
        btnSobreCurso = findViewById(R.id.btnSobreCurso);
        btnSobreCurso.setOnClickListener(this);

        cardInstagram = findViewById(R.id.cardInstagram);
        cardInstagram.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.cardGradeCurricular) {
            Intent intent = new Intent(this, CurriculoActivity.class);
            startActivity(intent);
        } else if (id == R.id.btnSobreCurso) {
            Intent intent = new Intent(this, SobreCursoActivity.class);
            startActivity(intent);
        } else if (id == R.id.cardInstagram) {
            String url = "https://www.instagram.com/broadcast.sisinfo/";
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse(url));
            startActivity(intent);
        }
    }
}