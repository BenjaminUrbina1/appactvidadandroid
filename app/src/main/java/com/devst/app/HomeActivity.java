package com.devst.app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.appbar.MaterialToolbar;

public class HomeActivity extends AppCompatActivity {

    private MaterialToolbar toolbar;
    private TextView tvBienvenida;
    private Button btnSms;
    private Button btnContactos;
    private Button btnEnviarCorreo;
    private Button btnConfiguracion;
    private Button btnCalendario;
    private Button btnCamara;
    private Button btnPerfil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // Inicializar vistas
        toolbar = findViewById(R.id.toolbar);
        tvBienvenida = findViewById(R.id.tvBienvenida);
        btnSms = findViewById(R.id.btnSms);
        btnContactos = findViewById(R.id.btnContactos);
        btnEnviarCorreo = findViewById(R.id.btnEnviarCorreo);
        btnConfiguracion = findViewById(R.id.btnConfiguracion);
        btnCalendario = findViewById(R.id.btnCalendario);
        btnCamara = findViewById(R.id.btnCamara);
        btnPerfil = findViewById(R.id.btnPerfil);

        // Configurar toolbar
        setSupportActionBar(toolbar);

        // Configurar listeners de botones
        btnSms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, ConfirmActivity.class);
                startActivity(intent);
            }
        });

        btnContactos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, ContactosActivity.class);
                startActivity(intent);
            }
        });

        btnEnviarCorreo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, FormCorreoActivity.class);
                startActivity(intent);
            }
        });

        btnConfiguracion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, ConfigActivity.class);
                startActivity(intent);
            }
        });

        btnCalendario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, FormEventoActivity.class);
                startActivity(intent);
            }
        });

        btnCamara.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, CamaraActivity.class);
                startActivity(intent);
            }
        });

        btnPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, PerfilActivity.class);

                // Pasar los datos del usuario
                String emailUsuario = getIntent().getStringExtra("email_usuario");
                intent.putExtra("email_usuario", emailUsuario);
                // Puedes pasar más datos: nombre, etc.

                startActivity(intent);
            }
        });
    }
}