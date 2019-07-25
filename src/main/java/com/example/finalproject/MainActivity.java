package com.example.finalproject;

import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        NavigationView navView = findViewById(R.id.navigation_view);
        NavController navController = Navigation.findNavController(this, R.id.navigation_controller);
        NavigationUI.setupWithNavController(navView, navController);
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph())
                        .setDrawerLayout((DrawerLayout) findViewById(R.id.drawer_layout))
                        .build();

    }
}
