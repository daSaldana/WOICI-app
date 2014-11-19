package com.proaiscool.woici_test;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class PageDos extends Activity {
    private ArrayAdapter<String> adaptador;
    private ArrayList <String> listadelugares = new ArrayList<String>();
    private ListView listaEnPantalla;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page_dos);

        Parse.initialize(this, "P1qS1Xi5QHPVdDWHTAG1iTHejwXuNEua6IGLb7vT", "gp9jN9prVVx7T2KslzsHE2CbFXMXwln4ISUhyfv3");

            crearLista();
            poblarLista();
        }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.page_dos, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }



    private void crearLista(){
        ListView listaEnPantalla = (ListView) findViewById(R.id.listaLugares);
        final String[] lugares = getResources().getStringArray(R.array.lugares);
        ArrayAdapter<String> adaptador = new ArrayAdapter<String>(this , R.layout.plantilla_lista , lugares);
        listaEnPantalla = (ListView) findViewById(R.id.listaLugares);
        adaptador = new MiListaPersonalizada();
        listaEnPantalla.setAdapter(adaptador);
               }

    private class  MiListaPersonalizada extends  ArrayAdapter<String> {


        public MiListaPersonalizada() {
            super( PageDos.this , R.layout.plantilla_lista , listadelugares);
        }

        public View getView( int position , View convertView , ViewGroup parent){

            View itemView = convertView;

            if(itemView == null ){

                itemView  = getLayoutInflater().inflate(R.layout.plantilla_lista, parent, false);

            }


		/*Aqui vamos a agregar el código para asignar los valores de la plantilla*/

            TextView titulo = (TextView) itemView.findViewById(R.id.textoPersonalizado);

            String tituloTexto = listadelugares.get(position);

            titulo.setText(tituloTexto);

            return itemView;
        }

    }



    private void poblarLista(){

                /*String[] planetas = getResources().getStringArray(R.array.lugares);

                Collections.addAll(listadelugares, planetas);*/

        ParseQuery<ParseObject> query = ParseQuery.getQuery("listaLugares");
        query.findInBackground(new FindCallback<ParseObject>() {

            public void done(List<ParseObject> parseObjects, com.parse.ParseException e) {

                if (e == null) {

                    for (ParseObject lugar :  parseObjects) {

                        listadelugares.add( lugar.get("lugarNombre").toString() );
                    }
                    crearLista();

                } else {
                    Log.d("score", "Error: " + e.getMessage());
                }

            }




        });




    }

    private  void registrarEventos(){

        listaEnPantalla.setOnItemClickListener( new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Intent intent = new Intent( getApplicationContext(), Detalles.class);

                String planetaNombre =listadelugares.get(i);

                intent.putExtra("Lugar_Nombre", planetaNombre);


                startActivity(intent);

            }
        });


    }





}