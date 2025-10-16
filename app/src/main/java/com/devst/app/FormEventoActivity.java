package com.devst.app;

import android.net.Uri;
import android.content.Intent;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Calendar;

public class FormEventoActivity extends AppCompatActivity {

    private EditText edtTitulo, edtUbicacion, edtDescripcion;
    private static final String GOOGLE_CALENDAR_PACKAGE = "com.google.android.calendar";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_evento);

        edtTitulo = findViewById(R.id.editTituloEvento);
        edtUbicacion = findViewById(R.id.editUbicacionEvento);
        edtDescripcion = findViewById(R.id.editDescripcionEvento);
        Button btnAgregar = findViewById(R.id.btnSiguienteEvento);
        Button btnCancelar = findViewById(R.id.btnCancelarEvento);

        // IMPLÍCITO DIRECTO: FormEventoActivity → Calendario (ORIGINAL QUE FUNCIONA)
        btnAgregar.setOnClickListener(v -> {
            String titulo = edtTitulo.getText().toString().trim();
            String ubicacion = edtUbicacion.getText().toString().trim();
            String descripcion = edtDescripcion.getText().toString().trim();

            if (validarFormulario(titulo, ubicacion, descripcion)) {
                agregarEventoCalendario(titulo, ubicacion, descripcion);
            }
        });

        btnCancelar.setOnClickListener(v -> finish());
    }

    private boolean validarFormulario(String titulo, String ubicacion, String descripcion) {
        if (titulo.isEmpty()) {
            edtTitulo.setError("Ingresa un título para el evento");
            return false;
        }
        if (ubicacion.isEmpty()) {
            edtUbicacion.setError("Ingresa una ubicación");
            return false;
        }
        return true;
    }

    // IMPLÍCITO: Agregar evento al calendario (MÉTODO ORIGINAL)
    // REEMPLAZA tu método actual con este
    private void agregarEventoCalendario(String titulo, String ubicacion, String descripcion) {
        try {
            // Intent que SÍ funciona con Google Calendar
            Intent calendarIntent = new Intent(Intent.ACTION_INSERT);
            calendarIntent.setData(CalendarContract.Events.CONTENT_URI);

            // Datos del evento
            calendarIntent.putExtra(CalendarContract.Events.TITLE, titulo);
            calendarIntent.putExtra(CalendarContract.Events.EVENT_LOCATION, ubicacion);
            calendarIntent.putExtra(CalendarContract.Events.DESCRIPTION, descripcion);

            // Fecha y hora (mañana a las 10 AM)
            Calendar beginTime = Calendar.getInstance();
            beginTime.add(Calendar.DAY_OF_YEAR, 1);
            beginTime.set(Calendar.HOUR_OF_DAY, 10);
            beginTime.set(Calendar.MINUTE, 0);

            Calendar endTime = Calendar.getInstance();
            endTime.add(Calendar.DAY_OF_YEAR, 1);
            endTime.set(Calendar.HOUR_OF_DAY, 12);
            endTime.set(Calendar.MINUTE, 0);

            calendarIntent.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, beginTime.getTimeInMillis());
            calendarIntent.putExtra(CalendarContract.EXTRA_EVENT_END_TIME, endTime.getTimeInMillis());
            calendarIntent.putExtra(CalendarContract.EXTRA_EVENT_ALL_DAY, false);

            // NO uses setPackage - deja que el sistema elija la app
            // calendarIntent.setPackage("com.google.android.calendar");

            // Lanza el intent
            startActivity(calendarIntent);
            Toast.makeText(this, "Abriendo calendario...", Toast.LENGTH_SHORT).show();
            finish();

        } catch (Exception e) {
            Toast.makeText(this, "Error: " + e.getMessage() + "\n¿Tienes Google Calendar instalado?", Toast.LENGTH_LONG).show();
        }
    }

}