package com.devst.app;

import android.widget.Toast;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class ConfigActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_config);

        // Configurar toolbar con botón "Atrás" como pide el PDF
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Configuración");
        }

        Button btnWifi = findViewById(R.id.btnConfigWifi);
        Button btnBluetooth = findViewById(R.id.btnConfigBluetooth);
        Button btnGeneral = findViewById(R.id.btnConfigGeneral);

        // IMPLÍCITO: Abrir configuración Wi-Fi del sistema
        btnWifi.setOnClickListener(v -> {
            Intent wifiIntent = new Intent(Settings.ACTION_WIFI_SETTINGS);
            if (wifiIntent.resolveActivity(getPackageManager()) != null) {
                startActivity(wifiIntent);
                Toast.makeText(this, "Abriendo configuración Wi-Fi", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "No se puede abrir configuración Wi-Fi", Toast.LENGTH_SHORT).show();
            }
        });

        // IMPLÍCITO: Abrir configuración Bluetooth
        btnBluetooth.setOnClickListener(v -> {
            Intent btIntent = new Intent(Settings.ACTION_BLUETOOTH_SETTINGS);
            if (btIntent.resolveActivity(getPackageManager()) != null) {
                startActivity(btIntent);
            }
        });

        // IMPLÍCITO: Abrir configuración general
        btnGeneral.setOnClickListener(v -> {
            Intent settingsIntent = new Intent(Settings.ACTION_SETTINGS);
            if (settingsIntent.resolveActivity(getPackageManager()) != null) {
                startActivity(settingsIntent);
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish(); // Para el botón "Atrás" de la toolbar
        return true;
    }
}