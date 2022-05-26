package com.moliner.projectem7appmonistrolericbenitogerardgimemzmarczuritagerardmoliner;

import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bottomNavigationView = findViewById(R.id.btnNav);

        getSupportActionBar().hide();


        bottomNavigationView.setOnNavigationItemSelectedListener(bottomNavMethod);


        bottomNavigationView.setVisibility(View.GONE);


        getSupportFragmentManager().beginTransaction().replace(R.id.container, new HomeFragment()).commit();



        (new Handler()).postDelayed(this::obrirFundacio5Segons, 1000);
    }

    private void obrirFundacio5Segons() {
        getSupportFragmentManager().beginTransaction().replace(R.id.container, new com.moliner.projectem7appmonistrolericbenitogerardgimemzmarczuritagerardmoliner.FundacioFragment()).commit();

        bottomNavigationView.setVisibility(View.VISIBLE);
    }

    private BottomNavigationView.OnNavigationItemSelectedListener bottomNavMethod=new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        Fragment fragment = null;

        switch (item.getItemId())
        {
            case R.id.vista_fundacio:
                fragment = new FundacioFragment();
                break;

            case R.id.vista_mapa:
                fragment = new MapaFragment();
                break;

            case R.id.vista_escultures:
                fragment = new EsculturesFragment();
                break;

            case R.id.vista_artistes:
                fragment = new ArtistesFragment();
                break;
        }
        getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment).commit();
            return true;
        }
    };
}