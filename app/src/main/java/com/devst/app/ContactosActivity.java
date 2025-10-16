package com.devst.app;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

    public class ContactosActivity extends AppCompatActivity {

        private TextView tvInfoContacto;
        private final ActivityResultLauncher<Intent> contactPickerLauncher =
                registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
                    if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                        Uri contactUri = result.getData().getData();
                        if (contactUri != null) {
                            mostrarInfoContacto(contactUri);
                        }
                    }
                });

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_contactos);

            tvInfoContacto = findViewById(R.id.tvInfoContacto);
            Button btnSeleccionar = findViewById(R.id.btnSeleccionarContacto);
            Button btnAgregar = findViewById(R.id.btnAgregarContacto);
            Button btnVolver = findViewById(R.id.btnVolverContactos);

            // EVENTO IMPLÍCITO: Seleccionar contacto existente
            btnSeleccionar.setOnClickListener(v -> {
                Intent pickContact = new Intent(Intent.ACTION_PICK);
                pickContact.setType(ContactsContract.Contacts.CONTENT_TYPE);
                contactPickerLauncher.launch(pickContact);
            });

            btnAgregar.setOnClickListener(v -> {
                // IMPLÍCITO: Abrir app de teléfono/marcador
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:123456789"));
                startActivity(intent);
                Toast.makeText(this, "Abriendo marcador telefónico", Toast.LENGTH_SHORT).show();
            });

            // EVENTO EXPLÍCITO: Volver al Home (navegación interna)
            btnVolver.setOnClickListener(v -> {
                finish(); // Cierra esta activity y vuelve a HomeActivity
            });
        }

        private void mostrarInfoContacto(Uri contactUri) {
            try {
                Cursor cursor = getContentResolver().query(
                        contactUri,
                        new String[]{ContactsContract.Contacts.DISPLAY_NAME},
                        null, null, null
                );

                if (cursor != null && cursor.moveToFirst()) {
                    String nombre = cursor.getString(0);

                    // ✅ EXPLÍCITO: ContactosActivity → DetalleActivity
                    Intent intent = new Intent(ContactosActivity.this, DetalleActivity.class);
                    intent.putExtra("tipo_item", "contacto");
                    intent.putExtra("nombre_contacto", nombre);
                    intent.putExtra("contact_uri", contactUri.toString());
                    startActivity(intent);

                    cursor.close();
                }
            } catch (Exception e) {
                Toast.makeText(this, "Error al leer contacto", Toast.LENGTH_SHORT).show();
            }
        }
    }