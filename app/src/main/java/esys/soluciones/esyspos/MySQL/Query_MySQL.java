package esys.soluciones.esyspos.MySQL;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import static esys.soluciones.esyspos.configuracion.General.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import esys.soluciones.esyspos.referencia.DatosConsultarReferencias;
import esys.soluciones.esyspos.configuracion.General;
import esys.soluciones.esyspos.referencia.AdaptadorConsultarReferencias;
import esys.soluciones.esyspos.MDI.mdi;
import esys.soluciones.esyspos.pedidos.AdaptadorPedidosReferencias;
import esys.soluciones.esyspos.cuadres.AdaptadorCuadreCaja;
import esys.soluciones.esyspos.pedidos.DatosReferenciasPedidos;

public class Query_MySQL extends Application {

    public static class login_mysql extends AsyncTask<String, Void, ResultSet> {

        Activity activity;
        public login_mysql(Activity context) {
            activity = context;
        }

        @Override
        protected void onPreExecute() {
            General.cargando(activity,true);
        }

        @Override
        protected ResultSet doInBackground(String... parametro) {
            Statement stmt = null;
            ResultSet rs = null;

            try {
                stmt = General.connection.createStatement();
                rs = stmt.executeQuery("SELECT SF_LOGIN('"+ parametro[0] +"', '"+ parametro[1].toUpperCase() +"')");
            } catch (NoClassDefFoundError e){
                Log.e("Definicion de clase",e.getMessage());
            } catch (Exception e) {
                Log.e("ERROR Conexion:",e.getMessage());
            }
            return rs;
        }
        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(ResultSet result) {
            try {
                while (result.next()){
                    if(result.getString(1).equals("Conectado")) {
                        Intent intent = new Intent(activity, mdi.class);
                        activity.startActivity(intent);
                        activity.finish();
                    }else{
                        Toast.makeText(activity,"Contraseña Incorrecta",Toast.LENGTH_LONG).show();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            General.cargando(activity,false);
        }
    }

    public static class consecutivo_pedido_mysql extends AsyncTask<String, Void, ResultSet> {

        Context activity;
        TextView consecutivo;

        public void setConsecutivo(TextView consecutivo) {
            this.consecutivo = consecutivo;
        }

        public consecutivo_pedido_mysql(Context context) {
            activity = context;
        }

        @Override
        protected ResultSet doInBackground(String... parametro) {
            Statement stmt = null;
            ResultSet rs = null;

            try {
                stmt = General.connection.createStatement();
                rs = stmt.executeQuery("SELECT SF_CONSECUTIVO_PEDIDO_ANDROID("+ General.Terminal+")");
            } catch (NoClassDefFoundError e){
                Log.e("Definicion de clase",e.getMessage());
            } catch (Exception e) {
                Log.e("ERROR Conexion:",e.getMessage());
            }
            return rs;
        }
        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(ResultSet result) {
            try {
                while (result.next()){
                    consecutivo.setText(result.getString(1));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            General.cargando(activity,false);
        }
    }

    public static class cuadre_de_caja_por_cajero_mysql extends AsyncTask<String, Void, ResultSet> {

        Context activity;
        List<String> listGroup;
        HashMap<String,List<String>> listItem;
        AdaptadorCuadreCaja adapter;
        private String fechaini,fechafin;

        public cuadre_de_caja_por_cajero_mysql(Context activity) {
            this.activity = activity;
        }

        public void setListGroup(List<String> listGroup) {
            this.listGroup = listGroup;
        }

        public void setListItem(HashMap<String, List<String>> listItem) {
            this.listItem = listItem;
        }

        public void setAdapter(AdaptadorCuadreCaja adapter) {
            this.adapter = adapter;
        }

        @Override
        protected void onPreExecute() {
            General.cargando(activity,true);
        }

        @Override
        protected ResultSet doInBackground(String... parametro) {
            Statement stmt = null;
            ResultSet rs = null;
            fechaini = parametro[0];
            fechafin = parametro[1];

            try {
                stmt = General.connection.createStatement();
                rs = stmt.executeQuery("CALL SP_CUADRE_CAJA_POR_CAJERO('"+ parametro[0] +"', '"+ parametro[1] +"')");
            } catch (NoClassDefFoundError e){
                Log.e("Definicion de clase",e.getMessage());
            } catch (Exception e) {
                Log.e("ERROR Conexion:",e.getMessage());
            }
            return rs;
        }
        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(ResultSet result) {


            List<String> list=new ArrayList<>();;

            formas_de_pago_mysql formas_de_pago_mysql =  new formas_de_pago_mysql();
            formas_de_pago_mysql.setList(list);
            formas_de_pago_mysql.setFechaini(fechaini);
            formas_de_pago_mysql.setFechafin(fechafin);
            formas_de_pago_mysql.setActivity(activity);
            formas_de_pago_mysql.setAdapter(adapter);
            formas_de_pago_mysql.setResultSet_totales(result);
            formas_de_pago_mysql.setListGroup(listGroup);
            formas_de_pago_mysql.setListItem(listItem);
            formas_de_pago_mysql.execute();
        }
    }

    public static class formas_de_pago_mysql extends AsyncTask<Void, Void, ResultSet> {

        List<String> list;
        ResultSet resultSet_totales;
        Context activity;
        List<String> listGroup;
        HashMap<String,List<String>> listItem;
        AdaptadorCuadreCaja adapter;
        private String fechaini,fechafin;

        public void setFechaini(String fechaini) {
            this.fechaini = fechaini;
        }

        public void setFechafin(String fechafin) {
            this.fechafin = fechafin;
        }

        public void setList(List<String> list) {
            this.list = list;
        }

        public void setResultSet_totales(ResultSet resultSet_totales) {
            this.resultSet_totales = resultSet_totales;
        }

        public void setActivity(Context activity) {
            this.activity = activity;
        }

        public void setListGroup(List<String> listGroup) {
            this.listGroup = listGroup;
        }

        public void setListItem(HashMap<String, List<String>> listItem) {
            this.listItem = listItem;
        }

        public void setAdapter(AdaptadorCuadreCaja adapter) {
            this.adapter = adapter;
        }

        @Override
        protected ResultSet doInBackground(Void... parametro) {
            Statement stmt = null;
            ResultSet rs = null;

            try {
                stmt = General.connection.createStatement();
                rs = stmt.executeQuery("CALL SP_CAJEROS_CUADRE_DE_CAJA_POR_CAJERO('"+fechaini+"', '"+fechafin+"')");
            } catch (NoClassDefFoundError e){
                Log.e("Definicion de clase",e.getMessage());
            } catch (Exception e) {
                Log.e("ERROR Conexion:",e.getMessage());
            }
            return rs;
        }
        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(ResultSet result_cajeros) {

            int lista_item = 0;
            try {

                while (result_cajeros.next()){
                    listGroup.add(result_cajeros.getString(1) + ": $"+result_cajeros.getString(2));
                    list = new ArrayList<>();
                    while (resultSet_totales.next()){
                        if (result_cajeros.getString(1).equals(resultSet_totales.getString(1))){
                            list.add(resultSet_totales.getString(3) +": " + resultSet_totales.getString(4));
                        }
                    }
                    resultSet_totales.first();
                    listItem.put(listGroup.get(lista_item), list);
                    lista_item= ++lista_item;
                }
                adapter.notifyDataSetChanged();

            } catch (Exception e) {
                e.printStackTrace();
            }

            General.cargando(activity,false);
        }
    }

    public static class consultar_precios_mysql extends AsyncTask<Void, Void, ResultSet> {


        Context activity;
        AdaptadorConsultarReferencias BuscarReferencias;
        List<DatosConsultarReferencias> datosConsultarReferencias;
        EditText txtBuscarReferencia;
        RecyclerView rv_BuscarReferencia;

        public consultar_precios_mysql(Context activity) {
            this.activity = activity;
        }

        public void setTxtBuscarReferencia(EditText txtBuscarReferencia) {
            this.txtBuscarReferencia = txtBuscarReferencia;
        }

        public void setRv_BuscarReferencia(RecyclerView rv_BuscarReferencia) {
            this.rv_BuscarReferencia = rv_BuscarReferencia;
        }

        public void filtrar(String texto){
            ArrayList<DatosConsultarReferencias> list = new ArrayList<>();

            for (DatosConsultarReferencias i : datosConsultarReferencias) {
                if (i.getNomref().toLowerCase().contains(texto.toLowerCase())) {
                    list.add(i);
                }
            }
            BuscarReferencias.filtrar(list);
        }

        @Override
        protected void onPreExecute() {
            General.cargando(activity,true);
        }

        @Override
        protected ResultSet doInBackground(Void... parametro) {
            Statement stmt = null;
            ResultSet rs = null;

            try {
                stmt = General.connection.createStatement();
                rs = stmt.executeQuery("SELECT t43.NOMREF, t43.CODIGOREF , \n" +
                        "format(t43.PREUCO,0), format(t43.PREVEN,0), \n" +
                        "SF_INVENTARIO_FINAL_ANDROID(t43.CODIGOREF) \n" +
                        "FROM t43 WHERE t43.MARANU = 0");
            } catch (NoClassDefFoundError e){
                Log.e("Definicion de clase",e.getMessage());
            } catch (Exception e) {
                Log.e("ERROR Conexion:",e.getMessage());
            }
            return rs;
        }
        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(ResultSet resultSet) {


            rv_BuscarReferencia.setLayoutManager(new GridLayoutManager(activity,1));
            datosConsultarReferencias = new ArrayList<>();

            try{
                while (resultSet.next()){
                    datosConsultarReferencias.add(new DatosConsultarReferencias(
                            resultSet.getString(1).toUpperCase(),
                            resultSet.getInt(2),
                            resultSet.getString(3),
                            resultSet.getString(4),
                            resultSet.getString(5)));
                }
                BuscarReferencias = new AdaptadorConsultarReferencias(activity,datosConsultarReferencias);
                rv_BuscarReferencia.setAdapter(BuscarReferencias);
            }catch (Exception e){

            }

           General.cargando(activity,false);
        }
    }

    public static class llenar_AutoCompleteTextView extends AsyncTask<Void, Void, ResultSet> {

        Context activity;
        AutoCompleteTextView textView;
        AlertDialog dialog;
        public llenar_AutoCompleteTextView(Context context) {
            activity = context;
        }

        public void setTextView(AutoCompleteTextView textView) {
            this.textView = textView;
        }

        public void setDialog(AlertDialog dialog) {
            this.dialog = dialog;
        }

        @Override
        protected void onPreExecute() {
            General.cargando(activity,true);
        }

        @Override
        protected ResultSet doInBackground(Void... parametro) {
            Statement stmt = null;
            ResultSet rs = null;

            try {
                stmt = General.connection.createStatement();
                rs = stmt.executeQuery("SELECT concat(trim(t64.NOMCLI),'-', t64.NITCLI) cliente FROM t64");
            } catch (NoClassDefFoundError e){
                Log.e("Definicion de clase",e.getMessage());
            } catch (Exception e) {
                Log.e("ERROR Conexion:",e.getMessage());
            }
            return rs;
        }
        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(ResultSet result) {
            try {
                ArrayList<String> sds = new ArrayList<>();
                while (result.next()){
                    sds.add(result.getString(1));
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(activity,android.R.layout.simple_list_item_1, sds);
                textView.setAdapter(adapter);
            } catch (Exception e) {
                e.printStackTrace();
            }
            General.cargando(activity,false);
            dialog.show();
        }
    }

    public static class llenar_datos_buscar_referencia_pedidos_mysql extends AsyncTask<Void, Void, ResultSet> {


        Context activity;
        List<DatosConsultarReferencias> datosConsultarReferencias;
        Query_MySQL.consecutivo_pedido_mysql consecutivoPedidoMysql;


        public llenar_datos_buscar_referencia_pedidos_mysql(Context activity) {
            this.activity = activity;
        }

        public void setDatosConsultarReferencias(List<DatosConsultarReferencias> datosConsultarReferencias) {
            this.datosConsultarReferencias = datosConsultarReferencias;
        }

        public void setConsecutivoPedidoMysql(consecutivo_pedido_mysql consecutivoPedidoMysql) {
            this.consecutivoPedidoMysql = consecutivoPedidoMysql;
        }

        @Override
        protected void onPreExecute() {
            General.cargando(activity,true);
        }

        @Override
        protected ResultSet doInBackground(Void... parametro) {
            Statement stmt = null;
            ResultSet rs = null;

            try {
                stmt = General.connection.createStatement();
                rs = stmt.executeQuery("SELECT t43.NOMREF, t43.CODIGOREF , \n" +
                        "format(t43.PREUCO,2), format(t43.PREVEN,2), \n" +
                        "SF_INVENTARIO_FINAL_ANDROID(t43.CODIGOREF) \n" +
                        "FROM t43 WHERE t43.MARANU = 0");
            } catch (NoClassDefFoundError e){
                Log.e("Definicion de clase",e.getMessage());
            } catch (Exception e) {
                Log.e("ERROR Conexion:",e.getMessage());
            }
            return rs;
        }
        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(ResultSet resultSet) {
            try{
                while (resultSet.next()){
                    datosConsultarReferencias.add(new DatosConsultarReferencias(
                            resultSet.getString(1).toUpperCase(),
                            resultSet.getInt(2),
                            resultSet.getString(3),
                            resultSet.getString(4),
                            resultSet.getString(5)));
                }
                consecutivoPedidoMysql.execute();
            }catch (Exception e){
                Toast.makeText(activity,e.getMessage(),Toast.LENGTH_LONG).show();
            }

        }
    }

    public static class insertar_pedido_mysql extends AsyncTask<Boolean, Void, Boolean> {

        Context activity;
        AdaptadorPedidosReferencias referencias;
        String nitcli_pedido,error;
        int Id_pedido;
        String comentario_pedido;
        String total_pedido;
        AlertDialog dialog;
        PreparedStatement stmt = null;

        List<DatosReferenciasPedidos> datosReferenciasPedidos;
        TextView idpedido;
        RecyclerView recyclerView_productos;
        TextView total_pedido_encabezado;


        public void setDialog(AlertDialog dialog) {
            this.dialog = dialog;
        }

        public insertar_pedido_mysql(Context context) {
            activity = context;
        }

        public void setReferencias(AdaptadorPedidosReferencias referencias) {
            this.referencias = referencias;
        }

        public void setNitcli_pedido(String nitcli_pedido) {
            this.nitcli_pedido = nitcli_pedido;
        }

        public void setId_pedido(int id_pedido) {
            Id_pedido = id_pedido + 1;
        }

        public void setComentario_pedido(String comentario_pedido) {
            this.comentario_pedido = comentario_pedido;
        }

        public void setTotal_pedido(String total_pedido) {
            this.total_pedido = total_pedido;
        }

        @Override
        protected void onPreExecute() {
            General.cargando(activity,true);
        }

        @Override
        protected Boolean doInBackground(Boolean... parametro) {
            try {
                General.connection.setAutoCommit(false);
                String sql = "INSERT INTO tv11 (IDTERMINAL, IDPEDIDO, FECHA, ESTADO, IDCLIENTE, COMENTARIO, TOTAL) VALUES(?,?,now(),'GUARDADO',?,?,?)";
                stmt = General.connection.prepareStatement(sql,ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
                stmt.setString(1, General.Terminal);
                stmt.setInt(2,Id_pedido);
                stmt.setString(3,nitcli_pedido);
                stmt.setString(4,comentario_pedido);
                stmt.setDouble(5, General.FormatoNumero(total_pedido));
                stmt.executeUpdate();
            } catch (Exception e) {
                error = e.getMessage();
                try {
                    General.connection.rollback();
                    General.cargando(activity,false);
                } catch (SQLException ex) {
                    General.cargando(activity,false);
                    ex.printStackTrace();
                    Toast.makeText(activity,e.getMessage(),Toast.LENGTH_LONG).show();
                }
                return false;
            }
            return true;
        }
        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(Boolean result) {
            try {
                if (result){
                    insertar_detalles_pedido_mysql detallesPedidoMysql = new insertar_detalles_pedido_mysql(activity);
                    detallesPedidoMysql.setReferencias(referencias);
                    detallesPedidoMysql.setId_pedido(Id_pedido);
                    detallesPedidoMysql.setDialog(dialog);
                    detallesPedidoMysql.execute();
                }else {
                    Toast.makeText(activity,error,Toast.LENGTH_LONG).show();
                    General.cargando(activity,false);
                }
            } catch (Exception e) {
                General.cargando(activity,false);
                e.printStackTrace();
            }
        }
    }

    public static class insertar_detalles_pedido_mysql extends AsyncTask<Boolean, Void, Boolean> {

        Context activity;
        AlertDialog dialog;
        String error;
        AdaptadorPedidosReferencias referencias;
        int Id_pedido;
        PreparedStatement stmt = null;

        public insertar_detalles_pedido_mysql(Context context) {
            activity = context;
        }

        public void setReferencias(AdaptadorPedidosReferencias referencias) {
            this.referencias = referencias;
        }

        public void setId_pedido(int id_pedido) {
            Id_pedido = id_pedido;
        }

        public void setDialog(AlertDialog dialog) {
            this.dialog = dialog;
        }

        @Override
        protected Boolean doInBackground(Boolean... parametro) {
            try {
                double valor;
                for (int i=0; i < referencias.getItemCount();i++){
                    String sql = "INSERT INTO tv10 (IDTERMINAL, IDPEDIDO, CODIGOREF, CANTIDAD, PRECIOUND, PRECIOTOT) VALUES (?,?,?,?,?,?)";
                    valor = FormatoNumero(referencias.datosReferenciasPedidos.get(i).getValor());
                    stmt = General.connection.prepareStatement(sql,ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
                    stmt.setInt(1,Integer.parseInt(General.Terminal));
                    stmt.setInt(2,Id_pedido);
                    stmt.setInt(3,referencias.datosReferenciasPedidos.get(i).getCodigoref());
                    stmt.setInt(4, referencias.datosReferenciasPedidos.get(i).getCantidad());
                    stmt.setDouble(5,valor);
                    stmt.setDouble(6,(valor *  referencias.datosReferenciasPedidos.get(i).getCantidad()));
                    stmt.executeUpdate();
                }
                General.connection.commit();
            } catch (Exception e) {
                error = e.getMessage();
                try {
                    General.connection.rollback();
                    General.cargando(activity,false);
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    Toast.makeText(activity,e.getMessage(),Toast.LENGTH_LONG).show();
                    General.cargando(activity,false);
                }
                return false;
            }
            return true;
        }
        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(Boolean result) {
            try {
                if (result){
                    dialog.dismiss();
                    Toast.makeText(activity,"Pedido Enviado con Exito!!!",Toast.LENGTH_LONG).show();
                }else{
                    dialog.dismiss();
                    Toast.makeText(activity,error,Toast.LENGTH_LONG).show();
                }
                General.cargando(activity,false);
            } catch (Exception e) {
                General.cargando(activity,false);
                e.printStackTrace();
            }
        }
    }

    public static class procedimientos_mysql extends AsyncTask<Void, Void, Void> {

        private Activity activity;
        private Statement stmt = null;
        private consultar_terminal_mysql terminalMysql;
        private int terminal;



        public procedimientos_mysql(Activity activity) {
            this.activity = activity;
        }

        public void setTerminal(int terminal) {
            this.terminal = terminal;
        }

        @Override
        protected Void doInBackground(Void... parametro) {
            try {
                stmt = General.connection.createStatement();
            } catch (Exception e) {
                Log.e("ERROR Conexion:",e.getMessage());
            }

            try {
                stmt.executeUpdate("DROP FUNCTION SF_LOGIN;");
            } catch (Exception e) {
                Log.e("ERROR Conexion:",e.getMessage());
            }

            try {
                stmt.executeUpdate("CREATE FUNCTION `SF_LOGIN`(\n" +
                    "\t`USER` VARCHAR(50),\n" +
                    "\t`PASS` VARCHAR(50)\n" +
                    ")\n" +
                    "RETURNS varchar(50) CHARSET utf8\n" +
                    "LANGUAGE SQL\n" +
                    "NOT DETERMINISTIC\n" +
                    "CONTAINS SQL\n" +
                    "SQL SECURITY DEFINER\n" +
                    "COMMENT ''\n" +
                    "BEGIN\n" +
                    "\n" +
                    "SET @ESTADO = (SELECT 1 FROM T80 \n" +
                    "\t\tWHERE PERFIL = USER  AND CLAVE = MD5(CONCAT(MD5('P0S2'),MD5(PASS))));\n" +
                    "\t\t\n" +
                    "IF @ESTADO = 1 THEN\n" +
                    "\treturn 'Conectado';\n" +
                    "ELSE\n" +
                    "\treturn 'Error De Conexion Verifique los Datos';\n" +
                    "END IF;           \n" +
                    "\n" +
                    "END");
            } catch (Exception e) {
                Log.e("ERROR Conexion:",e.getMessage());
            }

            try {
                stmt.executeUpdate("DROP PROCEDURE SP_CUADRE_CAJA_POR_CAJERO;");
            } catch (Exception e) {
                Log.e("ERROR Conexion:",e.getMessage());
            }

            try {
                stmt.executeUpdate("CREATE PROCEDURE `SP_CUADRE_CAJA_POR_CAJERO`(\n" +
                        "\tIN `_FECHA_INICIAL` VARCHAR(8),\n" +
                        "\tIN `_FECHA_FINAL` VARCHAR(8)\n" +
                        ")\n" +
                        "LANGUAGE SQL\n" +
                        "NOT DETERMINISTIC\n" +
                        "CONTAINS SQL\n" +
                        "SQL SECURITY DEFINER\n" +
                        "COMMENT ''\n" +
                        "BEGIN\n" +
                        "SELECT PERFIL,FORPAG ,DESFORPAG, CONCAT('$ ',FORMAT(VALOR,0)) VALOR FROM (SELECT T84.PERFIL,T84.FORPAG ,T08.DESFORPAG, \n" +
                        "SUM(IF(T84.DEBCRE=1 and T84.MARANU=0 ,T84.VALNET,0)) - SUM(IF(T84.DEBCRE=1 and T84.MARANU=0 ,T84.DESADI+T84.VALDES,0)) VALOR,\n" +
                        "SUM(IF(T84.NUMDOC LIKE 'N%' AND T84.DEBCRE=2 AND MARANU =0 ,T84.VALNET,0)) AS DEVOLUCIONES,  \n" +
                        "SUM(IF(T84.NUMDOC LIKE 'N%' AND T84.DEBCRE=2 AND MARANU =0 ,T84.DESADI,0)) AS DESDEV \n" +
                        "FROM T08 LEFT JOIN T84 ON T08.FORPAG = T84.FORPAG \n" +
                        "WHERE T84.FECDOC BETWEEN DATE_FORMAT(_FECHA_INICIAL,'%y%m%d')  AND DATE_FORMAT(_FECHA_FINAL,'%y%m%d')  AND T08.MUECAJA = 'S'\n" +
                        "GROUP BY T84.PERFIL , T84.FORPAG,T08.DESFORPAG\n" +
                        "UNION ALL \n" +
                        "SELECT T52.PERFIL,'RC' FORPAG,'RECIBOS', SUM(IF (T52.MARANU =0 ,IFNULL(T52.VALTRA,0)- IFNULL(T52.RETENCION,0) - IFNULL(T52.VALDES,0) - IFNULL(t52.RETEICA ,0) - IFNULL(t52.RETEIVA ,0) ,0)) AS RECIBOS, \n" +
                        "0 AS DEVOLUCIONES ,0 AS DESDEV \n" +
                        "FROM T52 LEFT JOIN T80 ON T80.PERFIL = T52.PERFIL \n" +
                        "WHERE T52.FECDOC BETWEEN DATE_FORMAT(_FECHA_INICIAL,'%y%m%d')  AND DATE_FORMAT(_FECHA_FINAL,'%y%m%d')   GROUP BY T52.PERFIL) AS TABLA ORDER BY PERFIL ;\n" +
                        "END");
            } catch (Exception e) {
                Log.e("ERROR Conexion:",e.getMessage());
            }

            try {
                stmt.executeUpdate("DROP PROCEDURE `SP_CAJEROS_CUADRE_DE_CAJA_POR_CAJERO`;");
            } catch (Exception e) {
                Log.e("ERROR Conexion:",e.getMessage());
            }

            try {
                stmt.executeUpdate("CREATE PROCEDURE `SP_CAJEROS_CUADRE_DE_CAJA_POR_CAJERO`(\n" +
                        "\tIN `_FECHA_INICIAL` VARCHAR(8),\n" +
                        "\tIN `_FECHA_FINAL` VARCHAR(80)\n" +
                        ")\n" +
                        "LANGUAGE SQL\n" +
                        "NOT DETERMINISTIC\n" +
                        "CONTAINS SQL\n" +
                        "SQL SECURITY DEFINER\n" +
                        "COMMENT ''\n" +
                        "BEGIN\n" +
                        "SELECT PERFIL, FORMAT(SUM(VALOR),0) VALOR FROM (SELECT T84.PERFIL,SUM(IF(T84.DEBCRE=1 and T84.MARANU=0 ,T84.VALNET,0)) - SUM(IF(T84.DEBCRE=1 and T84.MARANU=0 ,T84.DESADI+T84.VALDES,0)) VALOR\n" +
                        "FROM T08 LEFT JOIN T84 ON T08.FORPAG = T84.FORPAG \n" +
                        "WHERE T84.FECDOC BETWEEN DATE_FORMAT(_FECHA_INICIAL,'%y%m%d')  AND DATE_FORMAT(_FECHA_FINAL,'%y%m%d') AND T08.MUECAJA = 'S'\n" +
                        "GROUP BY T84.PERFIL , T84.FORPAG,T08.DESFORPAG\n" +
                        "UNION ALL \n" +
                        "SELECT T52.PERFIL,SUM(IF (T52.MARANU =0 ,IFNULL(T52.VALTRA,0)- IFNULL(T52.RETENCION,0) - IFNULL(T52.VALDES,0) - IFNULL(t52.RETEICA ,0) - IFNULL(t52.RETEIVA ,0) ,0)) AS RECIBOS\n" +
                        "FROM T52 LEFT JOIN T80 ON T80.PERFIL = T52.PERFIL \n" +
                        "WHERE T52.FECDOC BETWEEN DATE_FORMAT(_FECHA_INICIAL,'%y%m%d')  AND DATE_FORMAT(_FECHA_FINAL,'%y%m%d') GROUP BY T52.PERFIL) AS TABLA GROUP BY PERFIL ORDER BY PERFIL ;\n" +
                        "END");
            } catch (Exception e) {
                Log.e("ERROR Conexion:",e.getMessage());
            }

            try {
                stmt.executeUpdate("DROP PROCEDURE `SP_CREAR_TERCERO_ANDROID`;");
            } catch (Exception e) {
                Log.e("ERROR Conexion:",e.getMessage());
            }

            try {
                stmt.executeUpdate("CREATE PROCEDURE `SP_CREAR_TERCERO_ANDROID`(\n" +
                        "\tIN `_NITCLI` BIGINT,\n" +
                        "\tIN `_NOMCLI` VARCHAR(100),\n" +
                        "\tIN `_DIRCLI` VARCHAR(100),\n" +
                        "\tIN `_TELCLI` CHAR(50),\n" +
                        "\tIN `_EMAIL` CHAR(50)\n" +
                        ")\n" +
                        "LANGUAGE SQL\n" +
                        "NOT DETERMINISTIC\n" +
                        "CONTAINS SQL\n" +
                        "SQL SECURITY DEFINER\n" +
                        "COMMENT ''\n" +
                        "BEGIN\n" +
                        "INSERT INTO t64(\n" +
                        "CODCLI,NOMCLI,NITCLI,IDENTI,DIGVER,DIRCLI,CIUCLI,CONRET,\n" +
                        "NATCLI,CLACLI,TELCLI,TIPCLI,CODDEPMUN,LISPRE,TIPREG,CUPOCAR,\n" +
                        "ACUPUN,CODVEN,PLAZO,CONGCRE,FILLER64,FECNAC,TIPTAR,BARRIO,TELS,\n" +
                        "EMAIL,INGRESOS,SEXO,REFER1,TELREFER1,REFER2,TELREFER2,HABICREDI,\n" +
                        "FORPAG,NITAFI,CONCRE,PORCRE,FOTOT64,FOTORUT,CHELIS,CODZON,RETFUE,\n" +
                        "RETIVA,ONLINE,RETICA,PORCEN_RETE,PRIMER_NOMBRE,SEGUNDO_NOMBRE,\n" +
                        "PRIMER_APELLIDO,SEGUNDO_APELLIDO,CODIGO_CLASIFICACION, COD_DEPARTAMENTO, \n" +
                        "COD_MUNICIPIO) \n" +
                        "VALUES ('0',_NOMCLI,_NITCLI,'C','',_DIRCLI,'','N','N','N',_TELCLI,'0','0','0','C','0','0','0','0','0','0','20200611','TAREME','0', '0', \n" +
                        "_EMAIL, '0', 'M', '0', '0', '0', '0', 'N', 'EF',0,'0', '0', '', '0', 'N', '1', '', '', 'S', '0', '0', 'sd', 'asd', 'adsas', 'asda', '13', '01', '0001');\n" +
                        "END");

            } catch (Exception e) {
                Log.e("ERROR Conexion:",e.getMessage());
            }



            return null;
        }
        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(Void result) {
            try {
                terminalMysql = new consultar_terminal_mysql(activity);
                terminalMysql.setTerminal(terminal);
                terminalMysql.execute();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static class consultar_terminal_mysql extends AsyncTask<String, Void, ResultSet> {

        private Activity activity;
        private int terminal;
        private crear_terminal_mysql terminalMysql;

        public void setTerminal(int terminal) {
            this.terminal = terminal;
        }

        public consultar_terminal_mysql(Activity activity) {
            this.activity = activity;
        }

        @Override
        protected ResultSet doInBackground(String... parametro) {
            Statement stmt = null;
            ResultSet rs = null;

            try {
                stmt = General.connection.createStatement();
                rs = stmt.executeQuery("SELECT tv01.CODTER FROM tv01 WHERE tv01.CODTER = " + terminal );
            } catch (NoClassDefFoundError e){
                Log.e("Definicion de clase",e.getMessage());
            } catch (Exception e) {
                Log.e("ERROR Conexion:",e.getMessage());
            }
            return rs;
        }
        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(ResultSet result) {
            try {
                if (result.first() == false){
                    terminalMysql = new crear_terminal_mysql(activity);
                    terminalMysql.setTermianl(terminal);
                    terminalMysql.execute();
                }else {
                    General.cargando(activity,false);
                    activity.finish();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static class crear_terminal_mysql extends AsyncTask<Void, Void, Void> {

        Activity activity;
        PreparedStatement stmt = null;
        private int termianl;

        public void setTermianl(int termianl) {
            this.termianl = termianl;
        }

        public crear_terminal_mysql(Activity activity) {
            this.activity = activity;
        }

        @Override
        protected Void doInBackground(Void... parametro) {
            try {
                General.connection.setAutoCommit(false);
                String sql = "INSERT INTO tv01 (CODTER, NOMTER, CODVEN) VALUES(?,'TERMINAL ESYSPOS', 0)";
                stmt = General.connection.prepareStatement(sql,ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
                stmt.setInt(1, termianl);
                stmt.executeUpdate();
                General.connection.commit();
            } catch (Exception e) {
                try {
                    General.connection.rollback();
                    General.cargando(activity,false);
                } catch (SQLException ex) {
                    General.cargando(activity,false);
                    ex.printStackTrace();
                    Toast.makeText(activity,e.getMessage(),Toast.LENGTH_LONG).show();
                }
                return null;
            }
            return null;
        }
        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(Void result) {
            General.cargando(activity,false);
            activity.finish();
        }
    }

    public static class insertar_cliente_mysql extends AsyncTask<String, Void, Boolean> {

        Context activity;
        String error;
        PreparedStatement stmt = null;

        public insertar_cliente_mysql(Context activity) {
            this.activity = activity;
        }

        @Override
        protected void onPreExecute() {
            General.cargando(activity,true);
        }

        @Override
        protected Boolean doInBackground(String... p) {
            try {
                //prueba de sincronisacion

                General.connection.setAutoCommit(false);
                String sql = "CALL SP_CREAR_TERCERO_ANDROID(?, ?, ?, ?, ?)";
                stmt = General.connection.prepareStatement(sql,ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
                stmt.setString(1,p[0]);
                stmt.setString(2,p[1]);
                stmt.setString(3,p[2]);
                stmt.setString(4,p[3]);
                stmt.setString(5,p[4]);
                stmt.executeUpdate();
                General.connection.commit();
            } catch (Exception e) {
                error = e.getMessage();
                try {
                    General.connection.rollback();
                    General.cargando(activity,false);
                } catch (SQLException ex) {
                    General.cargando(activity,false);
                    ex.printStackTrace();
                    Toast.makeText(activity,e.getMessage(),Toast.LENGTH_LONG).show();
                }
                return false;
            }
            return true;
        }
        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(Boolean result) {
            try {
                if (result){
                    Toast.makeText(activity,"Tercero Guardado",Toast.LENGTH_LONG).show();
                    General.cargando(activity,false);
                }else {
                    Toast.makeText(activity,error,Toast.LENGTH_LONG).show();
                    General.cargando(activity,false);
                }
            } catch (Exception e) {
                General.cargando(activity,false);
                e.printStackTrace();
            }
        }
    }

}
