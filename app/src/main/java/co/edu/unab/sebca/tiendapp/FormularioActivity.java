package co.edu.unab.sebca.tiendapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class FormularioActivity extends AppCompatActivity {
    private EditText etNombreProducto;
    private EditText etPrecioProducto;
    private EditText etImagenProducto;
    private EditText etDescripcionProducto;
    private Button btFormularioProducto;
    private TextView tvTitulo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario);
        etNombreProducto = findViewById(R.id.et_nombre_producto_formulario);
        etPrecioProducto = findViewById(R.id.et_precio_producto_formulario);
        etImagenProducto = findViewById(R.id.et_imagen);
        etDescripcionProducto = findViewById(R.id.et_descripcion_producto_formulario);
        btFormularioProducto = findViewById(R.id.bt_producto_formulario);
        tvTitulo = findViewById(R.id.tv_titulo_formulario);

        Producto miViejoProducto = (Producto) getIntent().getSerializableExtra("producto");

        if (miViejoProducto!=null){
            tvTitulo.setText(R.string.txt_titulo_formulario_editar);
            etNombreProducto.setText(miViejoProducto.getNombre());
            etPrecioProducto.setText(String.valueOf(miViejoProducto.getPrecio()));
            etImagenProducto.setText(miViejoProducto.getUrlImagen());
            etDescripcionProducto.setText(miViejoProducto.getDescripcion());

            btFormularioProducto.setText(R.string.txt_titulo_formulario_editar);

            btFormularioProducto.setOnClickListener(view -> {
                String nombre = etNombreProducto.getText().toString();
                double precio = Double.parseDouble(etPrecioProducto.getText().toString());
                String imagen = etImagenProducto.getText().toString();
                String descripcion = etDescripcionProducto.getText().toString();

                miViejoProducto.setNombre(nombre);
                miViejoProducto.setPrecio(precio);
                miViejoProducto.setUrlImagen(imagen);
                miViejoProducto.setDescripcion(descripcion);

                Intent iDatos = new Intent();
                iDatos.putExtra("producto", miViejoProducto);
                setResult(RESULT_OK, iDatos);

                finish();

            });

        }else {

            btFormularioProducto.setOnClickListener(view -> {

                String nombre = etNombreProducto.getText().toString();
                double precio = Double.parseDouble(etPrecioProducto.getText().toString());
                String imagen = etImagenProducto.getText().toString();
                String descripcion = etDescripcionProducto.getText().toString();

                Producto miNuevoProducto = new Producto(nombre, precio, imagen);
                if (!descripcion.equals("")) {
                    miNuevoProducto.setDescripcion(descripcion);
                }

                Intent iDatos = new Intent();
                iDatos.putExtra("producto", miNuevoProducto);
                setResult(RESULT_OK, iDatos);

                finish();

            });
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_appbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.mi_cerrar_sesion:
                SharedPreferences preferencias = getSharedPreferences(getString(R.string.txt_nombre_preferencias), MODE_PRIVATE);
                SharedPreferences.Editor editable = preferencias.edit();
                editable.clear();
                editable.apply();

                Intent i = new Intent(FormularioActivity.this, MainActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                startActivity(i);

                finish();

                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}