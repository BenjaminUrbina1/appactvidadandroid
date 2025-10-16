package com.devst.app;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Calendar;

public class ConfirmActivity extends AppCompatActivity {

    private EditText edtNumero, edtMensaje;
    private TextView tvTitulo, tvDetalles;
    private Button btnConfirmar, btnCancelar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm);

        edtNumero = findViewById(R.id.edtNumero);
        edtMensaje = findViewById(R.id.edtMensaje);
        btnConfirmar = findViewById(R.id.btnConfirmar);
        btnCancelar = findViewById(R.id.btnCancelar);

        // Agregar estas referencias si no existen en tu layout


        // Determinar qué acción realizar
        String accion = getIntent().getStringExtra("accion");

        if ("agregar_evento".equals(accion)) {
            configurarModoCalendario();
        } else {
            configurarModoSMS(); // Comportamiento por defecto para SMS
        }

        btnCancelar.setOnClickListener(v -> finish());
    }

    private void configurarModoSMS() {
        if (tvTitulo != null) tvTitulo.setText("Confirmar envío de SMS");
        if (tvDetalles != null) tvDetalles.setText("Verifica los datos antes de enviar el SMS:");

        btnConfirmar.setOnClickListener(v -> validarYEnviarSMS());
    }

    private void configurarModoCalendario() {
        // Ocultar campos de SMS y mostrar detalles del evento
        edtNumero.setVisibility(android.view.View.GONE);
        edtMensaje.setVisibility(android.view.View.GONE);

        if (tvTitulo != null) tvTitulo.setText("Confirmar Evento de Calendario");

        // Recibir datos del evento
        String tituloEvento = getIntent().getStringExtra("titulo_evento");
        String ubicacionEvento = getIntent().getStringExtra("ubicacion_evento");
        String descripcionEvento = getIntent().getStringExtra("descripcion_evento");

        // Mostrar detalles del evento
        String detalles = "📅 Título: " + tituloEvento + "\n" +
                "📍 Ubicación: " + ubicacionEvento + "\n" +
                "📝 Descripción: " + descripcionEvento + "\n\n" +
                "¿Agregar este evento al calendario?";

        if (tvDetalles != null) tvDetalles.setText(detalles);

        btnConfirmar.setOnClickListener(v -> agregarEventoCalendario(tituloEvento, ubicacionEvento, descripcionEvento));
    }

    private void validarYEnviarSMS() {
        String numero = edtNumero.getText().toString().trim();
        String mensaje = edtMensaje.getText().toString().trim();

        if (numero.isEmpty() || mensaje.isEmpty()) {
            Toast.makeText(this, "Completa todos los campos", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            Intent intent = new Intent(Intent.ACTION_SENDTO);
            intent.setData(Uri.parse("smsto:" + numero));
            intent.putExtra("sms_body", mensaje);
            intent.setPackage("com.google.android.apps.messaging");
            startActivity(intent);
            finish();
        } catch (Exception e) {
            try {
                Intent fallback = new Intent(Intent.ACTION_VIEW);
                fallback.setData(Uri.parse("sms:" + numero));
                fallback.putExtra("sms_body", mensaje);
                startActivity(fallback);
                finish();
            } catch (Exception e2) {
                Toast.makeText(this, "Error: " + e2.getMessage(), Toast.LENGTH_LONG).show();
            }
        }
    }

    private void agregarEventoCalendario(String titulo, String ubicacion, String descripcion) {
        try {
            // IMPLÍCITO: Agregar evento al calendario
            Intent calendarIntent = new Intent(Intent.ACTION_INSERT);
            calendarIntent.setData(CalendarContract.Events.CONTENT_URI);

            // Datos del evento
            calendarIntent.putExtra(CalendarContract.Events.TITLE, titulo);
            calendarIntent.putExtra(CalendarContract.Events.EVENT_LOCATION, ubicacion);
            calendarIntent.putExtra(CalendarContract.Events.DESCRIPTION, descripcion);

            // Configurar fecha y hora (mañana a las 10 AM)
            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.DAY_OF_YEAR, 1);
            cal.set(Calendar.HOUR_OF_DAY, 10);
            cal.set(Calendar.MINUTE, 0);
            long startTime = cal.getTimeInMillis();
            long endTime = startTime + (2 * 60 * 60 * 1000); // Duración 2 horas

            calendarIntent.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, startTime);
            calendarIntent.putExtra(CalendarContract.EXTRA_EVENT_END_TIME, endTime);
            calendarIntent.putExtra(CalendarContract.EXTRA_EVENT_ALL_DAY, false);

            if (calendarIntent.resolveActivity(getPackageManager()) != null) {
                startActivity(calendarIntent);
                Toast.makeText(this, "Abriendo calendario...", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(this, "No hay app de calendario disponible", Toast.LENGTH_SHORT).show();
            }

        } catch (Exception e) {
            Toast.makeText(this, "Error al abrir calendario: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
}