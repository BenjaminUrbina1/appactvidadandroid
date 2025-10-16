package com.devst.app;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class FormCorreoActivity extends AppCompatActivity {

    private EditText edtDestinatario, edtAsunto, edtMensaje;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_correo);

        edtDestinatario = findViewById(R.id.edtDestinatario);
        edtAsunto = findViewById(R.id.edtAsunto);
        edtMensaje = findViewById(R.id.edtMensaje);
        Button btnEnviar = findViewById(R.id.btnEnviarCorreo);
        Button btnCancelar = findViewById(R.id.btnCancelarCorreo);

        // Recibir email del usuario si está disponible
        String emailUsuario = getIntent().getStringExtra("email_usuario");
        if (emailUsuario != null) {
            edtDestinatario.setText(emailUsuario); // Autocompletar con email del usuario
        }

        btnEnviar.setOnClickListener(v -> enviarCorreo());
        btnCancelar.setOnClickListener(v -> finish());
    }

    private void enviarCorreo() {
        String destinatario = edtDestinatario.getText().toString().trim();
        String asunto = edtAsunto.getText().toString().trim();
        String mensaje = edtMensaje.getText().toString().trim();

        // Validaciones
        if (destinatario.isEmpty()) {
            edtDestinatario.setError("Ingresa un destinatario");
            return;
        }
        if (asunto.isEmpty()) {
            edtAsunto.setError("Ingresa un asunto");
            return;
        }
        if (mensaje.isEmpty()) {
            edtMensaje.setError("Ingresa un mensaje");
            return;
        }

        // IMPLÍCITO: Abrir app de correo
        Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
        emailIntent.setData(Uri.parse("mailto:" + destinatario));
        emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{destinatario});
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, asunto);
        emailIntent.putExtra(Intent.EXTRA_TEXT, mensaje);

        try {
            startActivity(Intent.createChooser(emailIntent, "Enviar correo con:"));
            Toast.makeText(this, "Abriendo app de correo...", Toast.LENGTH_SHORT).show();
            finish(); // Cerrar esta activity después de abrir el correo
        } catch (Exception e) {
            Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
}