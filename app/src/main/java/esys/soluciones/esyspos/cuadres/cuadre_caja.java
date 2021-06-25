package esys.soluciones.esyspos.cuadres;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.InputType;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.Toast;

import esys.soluciones.esyspos.MySQL.Query_MySQL;
import esys.soluciones.esyspos.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Formatter;
import java.util.HashMap;
import java.util.List;

public class cuadre_caja extends AppCompatActivity {

    EditText ed_fecha_inicial,ed_fecha_final;
    Button button_consultar;
    Calendar calendar = Calendar.getInstance();

    String fecha_inicial,fecha_final;

    ExpandableListView expandableListView_cuadre_caja;
    List<String> listGroup;
    HashMap<String,List<String>> listItem;
    AdaptadorCuadreCaja adapter;
    Query_MySQL.cuadre_de_caja_por_cajero_mysql query_mySQL ;

    final int año = calendar.get(Calendar.YEAR);
    final int mes = calendar.get(Calendar.MONTH);
    final int dia = calendar.get(Calendar.DAY_OF_MONTH);

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cuadre_caja);


        expandableListView_cuadre_caja = findViewById(R.id.ExpandableListView_cuadre);
        ed_fecha_inicial = findViewById(R.id.editText_fechainicial);
        ed_fecha_final = findViewById(R.id.editText_fechafinal);
        button_consultar = findViewById(R.id.btnConsultar);

        ed_fecha_inicial.setInputType(InputType.TYPE_NULL);
        ed_fecha_final.setInputType(InputType.TYPE_NULL);

        button_consultar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //llenar el ListView--------------------------------------------


                listGroup = new ArrayList<>();
                listItem = new HashMap<>();
                adapter = new AdaptadorCuadreCaja(cuadre_caja.this,listGroup,listItem);
                expandableListView_cuadre_caja.setAdapter(adapter);
                query_mySQL = new Query_MySQL.cuadre_de_caja_por_cajero_mysql(cuadre_caja.this);
                query_mySQL.setAdapter(adapter);
                query_mySQL.setListGroup(listGroup);
                query_mySQL.setListItem(listItem);
                query_mySQL.execute(fecha_inicial.replace(" ",""),fecha_final.replace(" ",""));
                //iniListaDatos();
                //--------------------------------------------------------------

            }
        });

        ed_fecha_inicial.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final Formatter fmt = new Formatter();
                if(event.getAction() == MotionEvent.ACTION_DOWN){
                    DatePickerDialog datePickerDialog = new DatePickerDialog(cuadre_caja.this, new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                            month = month+1;
                            fecha_inicial = fmt.format("%1$02d %2$02d %3$02d", year,month,dayOfMonth).toString();
                            String date = dayOfMonth+"/"+month+"/"+year;
                            ed_fecha_inicial.setText(date);
                        }
                    },año,mes,dia);
                    datePickerDialog.show();
                }
                return false;
            }
        });

       ed_fecha_final.setOnTouchListener(new View.OnTouchListener() {
           @Override
           public boolean onTouch(View v, MotionEvent event) {
               final boolean[] retornar = {false};
               final Formatter fmt = new Formatter();
               if(event.getAction() == MotionEvent.ACTION_DOWN){
                   DatePickerDialog datePickerDialog = new DatePickerDialog(cuadre_caja.this, new DatePickerDialog.OnDateSetListener() {
                       @Override
                       public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                           month = month+1;
                           fecha_final = fmt.format("%1$02d %2$02d %3$02d", year,month,dayOfMonth).toString();
                           String date = dayOfMonth+"/"+month+"/"+year;
                           if(Integer.parseInt(ed_fecha_inicial.getText().toString().replace("/","")) > Integer.parseInt(date.replace("/",""))){
                               Toast.makeText(getApplicationContext(),"Fecha Incorrecta",Toast.LENGTH_LONG).show();
                               retornar[0] = false;
                           }else {
                               ed_fecha_final.setText(date);
                               retornar[0] = true;
                           }
                       }
                   },año,mes,dia);
                   datePickerDialog.show();
               }
               return retornar[0];
           }
       });

    }
}
