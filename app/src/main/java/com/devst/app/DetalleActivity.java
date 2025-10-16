package com.devst.app;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class DetalleActivity extends AppCompatActivity {

    private TextView tvTitulo, tvDetalle;
    private Button btnVolver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle);

        tvTitulo = findViewById(R.id.tvTituloDetalle);
        tvDetalle = findViewById(R.id.tvDetalleContenido);
        btnVolver = findViewById(R.id.btnVolverDetalle);

        // Recibir datos con getExtra() - Evento Explícito con datos
        String tipoItem = getIntent().getStringExtra("tipo_item");
        String nombreContacto = getIntent().getStringExtra("nombre_contacto");
        String contactUri = getIntent().getStringExtra("contact_uri");

        if ("contacto".equals(tipoItem)) {
            tvTitulo.setText("Detalle del Contacto");
            String detalle = "Nombre: " + nombreContacto +
                    "\nURI: " + contactUri +
                    "\n\nEste es el detalle del contacto seleccionado desde la app de contactos del sistema.";
            tvDetalle.setText(detalle);
        } else {
            tvTitulo.setText("Detalle del Item");
            tvDetalle.setText("Información general del item seleccionado.");
        }

        btnVolver.setOnClickListener(v -> {
            finish(); // Cerrar esta activity y volver al Home
        });
    }
}