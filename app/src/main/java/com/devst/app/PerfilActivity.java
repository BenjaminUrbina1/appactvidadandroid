package com.devst.app;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatActivity;
public class PerfilActivity extends AppCompatActivity {

    private TextView tvEmail, tvNombre;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);

        // Recibir datos del usuario
        String emailUsuario = getIntent().getStringExtra("email_usuario");

        // Mostrar en la UI
        tvEmail = findViewById(R.id.tvEmail); // Necesitas crear este TextView en XML
        tvEmail.setText("Email: " + emailUsuario);
    }
}