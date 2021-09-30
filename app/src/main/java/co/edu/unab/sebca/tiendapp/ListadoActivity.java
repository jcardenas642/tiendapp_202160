package co.edu.unab.sebca.tiendapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class ListadoActivity extends AppCompatActivity {

    private static final int CODIGO_AGREGAR_PRODUCTO = 100;
    private static final int CODIGO_DETALLE_PRODUCTO = 110;
    private ArrayList<Producto> listadoProductos;
    private RecyclerView rvProductos;
    private Button btFormulario;
    private ProductoAdapter miAdaptador;

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

                Intent i = new Intent(ListadoActivity.this, MainActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);

                finish();

                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listado);

        String email = getSharedPreferences(getString(R.string.txt_nombre_preferencias), MODE_PRIVATE).getString("email","");

        setTitle(email);

        rvProductos = findViewById(R.id.rv_productos_listado);
        btFormulario = findViewById(R.id.button);

        btFormulario.setOnClickListener(view -> {
            Intent miIntencion = new Intent(ListadoActivity.this, FormularioActivity.class);
            startActivityForResult(miIntencion, CODIGO_AGREGAR_PRODUCTO);
        });

        listadoProductos = new ArrayList<>();
        cargarProductosFake();

        miAdaptador = new ProductoAdapter(listadoProductos);
        rvProductos.setAdapter(miAdaptador);

        rvProductos.setLayoutManager(new LinearLayoutManager(ListadoActivity.this));
        rvProductos.setHasFixedSize(true);

        miAdaptador.setOnItemClickListener(miProducto -> {
                Intent miIntencion = new Intent(ListadoActivity.this, DetalleActivity.class);

                miIntencion.putExtra("producto", miProducto);
                startActivityForResult(miIntencion, CODIGO_DETALLE_PRODUCTO);

        });


//        lvProductos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//
//                Producto miProducto = listadoProductos.get(i);
//                Toast.makeText(ListadoActivity.this, "Hice clic "+miProducto.getNombre(), Toast.LENGTH_SHORT).show();
//                Intent miIntencion = new Intent(ListadoActivity.this, DetalleActivity.class);
//
//                miIntencion.putExtra("producto", miProducto);
//                startActivity(miIntencion);
//
//            }
//        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==CODIGO_AGREGAR_PRODUCTO && resultCode==RESULT_OK){

            if (data != null) {
                Producto miProducto = (Producto) data.getSerializableExtra("producto");
                Producto ultimo = listadoProductos.get(listadoProductos.size()-1);
                int id = ultimo.getId()+1;
                miProducto.setId(id);
                listadoProductos.add(miProducto);

                miAdaptador.setListado(listadoProductos);

                Toast.makeText(ListadoActivity.this, miProducto.toString(), Toast.LENGTH_SHORT).show();

            }

        }

        if (requestCode==CODIGO_DETALLE_PRODUCTO && resultCode==RESULT_OK){
            if (data!=null){
                Producto miProducto = (Producto) data.getSerializableExtra("producto");
                Boolean editar = data.getBooleanExtra("editar", false);

                for (Producto elemento: listadoProductos) {
                    Log.d("ELIMINAR", "Lista:" +elemento.getId()+" eliminar:"+miProducto.getId());
                    if(elemento.getId()==miProducto.getId()){
                        int posicion = listadoProductos.indexOf(elemento);

                        if(editar){
                            listadoProductos.set(posicion, miProducto);
                        }else{
                            listadoProductos.remove(elemento);
                            if (listadoProductos.isEmpty()){
                                cargarProductosFake();
                            }
                        }
                        break;
                    }
                }
                miAdaptador.setListado(listadoProductos);
            }
        }

    }

    private void cargarProductosFake(){

        Producto pr1 = new Producto("Memoria USB", 70000, "https://compucentro.co/wp-content/uploads/USB-32GB.jpeg");
        pr1.setId(1);
        listadoProductos.add(pr1);

        Producto pr2 = new Producto("Disco Duro", 120000, "https://http2.mlstatic.com/D_NQ_NP_833509-MCO43400412829_092020-O.jpg");
        pr2.setDescripcion("Disco Duro SSD 500Gb");
        pr2.setId(2);
        listadoProductos.add(pr2);

        Producto pr3 = new Producto("Mouse", 50000, "data:image/jpeg;base64,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");
        pr3.setId(3);
        listadoProductos.add(pr3);

        Producto pr4 = new Producto("Teclado", 80000, "https://m.media-amazon.com/images/I/71ILYXuGvlL._AC_SX450_.jpg");
        pr4.setId(4);
        listadoProductos.add(pr4);

        Producto pr5 = new Producto("Pantalla", 400000, "https://http2.mlstatic.com/D_NQ_NP_814855-MCO43098236595_082020-O.jpg");
        pr5.setId(5);
        listadoProductos.add(pr5);

    }
}