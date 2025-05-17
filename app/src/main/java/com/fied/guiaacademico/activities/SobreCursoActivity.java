package com.fied.guiaacademico.activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.fied.guiaacademico.R;
import com.fied.guiaacademico.fragments.AtuacaoFragment;
import com.fied.guiaacademico.fragments.InfoCursoFragment;
import com.fied.guiaacademico.fragments.ObjetivosFragment;
import com.fied.guiaacademico.fragments.PerfilEgressoFragment;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class SobreCursoActivity extends AppCompatActivity {

    private ViewPager2 viewPager;
    private TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sobre_curso);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        viewPager = findViewById(R.id.viewPager);
        tabLayout = findViewById(R.id.tabLayout);

        SobreCursoPagerAdapter pagerAdapter = new SobreCursoPagerAdapter(this);
        viewPager.setAdapter(pagerAdapter);

        new TabLayoutMediator(tabLayout, viewPager, (tab, position) -> {
            switch (position) {
                case 0:
                    tab.setText(R.string.tab_info);
                    break;
                case 1:
                    tab.setText(R.string.tab_objetivos);
                    break;
                case 2:
                    tab.setText(R.string.tab_atuacao);
                    break;
                case 3:
                    tab.setText(R.string.tab_perfil);
                    break;
            }
        }).attach();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private static class SobreCursoPagerAdapter extends FragmentStateAdapter {

        public SobreCursoPagerAdapter(FragmentActivity fragmentActivity) {
            super(fragmentActivity);
        }

        @Override
        public Fragment createFragment(int position) {
            switch (position) {
                case 0:
                    return new InfoCursoFragment();
                case 1:
                    return new ObjetivosFragment();
                case 2:
                    return new AtuacaoFragment();
                case 3:
                    return new PerfilEgressoFragment();
                default:
                    return new InfoCursoFragment();
            }
        }

        @Override
        public int getItemCount() {
            return 4;
        }
    }
}