package co.edu.unab.sebca.tiendapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private TextView tvTitulo;
    private EditText etCorreo;
    private EditText etPassword;
    private Button btIniciar;
    private SharedPreferences preferencias;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvTitulo = findViewById(R.id.tv_titulo_inicio);
        etCorreo = findViewById(R.id.et_correo_inicio);
        btIniciar = findViewById(R.id.bt_iniciar_inicio);
        etPassword = findViewById(R.id.et_password_inicio);

        preferencias = getSharedPreferences(getString(R.string.txt_nombre_preferencias), MODE_PRIVATE);

        boolean logueado = preferencias.getBoolean(getString(R.string.txt_prefencia_login), false);

        if(logueado){

            Intent miIntencion = new Intent(MainActivity.this, ListadoActivity.class);
            startActivity(miIntencion);

            finish();
        }

        btIniciar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String usu="juan@correo.com";
                String pass="juan123";

                String correo = etCorreo.getText().toString();
                String password = etPassword.getText().toString();

                if(correo.equals(usu) && password.equals(pass)){
                    Toast.makeText(MainActivity.this, "Bienvenido "+correo, Toast.LENGTH_LONG).show();

                    SharedPreferences.Editor editable = preferencias.edit();
                    editable.putBoolean(getString(R.string.txt_prefencia_login), true);
                    editable.putString("email", correo);
                    editable.apply();

                    Intent miIntencion = new Intent(MainActivity.this, ListadoActivity.class);
                    startActivity(miIntencion);

                    finish();


                }else{
                    Toast.makeText(MainActivity.this, "Datos errados", Toast.LENGTH_LONG).show();
                }


            }
        });


        Log.d("CicloVida","Ejecutando onCreate...");

    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("CicloVida","Ejecutando onStart...");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("CicloVida","Ejecutando onResume...");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("CicloVida","Ejecutando onPause...");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d("CicloVida","Ejecutando onRestart...");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("CicloVida","Ejecutando onStop...");
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("CicloVida","Ejecutando onDestroy...");
    }
}