package com.example.grupo6.Vistas;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import com.example.grupo6.Adapter.ListAdapterOrdenes;
import com.example.grupo6.Config.Ordenes;
import com.example.grupo6.R;

import java.util.ArrayList;
import java.util.List;

public class ActivityOrdenes extends AppCompatActivity {

    Toolbar toolbarOrdenes;
    ListAdapterOrdenes listAdapter;
    List<Ordenes> listOrdenes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ordenes);

        //---------CASTING-----------
        toolbarOrdenes = (Toolbar) findViewById(R.id.toolbarOrdenes);

        setSupportActionBar(toolbarOrdenes);

        //---------HABILITAR FLECHA DE RETROCESO-----------
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        llenarLista();

    }

    private void llenarLista() {
        listOrdenes = new ArrayList<>();
        listOrdenes.add(new Ordenes("#2345", "Tegucigalpa", "12/02/2024", "1,250 LPS"));
        listOrdenes.add(new Ordenes("#2346", "San Pedro Sula", "03/01/2024", "900 LPS"));

        listAdapter = new ListAdapterOrdenes(listOrdenes, this, new ListAdapterOrdenes.OnItemDoubleClickListener() {
            @Override
            public void onItemDoubleClick(Ordenes ordenes) {
              alertaDetalle(ordenes);
            }
        });
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(listAdapter);
    }

    private void alertaDetalle(Ordenes ordenes){
        AlertDialog.Builder builder = new AlertDialog.Builder(ActivityOrdenes.this);
        builder.setTitle("Detalle de orden.");
        builder.setMessage("¿Desea ver el detalle de la orden seleccionada?");

        // Agregar botón de actualizar
        builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                int selectedItemIndex = ListAdapterOrdenes.getSelectedItem();
                if (selectedItemIndex != -1) {
                    Ordenes contactos = listOrdenes.get(selectedItemIndex);

                    Intent intent = new Intent(getApplicationContext(), ActivityDetalleOrden.class);
                    //intent.putExtra("latitud", contactos.getLatitud_gps());
                    intent.putExtra("longitud", ordenes.getCiudad());
                    startActivity(intent);

                }
            }
        });

        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Si el usuario cancela la eliminación, no hacer nada
            }
        });

        builder.show();
    }
}