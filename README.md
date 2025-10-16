[README.md](https://github.com/user-attachments/files/22942562/README.md)
# CONNECTIA "Conecta Personas, Datos y Tiempo"
Programación Android


Resumen del proyecto:
Esta es una actividad de la clase de Android. Este proyecto es un sistema de gestión de mensajes. Desde esta app se podrán enviar SMS y correos. También se podrá gestionar los contactos, agendar eventos y acceder a las configuraciones del sistema.

Versión de Android/AGP: 36


# Indents implicitos utilizados:

1-Enviar sms✨📩



https://github.com/user-attachments/assets/7e5d9eb5-7d3f-4ef9-b728-c117ae334c03



En el menú principal, se debe presionar el botón 'Enviar SMS'. En la siguiente vista, se deberá ingresar el número de la persona que recibirá el mensaje y luego escribir el mensaje en sí. Al presionar el botón de enviar, se abrirá automáticamente la app de mensajería, donde podrás enviar el SMS escrito en la vista anterior


2-ver contactos✨📱




https://github.com/user-attachments/assets/6f29af57-d56a-4559-9a28-8de8eb1ffda9

Para ver los contactos, en el menú principal se debe presionar el botón de 'Contactos'. En esta vista, se podrán ver los contactos y agregar un número. Al presionar cualquiera de las dos opciones, serás dirigido a la aplicación donde están guardados los números. Dependiendo de la opción que hayas elegido, se abrirá el listado de contactos o se mostrará la sección para agregar nuevos números.


3-enviar correo ✨📮



https://github.com/user-attachments/assets/1b3206b9-39a7-4c31-8c3d-d11199c723b4

Para enviar un correo, en el menú principal se debe presionar la opción 'Enviar correo'. Dentro de la vista, se deberá ingresar el nombre del destinatario, el asunto y el mensaje. Al presionar el botón de enviar, se abrirá automáticamente la aplicación de correos.


4-abrir las configuraciones ✨🔩





https://github.com/user-attachments/assets/63e1de14-ec02-4dcf-95f4-b8e410c01e76

Para acceder a las configuraciones del dispositivo, en el menú principal se debe presionar el botón de 'Configuraciones'. Aquí tendrás tres opciones: configuración de red, de Bluetooth y configuraciones generales. Al presionar cualquiera de los tres botones, se abrirán las configuraciones del sistema operativo.

5-agregar eventos al calendario ✨📆



https://github.com/user-attachments/assets/09f24f23-225c-4950-bf00-89c4b38f42d9

Para agregar eventos al calendario, en el menú principal se debe presionar el botón 'Agregar eventos'. En la vista siguiente, se solicitarán algunos datos que el usuario deberá completar. Al presionar 'Siguiente', se abrirá automáticamente el calendario de Google.





# INDENTS EXPLICITOS USADOS:

ConfirmActivity:
ConfirmActivity funciona como una pantalla de confirmación versátil que se adapta dinámicamente según la acción requerida, sirviendo como punto intermedio de validación antes de ejecutar operaciones críticas. Cuando se necesita enviar un SMS, muestra campos para verificar número y mensaje, mientras que para agregar eventos al calendario presenta todos los detalles del evento recibidos desde la pantalla anterior. Esta actividad actúa como un "filtro de seguridad" que garantiza que los datos estén correctos antes de proceder con acciones implícitas, mejorando la experiencia del usuario mediante confirmaciones contextuales y previniendo errores.

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
                "➕¿Agregar este evento al calendario?";

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

Codigo relevante:

        (modo calendario osms)

        String accion = getIntent().getStringExtra("accion");

        if ("agregar_evento".equals(accion)) {
            configurarModoCalendario();
        } else {
            configurarModoSMS(); // Comportamiento por defecto para SMS
        }
        -------------------------------------------------------------------------

        (validacion de mensaje de texto)

        private void validarYEnviarSMS() {
    if (numero.isEmpty() || mensaje.isEmpty()) {
        Toast.makeText(this, "Completa todos los campos", Toast.LENGTH_SHORT).show();
        return;  // 🛑 Validación explícita
    }
---------------------------------------------------------------------------------------------------------------------------------------------

DetalleActivity:

Este evento explicito nos ayuda a obtener informacion sobre un item que el usuario selecciono, en el contexto de este proyecto, al momento de presionar un contacto, aparecera la informacion de este.

(nos ubicamos dentro de ContactoActivity)

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

                //  EXPLÍCITO: ContactosActivity → DetalleActivity
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

El codigo relevante es el siguiente:

        if (cursor != null && cursor.moveToFirst()) {
                    String nombre = cursor.getString(0);

                    //  EXPLÍCITO: ContactosActivity → DetalleActivity
                    Intent intent = new Intent(ContactosActivity.this, DetalleActivity.class);
                    intent.putExtra("tipo_item", "contacto");
                    intent.putExtra("nombre_contacto", nombre);
                    intent.putExtra("contact_uri", contactUri.toString());
                    startActivity(intent);

                    cursor.close();
                }

-----------------------------------------------------------------------------------------------------------------------------------------------
ConfigActivity:

ConfigActivity funciona como un centro de control interno que permite acceder rápidamente a las configuraciones del sistema Android


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

el codigo importante es el siguiente..

         btnConfiguracion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, ConfigActivity.class);
                startActivity(intent);
            }







## Instalación

### Descargar APK
El archivo APK está disponible en: `app/build/outputs/apk/debug/app-debug.apk`

Para instalarlo:
1. Descarga el archivo `app-debug.apk` en tu dispositivo Android
2. Ve a la carpeta de descargas y ábrelo
3. Permite la instalación desde fuentes desconocidas si es necesario
4. Instala y ejecuta la aplicación



